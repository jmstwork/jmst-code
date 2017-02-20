package com.founder.web.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface RuleCheckWebservice {

	public String ruleCheck(@WebParam(name="msg") String msg);
}
