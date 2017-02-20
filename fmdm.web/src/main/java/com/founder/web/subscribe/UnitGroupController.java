package com.founder.web.subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.Constants;
import com.founder.fmdm.entity.SubsUnitGroup;
import com.founder.fmdm.entity.SubsUnitGroupInfo;
import com.founder.fmdm.service.subscribe.UnitGroupService;
import com.founder.fmdm.service.term.dto.SubsUnitGroupDto;
import com.founder.fmdm.service.term.dto.UnitGroupDetailDto;
import com.founder.util.StringUtil;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/subscribe")
public class UnitGroupController extends AbstractController {

	@Autowired
	UnitGroupService unitGroupService;
	private final int EDIT_FLAG=1;
	private final int ADD_FLAG=0;
	
	@Autowired
	LogUtils logUtils;
	/**
	 * 科室组列表查询
	 * 
	 * @param 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/unitGroupList.html")
	public ModelAndView unitGroupList(final SubscribeSearchCondition cond,
			final ModelMap model) throws Exception {

		model.addAttribute("page_title", "科室组列表");
		SelectOptions options = getSelectOptions(cond);
		//医疗机构信息
		List<Map<String, Object>> hospitalList= unitGroupService.getHospitalList(null);
		// 获取科室组列表信息
		List<SubsUnitGroupDto> unitGroupList = unitGroupService
				.selectunitGroupTable(cond.getUnitGroupId(),cond.getUnitGroupName(),cond.getHospitalCode(), options);
		
		model.addAttribute("hospitalList", hospitalList);
		model.addAttribute("unitGroupList", unitGroupList);
		pageSetting(cond, options);
		return includeTemplate(model, "subscribe/unitGrouplist");
	}
	/**
	 * 科室组详细列表
	 * @param cond
	 * @param unitGroupId
	 * @return
	 */
	@RequestMapping(value = "/unitGroupDetail")
	public ModelAndView unitGroupDetail(SubscribeSearchCondition cond) {
		ModelAndView mav = new ModelAndView("subscribe/unitGroupDetail");
		SelectOptions options = getSelectOptions(cond);
		//根据医疗机构选择相应院区科室字典表名
		String tableName = unitGroupService.selectTableName(cond.getHospitalCode(),Constants.DEPARTMENT_CODE);
		List<UnitGroupDetailDto> unitGroupDetailList =  unitGroupService.selectUnitByGroupTable(cond.getUnitGroupId(),
					cond.getUnitId(),cond.getUnitName(),tableName,options);
		SubsUnitGroup unitGroup = unitGroupService.selectUnitGroupById(cond.getUnitGroupId());
		mav.addObject("unitGroupDetailList",unitGroupDetailList);
		mav.addObject("unitGroupName",unitGroup.getUnitGroupName());
		pageSetting(cond, options);
		return mav;
	}
	/**
	 * 科室组新增和修改画面
	 * @param cond
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/unitGroupEdit")
	public ModelAndView unitGroupEdit(final String editUnitGroupId,
			final ModelMap model) throws Exception {
		model.clear();
		model.put("unitGroupId", editUnitGroupId);
//		model.addAttribute("page_title", "用户管理");
		ModelAndView mav = new ModelAndView("subscribe/unitGroupEdit");
		//医疗机构信息
		List<Map<String, Object>> hospitalList= unitGroupService.getHospitalList(null);
		//科室组类别
		List<Map<String, Object>> unitGroupTypeList= unitGroupService.getUnitGroupTypeList();
		SubsUnitGroup unitGroup=new SubsUnitGroup();
		//已选择科室详细信息
		if(editUnitGroupId!=null && !"".equals(editUnitGroupId)){
			//科室组信息
			unitGroup = unitGroupService.selectUnitGroupById(editUnitGroupId);
			//科室组Id在订阅关系中引用,科室组类别不允许修改
			int groupTypeFlg = unitGroupService.selectSubscribesByGroupId(editUnitGroupId);
			mav.addObject("flag", EDIT_FLAG);
			mav.addObject("groupTypeFlg", groupTypeFlg);
			mav.addObject("title", " 编辑科室组");
		}else{
			mav.addObject("flag", ADD_FLAG);
			mav.addObject("title", " 新增科室组");
		}
		mav.addObject("unitGroup", unitGroup);
		mav.addObject("hospitalList", hospitalList);
		mav.addObject("unitGroupTypeList", unitGroupTypeList);
		return mav;
	}
	
	/**
	 * 科室设定
	 * @param editUnitGroupId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unitSetting")
	public ModelAndView unitSetting(final String editUnitGroupId,
									SubscribeSearchCondition cond,
									final ModelMap model) throws Exception {
		model.clear();
		model.put("unitGroupId", editUnitGroupId);
		model.put("editUnitGroupId", editUnitGroupId);
		model.addAttribute("page_title", "科室设定");
		//科室组类别
		SubsUnitGroup unitGroup=new SubsUnitGroup();
		//已选择科室详细信息
		List<UnitGroupDetailDto> unitGroupDetailList = new ArrayList<UnitGroupDetailDto>();
		SelectOptions options = getSelectOptions(cond);
		if(editUnitGroupId!=null && !"".equals(editUnitGroupId)){
			//科室组信息
			unitGroup = unitGroupService.selectUnitGroupById(editUnitGroupId);
			//根据医疗机构选择相应院区科室字典表名
			model.put("hospitalCode", unitGroup.getHospitalCode());
			List<Map<String, Object>> hospitalList= unitGroupService.getHospitalList(unitGroup.getHospitalCode());
			model.put("unitGroupInfo", " ["+hospitalList.get(0).get("cd_value")+"_"+ unitGroup.getUnitGroupName()+"]");
			
			//根据医疗机构选择相应院区科室字典表名
			String tableName = unitGroupService.selectTableName(unitGroup.getHospitalCode(),Constants.DEPARTMENT_CODE);
			//已选择科室详细信息
			unitGroupDetailList =  unitGroupService.selectUnitByGroup(editUnitGroupId,tableName,options);
			
			/*String tableName = unitGroupService.selectTableName(unitGroup.getHospitalCode(),Constants.DEPARTMENT_CODE);
			unitGroupDetailList =  unitGroupService.selectUnit(tableName,cond.getUnitId(),cond.getUnitName(),options);*/
		}
		pageSetting(cond, options);
		model.addAttribute("unitGroupDetailList", unitGroupDetailList);
		model.addAttribute("subscribeSearchCondition", cond);
		
		return includeTemplate(model, "subscribe/unitSetting");
	}
	
	/**
	 * 科室删除
	 * @param unitGroupIds
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteUnit")
	public @ResponseBody Object deleteUnit(String unitIds,String unitGroupId,
			final ModelMap model) {
		model.clear();
		int result = -1;
		try {
			String[] idArr = unitIds.split(",");
			for(String id : idArr){
				//根据科室组Id检索科室组信息
				SubsUnitGroupInfo ugi = unitGroupService.selectUnitGroupInfoByCondition(id,unitGroupId);
				ugi.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
				ugi.setDeleteTime(new Date());
				ugi.setDeleteFlg(1);
				result = unitGroupService.updateUnitGroupInfo(ugi);
			}
			logUtils.insertLog("00501", "00501005", "为科室组["+ unitGroupId +"]删除科室[{}]", unitIds);
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	
	/**
	 * 校验科室组Id是否在订阅中订阅
	 * @param unitGroupIds
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkRepeatUnitGroup")
	public @ResponseBody Object checkRepeatUnitGroup(String unitGroupIds,
			final ModelMap model) {
		model.clear();
		String  repeatIds="";
		try {
			String[] idArr = unitGroupIds.split(",");
			for(String id : idArr){
				//根据科室组Id检索订阅关系中引用的科室组
				int count = unitGroupService.selectSubscribesByGroupId(id);
				if(count>0){
					if(repeatIds==""){
						repeatIds = id;
					}else{
						repeatIds = repeatIds+","+id;
					}
				}
			}
		} catch (Exception e) {
			repeatIds ="exception";
		}
		model.addAttribute("repeatId", repeatIds);
		return model;
	}
	
	/**
	 * 科室组删除
	 * @param unitGroupIds
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteUnitGroup")
	public @ResponseBody Object deleteUnitGroup(String unitGroupIds,
			final ModelMap model) {
		model.clear();
		int result;
		try {
			String[] idArr = unitGroupIds.split(",");
			for(String id : idArr){
				//根据科室组Id检索科室组信息
				SubsUnitGroup unitGroup = unitGroupService.selectUnitGroupById(id);
				unitGroup.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
				unitGroup.setDeleteTime(new Date());
				unitGroup.setDeleteFlg(1);
				unitGroupService.updateUnitGroup(unitGroup);
				logUtils.insertLog("00501", "00501003", "删除科室组[{}]", unitGroup.getUnitGroupName());
				//根据科室组Id检索科室分组关联表信息
				List<SubsUnitGroupInfo> unitGroupInfoList = unitGroupService.selectUnitGroupInfoById(id);
				for (SubsUnitGroupInfo unitGroupInfo:unitGroupInfoList){
					unitGroupService.deleteUnitGroupInfo(unitGroupInfo);
				}
				
			}
			result = 1;
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	//科室选择
	@RequestMapping(value = "/selectUnit")
	public ModelAndView selectUnit(final SubscribeSearchCondition cond) {
		ModelAndView mav = new ModelAndView("subscribe/selectUnit");
		cond.setPageCount(8);
		SelectOptions options = getSelectOptions(cond);
		//根据医疗机构选择相应院区科室字典表名
		String tableName = unitGroupService.selectTableName(cond.getHospitalCode(),Constants.DEPARTMENT_CODE);
		List<UnitGroupDetailDto> unitGroupDetailList =  unitGroupService.selectUnit(tableName,cond.getUnitId(),
				cond.getUnitName(),options);
		mav.addObject("unitGroupDetailList",unitGroupDetailList);
		pageSetting(cond, options);
		return mav;
	}
	//已选择科室展示列表
	@RequestMapping(value = "/selectUnits")
	public @ResponseBody Object selectUnits(String unitIds,String hospitalCode,final ModelMap model) {
		String[] unitIdArry=unitIds.split(",");
		List unitIdList =Arrays.asList(unitIdArry);
		//根据医疗机构选择相应院区科室字典表名
		String tableName = unitGroupService.selectTableName(hospitalCode,Constants.DEPARTMENT_CODE);
		List<UnitGroupDetailDto> unitGroupDetailList =  unitGroupService.selectUnits(tableName,unitIdList);
		model.addAttribute("unitGroupDetailList",unitGroupDetailList);
		return model;
	}
	/**
	 * 检验科室组是否重复
	 * @param cond
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkUnitGroupExist")
	public @ResponseBody Object checkUnitGroupExist(final SubscribeSearchCondition cond,
			final ModelMap model) {
		model.clear();
		int result;
		SubsUnitGroup unitGroup = unitGroupService.selectUnitGroupById(cond.getUnitGroupId());
		if(unitGroup!=null)
			result = 1;
		else
			result = 0;
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	@RequestMapping(value = "/saveUnitGroup")
	public @ResponseBody Object saveUnitGroup(final SubscribeSearchCondition cond,String unitIds,
			final ModelMap model) {
		model.clear();
		int result;
		try {
			
			SubsUnitGroup unitGroup = new SubsUnitGroup();
			if(cond.getFlag() == EDIT_FLAG){
				//记录日志
				unitGroup = unitGroupService.selectUnitGroupById(cond.getUnitGroupId());
				String editDescribe = "";
	    		if(StringUtils.isNotEmpty(cond.getUnitGroupId()) && !StringUtils.equals(unitGroup.getUnitGroupId(), cond.getUnitGroupId())){
	    			editDescribe = editDescribe + "原科室组ID为["+unitGroup.getUnitGroupId()+"],现科室组ID为["+cond.getUnitGroupId()+"]";
	    		}
	    		if(StringUtils.isNotEmpty(cond.getUnitGroupName()) && !StringUtils.equals(unitGroup.getUnitGroupName(), cond.getUnitGroupName())){
	    			editDescribe = editDescribe + "原科室组名称为["+unitGroup.getUnitGroupName()+"],现科室组名称为["+cond.getUnitGroupName()+"]";
	    		}
	    		if(StringUtils.isNotEmpty(cond.getHospitalCode()) && !StringUtils.equals(unitGroup.getHospitalCode(), cond.getHospitalCode())){
	    			editDescribe = editDescribe + "原医院编码为["+unitGroup.getHospitalCode()+"],现医院编码为["+cond.getHospitalCode()+"]";
	    		}
	    		if(StringUtils.isNotEmpty(cond.getUnitGroupType()) && !StringUtils.equals(unitGroup.getUnitGroupType(), cond.getUnitGroupType())){
	    			editDescribe = editDescribe + "原科室组类别为["+unitGroup.getUnitGroupType()+"],现科室组类别为["+cond.getUnitGroupType()+"]";
	    		}
	    		if(StringUtils.isNotEmpty(cond.getUnitGroupDesc()) && !StringUtils.equals(unitGroup.getUnitGroupDesc(), cond.getUnitGroupDesc())){
	    			editDescribe = editDescribe + "原科室组描述为["+unitGroup.getUnitGroupDesc()+"],现科室组描述为["+cond.getUnitGroupDesc()+"]";
	    		}
	    		if(!"".equals(editDescribe)){
	    			editDescribe = "(" + editDescribe + ")";
	    		}
				unitGroup.setUnitGroupName(cond.getUnitGroupName());
				unitGroup.setUnitGroupType(cond.getUnitGroupType());
				unitGroup.setUnitGroupDesc(cond.getUnitGroupDesc());
				unitGroup.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				unitGroup.setLastUpdateTime(new Date());
				result = unitGroupService.updateUnitGroup(unitGroup);
				if(0 < result){
					logUtils.insertLog("00501", "00501002", "编辑科室组[{}]" + editDescribe, cond.getUnitGroupName());
				}
			}else{
				unitGroup.setId(StringUtil.getUUID());
				unitGroup.setUnitGroupId(cond.getUnitGroupId());
				unitGroup.setUnitGroupName(cond.getUnitGroupName());
				unitGroup.setHospitalCode(cond.getHospitalCode());
				unitGroup.setUnitGroupType(cond.getUnitGroupType());
				unitGroup.setUnitGroupDesc(cond.getUnitGroupDesc());
				unitGroup.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				unitGroup.setCreateTime(new Date());
				unitGroup.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				unitGroup.setLastUpdateTime(new Date());
				unitGroup.setDeleteFlg(0);
				result = unitGroupService.addUnitGroup(unitGroup);
				if(0 < result){
					logUtils.insertLog("00501", "00501001", "新建科室组[{}]", unitGroup.getUnitGroupName());
				}
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	@RequestMapping(value = "/saveUnit")
	public @ResponseBody Object saveUnit(final String unitGroupId,String unitIds, 
			final ModelMap model) {
		model.clear();
		int result=-1;
		try {
			result=	unitGroupService.addUnitGroupInfo(unitIds,unitGroupId);
			logUtils.insertLog("00501", "00501004", "为科室组["+ unitGroupId +"]新增科室[{}]", unitIds);
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? (result == 2?2:1) : 0);
		return model;
	}
}
