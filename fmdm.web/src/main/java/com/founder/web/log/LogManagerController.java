package com.founder.web.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fmdm.service.iamlog.IamLogService;
import com.founder.fmdm.service.iamlog.dto.LogDetailDto;
import com.founder.fmdm.service.iamlog.dto.LogManagerDetailDto;
import com.founder.web.log.dto.LogManagerCondition;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/logManager")
public class LogManagerController extends AbstractController {

	@Autowired
	IamLogService iamLogService;

	/***
	 * 日志管理——初始化
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(final ModelMap model, LogManagerCondition cond) throws Exception {
		List<String> messages = new ArrayList<String>();
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
			cond.setOperDt1(operDt1);
			cond.setOperDt2(operDt2);
		}

		// 操作模块下拉框显示方法详细
		List<LogManagerDetailDto> listModu = iamLogService.selectModu();
		model.addAttribute("listModu", listModu);
		/*
		 List<LogManagerDetailDto> listObj = iamLogService.selectObj(cond.getResrCode());
		// model.addAttribute("status", 1);
		model.addAttribute("listObj", listObj);
		 * */

		// 操作对象下拉框显示方法详细
		List<LogManagerDetailDto> listObj = iamLogService.selectObj(cond.getOperModu());
		model.addAttribute("listObj", listObj);
		
		
		model.addAttribute("messages", messages);

		model.addAttribute("logManagerDetailDto", cond);

		model.addAttribute("page_title", "日志管理");
		SelectOptions options = getSelectOptions(cond);
		// 操作对象下拉框显示方法详细
		/*if(!StringUtils.isEmpty(cond.getOperModu())){
			List<LogManagerDetailDto> listObj = iamLogService.selectObj(cond.getResrCode());
			model.addAttribute("listObj", listObj);
		}else{
			
		}*/

		// 查询规则组信息
		LogManagerDetailDto dto = new LogManagerDetailDto();
		dto.setOperorId(cond.getOperorId());
		dto.setOperModu(cond.getOperModu());
		dto.setOperObj(cond.getOperObj());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!StringUtils.isEmpty(cond.getOperDt1())) {
			dto.setOperDt1(sdf.parse(cond.getOperDt1()));
		}
		if (!StringUtils.isEmpty(cond.getOperDt2())) {
			dto.setOperDt2(sdf.parse(cond.getOperDt2()));
		}
		List<LogDetailDto> grolist = iamLogService.selectLogManager(dto, options);

		model.addAttribute("grolist", grolist);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/logManagerList");
	}

	/***
	 * 日志管理——下拉框联动
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajaxLinkage")
	public @ResponseBody Object ajaxLinkage(final ModelMap model,
			/* LogManagerDetailDto logManagerDetailDto */ LogManagerCondition cond) throws Exception {
		model.clear();
		// 操作对象下拉框显示方法详细
		List<LogManagerDetailDto> listObj = iamLogService.selectObj(cond.getResrCode());
		// model.addAttribute("status", 1);
		model.addAttribute("listObj", listObj);
		return model;
	}
}
