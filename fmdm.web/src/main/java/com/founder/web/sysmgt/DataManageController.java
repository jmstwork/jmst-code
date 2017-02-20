package com.founder.web.sysmgt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.founder.core.log.LogUtils;
import com.founder.fmdm.service.sysmgt.DataManageService;
import com.founder.fmdm.service.sysmgt.dto.DataManageDto;
import com.founder.fmdm.service.sysmgt.dto.DictMultihospitalInfoDto;
import com.founder.fmdm.service.sysmgt.dto.SubsSysDto;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/dataManage")
public class DataManageController extends AbstractController {
	@Autowired
	DataManageService dataManageService;

	@Autowired
	LogUtils logUtils;
	
	@RequestMapping("/dataManageList")
	public ModelAndView showDatas(final DataManageCondition cond, final ModelMap model) throws Exception {
		model.clear();
		model.put("dataManageCondition", cond);
		model.addAttribute("page_title", "基础数据管理");
		SelectOptions options = getSelectOptions(cond);
		List<DataManageDto> dataManageList = dataManageService.selecManageDatas(cond.getDataName(),cond.getTableName(),options);
		model.put("dataManageList", dataManageList);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/dataManageList");
	}
	
	@RequestMapping("/dataInfo")
	public ModelAndView showDataInfo(final DataManageCondition cond, final ModelMap model, HttpServletRequest request) throws Exception {
		String dataId = request.getParameter("dataId");
		ModelAndView mav = new ModelAndView();
		if ("001".equals(dataId)) {
			mav = selectFromSubsSys(dataId,new SubsSysCondition(),model);
		}else if("002".equals(dataId)){
			mav = selectFromDictMultihospitalInfo(dataId,new DictMultihospitalInfoCondition(),model);
		}
		return mav;
	}
	
	@RequestMapping("/subsSysDatas")
	private ModelAndView selectFromSubsSys(String dataId,final SubsSysCondition cond, final ModelMap model) throws Exception {
		model.clear();
		model.put("subsSysCondition", cond);
		model.addAttribute("page_title", "平台系统");
		SelectOptions options = getSelectOptions(cond);
		List<SubsSysDto> subsSysList = dataManageService.selectSubsSysDatas(cond.getSysId(),cond.getSysName(),cond.getSysApply(),cond.getHospitalId(),options);
		pageSetting(cond, options);
		model.put("subsSysList", subsSysList);
		List<DictMultihospitalInfoDto> hospitalList = dataManageService.selectHospitalInfoDatasByCode(null);
		Map<String,String> hospiCodeName = new HashMap<String,String>();
		for(int i=0;i<hospitalList.size();i++){
			String hospiCode = hospitalList.get(i).getHospitalCode();
			String hospiName = hospitalList.get(i).getHospitalName();
			hospiCodeName.put(hospiCode, hospiName);
		}
		model.addAttribute("hospiCodeName", hospiCodeName);
		model.addAttribute("hospitalList", hospitalList);
		return includeTemplate(model, "sysmgt/subsSysList");
	}
	
	@RequestMapping("/dictMultihospitalInfoDatas")
	private ModelAndView selectFromDictMultihospitalInfo(String dataId,final DictMultihospitalInfoCondition cond, final ModelMap model) throws Exception {
		model.clear();
		model.put("dictMultihospitalInfoCondition", cond);
		model.addAttribute("page_title", "平台医嘱执行分类编码");
		SelectOptions options = getSelectOptions(cond);
		List<DictMultihospitalInfoDto> dictMultihospitalInfoList = dataManageService.selectHospitalInfoDatas(cond.getHospitalCode(),cond.getHospitalName(),options);
		pageSetting(cond, options);
		model.put("dictMultihospitalInfoList", dictMultihospitalInfoList);
		return includeTemplate(model, "sysmgt/dictMultihospitalInfoList");
	}
	
	@RequestMapping("/dataEditShow")
	public ModelAndView dataEditShow(final ModelMap model,String uniKey,String tableName){
		List<String> fieldList = null;
		if("SUBS_SYS".equals(tableName)){
			fieldList = dataManageService.getSysfieldList();
			List<DictMultihospitalInfoDto> hospitalList = dataManageService.selectHospitalInfoDatasByCode(null);
			model.addAttribute("hospitalList", hospitalList);
		}else if("DICT_HOSPITAL".equals(tableName)){
			fieldList = dataManageService.getHospitalfieldList();
		}
		model.addAttribute("termItemDtoList", fieldList);
		Map<String,String> dataMap = dataManageService.getDataListMapByUniKey(fieldList,uniKey,tableName);
		model.addAttribute("termDataMap",dataMap);
		model.addAttribute("uniKey",uniKey);
		model.addAttribute("tableName",tableName);
			if(uniKey != null && !"".equals(uniKey)){
				model.addAttribute("editStatus","u");
				model.addAttribute("titleDesc","术语项目编辑");
			}else{
				model.addAttribute("editStatus","a");
				model.addAttribute("titleDesc","术语项目新增");
			}
		ModelAndView mav = new ModelAndView("sysmgt/dataEditPage", model);
		return mav;
	}
	
