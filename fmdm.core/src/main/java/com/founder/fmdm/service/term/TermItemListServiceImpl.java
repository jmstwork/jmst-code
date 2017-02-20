package com.founder.fmdm.service.term;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.core.AppSettings;
import com.founder.core.CommonMessage;
import com.founder.core.log.LogUtils;
import com.founder.fmdm.Constants;
import com.founder.fmdm.dao.term.TermItemDao;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.service.term.dto.DictItemDto;
import com.founder.msg.utils.Message;
import com.founder.msg.utils.MessageHead;
import com.founder.msg.utils.MessageUtils;
import com.founder.wmq.WMQMessageSender;

@Service
public class TermItemListServiceImpl implements TermItemListService {

	private static Logger logger = LoggerFactory.getLogger(TermItemListServiceImpl.class);

	@Autowired
	private TermItemDao termItemDao;

	@Autowired
	private TermStructService termStructureService;
	@Autowired
	LogUtils logUtils;
	@Autowired
	private WMQMessageSender wmqMessageSender;

	private final static String Y = "Y";
	private final static String N = "N";
	
	//opt_state
	private final static String OPT_STATUS_A = "a";
	private final static String OPT_STATUS_U = "u";
	private final static String OPT_STATUS_D = "d";
	
	//release_state "c":现在,"h":历史,"a":待发布,"r":驳回,"d":待审批
	private static final String RELEASE_STATE_C = "c";
	private static final String RELEASE_STATE_D = "d";
	private static final String RELEASE_STATE_A = "a";
	private static final String RELEASE_STATE_R = "r";
	private static final String RELEASE_STATE_H = "h";
	//status "1":isShow,"2":isDefault,"3":isPrimary
	//待审批
	private final static String STATUS_0= "0";
	//待发布
	private final static String STATUS_1= "1";
	//已发布
	private final static String STATUS_2= "2";
	//待审批 待发布
	private final static String STATUS_3= "3";
	
	private final static String S028 = "S028";
	
	private final static String SEND_QUEUE_NAME = AppSettings
			.getConfig("term.send_queue_name");
	
	private MessageHead msgHead = null;

	/**
	 * 查询字典字段信息
	 * 
	 * @param dictId
	 * @param status
	 *            "1" : isShow,"2" : isDefault,"3" : isPrimary
	 * @return
	 */
	@Override
	@Transactional
	public List<DictItemDto> getDictFieldList(String dictId, String status) {
		List<DictItemDto> dictItemDtoList = termItemDao.selectDictFieldList(
				dictId, status,null);
		return dictItemDtoList;
	}

	/**
	 * 查询对应字段的数据
	 * 
	 * @param tableName
	 *            表名
	 * @param dictItemList
	 *            字段
	 * @param release_status
	 *            数据状态
	 * @param uni_key
	 *            数据ID
	 * @return
	 */
	@Override
	@Transactional
	public List<Map<String, Object>> getDictDataList(String tableName,
			List<String> dictItemList, List<String> release_status,
			String uni_key) {
		List<Map<String, Object>> a = termItemDao.selectDictDataList(tableName, dictItemList,
				release_status, uni_key);
		return a;
	}

