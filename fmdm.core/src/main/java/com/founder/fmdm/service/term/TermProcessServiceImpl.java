package com.founder.fmdm.service.term;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.core.AppSettings;
import com.founder.core.CommonMessage;
import com.founder.fmdm.Constants;
import com.founder.fmdm.MessageEvent;
import com.founder.fmdm.dao.MqMessageLibDao;
import com.founder.fmdm.dao.term.TermItemDao;
import com.founder.fmdm.entity.MqMessageLib;
import com.founder.fmdm.service.term.dto.DictItemDto;
import com.founder.msg.utils.Message;
import com.founder.msg.utils.MessageHead;
import com.founder.msg.utils.MessageUtils;
import com.founder.util.StringUtil;
import com.founder.wmq.WMQMessageSender;
import com.ibm.mq.MQException;

@Service
public class TermProcessServiceImpl implements TermProcessService {

	private static Logger logger = LoggerFactory.getLogger("readerApp_pf");
	
	@Autowired
	private TermItemDao termItemDao;

	@Autowired
	private MqMessageLibDao mqMessageLibDao;

	@Autowired
	TermItemListService termItemListService;

	@Autowired
	private WMQMessageSender wmqMessageSender;

	// batch_user
	private final static String BATCH_USER = "batch_user";

	private final static String N = "N";

	private final static String SEND_QUEUE_NAME = AppSettings
			.getConfig("term.send_queue_name");
	// opt_state
	private final static String OPT_STATUS_A = "a";
	private final static String OPT_STATUS_U = "u";
	private final static String OPT_STATUS_D = "d";

	// release_state "c":现在,"h":历史,"a":待发布,"r":驳回,"d":待审批
	private static final String RELEASE_STATE_C = "c";
	private static final String RELEASE_STATE_H = "h";

	private final static String S028 = "S028";
	private String retryTimes = AppSettings.getConfig("send.retry_time");

