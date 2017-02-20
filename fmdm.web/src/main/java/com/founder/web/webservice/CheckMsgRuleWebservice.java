package com.founder.web.webservice;

import javax.jws.WebService;


@WebService
public interface CheckMsgRuleWebservice {
	 /**
     * 规则校验
	 * @throws Exception 
     */

	public String checkMsg(String ruleType,String msg) ;
}
