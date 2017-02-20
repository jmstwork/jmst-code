package com.founder.web.webservice.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.drools.definition.type.FactType;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;

import com.founder.fmdm.common.CommonUtil;
import com.founder.fmdm.common.ReadResource;
import com.founder.fmdm.dao.rule.RuleManagerDao;
import com.founder.fmdm.service.rule.CreateDrl;
import com.founder.web.webservice.RuleCheckWebservice;
import com.founder.web.webservice.utils.RuleEngineFacade;
@WebService
public class RuleCheckWebserviceImpl implements RuleCheckWebservice {

	/** LOGGER */
	static Logger logger = Logger.getLogger(CheckMsgRuleWebserviceImpl.class);

	/** 默认规则路径 */
	private static final String UNDER_LINE = "_";

	/** 默认规则路径 */
	private static final String RULE_PACKATE = "com.founder.fmdm.service.rule";
	
	private static final String HOSPITAL_CODE ="hospitalCode";
	private static final String VIST_DEPT = "visitDept";
	private static final String RULE_GROUP = "roleGroupType";
	

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
	private static final String XPATH = "/msg/row";
	
	
	@Autowired
    CreateDrl createDrl;
	
	private static String MSG_hospitalCode_XPATH = "//hospitalCode";
	private static String MSG_visitDept_XPATH = "//visitDept";
	private static String MSG_roleGroupType_XPATH = "//roleGroupType";
	
	private static String MSG_ROW_XPATH = "//row";
	private static String MSG_ROW_KEY_XPATH = "rowKey";
	private static String MSG_ROW_FACT_TYPE_XPATH = "factType";
	private static String MSG_ITEM_XPATH = "item";
	private static String MSG_ITEMNAME_XPATH = "item/itemName";
	private static String MSG_VALUE_XPATH = "item/value";
	
	private static String RULE_CHECK_RESULT = "RULE_CHECK_RESULT.xml";
	
	private static String createTime_XPath = "//createTime";
	
	
	@Autowired
	RuleManagerDao ruleManagerDao;
	
	public String ruleCheck(String msg){
		try {
			//做成dom对象
			Document document = ReadResource.parseXmlString(msg);
			// 获得模型类型
			// 获得校验引擎回话
			StatefulKnowledgeSession ksession = RuleEngineFacade.getStatefulKnowledgeSession();
			
			// 取得医疗机构、科室和规则组信息
			String hospitalCode = ((Element)document.selectNodes(MSG_hospitalCode_XPATH).get(0)).getStringValue();
			String visitDept = ((Element)document.selectNodes(MSG_visitDept_XPATH).get(0)).getStringValue();
			String ruleGroup = ((Element)document.selectNodes(MSG_roleGroupType_XPATH).get(0)).getStringValue();
			
			//结果输出
			Document resultDoc = ReadResource.parseXmlString(ReadResource
					.getResourceAsString(RULE_CHECK_RESULT));
			Element createTimeNode = (Element) resultDoc.selectNodes(
					createTime_XPath).get(0);
			createTimeNode.setText(CommonUtil.dateToString(new Date()));
			
			List<Element> rowList = (List<Element>)document.selectNodes(MSG_ROW_XPATH);
			for(int i=0;i<rowList.size();i++){
				Element row = rowList.get(i);
				String rowKey = ((Element)row.selectNodes(MSG_ROW_KEY_XPATH).get(0)).getStringValue();
				String factType = ((Element)row.selectNodes(MSG_ROW_FACT_TYPE_XPATH).get(0)).getStringValue();
				
				FactType appType = RuleEngineFacade.getFactType(RULE_PACKATE, factType);
				
				List<Element> itemList = (List<Element>)row.selectNodes(MSG_ITEM_XPATH);
				
				List<Element> itemNameList = (List<Element>)row.selectNodes(MSG_ITEMNAME_XPATH);
				List<Element> valueList = (List<Element>)row.selectNodes(MSG_VALUE_XPATH);
				
				Element rowEl = resultDoc.getRootElement().addElement("row");
				Element rowKeyEl = rowEl.addElement(MSG_ROW_KEY_XPATH);
				rowKeyEl.setText(rowKey);
				
				Object checkItem = appType.newInstance();
				String itemName = null;
				String value = null;
				for(int j=0;j<itemList.size();j++){
					itemName = itemNameList.get(j).getStringValue();
					value = valueList.get(j).getStringValue();
					Class clazz = checkItem.getClass().getDeclaredField(itemName).getType();
					Object v = null;
					if(!clazz.getSimpleName().equals(value.getClass().getSimpleName())){
						Method method = clazz.getMethod("valueOf", String.class);
						v = method.invoke(clazz, value);
						appType.set(checkItem, itemName, v);
					}else{
						appType.set(checkItem, itemName, value);
					}
				}
				
				executRule(checkItem, visitDept, ruleGroup, ksession);
				
				Field[] fields = checkItem.getClass().getDeclaredFields();
				for(int f=0;f<fields.length;f++){
					String fieldName = fields[f].getName();
					Object obj = getFieldValueByName(fieldName,checkItem);
					Element itemEl = rowEl.addElement(MSG_ITEM_XPATH);
					Element itemNameEl = itemEl.addElement("itemName");
					Element valueEl = itemEl.addElement("value");
					itemNameEl.setText(fieldName);
					valueEl.setText(obj == null ?"":String.valueOf(obj));
				}
			}
			// 关闭session
			ksession.dispose();
			// 做成返回结果xml
			msg = ReadResource.format(resultDoc);
		} catch (Exception e) {
			logger.error("rule check error!", e);
			throw new RuntimeException(e);
		}
		return msg;
	}
	
	/**
	 * 使用反射根据属性名称获取属性值
	 * 
	 * @param fieldName
	 *            属性名称
	 * @param o
	 *            操作对象
	 * @return Object 属性值
	 */

	private Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			System.out.println("属性不存在");
			return null;
		}
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
		appType.set(checkItem, "checkKey", itemCode);
		appType.set(checkItem, "itemNumValue", itemNumValue);
		appType.set(checkItem, "itemStrValue", itemStrValue);
		appType.set(checkItem, "unit", itemUnit);
		return checkItem;
	}
	
	/**
	 * 执行规则校验
	 * 
	 * @param checkItem
	 * @param ruleGroup
	 * @param visitDept
	 * @param ksession
	 * 
	 */
	private void executRule(Object checkItem,
			String visitDept, String ruleGroup, StatefulKnowledgeSession ksession) {
		FactHandle fh = ksession.insert(checkItem);
		ksession.getAgenda()
				.getAgendaGroup(visitDept + UNDER_LINE + ruleGroup)
				.setFocus();
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
		if (appType.get(checkItem, "checkMessage") != null) {
			checkMessage = appType.get(checkItem, "checkMessage").toString();
			checkStatus = appType.get(checkItem, "checkStatus").toString();
		}

		itemInsideComponent.addComment("检验结果状态");
		Element checkStatusElm = itemInsideComponent.addElement("checkStatus");
		checkStatusElm.setText(checkStatus);
		itemInsideComponent.addComment("检验结果信息");
		Element checkMessageElm = itemInsideComponent
				.addElement("checkMessage");
		checkMessageElm.setText(checkMessage);

	}
}
