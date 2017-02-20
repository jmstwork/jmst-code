package com.founder.fmdm.service.audit;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.common.CommonUtil;
import com.founder.fmdm.common.ReadResource;
import com.founder.fmdm.dao.audit.AuditInformationDao;
import com.founder.fmdm.dao.audit.AuditManageDao;
import com.founder.fmdm.entity.AudContentInfo;
import com.founder.fmdm.entity.AudInfo;

@Service
public class AuditMsgUpdateDataServiceImpl implements AuditMsgUpdateDataService {

	private static String HEAD_msgId_XPATH = "//msgId";
	private static String HEAD_msgName_XPATH = "//msgName";
	private static String HEAD_sourceSysCode_XPATH = "//sourceSysCode";
	private static String HEAD_targetSysCode_XPATH = "//targetSysCode";
//	private static String HEAD_createTime_XPATH = "//createTime";
	
	private static String BODY_operDateTime_XPATH = "//operDateTime";
	private static String BODY_operUserId_XPATH = "//operUserId";
	private static String BODY_operUnitId_XPATH = "//operUnitId";
	private static String BODY_hospitalCode_XPATH = "//hospitalCode";
	private static String BODY_hospitalName_XPATH = "//hospitalName";
	private static String BODY_auditEvent_XPATH = "//auditEvent";
	private static String BODY_auditSystemId_XPATH = "//auditSystemId";
	private static String BODY_content_XPATH = "//content";
	private static String BODY_tableName_XPATH = "tableName";
	
	private static String BODY_row_XPATH = "row";
	private static String BODY_row_pro_XPATH = "action";
	private static String BODY_item_XPATH = "item";
	private static String BODY_itemName_XPATH = "item/itemName";
	private static String BODY_oldValue_XPATH = "item/oldValue";
	private static String BODY_newValue_XPATH = "item/newValue";
	
	private static String BODY_computerName_XPATH = "//computerName";
	private static String BODY_ipAddress_XPATH = "//ipAddress";
	private static String BODY_Field1_XPATH = "//Field1";
	private static String BODY_Field2_XPATH = "//Field2";
	private static String BODY_Field3_XPATH = "//Field3";
	
	/*@Autowired
	EntityDao entityDao;*/

	@Autowired
	AuditManageDao auditManageDao;
	
	@Autowired
	AuditInformationDao auditInformationDao;

	private Map<String, Class<?>> entityClassMap;

	private Map<String, String> constants4TabNameMap;

	private Map<String, String> msgIdAndNameMap;

	static Logger logger = Logger
			.getLogger(AuditMsgUpdateDataServiceImpl.class);

	public AuditMsgUpdateDataServiceImpl() {
		/*entityClassMap = DictConstants.CONSTANTS_ENTITYCLASS_MAP;
		constants4TabNameMap = DictConstants4TabNameByMsgId.CONSTANTS_4TableName_MAP;
		msgIdAndNameMap = DictConstants4MsgIdAndNameByTabName.CONSTANTS_4MsgIdAndName_MAP;*/
	}

