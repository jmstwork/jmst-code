package com.founder.web.audit;

import java.util.ArrayList;
import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.AudEvent;
import com.founder.fmdm.service.audit.AuditManageService;
import com.founder.fmdm.service.audit.dto.AuditManage;
import com.founder.fmdm.service.audit.dto.AuditManageDto;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping(value = "/auditManage")
public class AuditManageController extends AbstractController {

	@Autowired
	AuditManageService auditManageService;
	
	
	@Autowired
	LogUtils logUtils;

	/***
	 * 查询审计事件请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectAudit")
	public ModelAndView selectAudit(AuditManageDto auditManageDto, AuditSearchCondition cond) throws Exception {

		ModelAndView mav = new ModelAndView();
		ModelMap model = mav.getModelMap();
		model.addAttribute("page_title", "审计事件管理");

		List<String> messages = new ArrayList<String>();
		// mav.addObject("messages", messages);
		model.addAttribute("messages", messages);
		
		
		model.addAttribute("auditManageDto", auditManageDto);

		SelectOptions options = getSelectOptions(cond);
		List<AuditManage> auditMd = auditManageService.selectAuditManage(auditManageDto, options);
		model.addAttribute("resultSet", auditMd);
		pageSetting(cond, options);

		// mav.setViewName("/audit/auditManage");
		return includeTemplate(model, "audit/auditManageList");
	}

	/***
	 * 查询审计事件信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/audit/ajaxSearch")
	public ModelAndView searchSysReg(AuditManageDto auditManageDto, AuditSearchCondition cond) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			SelectOptions options = getSelectOptions(cond);
			// 查询audEvent表中的数据
			List<AuditManage> auditMd = auditManageService.selectAuditManage(auditManageDto, options);
			mav.addObject("resultSet", auditMd);
			mav.setViewName("springJsonView");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return mav;
	}

	/***
	 * 新增审计初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addAuditEvent")
	public ModelAndView addAuditEvent(AuditManageDto auditManageDto, AuditSearchCondition cond) throws Exception {
		ModelAndView mav = new ModelAndView("audit/addAuditEvent");
		ModelMap model = mav.getModelMap();
		model.addAttribute("page_title", "审计事件管理");
		SelectOptions options = getSelectOptions(cond);
		pageSetting(cond, options);

		List<String> messages = new ArrayList<String>();
		this.commonSearch(mav, auditManageDto, messages);
		mav.addObject("messages", messages);
		//return includeTemplate(model, "audit/addAuditEvent");
		return mav;
	}

	/***
	 * 保存审计
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveAuditEvent")
	public @ResponseBody Object saveAuditEvent(AudEvent audEvent, AuditManageDto auditManageDto, final ModelMap model)
			throws Exception {
		model.clear();
		List<String> messages = new ArrayList<String>();
		int auditEvent = auditManageService.saveAuditEvent(audEvent);
		if(0 == auditEvent){
			logUtils.insertLog("00501", "00501001", "新增审计事件[{}]", audEvent.getEventName());
		}
		model.addAttribute("status", 0 == auditEvent ? 1 : 0);
		model.addAttribute("messages", messages);
		return model;
	}

	/***
	 * 编辑审计事件——初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editAuditInit")
	public ModelAndView editAuditEvent(AuditManageDto auditManageDto, WebRequest request, AuditSearchCondition cond)
			throws Exception {
		// ModelAndView mav = new ModelAndView();
		ModelAndView mav = new ModelAndView("audit/editAuditEvent");
		ModelMap model = mav.getModelMap();
		model.addAttribute("page_title", "编辑审计事件");
		SelectOptions options = getSelectOptions(cond);

		List<String> messages = new ArrayList<String>();
		this.commonSearch(mav, auditManageDto, messages);
		auditManageDto.setEventCode(request.getParameter("evenCode"));
		AudEvent event = auditManageService.selectAuditMess(auditManageDto.getEventCode());
		mav.addObject("messages", messages);
		model.addAttribute("auditDto", event);
		pageSetting(cond, options);

		//return includeTemplate(model, "audit/editAuditEvent");

		return mav;

	}

	/***
	 * 编辑审计事件——保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editAuditSave")
	public @ResponseBody Object editSave(AuditManageDto auditManageDto, AuditSearchCondition cond, final ModelMap model)
			throws Exception {
		model.clear();
		List<String> messages = new ArrayList<String>();
		AudEvent audEvent = (AudEvent) auditManageService.selectAuditMess(auditManageDto.getEventCode());
		String editDescribe = "";
		if(null != auditManageDto.getEventName() && !audEvent.getEventName().equals(auditManageDto.getEventName())){
			editDescribe = editDescribe + "原审计事件名称为[" + audEvent.getEventName() + "],新审计事件名称为[" + auditManageDto.getEventName() + "]";
		}
		if(!"".equals(editDescribe)){
			editDescribe = "(" +  editDescribe + ")";
		}
		audEvent.setEventCode(auditManageDto.getEventCode());
		audEvent.setEventName(auditManageDto.getEventName());
		
		int auditEvent = auditManageService.updateAduit(audEvent);
		if(1 == auditEvent){
			logUtils.insertLog("00501", "00501002", "修改审计事件[{}]" + editDescribe, auditManageDto.getEventName());
		}
		model.addAttribute("status", 1 == auditEvent ? 1 : 0);
		model.addAttribute("messages", messages);

		return model;

	}
	
	
	/***
	 * 编辑审计事件——保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAuditInit")
	public @ResponseBody Object deleteAuditInit(AuditManageDto auditManageDto, AuditSearchCondition cond, final ModelMap model)
			throws Exception {
		model.clear();
		List<String> messages = new ArrayList<String>();
		int eventCount = auditManageService.selectAduitEventCount(auditManageDto.getEventCode());
		if(eventCount > 0){
			model.addAttribute("status", 2);
			//model.addAttribute("messages", "该审计事件已被应用，无法删除！");
			return model;
		}
		AudEvent audEvent = (AudEvent) auditManageService.selectAuditMess(auditManageDto.getEventCode());
		
		int auditEvent = auditManageService.deleteEvent(audEvent);
		if(1 == auditEvent){
			logUtils.insertLog("00501", "00501003", "删除审计事件[{}]", audEvent.getEventName());
		}
		model.addAttribute("status", 1 == auditEvent ? 1 : 0);
		model.addAttribute("messages", messages);

		return model;

	}

	private void commonSearch(ModelAndView mav, AuditManageDto auditManageDto, List<String> messages) throws Exception {
		// 查询全部审计系统信息
		List<AuditManageDto> auditEvent = auditManageService.selectAuditEventName();
		mav.addObject("auditEvent", auditEvent);
	}

}