	@RequestMapping("/saveSysData")
	public @ResponseBody Object saveSysData(final SubsSysCondition cond,
			final ModelMap model,String tableName) {
		model.clear();
		int result = 0;
		try {
			String uniKey = cond.getUniKey();
			if(uniKey == null){
				SubsSysDto data = new SubsSysDto();
				data.setUniKey(getUUID());
				data.setSysName(cond.getSysName());
				data.setHospitalId(cond.getHospitalId());
				data.setSysId(cond.getSysId());
				data.setSysApply(cond.getSysApply());
				data.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				data.setCreateTime(new Date());
				data.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				data.setLastUpdateTime(new Date());
				data.setUpdateCount(0);
				data.setDeleteFlg("0");
				data.setItemVersion(1);
				data.setOptStatus("a");//新增
				data.setReleaseStatus("c");//已发布
				result = dataManageService.addSys(data);
				logUtils.insertLog("00106", "00106000", "给表[{}]新增系统"+data.getSysId(),tableName);
			}else{
				SubsSysDto data = dataManageService.selectSysDatasById(uniKey);
				data.setSysName(cond.getSysName());
				data.setHospitalId(cond.getHospitalId());
				data.setSysId(cond.getSysId());
				data.setSysApply(cond.getSysApply());
				data.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				data.setLastUpdateTime(new Date());
				data.setOptStatus("u");//修改
				data.setReleaseStatus("c");//已发布
				result=dataManageService.updateSys(data);
				logUtils.insertLog("00106", "00106002", "给表[{}]修改系统"+data.getSysId(),tableName);
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	@RequestMapping("/saveHospitalData")
	public @ResponseBody Object saveHospitalData(final DictMultihospitalInfoDto cond,
			final ModelMap model,String tableName) {
		model.clear();
		int result = 0;
		try {
			String uniKey = cond.getUniKey();
			if(uniKey == null){
				DictMultihospitalInfoDto data = new DictMultihospitalInfoDto();
				data.setUniKey(getUUID());
				data.setHospitalCode(cond.getHospitalCode());
				data.setHospitalName(cond.getHospitalName());
				data.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				data.setCreateTime(new Date());
				data.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				data.setLastUpdateTime(new Date());
				data.setUpdateCount(0);
				data.setDeleteFlg("0");
				result = dataManageService.addHospital(data);
				logUtils.insertLog("00106", "00106000", "给表[{}]新增医疗机构"+data.getHospitalCode(),tableName);
			}else{
				DictMultihospitalInfoDto data = dataManageService.selectHospitalInfoDatasById(uniKey);
				data.setHospitalCode(cond.getHospitalCode());
				data.setHospitalName(cond.getHospitalName());
				data.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				data.setLastUpdateTime(new Date());
				result=dataManageService.updateHospital(data);
				logUtils.insertLog("00106", "00106002", "给表[{}]修改医疗机构"+data.getHospitalCode(),tableName);
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	@RequestMapping("/checkUniOne")
	public @ResponseBody Object checkUniOne(final ModelMap model,String code,String tableName){
		boolean status = dataManageService.checkUniOne(code,tableName);
		model.addAttribute("status",status);
		return model;
	}
	
	@RequestMapping("/deleteDatas")
	public @ResponseBody Object deleteDatas(final SubsSysCondition cond,
			final ModelMap model,String tableName) {
		model.clear();
		int result = 0;
		try {
			String ids = cond.getUniKey();
			String[] idArr = ids.split(",");
			for(String uniKey : idArr){
				if("SUBS_SYS".equals(tableName)){
					SubsSysDto data = dataManageService.selectSysDatasById(uniKey);
					//表SUBS_SERVICE_SUBSCRIBES的SYS_ID字段，表SUBS_SUBSCRIBES的SEND_SYS_ID字段。
					String sysId = data.getSysId();
					data.setDeleteFlg("1");
					data.setDeleteTime(new Date());
					data.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
					result=dataManageService.updateSys(data);
					logUtils.insertLog("00106", "00106001", "从表[{}]中删除系统"+data.getSysId(),tableName);
					model.addAttribute("sysId", sysId);
				}else if("DICT_HOSPITAL".equals(tableName)){
					DictMultihospitalInfoDto data = dataManageService.selectHospitalInfoDatasById(uniKey);
					String hospitalId = data.getHospitalCode();
				   	data.setDeleteFlg("1");
					data.setDeleteTime(new Date());
					data.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
					result=dataManageService.updateHospital(data);
					logUtils.insertLog("00106", "00106001", "从表[{}]中删除医疗机构"+data.getHospitalCode(),tableName);
					model.addAttribute("hospitalId", hospitalId);
				}
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result);
		return model;
	}
	
}