	@Override
	@Transactional
	public int termRecieveProcess(MessageEvent event,
			List<Map<String, Object>> insertData,
			List<Map<String, Object>> updateData,
			List<Map<String, Object>> deleteData) throws Exception {
		// 处理结果
		List<String> processUidList = new ArrayList<String>();
		// 查询所有业务字段
		List<DictItemDto> allKeys = termItemDao.selectDictFieldList(null, null,
				event.getMessageType());
		// 查询业务主键字段
		List<DictItemDto> primaryKeys = termItemDao.selectDictFieldList(null,
				"3", event.getMessageType());
		
		// 设置术语物理表名
		if (allKeys.size() > 0) {
			event.tableName = allKeys.get(0).getTable_name();
			event.termName = allKeys.get(0).getDict_name();
		}
		/**
		 * 增加数据处理
		 */
		for (Map<String, Object> dataMap : insertData) {
			// 业务主键校验
			primaryKeysCheck(dataMap,primaryKeys);
			// 如果术语数据已经存在(增加、删除)
			BigDecimal item_version = new BigDecimal(1);
			Map<String, Object> oldData = termItemDao.selectMaxVersionTermData(
					event.tableName, dataMap, primaryKeys);
			if(oldData!=null&&!OPT_STATUS_D.equals(oldData.get("opt_status"))){
				updateData.add(dataMap);
				continue;
			}else if(oldData!=null&&OPT_STATUS_D.equals(oldData.get("opt_status"))){
				item_version = itemVersionUp(oldData.get("item_version"));
				// 最新数据历史版本处理
				Map<String, Object> dataMapForUpdate = new HashMap<String, Object>();
				dataMapForUpdate.put("uni_key", oldData.get("uni_key"));
				dataMapForUpdate.put("release_status", RELEASE_STATE_H);
				dataMapForUpdate.put("last_update_by", BATCH_USER);
				dataMapForUpdate.put("last_update_time", new Date());
				dataMapForUpdate.put("update_count", oldData.get("update_count"));
				termItemDao.executeUpdate(allKeys, dataMapForUpdate);
			}
			String insertUid = StringUtil.getUUID();
			dataMap.put("opt_status", OPT_STATUS_A);
			dataMap.put("uni_key", insertUid);
			dataMap.put("create_by", BATCH_USER);
			dataMap.put("create_time", new Date());
			dataMap.put("delete_flg", 0);
			dataMap.put("update_count", 0);
			dataMap.put("release_status", RELEASE_STATE_C);// c现在,h历史,a待发布,r驳回,d待审批
			dataMap.put("item_version", item_version);
			termItemDao.executeAdd(allKeys, dataMap);
			processUidList.add(insertUid);
		}
		/**
		 * 更新数据处理
		 */
		for (Map<String, Object> dataMap : updateData) {
			// 业务主键校验
			primaryKeysCheck(dataMap,primaryKeys);
			// 最新版本数据取得
			Map<String, Object> oldData = termItemDao.selectMaxVersionTermData(
					event.tableName, dataMap, primaryKeys);
			//added by yang_jianbo @ 2014-11-3 @ 由于发送方发送update时，可能找不到对应 的记录，需要将update做inert处理  bug0050316
			// 最新数据历史版本处理
			// 最新版本数据插入
			String updeteUid = StringUtil.getUUID();
			dataMap.put("opt_status", OPT_STATUS_U);
			dataMap.put("uni_key", updeteUid);
			dataMap.put("create_by", BATCH_USER);
			dataMap.put("create_time", new Date());
			dataMap.put("delete_flg", 0);
			dataMap.put("update_count", 0);
			dataMap.put("release_status", RELEASE_STATE_C);// c现在,h历史,a待发布,r驳回,待审批
			
			
			if(oldData!=null){
				Map<String, Object> dataMapForUpdate = new HashMap<String, Object>();
				dataMapForUpdate.put("release_status", RELEASE_STATE_H);
				dataMapForUpdate.put("last_update_by", BATCH_USER);
				dataMapForUpdate.put("last_update_time", new Date());
				dataMapForUpdate.put("uni_key", oldData.get("uni_key"));
				dataMapForUpdate.put("update_count",
						itemVersionUp(oldData.get("update_count")));
				termItemDao.executeUpdate(allKeys, dataMapForUpdate);
				
				// 版本号处理
				dataMap.put("item_version",
						itemVersionUp(oldData.get("item_version")));
				
				// 复制覆盖原术语信息
				oldData.putAll(dataMap);
				// 插入新术语信息
				termItemDao.executeAdd(allKeys, oldData);
			}else{
				dataMap.put("item_version",1);
				termItemDao.executeAdd(allKeys, dataMap);
			}
			processUidList.add(updeteUid);
		}

		/**
		 * 删除数据处理
		 */
		for (Map<String, Object> dataMap : deleteData) {
			// 业务主键校验
			primaryKeysCheck(dataMap,primaryKeys);
			// 最新版本数据取得
			Map<String, Object> oldData = termItemDao.selectMaxVersionTermData(
					event.tableName, dataMap, primaryKeys);
			// 最新数据历史版本处理
			Map<String, Object> dataMapForUpdate = new HashMap<String, Object>();
			dataMapForUpdate.put("uni_key", oldData.get("uni_key"));
			dataMapForUpdate.put("release_status", RELEASE_STATE_H);
			dataMapForUpdate.put("last_update_by", BATCH_USER);
			dataMapForUpdate.put("last_update_time", new Date());
			dataMapForUpdate.put("update_count", oldData.get("update_count"));
			termItemDao.executeUpdate(allKeys, dataMapForUpdate);
			// 最新版本数据插入
			String deleteUid = StringUtil.getUUID();
			dataMap.put("opt_status", OPT_STATUS_D);
			dataMap.put("uni_key", deleteUid);
			dataMap.put("create_by", BATCH_USER);
			dataMap.put("create_time", new Date());
			dataMap.put("delete_flg", 0);
			dataMap.put("update_count", 0);
			dataMap.put("release_status", RELEASE_STATE_C);// c现在,h历史,a待发布,r驳回,待审批
			dataMap.put("item_version",
					itemVersionUp(oldData.get("item_version")));
			termItemDao.executeAdd(allKeys, dataMap);
			processUidList.add(deleteUid);
		}
		/**
		 * 消息发送处理
		 * 消息重试发送
		 */
		int runTimes = 0;
		while(runTimes<Integer.valueOf(retryTimes)+1){
			try{
				releaseStatusUpdate(processUidList, event, allKeys, BATCH_USER);
				// 处理结束
				MqMessageLib msgLib = event.getEvent();
				MqMessageLib tempMsgLib = new MqMessageLib();
				tempMsgLib.setMqmsgId(msgLib.getMqmsgId());
				tempMsgLib.setSendFlg(1);
				tempMsgLib.setSendTime(new Date());
				mqMessageLibDao.updateMqMessageLib(tempMsgLib);
				break;
			}catch(MQException e){
				runTimes++;
				continue;
			}
		}
		return 0;
	}