	@Override
	@Transactional
	public void addDataThroughMQMsg(Object msg) {
		String message = String.valueOf(msg);
		try {
			if (!"".equalsIgnoreCase(message.toString())) {
				Document document = ReadResource.parseXmlString(message);
				
				AudInfo audInfo = new AudInfo();
				StringBuffer sb = new StringBuffer();

				String msgId = ((Element)document.selectNodes(HEAD_msgId_XPATH).get(0)).getStringValue();
				String msgName = ((Element)document.selectNodes(HEAD_msgName_XPATH).get(0)).getStringValue();
				String sourceSysCode = ((Element)document.selectNodes(HEAD_sourceSysCode_XPATH).get(0)).getStringValue();
				String targetSysCode = ((Element)document.selectNodes(HEAD_targetSysCode_XPATH).get(0)).getStringValue();
//				String createTime = ((Element)document.selectNodes(HEAD_createTime_XPATH).get(0)).getStringValue();
				
				
				String operDateTime = ((Element)document.selectNodes(BODY_operDateTime_XPATH).get(0)).getStringValue();
				String operUserId = ((Element)document.selectNodes(BODY_operUserId_XPATH).get(0)).getStringValue();
				String operUnitId = ((Element)document.selectNodes(BODY_operUnitId_XPATH).get(0)).getStringValue();
				String hospitalCode = ((Element)document.selectNodes(BODY_hospitalCode_XPATH).get(0)).getStringValue();
				String hospitalName = ((Element)document.selectNodes(BODY_hospitalName_XPATH).get(0)).getStringValue();
				String auditEvent = ((Element)document.selectNodes(BODY_auditEvent_XPATH).get(0)).getStringValue();
				String auditSystemId = ((Element)document.selectNodes(BODY_auditSystemId_XPATH).get(0)).getStringValue();
				
				List<Element> contentList = (List<Element>)document.selectNodes(BODY_content_XPATH);
				
//				List<Element> tableNameList = (List<Element>)document.selectNodes(BODY_tableName_XPATH);
//				
//				List<Element> rowList = (List<Element>)document.selectNodes(BODY_row_XPATH);
//				List<Element> itemList = (List<Element>)document.selectNodes(BODY_item_XPATH);
//				List<Element> itemNameList = (List<Element>)document.selectNodes(BODY_itemName_XPATH);
//				List<Element> oldValueList = (List<Element>)document.selectNodes(BODY_oldValue_XPATH);
//				List<Element> newValueList = (List<Element>)document.selectNodes(BODY_newValue_XPATH);
				
				String computerName = ((Element)document.selectNodes(BODY_computerName_XPATH).get(0)).getStringValue();
				String ipAddress = ((Element)document.selectNodes(BODY_ipAddress_XPATH).get(0)).getStringValue();
				String field1 = ((Element)document.selectNodes(BODY_Field1_XPATH).get(0)).getStringValue();
				String field2 = ((Element)document.selectNodes(BODY_Field2_XPATH).get(0)).getStringValue();
				String field3 = ((Element)document.selectNodes(BODY_Field3_XPATH).get(0)).getStringValue();
				
				// bug0032672 对消息的必填项进行验证，有误则发应急
				if (StringUtils.isEmpty(msgId)
						|| StringUtils.isEmpty(msgName)
						||StringUtils.isEmpty(sourceSysCode)
//						|| StringUtils.isEmpty(createTime)
						|| StringUtils.isEmpty(operDateTime)
						|| StringUtils.isEmpty(operUserId)
						|| StringUtils.isEmpty(operUnitId)
						|| StringUtils.isEmpty(hospitalCode)
						|| StringUtils.isEmpty(hospitalName)
						|| StringUtils.isEmpty(auditEvent)
						|| StringUtils.isEmpty(auditSystemId)
						|| contentList.subList(0, contentList.size()).size() == 0) {
					throw new RuntimeException("必填项为空");
				}

				//解析并保存消息
				audInfo.setAudId(String.valueOf(new Date().getTime()));
				audInfo.setSysId(auditSystemId);
				audInfo.setOptDt(CommonUtil.formatToDate(operDateTime, "yyyyMMddHHmmss"));
				audInfo.setEventCode(auditEvent);
				audInfo.setHospitalCode(hospitalCode);
				audInfo.setHospitalName(hospitalName);
				audInfo.setDeptCode(operUnitId);
				audInfo.setUserId(operUserId);
				audInfo.setMachineName(computerName);
				audInfo.setIpAddress(ipAddress);
				audInfo.setRemark1(field1);
				audInfo.setRemark2(field2);
				audInfo.setRemark3(field3);
				audInfo.setCreateBy("SYSTEM");
				audInfo.setCreateDt(new Date());
				/*audInfo.setUpdateBy("SYSTEM");
				audInfo.setUpdateDt(new Date());*/
				audInfo.setDeleteFlg(0);
				//audInfo.setUpdateDt(new Date());
				
				auditInformationDao.executeAddInfo(audInfo);
				
				
				for(int i=0;i<contentList.size();i++){
					AudContentInfo contentInfo = new AudContentInfo();
					Element content = contentList.get(i);
					String tableName = ((Element)content.selectNodes(BODY_tableName_XPATH).get(0)).getStringValue();
					
					List<Element> rowList = (List<Element>)content.selectNodes(BODY_row_XPATH);
					
					int rowNo = 0;
					for(int j=0;j<rowList.size();j++){
						Element row = rowList.get(j);
						String action = row.attributeValue(BODY_row_pro_XPATH);
						List<Element> itemList = (List<Element>)row.selectNodes(BODY_item_XPATH);
						
						List<Element> itemNameList = (List<Element>)row.selectNodes(BODY_itemName_XPATH);
						List<Element> oldValueList = (List<Element>)row.selectNodes(BODY_oldValue_XPATH);
						List<Element> newValueList = (List<Element>)row.selectNodes(BODY_newValue_XPATH);
						
						String itemName = null;
						String oldValue = null;
						String newValue = null;
						int contentNo = 0;
						for(int e=0;e<itemList.size();e++){
							
							itemName = itemNameList.get(e).getStringValue();
							oldValue = oldValueList.get(e).getStringValue();
							newValue = newValueList.get(e).getStringValue();
							
							//设置项目内容
							String id = String.valueOf(new Date().getTime());
							contentInfo.setItemId(id);
							contentInfo.setAudId(audInfo.getAudId());
							contentInfo.setRowName(tableName);
							contentInfo.setRowAction(action);
							contentInfo.setRowNo(String.valueOf(rowNo));
							contentInfo.setContentNo(String.valueOf(contentNo));
							contentInfo.setItemName(itemName);
							contentInfo.setOldValue(oldValue);
							contentInfo.setNewValue(newValue);
							contentInfo.setCreateDt(new Date());
							contentInfo.setCreateBy("SYSTEM");
							contentInfo.setDeleteFlg(0);
							auditInformationDao.executeAddContent(contentInfo);
							
							contentNo++;
						}
						rowNo++;
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
