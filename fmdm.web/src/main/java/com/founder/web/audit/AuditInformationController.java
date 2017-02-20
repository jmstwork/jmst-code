package com.founder.web.audit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.AppSettings;
import com.founder.fmdm.common.CommonUtil;
import com.founder.fmdm.service.audit.AuditInformationService;
import com.founder.fmdm.service.audit.dto.AudContentInfo004Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfo005Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfoDto;
import com.founder.fmdm.service.audit.dto.AuditInformation;
import com.founder.fmdm.service.audit.dto.AuditInformationDto;
import com.founder.web.term.AbstractController;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/auditInfor")
public class AuditInformationController extends AbstractController {

	@Autowired
	private AuditInformationService auditInformationService;

	private final static String HOSPITAL_CODES = "hospital.codes";

	private final static String HOSPITAL_NAMES = "hospital.names";

	/***
	 * 查询审计信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/auditInformation")
	public ModelAndView selectAudit(/*AuditInformationDto auditInformationDto, */AuditSearchCondition cond)
			throws Exception {

		ModelAndView mav = new ModelAndView();
		ModelMap model = mav.getModelMap();
		model.addAttribute("page_title", "查询审计信息");

		List<String> messages = new ArrayList<String>();
		AuditInformationDto auditInformationDto = new AuditInformationDto();
		auditInformationDto.setHospitalCode(cond.getHospitalCode());
		auditInformationDto.setAudId(cond.getAudId());
		auditInformationDto.setSysId(cond.getSysId());
		auditInformationDto.setEventCode(cond.getEventCode());
		auditInformationDto.setOptDt1(cond.getOptDt1());
		auditInformationDto.setOptDt2(cond.getOptDt2());
		auditInformationDto.setUserNo(cond.getUserNo());
		auditInformationDto.setUserName(cond.getUserName());
		auditInformationDto.setDeptName(cond.getDeptName());
		
		if ( null == cond.getCurrentPage()) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1); // 得到前一天
			Date date = calendar.getTime();
			Calendar calendar1 = Calendar.getInstance();
			calendar1.add(Calendar.DATE, 0); // 得到当天
			Date date1 = calendar1.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String operDt1 = df.format(date);
			String operDt2 = df.format(date1);
			auditInformationDto.setOptDt1(operDt1);
			auditInformationDto.setOptDt2(operDt2);
			cond.setOptDt1(operDt1);
			cond.setOptDt2(operDt2);
		}
		
		
		this.commonSearch(mav, auditInformationDto, messages);
	/*	// 获取昨天至今天的时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到前一天
		Date date1 = calendar.getTime();
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.DATE, 0); // 得到当天
		Date date2 = calendar1.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = df.format(date1);
		String endTime = df.format(date2);*/
		// 动态加载医疗机构信息
		String[] codeArr = AppSettings.getConfig(HOSPITAL_CODES).split("@");
		String[] nameArr = AppSettings.getConfig(HOSPITAL_NAMES).split("@");

		Map<String, String> hospitalMap = new HashMap<String, String>();
		if (codeArr.length == nameArr.length) {
			for (int i = 0; i < codeArr.length; i++) {
				hospitalMap.put(codeArr[i], nameArr[i]);
			}
		}

		model.addAttribute("hospitalMap", hospitalMap);
		model.addAttribute("startTime", auditInformationDto.getOptDt1());
		model.addAttribute("endTime", auditInformationDto.getOptDt2());
		model.addAttribute("messages", messages);

		model.addAttribute("nowDate", new Date());

		SelectOptions options = getSelectOptions(cond);

		// 查询审计信息数据
		/*if (null == auditInformationDto.getOptDt1() && null == auditInformationDto.getOptDt2()) {
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String optDt = dateFormater.format(new Date());
			auditInformationDto.setOptDt1(optDt);
			auditInformationDto.setOptDt2(optDt);
		}*/
		List<AuditInformation> auditQy = auditInformationService.selectAuditQuery(auditInformationDto, options);
		pageSetting(cond, options);
		model.addAttribute("resultSet", auditQy);

		return includeTemplate(model, "audit/auditInformation");
	}

	private void commonSearch(ModelAndView mav, AuditInformationDto auditInformationDto, List<String> messages) {
		// 审计系统查询
		List<AuditInformationDto> auditSysName = auditInformationService.selectAuditSysName();
		mav.addObject("auditSysName", auditSysName);
		// 审计事件查询
		List<AuditInformationDto> auditEventName = auditInformationService.selectAuditEventName();
		mav.addObject("auditEventName", auditEventName);
		// 职务查询
		List<AuditInformationDto> auditGroupName = auditInformationService.selectGroupName();
		mav.addObject("auditGroupName", auditGroupName);
		// 科室查询
		List<AuditInformationDto> auditDeptName = auditInformationService.selectDeptName();
		JSONArray departDtoJson = JSONArray.fromObject(auditDeptName);
		String dictDepartmentJson = departDtoJson.toString();
		mav.addObject("dictDepartmentJson", dictDepartmentJson);
	}

	/***
	 * 查询审计信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajaxAuditQuery")
	public ModelAndView searchAuditQuery(AuditInformationDto auditInformationDto, AuditSearchCondition cond)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("springJsonView");
		return mav;
	}

	@RequestMapping("/auditDetailView")
	public ModelAndView searchAuditDetail(WebRequest request,AuditSearchCondition cond) {
		ModelAndView mav = new ModelAndView();
		// 查询审计信息数据
		AuditInformationDto audInfoDto = new AuditInformationDto();
		//String selectItem = request.getParameter("id");
		audInfoDto.setAudId(request.getParameter("id"));
		SelectOptions options = getSelectOptions(cond);
		List<AuditInformation> auditInfo = auditInformationService.selectAuditQuery(audInfoDto, options);
		audInfoDto.setMachineName(
				auditInfo.get(0).getMachineName() == null ? "" : auditInfo.get(0).getMachineName().toString());
		audInfoDto.setIpAddress(
				auditInfo.get(0).getIpAddress() == null ? "" : auditInfo.get(0).getIpAddress().toString());
		audInfoDto.setEventCode(auditInfo.get(0).getEventCode().toString());
		audInfoDto.setEventName(
				auditInfo.get(0).getEventName() == null ? "" : auditInfo.get(0).getEventName().toString());
		audInfoDto.setSysName(auditInfo.get(0).getSysName() == null ? "" : auditInfo.get(0).getSysName().toString());
		audInfoDto.setUserName(auditInfo.get(0).getUserName() == null ? "" : auditInfo.get(0).getUserName().toString());
		audInfoDto.setUserNo(auditInfo.get(0).getUserId() == null ? "" : auditInfo.get(0).getUserId().toString());
		audInfoDto.setDeptName(auditInfo.get(0).getDeptName() == null ? "" : auditInfo.get(0).getDeptName().toString());
		audInfoDto.setOptDt(auditInfo.get(0).getOptDate() == null ? "" : auditInfo.get(0).getOptDate().toString());

		StringBuffer content = new StringBuffer();
		if ("001".equals(audInfoDto.getEventCode())) {
			List<AudContentInfoDto> contentList = auditInformationService
					.selectContentInfoById(auditInfo.get(0).getAudId().toString());
			for (AudContentInfoDto obj : contentList) {
				if ("insert".equals(obj.getRowAction())) {
					if (content.length() > 0)
						content.append("\n");
					content.append("员工编号" + audInfoDto.getUserNo() + "于" + audInfoDto.getOptDt() + "新增药品"
							+ obj.getDrugName() + "。");
				} else if ("update".equals(obj.getRowAction())) {
					if (content.length() > 0)
						content.append("\n");
					content.append("员工编号" + audInfoDto.getUserNo() + "于" + audInfoDto.getOptDt() + "变更药品 "
							+ obj.getDrugName() + "（包装序号" + obj.getPackageNo() + "）的零售价，");
					content.append("变更前价格为");
					content.append(obj.getDrugPriceOld());
					content.append("，变更后价格为");
					content.append(obj.getDrugPriceNew());
					content.append("，生效日期为");
					content.append(CommonUtil.dateFormatToStr(CommonUtil.formatToDate(obj.getEffectDate(), "yyyyMMdd"),
							"yyyy-MM-dd"));
					content.append("。");
				} else {
					if (content.length() > 0)
						content.append("\n");
					content.append("员工编号" + audInfoDto.getUserNo() + "于" + audInfoDto.getOptDt() + "删除药品"
							+ obj.getDrugName() + "。");
				}
			}
		} else if ("004".equals(audInfoDto.getEventCode())) {
			List<AudContentInfo004Dto> contentList = auditInformationService
					.selectContentInfoBy004(auditInfo.get(0).getAudId().toString());
			for (AudContentInfo004Dto obj : contentList) {
				if (content.length() > 0)
					content.append("\n");
				content.append("员工编号" + audInfoDto.getUserNo() + "于" + audInfoDto.getOptDt() + "访问患者["
						+ (obj.getClinicNo() != null ? "门诊号" : "住院号") + "]"
						+ (obj.getClinicNo() != null ? obj.getClinicNo() : obj.getHospitalNo()) + "的临床数据。数据内容："
						+ obj.getClinicDataCategory() + "，报告号：" + obj.getClinicDataId() + "，检验项目为"
						+ obj.getExamineName() + "。");
			}
		} else if ("005".equals(audInfoDto.getEventCode())) {
			List<AudContentInfo005Dto> contentList = auditInformationService
					.selectContentInfoBy005(auditInfo.get(0).getAudId().toString());
			for (AudContentInfo005Dto obj : contentList) {
				if (content.length() > 0)
					content.append("\n");
				content.append(
						"员工编号" + audInfoDto.getUserNo() + "于" + audInfoDto.getOptDt() + "退出患者住院号 " + obj.getHospitalNo()
								+ "的临床路径，临床路径名称为" + obj.getClinicPathName() + "，退出原因为" + obj.getExitTxt() + "。");
			}
		}
		audInfoDto.setContent(content.toString());
		mav.addObject("audInfoDto", audInfoDto);
		mav.setViewName("/audit/auditInfoDetail");

		return mav;
	}
}
