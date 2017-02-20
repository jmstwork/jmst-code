package com.founder.web.subscribe;

import java.util.Date;
import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.Constants;
import com.founder.fmdm.entity.DictVisitType;
import com.founder.fmdm.entity.SubsExtendSubId;
import com.founder.fmdm.entity.SubsOrderExecId;
import com.founder.fmdm.entity.SubsService;
import com.founder.fmdm.entity.SubsServiceSubscribes;
import com.founder.fmdm.entity.SubsSubscribes;
import com.founder.fmdm.entity.SubsSys;
import com.founder.fmdm.entity.SubsUnitGroup;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.service.subscribe.SubscribeService;
import com.founder.fmdm.service.subscribe.SystemManagerService;
import com.founder.fmdm.service.subscribe.UnitGroupService;
import com.founder.fmdm.service.subscribe.dto.ServiceDto;
import com.founder.util.StringUtil;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController extends AbstractController {
	@Autowired
	LogUtils logUtils;

	@Autowired
	SystemManagerService systemManagerService;
	
	@Autowired
	SubscribeService subscribeService;
	
	@Autowired
	UnitGroupService unitGroupService;
	
	//@Autowired
	//VisitTypeService visitTypeService;
	
	@RequestMapping(value = "/detail",method = RequestMethod.POST)
	public ModelAndView detailPage(final SubscribeSearchCondition cond,
			final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.put("subscribeSearchCondition", cond);
		model.addAttribute("page_title", "订阅详细");
		if(!StringUtils.isEmpty(cond.getMethod())){
			cond.setHospitalCode("");
			cond.setServiceId("");
			cond.setServiceName("");
		}
		
		if("000".equalsIgnoreCase(cond.getHospitalCode())){
			cond.setHospitalCode("");
		}
		List<SystemCode> codeList = subscribeService.selectHospitalList("C011");
		List<ServiceDto> serviceList = subscribeService.selectServiceList("show",StringUtil.trim(StringUtils.isEmpty(cond.getParamSysId())?cond.getSysId():cond.getParamSysId()),StringUtil.trim(cond.getServiceId()), StringUtil.trim(cond.getServiceName()), StringUtil.trim(cond.getHospitalCode()), options);
		
		model.put("codeList",codeList);
		model.put("serviceList",serviceList);
		model.put("msgHeaderType",Constants.MSG_HEADER_TYPE);
		
		pageSetting(cond, options);
		return includeTemplate(model, "subscribe/subscribeDetail");
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView addPage(final SubscribeSearchCondition cond,
			final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		cond.setMsgHeaderType(Constants.MSG_HEADER_TYPE);
		model.put("subscribeSearchCondition", cond);
		model.addAttribute("page_title", "新增订阅");
		List<SystemCode> codeList = subscribeService.selectHospitalList("C011");
		List<SubsUnitGroup> applyList= subscribeService.selectGroupList("0");
		List<SubsUnitGroup> execList= subscribeService.selectGroupList("1");
		//Author:liu_hongjie
        //Date : 2014/9/9 15:58
        //[BUG]0047611 ADD BEGIN
		List<DictVisitType> visitTypeList = subscribeService.selectVisitTypeList();
		List<SubsOrderExecId> subsOrderExecIdList = subscribeService.selectSubsOrderExecIdList();//医嘱执行分类	
		List<SubsExtendSubId> subsExtendSubIdList = subscribeService.selectSubsExtendSubIdList();//扩展码	
		//发送系统id add by wang_po
		List<SubsSys> subsSysList = subscribeService.selectSubsSysList(null);
		model.put("codeList",codeList);
		model.put("applyList",applyList);
		model.put("execList",execList);
		model.put("visitTypeList",visitTypeList);
		model.put("subsOrderExecIdList",subsOrderExecIdList);
		model.put("subsExtendSubIdList",subsExtendSubIdList);
		model.put("subsSysList",subsSysList);
		//[BUG]0047611 ADD END
		model.put("msgHeaderType",Constants.MSG_HEADER_TYPE);
		pageSetting(cond, options);
		return includeTemplate(model, "subscribe/subscribeAdd");
	}
	
	@RequestMapping(value = "/serviceList")
	public ModelAndView serviceListPage(final SubscribeSearchCondition cond,
			final ModelMap model) {
		model.clear();
		ModelAndView mav = new ModelAndView("subscribe/serviceList");
		SelectOptions options = getSelectOptions(cond);
		model.put("subscribeSearchCondition", cond);
		model.addAttribute("page_title", "服务信息一览");
		List<SystemCode> codeList = subscribeService.selectHospitalList("C011");
		if("000".equalsIgnoreCase(cond.getHospitalCode())){
			cond.setHospitalCode("");
		}
		String serviceName = cond.getServiceName()== null?cond.getServiceName():cond.getServiceName().trim();
		List<ServiceDto> serviceList = subscribeService.selectServiceList("choose",cond.getSysId(),cond.getServiceId(), serviceName, cond.getHospitalCode(), options);
		model.put("serviceList",serviceList);
		model.put("codeList",codeList);
		model.put("msgHeaderType",Constants.MSG_HEADER_TYPE);
		pageSetting(cond, options);
		return mav;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object saveSubscribeData(final SubscribeSearchCondition cond,
			final ModelMap model) {
		model.clear();
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		String userNo = auth.getName();
		
		//CDR申请科室组和执行科室组取全部，用"*"标识，默认插入一条，如果存在不再重复插入
		if("*".equalsIgnoreCase(cond.getApplyUnitGroupId())||"*".equalsIgnoreCase(cond.getExecUnitGroupId())){
			SubsUnitGroup u = new SubsUnitGroup();
			u = unitGroupService.selectUnitGroupById("*");
			if(u==null){
				u = new SubsUnitGroup();
				u.setId(StringUtil.getUUID());
				u.setUnitGroupId("*");
				u.setUnitGroupName("不区分");
				u.setHospitalCode("*");
				u.setUnitGroupType("-1");
				u.setUnitGroupDesc("");
				u.setCreateBy(userNo);
				u.setCreateTime(new Date());
				u.setLastUpdateBy(userNo);
				u.setLastUpdateTime(new Date());
				u.setDeleteFlg(0);
				unitGroupService.addUnitGroup(u);
			}
		}
		
		//如果是用户自定义，那么需要更新相应字段信息
		int r1 = 0;
		SubsSubscribes o = new SubsSubscribes();
		String subscribeId = StringUtil.getUUID();
		if(!StringUtils.isEmpty(cond.getSubscribeId())){
			o = subscribeService.selectSubsDataById(cond.getSubscribeId());
			if(o!=null && !cond.getSubsDesc().equalsIgnoreCase(o.getSubsDesc())){
				o.setLastUpdateBy(userNo);
				o.setLastUpdateTime(new Date());
				o.setSubsDesc(cond.getSubsDesc());
				subscribeService.updateSubsData(o);
			}
		}else{
			o.setSubscribeId(subscribeId);
			o.setHospitalId(cond.getHospitalId());
			o.setServiceId(cond.getServiceId());
			o.setDomainId(cond.getVisitTypeId());
			o.setApplyUnitGroupId(cond.getApplyUnitGroupId());
			o.setExecUnitGroupId(cond.getExecUnitGroupId());
			o.setOrderExecId(cond.getOrderExecId());
			o.setExtendSubId(cond.getExtendSubId());
			o.setSubsDesc(cond.getSubsDesc());
			o.setCreateBy(userNo);
			o.setCreateTime(new Date());
			o.setLastUpdateBy(userNo);
			o.setLastUpdateTime(new Date());
			o.setDeleteFlg(0);
			o.setSendSysId(cond.getSendSysId());
			r1 = subscribeService.saveSubsData(o);
		}
		
		
		
		int r2 = 0;
		//将信息插入到系统订阅表中
		SubsServiceSubscribes s = new SubsServiceSubscribes();
		s.setSubsId(StringUtil.getUUID());
		s.setSubscribeId(StringUtils.isEmpty(cond.getSubscribeId())?subscribeId:cond.getSubscribeId());
		s.setSysId(cond.getParamSysId());
		s.setOutputQueueName(cond.getOutputQueueName());
		s.setCreateBy(userNo);
		s.setCreateTime(new Date());
		s.setLastUpdateBy(userNo);
		s.setLastUpdateTime(new Date());
		s.setDeleteFlg(0);
		r2 = subscribeService.saveSubsServiceData(s);
		logUtils.insertLog("00601", "00601000", "给系统["+s.getSysId()+"]新增订阅[{}]" , cond.getServiceName());
		model.addAttribute("result","保存成功!");
		return model;
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Object deleteSubscribeData(final SubscribeSearchCondition cond,
			final ModelMap model) {
		model.clear();
		int result = 0;
		int cnt = 0;
		//根据subsId查询具体的订阅映射记录，然后逐一删除,实际操作时每次复制新增一条具有删除信息的记录
		String subsIds = cond.getSubsIds();
		if(!StringUtils.isEmpty(subsIds)){
			for(int i=0;i<subsIds.split(",").length;i++){
				SubsServiceSubscribes s = new SubsServiceSubscribes();
				s = subscribeService.selectSubsServiceDataById(subsIds.split(",")[i].split("_")[0]);
				if(s!=null){
					result = subscribeService.deleteSubsServiceData(s);
					logUtils.insertLog("00601", "00601001", "从系统["+s.getSysId()+"]删除订阅[{}]" , subsIds.split(",")[i].split("_")[1]);
					//删除映射记录时，还需要判断该服务是否还被人订阅了，如果一个服务没有被任何系统订阅，那么为了数据不冗余，自动清理该服务的信息
					cnt = subscribeService.selectServiceCount(s.getSubscribeId());
					if(cnt==0){
						SubsSubscribes o = new SubsSubscribes();
						o = subscribeService.selectSubsDataById(s.getSubscribeId());
						cnt = subscribeService.deleteSubsData(o);
					}
				}
			}
		}
		if(result==1 && cnt==1){
			model.addAttribute("result","1");
		}
		return model;
	}
	
	/**
	 * 根据serviceId获取对应的serviceName
	 * @param cond
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/selectServiceName", method = RequestMethod.POST)
	public @ResponseBody Object selectServiceName(final SubscribeSearchCondition cond,
			final ModelMap model){
		model.clear();
		List<SubsService>  l = subscribeService.selectServiceName(cond.getServiceId());
		if(l.size()>0){
			model.addAttribute("serviceName",l.get(0).getServiceName());
		}else{
			model.addAttribute("serviceName","");
		}
		return model;
	}
	
	/**
	 * 根据hospitalCode帅选对应的组信息
	 * @param cond
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/selectGroupByHospitalCode", method = RequestMethod.POST)
	public @ResponseBody Object selectGroupByHospitalCode(final SubscribeSearchCondition cond,
			final ModelMap model){
		model.clear();
		//申请科室组
		List<SubsUnitGroup>  s1 = subscribeService.selectGroupByHospitalCode(cond.getHospitalCode(),"0");
		
		
		StringBuffer sb1 = new StringBuffer();
		sb1.append("[{\"id\":\"000\",\"name\":\"请选择...\"},{\"id\":\"*\",\"name\":\"不区分\"}");
		for(SubsUnitGroup entity : s1){
			sb1.append(",{\"id\":\"");
			sb1.append(entity.getUnitGroupId());
			sb1.append("\",\"name\":\"");
			sb1.append(entity.getUnitGroupName());
			sb1.append("\"}");
		}
		sb1.append("]");
		
		//执行科室组
		List<SubsUnitGroup>  s2 = subscribeService.selectGroupByHospitalCode(cond.getHospitalCode(),"1");
		StringBuffer sb2 = new StringBuffer();
		sb2.append("[{\"id\":\"000\",\"name\":\"请选择...\"},{\"id\":\"*\",\"name\":\"不区分\"}");
		for(SubsUnitGroup entity : s2){
			sb2.append(",{\"id\":\"");
			sb2.append(entity.getUnitGroupId());
			sb2.append("\",\"name\":\"");
			sb2.append(entity.getUnitGroupName());
			sb2.append("\"}");
		}
		sb2.append("]");
		
		//执行科室组
		List<SubsSys>  s3 = subscribeService.selectSubsSysList(cond.getHospitalCode());
		StringBuffer sb3 = new StringBuffer();
		sb3.append("[{\"id\":\"000\",\"name\":\"请选择...\"},{\"id\":\"*\",\"name\":\"不区分\"}");
		for(SubsSys entity : s3){
			sb3.append(",{\"id\":\"");
			sb3.append(entity.getSysId());
			sb3.append("\",\"name\":\"");
			sb3.append(entity.getSysName());
			sb3.append("\"}");
		}
		sb3.append("]");
		
		model.addAttribute("applyGroup",sb1.toString());
		model.addAttribute("execGroup",sb2.toString());
		model.addAttribute("sendSys",sb3.toString());
		return model;
	}
	
}
