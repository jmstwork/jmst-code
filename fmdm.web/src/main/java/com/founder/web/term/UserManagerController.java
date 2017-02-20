package com.founder.web.term;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fmdm.service.sysmgt.UserManagerService;
import com.founder.fmdm.service.term.dto.UserInfoDto;

@Controller
@RequestMapping("/term")
public class UserManagerController extends AbstractController{
	
	@Autowired
	UserManagerService userManagerService;
	/**
	 * 账户列表
	 * @param cond
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/userlist11")
	public ModelAndView dataSourceList(final TermSearchCondition cond,
			final ModelMap model) throws Exception {
		model.addAttribute("page_title", "账户管理");
		SelectOptions options = getSelectOptions(cond);
		if(cond.getStatusName() ==null){
			cond.setStatus(-1);
		}
		List<UserInfoDto> userInfoList = userManagerService.selectUserInfoTable(cond.getUserNo(),
				cond.getUserName(),cond.getStatus(),options);
		model.addAttribute("userInfoList", userInfoList);
		pageSetting(cond, options);
		return includeTemplate(model, "term/userlist");
	}

}
