package com.founder.web.subscribe;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fmdm.entity.SubsSys;
import com.founder.fmdm.service.subscribe.SystemManagerService;
import com.founder.util.StringUtil;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/subscribe")
public class SystemListController extends AbstractController {

	@Autowired
	SystemManagerService systemManagerService;
	
	
	@RequestMapping(value = "/sysList")
	public ModelAndView initSysListPage(final SubscribeSearchCondition cond,
			final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.put("subscribeSearchCondition", cond);
		model.addAttribute("page_title", "应用系统一览表");
		
		List<SubsSys> sysList = systemManagerService.selecSystemData(StringUtil.trim(cond.getSysId()), StringUtil.trim(cond.getSysName()), options);
		model.put("sysList",sysList);
		
		pageSetting(cond, options);
		return includeTemplate(model, "subscribe/subscribeSysList");
	}
}