	/**
	 * 修改字典数据
	 * 
	 * @param statusMap
	 *            状态Map
	 * @param dataMap
	 *            数据Map
	 * @param release_status
	 *            字典字段发布状态
	 * @return
	 */
	@Override
	@Transactional
	public int editTermData(Map<String, Object> statusMap,
			Map<String, Object> dataMap, List<String> release_state) {
		int status = 0;
		String finalOptStatus = "";//用于判断数据的操作状态
		try {
			String dictId = (String) statusMap.get("dictId");
			//String editStatus = (String) statusMap.get("editStatus");
			String uni_key = (String) statusMap.get("uni_key");
			String new_uni_key = (String) statusMap.get("new_uni_key");
			String new_user_no = (String) statusMap.get("new_user_no");
			String title = (String) statusMap.get("title");//根据title的值确定操作的模块，0为术语编辑，1为术语审批
			List<DictItemDto> termItemList = termItemDao.selectDictFieldList(
					dictId, null,null);
			// 如果为null，变成0
			BigDecimal update_count = new BigDecimal(0);
			BigDecimal item_version = new BigDecimal(1);
			//新增
			if (uni_key == null || "".equals(uni_key)) {
				// 1待审批，2待发布，3已发布
				dataMap.put("opt_status", OPT_STATUS_A);
				dataMap.put("uni_key", new_uni_key);
				dataMap.put("create_by", new_user_no);
				dataMap.put("create_time", new Date());
				dataMap.put("delete_flg", "0");
				dataMap.put("update_count", update_count + "");
				dataMap.put("release_status", RELEASE_STATE_D);// c现在,h历史,a待发布,r驳回,d待审批
				dataMap.put("item_version", item_version + "");
				status = termItemDao.executeAdd(termItemList, dataMap);
				finalOptStatus = OPT_STATUS_A;
			} 
			//修改
			else {
				finalOptStatus = OPT_STATUS_U;
				Map<String, Object> dataMapOld = getTermListMapByUniKey(
						termItemList, release_state, uni_key);
				if (dataMapOld.get("update_count") != null
						&& !"".equals(dataMapOld.get("update_count"))) {
					update_count = (BigDecimal) dataMapOld.get("update_count");
					update_count = update_count.add(new BigDecimal(1));
				}
				//已发布的数据
				if (RELEASE_STATE_C.equals((String) dataMapOld.get("release_status"))) {
					dataMap.put("uni_key", new_uni_key);
					dataMap.put("create_by", new_user_no);
					dataMap.put("create_time", new Date());
					dataMap.put("delete_flg", "0");
					dataMap.put("update_count", update_count + "");
					dataMap.put("release_status", RELEASE_STATE_D);// c现在,h历史,a待发布,r驳回,d待审批
					if (dataMapOld.get("item_version") != null
							&& !"".equals(dataMapOld.get("item_version"))) {
						item_version = (BigDecimal) dataMapOld.get("item_version");
						item_version = item_version.add(new BigDecimal(1));
					}
					dataMap.put("item_version", item_version + "");
					// insert
					dataMap.put("opt_status", OPT_STATUS_U);
					// update
					Map<String, Object> dataMapForUpdate = new HashMap<String, Object>();
					dataMapForUpdate.put("uni_key", uni_key);
					dataMapForUpdate.put("release_status", RELEASE_STATE_H);
					dataMapForUpdate.put("last_update_by", new_user_no);
					dataMapForUpdate.put("last_update_time", new Date());
					
					status = termItemDao.executeAdd(termItemList, dataMap);
					int status1 = termItemDao.executeUpdate(termItemList,
							dataMapForUpdate);
					if (status1 == 0) {
						status = 0;
					}
				} 
				//未发布的数据
				else {
					// update
					dataMap.put("uni_key", uni_key);
					dataMap.put("update_count", update_count + "");
					dataMap.put("release_status", RELEASE_STATE_D);
					dataMap.put("last_update_by", new_user_no);
					dataMap.put("last_update_time", new Date());
					String optStatus = (String)dataMapOld.get("opt_status");
					if(OPT_STATUS_A.equals(optStatus)){
						dataMap.put("opt_status", OPT_STATUS_A);
					}else{
						dataMap.put("opt_status", OPT_STATUS_U);
					}
					status = termItemDao.executeUnpublishDataUpdate(termItemList, dataMap);
				}
			}
			//修改dict_main发布状态
			List<DictItemDto> termItemDtoList = termItemDao.selectDictFieldList(dictId, null,null);
			String tableName = termItemDtoList.get(0).getTable_name();
			getAndUpdateReleaseStatusCount(tableName, dictId, new_user_no);
//			String optStatus = (String)getTermListMapByUniKey(
//					termItemList, release_state, uni_key).get("opt_status");
			if(OPT_STATUS_A.equals(finalOptStatus)){//为新增术语
				if("0".equals(title)){//术语编辑-新增术语
					logUtils.insertLog("00605", "00605000", "新增术语项目[{}]", termItemDtoList.get(0).getDict_name());
				}
			}else{//编辑术语
				if("0".equals(title)){//术语编辑-编辑术语
					logUtils.insertLog("00605", "00605001", "编辑术语项目[{}]", termItemDtoList.get(0).getDict_name());
				}else if("1".equals(title)){//术语审批-编辑术语
					logUtils.insertLog("00607", "00607003", "审批-编辑术语项目[{}]", termItemDtoList.get(0).getDict_name());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return status;
	}

	/**
	 * 通过uni_key获取字典数据
	 * 
	 * @param termItemList
	 *            字典字段
	 * @param release_status
	 *            字典字段发布状态
	 * @param uni_key
	 * @return
	 */
	@Override
	@Transactional
	public Map<String, Object> getTermListMapByUniKey(
			List<DictItemDto> termItemList, List<String> release_status,
			String uni_key) {
		Map<String, Object> termDataMap = new HashMap<String, Object>();
		if (uni_key != null && !"".equals(uni_key)) {
			String tableName = termItemList.get(0).getTable_name();
			List<String> termNameList = new ArrayList<String>();
			for (int i = 0; i < termItemList.size(); i++) {
				termNameList.add(termItemList.get(i).getField_name());
			}
			termDataMap = this.getDictDataList(tableName, termNameList,
					release_status, uni_key).get(0);
		} else {
			for (int i = 0; i < termItemList.size(); i++) {
				termDataMap.put(termItemList.get(i).getField_name(), "");
			}
		}
		return termDataMap;
	}

	/**
	 * 获取数据对比信息（两条）
	 * @param uni_key
	 * @param dictId
	 * @param title
	 * @return
	 */
	@Override
	@Transactional
	public List<Map<String, Object>> getTermDataCompare(String uni_key,
			String dictId, String title, List<String> release_state_forlist) {
		List<Map<String, Object>> differentData = new ArrayList<Map<String, Object>>();
		List<DictItemDto> termItemDtoList = termItemDao.selectDictFieldList(dictId, null,null);
		List<String> termItemList = new ArrayList<String>();
		List<String> termItemPrimary = new ArrayList<String>();
		List<String> termItemShow = new ArrayList<String>();
		for (int i = 0; i < termItemDtoList.size(); i++) {
			DictItemDto termItemDto = termItemDtoList.get(i);
			if (Y.equals(termItemDto.getIs_primary())
					&& N.equals(termItemDto.getIs_default())) {
				termItemPrimary.add(termItemDto.getField_name());
			}
			if (Y.equals(termItemDto.getIs_show())) {
				termItemShow.add(termItemDto.getField_name());
			}
			termItemList.add(termItemDto.getField_name());
		}
		String tableName = termItemDtoList.get(0).getTable_name();
		List<Map<String, Object>> termData = termItemDao.selectDictDataList(
				tableName, termItemPrimary, release_state_forlist, uni_key);
		BigDecimal item_version = (BigDecimal) termData.get(0).get(
				"item_version");
		BigDecimal item_version_last = item_version.subtract(new BigDecimal(1));
		List<String> termItemFiter = new ArrayList<String>();
		Map<String, Object> entryMap = termData.get(0);
		for (int i = 0; i < termItemPrimary.size(); i++) {
			termItemFiter.add(termItemPrimary.get(i) + "= '"
					+ entryMap.get(termItemPrimary.get(i)) + "'");
		}
		termItemFiter.add("item_version in ('" + item_version + "','"
				+ item_version_last + "')");
		differentData = termItemDao.selectTermDataCompareList(tableName,
				termItemList, termItemFiter);
		List<Map<String, Object>> differentDataReturn = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 2; i++) {
			if (RELEASE_STATE_H.equals(differentData.get(i).get("release_status"))) {
				differentData.get(i).put("update_flg", "修改前");
				differentDataReturn.add(differentData.get(i));
			} else {
				differentData.get(i).put("update_flg", "修改后");
				differentDataReturn.add(differentData.get(i));
			}
		}

		return differentDataReturn;
	}
	
	/**
	 * 审批。发布字典
	 * @param uni_keys 字典字段uni_key
	 * @param tableName 字典表名
	 * @param dictId 字典ID
	 * @param status 状态(审批，发布)
	 * @param userNo 用户
	 * @return
	 */
	@Override
	@Transactional
	public int releaseStatusUpdate(List<String> uniKeys, String tableName,
			String dictId, String status, String user_no,String comment) {
		int state = 0;
		if(RELEASE_STATE_C.equals(status)){
			List<DictItemDto> termItemDtoList = termItemDao.selectDictFieldList(dictId, null,null);
			List<String> termItems = new ArrayList<String>();
			for (int i = 0; i < termItemDtoList.size(); i++) {
				DictItemDto termItemDto = termItemDtoList.get(i);
				if (N.equals(termItemDto.getIs_default())) {
					termItems.add(termItemDto.getField_name());
				}
			}
			List<Map<String,Object>> datas = termItemDao.selectDictDataListForPublish(uniKeys,termItems,termItemDtoList.get(0).getTable_name());
			logUtils.insertLog("00608", "00608000", "发布[{}]变更通过", termItemDtoList.get(0).getDict_name());
			List<String> action = new ArrayList<String>();
			for(int i=0;i<datas.size();i++){
				if(OPT_STATUS_A.equals((String)datas.get(i).get("opt_status"))){
					action.add(Constants.OPT_STATUS_INSERT);
				}else if(OPT_STATUS_U.equals((String)datas.get(i).get("opt_status"))){
					action.add(Constants.OPT_STATUS_UPDATE);
				}else if(OPT_STATUS_D.equals((String)datas.get(i).get("opt_status"))){
					action.add(Constants.OPT_STATUS_DELETE);
				}
				datas.get(i).remove("opt_status");
			}
			msgHead = new MessageHead();
			String serviceId = termItemDtoList.get(0).getService_id();
			if(serviceId != null && !"".equals(serviceId)){
				String valueInfo = null;
				try {
					valueInfo = AppSettings.getConfig(serviceId);
				} catch (Exception e) {
					logger.error("配置文件未配置此服务ID！");
				}
				if(valueInfo !=null && !"".equals(valueInfo)){
					msgHead.setMsgId(valueInfo);
				}else{
					msgHead.setMsgId(serviceId);
				}
			}else{
				msgHead.setMsgId(serviceId);
			}
			
			msgHead.setMsgName(termItemDtoList.get(0).getDict_name());
			msgHead.setSourceSysCode(Constants.SYSTEM_CODE);
			Message msg = MessageUtils.buildMessage(msgHead != null ? msgHead
					: new MessageHead(), action, datas.toArray(new Map[0]),true);
			String result = MessageUtils.marshal(msg);
			Map<String,Object> header = new HashMap<String,Object>();
			
			
//			header.put(Constants.HEADER_DOMAIN_ID, Constants.CHAR_0);
//			header.put(Constants.HEADER_SERVICE_ID, termItemDtoList.get(0).getService_id());
//			header.put(Constants.HEADER_APPLY_UNIT_ID,Constants.CHAR_0);
//			header.put(Constants.HEADER_EXEC_UNIT, Constants.CHAR_0);
//			header.put(Constants.HEADER_HOSPITAL_ID,Constants.CHAR_0);
//			header.put(Constants.HEADER_SEND_SYS_ID,S028);
//			header.put(Constants.HEADER_EXTEND_SUB_ID, Constants.CHAR_0);
			
			if (Constants.MSG_HEADER_TYPE_BDRM.equals(Constants.MSG_HEADER_TYPE)) {//7个消息头
				header.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
				header.put(Constants.HEADER_SERVICE_ID, termItemDtoList.get(0).getService_id());
				header.put(Constants.HEADER_APPLY_UNIT_ID, Constants.HEADER_APPLY_UNIT_ID_VALUE);
				header.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
				header.put(Constants.HEADER_HOSPITAL_ID, Constants.HEADER_HOSPITAL_ID_VALUE);
				header.put(Constants.HEADER_SEND_SYS_ID, Constants.HEADER_SEND_SYS_ID_VALUE);
				header.put(Constants.HEADER_EXTEND_SUB_ID, Constants.HEADER_EXTEND_SUB_ID_VALUE);
			}else if(Constants.MSG_HEADER_TYPE_JSRM.equals(Constants.MSG_HEADER_TYPE)) {//8个消息头
				header.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
				header.put(Constants.HEADER_SERVICE_ID, termItemDtoList.get(0).getService_id());
				header.put(Constants.HEADER_APPLY_UNIT_ID, Constants.HEADER_APPLY_UNIT_ID_VALUE);
				header.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
				header.put(Constants.HEADER_HOSPITAL_ID, Constants.HEADER_HOSPITAL_ID_VALUE);
				header.put(Constants.HEADER_SEND_SYS_ID, Constants.HEADER_SEND_SYS_ID_VALUE);
				// 消息头添加医嘱执行分类编码
				header.put(Constants.ORDER_EXEC_ID, Constants.ORDER_EXEC_ID_VALUE);
				header.put(Constants.HEADER_EXTEND_SUB_ID, Constants.HEADER_EXTEND_SUB_ID_VALUE);
			}else if(Constants.MSG_HEADER_TYPE_PKURM.equals(Constants.MSG_HEADER_TYPE)){//4个消息头
				header.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
				header.put(Constants.HEADER_SERVICE_ID, termItemDtoList.get(0).getService_id());
				header.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
				header.put(Constants.ORDER_DISPATCH_TYPE_ID, Constants.ORDER_DISPATCH_TYPE_CODE);
			}		
			CommonMessage commonMessage = new CommonMessage(header, result);
			try {
				wmqMessageSender.sendMessage(commonMessage, SEND_QUEUE_NAME);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			state = termItemDao.executeStatusUpdate(uniKeys, tableName, status,
					user_no);
		}else{
			state = termItemDao.executeStatusUpdate(uniKeys, tableName, status,
					user_no);
			List<DictItemDto> termItemDtoList = termItemDao.selectDictFieldList(dictId, null,null);
			if("a".equals(status)){//a表示为审批同意
				logUtils.insertLog("00607", "00607000", "审批[{}]通过-意见[{}]", termItemDtoList.get(0).getDict_name(),
						"".equals(comment)?"暂无":comment);
			}else if("r".equals(status)){//r表示为审批驳回
				logUtils.insertLog("00607", "00607001", "审批[{}]未通过-意见[{}]", termItemDtoList.get(0).getDict_name(),
						"".equals(comment)?"暂无":comment);
			}
			state = termItemDao.addExamineComments(uniKeys,tableName,status, user_no, comment);
		}
		return state;
	}

	/**
	 * 修改术语主表状态
	 * @param tableName
	 * @param dictId
	 * @param user_no
	 * @return
	 */
	@Override
	@Transactional
	public int getAndUpdateReleaseStatusCount(String tableName, String dictId,
			String userNo) {
		// 待审批数据
		int statusDcount = termItemDao.selectReleaseStatusCount(tableName, RELEASE_STATE_D,
				null);
		// 待发布数据
		int statusAcount = termItemDao.selectReleaseStatusCount(tableName, RELEASE_STATE_A,
				null);

		List<DictMain> entityList = termStructureService
				.selectTermStructById(dictId);
		DictMain entity = entityList.get(0);
		termStructureService.updateMainData(entity);
		if (statusDcount == 0 && statusAcount == 0) {
			// only已发布
			entity.setStatus(STATUS_2);
		} else if (statusDcount != 0 && statusAcount == 0) {
			// only待审批
			entity.setStatus(STATUS_0);
		} else if (statusDcount == 0 && statusAcount != 0) {
			// only待发布
			entity.setStatus(STATUS_1);
		} else if (statusDcount != 0 && statusAcount != 0) {
			// both 待审批 and 待发布
			entity.setStatus(STATUS_3);
		}
		entity.setLastUpdateBy(userNo);
		entity.setLastUpdateTime(new Date());
		termStructureService.updateMainData(entity);
		return 1;
	}

	/**
	 * 删除术语数据
	 * @param tableName
	 * @param uniKey
	 * @param dictId
	 * @return
	 * @throws ParseException 
	 */
	//术语编辑和术语审批公用的删除术语函数
	@Override
	@Transactional
	public int deleteTermData(String uniKey, String userNo,List<String> releaseStatus,
			String dictId,String newUniKey,String title) throws ParseException {
		int status=0;
		List<DictItemDto> termItemDtoList = termItemDao.selectDictFieldList(dictId, null,null);
		String tableName = termItemDtoList.get(0).getTable_name();
		List<Map<String, Object>> termData = termItemDao.selectDictDataList(
				tableName, new ArrayList<String>(), releaseStatus, uniKey);
		Map<String,Object> termDataMap = termData.get(0);
		//新增的数据，未发布  进行删除  
		if(OPT_STATUS_A.equals(termDataMap.get("opt_status"))&&!RELEASE_STATE_C.equals(termDataMap.get("release_status"))){
			status = termItemDao.deleteTermAddData(tableName, uniKey, userNo, RELEASE_STATE_H);
		}
		//已发布过的数据，逻辑删除 releaseStatus 变为d ,optStatus变为d
		else{
			//原数据
			Map<String, Object> dataMapOld = getTermListMapByUniKey(
					termItemDtoList, releaseStatus, uniKey);
			BigDecimal update_count = new BigDecimal(0);
			BigDecimal item_version = new BigDecimal(0);
			if (dataMapOld.get("update_count") != null
					&& !"".equals(dataMapOld.get("update_count"))) {
				update_count = (BigDecimal) dataMapOld.get("update_count");
				update_count = update_count.add(new BigDecimal(1));
			}
			//已发布的版本号加一
			if(RELEASE_STATE_C.equals((String)(dataMapOld.get("release_status")))){
				if (dataMapOld.get("item_version") != null
						&& !"".equals(dataMapOld.get("item_version"))) {
					item_version = (BigDecimal) dataMapOld.get("item_version");
					item_version = item_version.add(new BigDecimal(1));
				}
			}
			dataMapOld.put("uni_key", newUniKey);//创建了新的uni_key
			dataMapOld.put("create_by", userNo);
			dataMapOld.put("create_time", new Date());
			dataMapOld.put("delete_flg", "0");
			dataMapOld.put("update_count", update_count + "");
			dataMapOld.put("release_status", RELEASE_STATE_D);// c现在,h历史,a待发布,r驳回,d待审批
			if (dataMapOld.get("item_version") != null
					&& !"".equals(dataMapOld.get("item_version"))) {
				item_version = (BigDecimal) dataMapOld.get("item_version");
				item_version = item_version.add(new BigDecimal(1));
			}
			dataMapOld.put("item_version", item_version + "");
			// insert
			dataMapOld.put("opt_status", OPT_STATUS_D);//这句有问题
			// update
			Map<String, Object> dataMapForUpdate = new HashMap<String, Object>();
			dataMapForUpdate.put("uni_key", uniKey);
			dataMapForUpdate.put("release_status", RELEASE_STATE_H);
			dataMapForUpdate.put("last_update_by", userNo);
			dataMapForUpdate.put("last_update_time", new Date());
			//新增同原数据一样 的数据并设置opt_status=d release_status=d
			status = termItemDao.executeAdd(termItemDtoList, dataMapOld);
			//更新原数据 并设置opt_status=d release_status=d
			int status1 = termItemDao.executeUpdate(termItemDtoList,
					dataMapForUpdate);
			if (status1 == 0) {
				status = 0;
			}
			if("0".equals(title)){//如果title为0，为术语编辑的操作
				logUtils.insertLog("00605", "00605002", "删除术语项目[{}]", termItemDtoList.get(0).getDict_name());
			}else{//如果title为1，为术语审批模块的操作
				logUtils.insertLog("00607", "00607004", "审批-删除术语项目[{}]", termItemDtoList.get(0).getDict_name());
			}
		}
		getAndUpdateReleaseStatusCount(tableName, dictId,
				userNo);
		return status;
	}

	/**
	 * 为一览画面查询对应字段的数据
	 * @param tableName 表名
	 * @param dictItemList 字段
	 * @param release_status 数据状态
	 * @param mapSearch 查询条件
	 * @param options
	 * @return
	 */
	@Override
	@Transactional
	public List<Map<String, Object>> getDictDataForSearchList(String tableName,
			List<String> dictItemList, List<String> releaseStatus,
			Map<String,Object> mapSearchValue,Map<String,Object> mapSearchType, SelectOptions options, String export) {
		return termItemDao.selectDictDataListForSearch(tableName, dictItemList, releaseStatus, mapSearchValue,mapSearchType, options, export);
	}
	
	/**
	 * 唯一性校验
	 * @param dictId
	 * @param primaryKeys
	 * @return 如果不唯一返回false， 如果唯一返回true
	 */
	@Override
	@Transactional
	public boolean checkUniPrimaryKey(String dictId, String primaryKeys) {
		boolean status = true;
		if(StringUtils.isEmpty(primaryKeys)){
			return status;
		}
		int count = termItemDao.checkUniPrimaryKey(dictId, primaryKeys);
		if(count!=0){
			status=false;
		}
		return status;
	}

	@Override
	public DictMain selectDictMainByDictId(String dictId) {
		return termItemDao.selectDictMainByDictId(dictId);
	}

	@Override
	public int selectFilterOrderByViewIdAndFieldId(String viewId, String fieldId) {
		return termItemDao.selectFilterOrderByViewIdAndFieldId(viewId,fieldId);
	}

}
