package com.founder.web.rule;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.founder.fmdm.dao.rule.RuleManagerDao;
import com.founder.fmdm.entity.RlmgRuleVersion;
import com.founder.web.webservice.utils.RuleEngineFacade;

import net.sf.ehcache.CacheManager;

//将最新版本的drl加载到规则引擎
public class RuleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired(required = true)
	RuleManagerDao ruleManagerDao;

	public void init(ServletConfig servletConfig) throws ServletException {
	    super.init(servletConfig);  
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletConfig.getServletContext());          
	    String drl = "";
	    List<RlmgRuleVersion> list = ruleManagerDao.selectNewestDrl();
	    for(RlmgRuleVersion rlmgRuleVersion :list){
	        drl = rlmgRuleVersion.getVersionDrl();
	    }
	    RuleEngineFacade.startUp(drl);
	}

	@Override
	public void destroy() {
		super.destroy();
		 CacheManager.getInstance().shutdown();
	}
	
}
