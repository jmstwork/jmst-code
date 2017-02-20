package com.founder.web.webservice.impl;

import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.drools.definition.type.FactType;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;

import com.founder.fmdm.common.ReadResource;
import com.founder.fmdm.dao.rule.RuleManagerDao;
import com.founder.web.webservice.CheckMsgRuleWebservice;
import com.founder.web.webservice.utils.RuleEngineFacade;

@WebService
public class CheckMsgRuleWebserviceImpl implements CheckMsgRuleWebservice {
	/** LOGGER */
	static Logger logger = Logger.getLogger(CheckMsgRuleWebserviceImpl.class);

	/** 默认规则路径 */
	private static final String UNDER_LINE = "_";

	/** 默认规则路径 */
	private static final String RULE_PACKATE = "com.founder.commsrv.rule";

	/** 危急值、异常值规则模型 */
	private static final String TARGET_MODEL_NAME = "checkItem";

	/** 异常值规则组名 */
	private static final String EXCEPTION_GROUP = "ExceptionGroup";

	/** 危急值规则组名 */
	private static final String EMERGENCY_GROUP = "EmergencyGroup";

	/** 校验通过信息 */
	private static final String RESULT_MESSAGE = "正常";

	/** 校验通过状态 */
	private static final String RESULT_STATUS = "false";
	
	/**xml路径*/
	private static final String XPATH = "/msg/report/component/reportResult/component";
	
	@Autowired
	RuleManagerDao ruleManagerDao;
	
	public String checkMsg(String ruleType, String msg) {
		try {
			//做成dom对象
			Document document = ReadResource.parseXmlString(msg);
			// 获得模型类型
			FactType appType = RuleEngineFacade.getFactType(RULE_PACKATE, TARGET_MODEL_NAME);
			// 获得校验引擎回话
			StatefulKnowledgeSession ksession = RuleEngineFacade.getStatefulKnowledgeSession();
			// 取得root节点
			Element rootElt = document.getRootElement();
			// 取得科室信息
			String visitDept = rootElt.elementTextTrim("visitDept");
			visitDept = ruleDept(ruleType,visitDept);
			// 取得报告实体
			List<Element> subItemList = document.selectNodes(XPATH);
			Iterator subItemIter = subItemList.iterator();
			while(subItemIter.hasNext()){
				// 获取xml子项目结点数据封装到checkItem对象,执行规则
				Element checkObject = (Element) subItemIter.next();
				// 做成校验对象
				Object checkItem = createModleObeject(checkObject, appType);
				// 执行规则
				executRule(checkItem, ruleType, visitDept, ksession);
				// 将校验结果返回
				returnResult(appType, checkItem, checkObject);
			}
			// 关闭session
			ksession.dispose();
			// 做成返回结果xml
			msg = ReadResource.format(document);
		} catch (Exception e) {
			logger.error("rule check error!", e);
			throw new RuntimeException(e);
		}
		return msg;
	}
	/**
	 * 校验是否为全院规则
	 */
	private String ruleDept(String ruleType,String visitDept){
		int count = ruleManagerDao.selectRuleByDept(ruleType,visitDept);
		if(count==0){
			visitDept ="0000000";
		}
		return visitDept;
	}
			


	/**
	 * 封装校验模型对象
	 * 
	 * @param Element
	 * @param FactType
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 */
	private Object createModleObeject(Element checkObject, FactType appType)
			throws InstantiationException, IllegalAccessException {
		// 获得模型实体
		Object checkItem = appType.newInstance();
		// 子项目编码取得
		String itemCode = checkObject.elementTextTrim("itemCode");
		// 检验数值结果
		float itemNumValue = Float.parseFloat(checkObject.elementTextTrim(
				"itemNumValue").equals("") ? "0" : checkObject
				.elementTextTrim("itemNumValue"));
		// 检验字符串结果
		String itemStrValue = (checkObject.elementTextTrim("itemStrValue"));
		// 结果单位
		String itemUnit = checkObject.elementTextTrim("itemUnit");
		//科室
		String checkOffice = checkObject.elementTextTrim("itemDept");
		appType.set(checkItem, "checkKey", itemCode);
		appType.set(checkItem, "itemNumValue", itemNumValue);
		appType.set(checkItem, "itemStrValue", itemStrValue);
		appType.set(checkItem, "unit", itemUnit);
		appType.set(checkItem, "checkOffice", checkOffice);
		return checkItem;
	}

	/**
	 * 执行规则校验
	 * 
	 * @param checkItem
	 * @param ruleType
	 * @param visitDept
	 * @param ksession
	 * 
	 */
	private void executRule(Object checkItem, String ruleType,
			String visitDept, StatefulKnowledgeSession ksession) {
		FactHandle fh = ksession.insert(checkItem);
		if (EXCEPTION_GROUP.equals(ruleType)) {
			ksession.getAgenda()
					.getAgendaGroup(visitDept + UNDER_LINE + EXCEPTION_GROUP)
					.setFocus();
		} else if (EMERGENCY_GROUP.equals(ruleType)) {
			ksession.getAgenda()
					.getAgendaGroup(visitDept + UNDER_LINE + EMERGENCY_GROUP)
					.setFocus();
		}
		ksession.fireAllRules();
		// 校验后，将session里的对象清空
		ksession.retract(fh);
	}

	/**
	 * 回写校验结果
	 * 
	 * @param FactType
	 * @param Object
	 * @param Element
	 */
	private void returnResult(FactType appType, Object checkItem,
			Element itemInsideComponent) {
		String checkMessage = RESULT_MESSAGE;
		String checkStatus = RESULT_STATUS;
		String suggestion = "";
		if (appType.get(checkItem, "checkMessage") != null) {
			checkMessage = appType.get(checkItem, "checkMessage")==null?checkMessage:appType.get(checkItem, "checkMessage").toString();
			checkStatus = appType.get(checkItem, "checkStatus")==null?checkStatus:appType.get(checkItem, "checkStatus").toString();
			suggestion = appType.get(checkItem, "suggestion")==null?suggestion:appType.get(checkItem, "suggestion").toString();
		}
		itemInsideComponent.addComment("检验结果状态");
		Element checkStatusElm = itemInsideComponent.addElement("checkStatus");
		checkStatusElm.setText(checkStatus);
		itemInsideComponent.addComment("检验结果信息");
		Element checkMessageElm = itemInsideComponent
				.addElement("checkMessage");
		checkMessageElm.setText(checkMessage);
		
		/** 处理建议 */
		itemInsideComponent.addComment("处理建议");
		Element adviseElm = itemInsideComponent.addElement("suggestion");
		adviseElm.setText(suggestion);

	}

}