	private void releaseStatusUpdate(List<String> uniKeys, MessageEvent event,
			List<DictItemDto> allKeys, String user_no) throws Exception {
		long starttime = System.currentTimeMillis();
		// 查询字段处理
		List<String> termItems = new ArrayList<String>();
		for (int i = 0; i < allKeys.size(); i++) {
			DictItemDto termItemDto = allKeys.get(i);
			if (N.equals(termItemDto.getIs_default())) {
				termItems.add(termItemDto.getField_name());
			}
		}
		// 数据查询
		List<Map<String, Object>> datas = termItemDao
				.selectDictDataListForPublish(uniKeys, termItems,
						event.tableName);
		long middletime = System.currentTimeMillis();;
		List<String> action = new ArrayList<String>();
		for (int i = 0; i < datas.size(); i++) {
			if (OPT_STATUS_A.equals((String) datas.get(i).get("opt_status"))) {
				action.add(Constants.OPT_STATUS_INSERT);
			} else if (OPT_STATUS_U.equals((String) datas.get(i).get(
					"opt_status"))) {
				action.add(Constants.OPT_STATUS_UPDATE);
			} else if (OPT_STATUS_D.equals((String) datas.get(i).get(
					"opt_status"))) {
				action.add(Constants.OPT_STATUS_DELETE);
			}
			datas.get(i).remove("opt_status");
		}
		MessageHead msgHead = new MessageHead();
		msgHead.setMsgId(event.oldValue);
		msgHead.setMsgName(event.termName);
		msgHead.setSourceSysCode(Constants.SYSTEM_CODE);
		Message msg = MessageUtils.buildMessage(msgHead, action,
				datas.toArray(new Map[0]), true);
		String result = MessageUtils.marshal(msg);
		//$Author :LSG
	    //$Date : 2014/10/16 14:27$
	    //[BUG]0049708 MODIFY BEGIN 
		// 消息头设置
		Map<String, Object> header = new HashMap<String, Object>();
		if (Constants.MSG_HEADER_TYPE_BDRM.equals(Constants.MSG_HEADER_TYPE)) {//7个消息头
			header.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
			header.put(Constants.HEADER_SERVICE_ID, event.messageType);
			header.put(Constants.HEADER_APPLY_UNIT_ID, Constants.HEADER_APPLY_UNIT_ID_VALUE);
			header.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
			header.put(Constants.HEADER_HOSPITAL_ID, Constants.HEADER_HOSPITAL_ID_VALUE);
			header.put(Constants.HEADER_SEND_SYS_ID, Constants.HEADER_SEND_SYS_ID_VALUE);
			header.put(Constants.HEADER_EXTEND_SUB_ID, Constants.HEADER_EXTEND_SUB_ID_VALUE);
		}else if(Constants.MSG_HEADER_TYPE_JSRM.equals(Constants.MSG_HEADER_TYPE)) {//8个消息头
			header.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
			header.put(Constants.HEADER_SERVICE_ID, event.messageType);
			header.put(Constants.HEADER_APPLY_UNIT_ID, Constants.HEADER_APPLY_UNIT_ID_VALUE);
			header.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
			header.put(Constants.HEADER_HOSPITAL_ID, Constants.HEADER_HOSPITAL_ID_VALUE);
			header.put(Constants.HEADER_SEND_SYS_ID, Constants.HEADER_SEND_SYS_ID_VALUE);
			// 消息头添加医嘱执行分类编码
			header.put(Constants.ORDER_EXEC_ID, Constants.ORDER_EXEC_ID_VALUE);
			header.put(Constants.HEADER_EXTEND_SUB_ID, Constants.HEADER_EXTEND_SUB_ID_VALUE);
		}else if(Constants.MSG_HEADER_TYPE_PKURM.equals(Constants.MSG_HEADER_TYPE)){//4个消息头
			header.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
			header.put(Constants.HEADER_SERVICE_ID, event.messageType);
			header.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
			header.put(Constants.ORDER_DISPATCH_TYPE_ID, Constants.ORDER_DISPATCH_TYPE_CODE);
		}
		//[BUG]0049708 MODIFY End
			
		CommonMessage commonMessage = new CommonMessage(header, result);
		logger.debug("消息头设置："+header.toString());
		long middletime2 = System.currentTimeMillis();
		wmqMessageSender.sendMessage(commonMessage, SEND_QUEUE_NAME);
		long endtime = System.currentTimeMillis();
		logger.debug("消息再送操作，数据查询{}ms，消息组装时间{}ms，消息发送时间{}ms，总时间ms{}",
				(middletime - starttime), (middletime2 - middletime),
				(endtime - middletime2), (endtime - starttime));
	}

	private BigDecimal itemVersionUp(Object oldVer) {
		// 版本号处理
		BigDecimal item_version = new BigDecimal(0);
		if (oldVer != null && !"".equals(oldVer)) {
			item_version = (BigDecimal) oldVer;
			item_version = item_version.add(new BigDecimal(1));
		}
		return item_version;
	}

	private Boolean primaryKeysCheck(Map<String, Object> dataMap,
			List<DictItemDto> primaryKeys) throws Exception {
		StringBuffer fields = new StringBuffer();
		for (DictItemDto field : primaryKeys) {
			if (!dataMap.containsKey(field.getField_name())) {
				fields.append(field.getField_name()+" ");
			} 
		}
		if (fields.length() > 0) {
			throw new Exception("术语数据中业务主键[" + fields + "]字段缺失！");
		}
		return true;
	}
}
