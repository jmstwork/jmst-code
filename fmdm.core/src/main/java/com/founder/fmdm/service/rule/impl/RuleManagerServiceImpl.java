package com.founder.fmdm.service.rule.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.common.CommonUtil;
import com.founder.fmdm.dao.log.FindRightName4PropertiesFile;
import com.founder.fmdm.dao.rule.GroupManagerDao;
import com.founder.fmdm.dao.rule.RlmgModelDao;
import com.founder.fmdm.dao.rule.RuleManagerDao;
import com.founder.fmdm.entity.DictDepartment;
import com.founder.fmdm.entity.IamLog;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.service.rule.RuleManagerService;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.rule.dto.RuleDto;
import com.founder.fmdm.service.rule.dto.RuleListDto;
import com.founder.fmdm.service.sysmgt.ConstantsDef;
import com.founder.fmdm.service.sysmgt.WarningSettingService;

@Service
public class RuleManagerServiceImpl implements RuleManagerService {

	private static final int List = 0;

	@Autowired
	RuleManagerDao ruleManagerDao;
	
	//@Autowired
	//WarningSettingService warningSettingService;

	@Autowired
	GroupManagerDao groupManagerDao;

	@Autowired
	RlmgModelDao rlmgModelDao;
	
	@Autowired
	LogUtils logUtils;

	Properties props = new Properties();

	private Map<String, String> findRightNameMap;

	/*
	 * @Autowired private EntityDao entityDao;
	 */

	public int checkExistRule(String ruleName, String ruleId) {
		List<String> list = ruleManagerDao.selectExistRule(ruleName, ruleId);
		if (list.isEmpty()) {
			return 0;
		} else {
			return 1;
		}
	}

	public List<RuleListDto> selectReleaseRules(RuleListDto ruleListDto, SelectOptions options) {
		String ruleNameValue = ruleListDto.getCondRuleName() == null ? "" : ruleListDto.getCondRuleName();
		String ruleName = ruleNameValue.trim();
		String rulegroupNameValue = ruleListDto.getCondRuleGroName() == null ? "" : ruleListDto.getCondRuleGroName();
		String rulegroupName = rulegroupNameValue.trim();
		String modelNameValue = ruleListDto.getCondModelName() == null ? "" : ruleListDto.getCondModelName();
		String modelName = modelNameValue.trim();
		String StatusValue = ruleListDto.getCondStatus() == null || "-1".equals(ruleListDto.getCondStatus()) ? ""
				: ruleListDto.getCondStatus();
		String Status = StatusValue.trim();
		return ruleManagerDao.selectReleaseRules(ruleName, rulegroupName, modelName, Status, options);
	}

	

	public RuleManagerServiceImpl() {
		findRightNameMap = FindRightName4PropertiesFile.CONSTANTS_4RIGHTNAME_MAP;

		InputStream ips = null;
		try {
			String filePath = this.getClass().getResource("/").getPath();
			ips = new BufferedInputStream(new FileInputStream(filePath + "ApplicationResources_zh_CN.properties"));
			props.load(ips);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<RuleListDto> selectRules(RuleListDto ruleListDto, String orderBy, SelectOptions options) {
		String ruleNameValue = ruleListDto.getCondRuleName() == null ? "" : ruleListDto.getCondRuleName();
		String ruleName = ruleNameValue.trim();
		String rulegroupNameValue = ruleListDto.getCondRuleGroName() == null ? "" : ruleListDto.getCondRuleGroName();
		String rulegroupName = rulegroupNameValue.trim();
		String modelNameValue = ruleListDto.getCondModelName() == null ? "" : ruleListDto.getCondModelName();
		String modelName = modelNameValue.trim();
		String StatusValue = ruleListDto.getCondStatus() == null ? "" : ruleListDto.getCondStatus();
		String Status = StatusValue.trim();

		return ruleManagerDao.selectRules(ruleName, rulegroupName, modelName, Status, orderBy, options);
	}

	public int deleteRule(String ruleId) {
		try {
			// 删除数据
			List<RlmgRuleData> ruleDataList = ruleManagerDao.selectRuleDataByIdForCreatRule(ruleId);
			for (RlmgRuleData ruleData : ruleDataList) {
				RlmgRuleData entity = (RlmgRuleData) ruleManagerDao.selectById(ruleData.getDataId());
				entity.setDeleteFlg(1);
				entity.setDeleteTime(new Date());
				ruleManagerDao.updateRlmgRuleData(entity);
			}
			// 删除规则
			RlmgRule rlmgRule = (RlmgRule) ruleManagerDao.selectRlmgRuleById(ruleId);
			rlmgRule.setDeleteFlg(1);
			ruleManagerDao.updateRlmgRule(rlmgRule);
			logUtils.insertLog("00302", "00403004", "删除规则[{}]", rlmgRule.getRuleName());
		} catch (Exception e) {
			e.printStackTrace();
			return ConstantsDef.OPERATION_FAILURE;
		}
		return ConstantsDef.OPERATION_SUCCESS;
	}

	public List<RlmgRuleDetail> selectById(String ruleId) {
		List<RlmgRuleDetail> rlmgRuleDetailList = (List<RlmgRuleDetail>) ruleManagerDao.selectRuleDetailById(ruleId);
		return rlmgRuleDetailList;
	}

	public Map<String, Object>[] selectRuleDataById(String ruleId, String dataValue, SelectOptions options) {

		List<RlmgRuleData> rlmgRuleDataList = (List<RlmgRuleData>) ruleManagerDao.selectRuleDataById(ruleId, dataValue,
				options);

		// RlmgRuleData 有dataTowNo，dataItemId,dataValue三个值
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
		int rowNo = 0;
		int i = 0;
		for (RlmgRuleData rlmgRuleData : rlmgRuleDataList) {
			if (i == 0) {
				rowNo = rlmgRuleData.getDataRowNo();
			}
			if (rowNo == rlmgRuleData.getDataRowNo()) {
				tempMap.put(rlmgRuleData.getRuleItemId(), rlmgRuleData);
				i++;
				continue;
			} else {
				// ruleDataMap.put(rowNo,tempMap);
				dataList.add(tempMap);
				rowNo = rlmgRuleData.getDataRowNo();
				tempMap = new LinkedHashMap<String, Object>();
				tempMap.put(rlmgRuleData.getRuleItemId(), rlmgRuleData);
			}
			i++;
		}
		if (tempMap.size() != 0) {
			dataList.add(tempMap);
		}
		// ruleDataMap.put(rowNo,tempMap);
		return dataList.toArray(new Map[0]);
	}

	/**
	 * 编辑规则
	 * 
	 * @param ruleDto
	 */
	@Transactional
	public void updateRule(RuleDto ruleDto) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		// 编辑规则
		String ruleId = null;
				//ruleDto.getRuleId();
		if(null != ruleDto.getCreateType() && "copy".equals(ruleDto.getCreateType())){
			ruleId = CommonUtil.getUUID();
		}else{
			ruleId = ruleDto.getRuleId();
		}
		if (ruleId != null && !"".equals(ruleId)) {

			RlmgRule rule = (RlmgRule) ruleManagerDao.selectRlmgRuleById(ruleId);
			if(null == rule){
				rule = new RlmgRule();
				rule.setRuleId(ruleId);
			}
			if(StringUtils.isNotEmpty(ruleDto.getRuleName()) && !"undefined".equalsIgnoreCase(ruleDto.getRuleName())){
				rule.setRuleName(ruleDto.getRuleName());
			}
			if(StringUtils.isNotEmpty(ruleDto.getRulegroupId())  && !"undefined".equalsIgnoreCase(ruleDto.getRulegroupId())){
				rule.setRulegroupId(ruleDto.getRulegroupId());
			}
			if(StringUtils.isNotEmpty(ruleDto.getUnitId()) && !"undefined".equalsIgnoreCase(ruleDto.getUnitId())){
				rule.setUnitId(ruleDto.getUnitId());
			}
			rule.setStatus(0);
			if(StringUtils.isNotEmpty(ruleDto.getModelId()) && !"undefined".equalsIgnoreCase(ruleDto.getModelId())){
				rule.setModelId(ruleDto.getModelId());
			}
			if(!"undefined".equalsIgnoreCase(ruleDto.getMemo())){
				rule.setMemo(ruleDto.getMemo());
			}
			rule.setLastUpdateBy(userName);
			rule.setLastUpdateTime(new Date());
			if("copy".equals(ruleDto.getCreateType())){
				ruleManagerDao.insertRlmgRule(rule);
			}else{
				ruleManagerDao.updateRlmgRule(rule);
			}
			
			/**
			 * 更新规则数据
			 * */			
			List<RlmgRuleData> deleteRlmgRuleDetailList =  ruleManagerDao.selectDataOfEditRule(ruleId,null);
			for(int i = 0; i < deleteRlmgRuleDetailList.size();i++){
				ruleManagerDao.deleteRlmgRuleData(deleteRlmgRuleDetailList.get(i));
			}
			List<RlmgRuleDetail> detailList = ruleManagerDao.selectRuleDetailById(ruleId);
			for(int i = 0; i < detailList.size(); i++){
				ruleManagerDao.deleteRlmgRuleDetail(detailList.get(i));
			}
			
			String titlesStr = ruleDto.getTitles();
			String exeStr = ruleDto.getExe();
			String dataStr = ruleDto.getData();
			
			String[] titleArray = titlesStr.split("#", -1);
			String[] exeArray = exeStr.split("#", -1);
			String[] dataArray = dataStr.split("#", -1);
			
			if (titleArray.length == exeArray.length) {
				RlmgRuleDetail itemDesc = null;
				for (int i = 0; i < titleArray.length; i++) {
					if (i == 0) {
						// 添加描述列
						itemDesc = new RlmgRuleDetail();
						itemDesc.setRuleItemId(CommonUtil.getUUID());
						itemDesc.setRuleId(rule.getRuleId());
						itemDesc.setRuleItemNo(i);
						itemDesc.setRuleItemName("描述");
						itemDesc.setEditType(0);
						itemDesc.setItemType(0);
						// itemDesc.setItemExpression("");
						// itemDesc.setDefaultValue((itemExeArray[1]!=null&&itemExeArray[1]!="")?itemExeArray[1].toString():"");
						itemDesc.setCreateBy(userName);
						itemDesc.setCreateTime(new Date());
						ruleManagerDao.insertRlmgRuleDetail(itemDesc);

						String[] descArray = dataArray[i].split(":", -1);
						for (int j = 0; j < descArray.length; j++) {
							RlmgRuleData ruleData = new RlmgRuleData();
							ruleData.setDataId(CommonUtil.getUUID());
							ruleData.setRuleId(rule.getRuleId());
							ruleData.setDataRowNo(j);
							ruleData.setRuleItemId(itemDesc.getRuleItemId());
							ruleData.setDataValue((descArray[j] != null && descArray[j] != "") ? descArray[j].trim() : "");
							ruleData.setCreateBy(userName);
							ruleData.setCreateTime(new Date());
							ruleManagerDao.insertRlmgRuleData(ruleData);
						}
					}
					RlmgRuleDetail item = new RlmgRuleDetail();
					String[] itemArray = titleArray[i].split(":", -1);
					String[] itemExeArray = exeArray[i].split(":", -1);
					item.setRuleItemId(CommonUtil.getUUID());
					item.setRuleItemNo((i + 1));
					item.setRuleId(rule.getRuleId());
					item.setRuleItemName(
							(itemArray[0] != null && itemArray[0] != "") ? itemArray[0].toString().trim() : "");
					item.setEditType(
							(itemArray[1] != null && itemArray[1] != "") ? Integer.valueOf(itemArray[1].toString()) : 1);
					item.setItemType(
							(itemArray[2] != null && itemArray[2] != "") ? Integer.valueOf(itemArray[2].toString()) : 1);
					item.setItemExpression(
							(itemExeArray[0] != null && itemExeArray[0] != "") ? itemExeArray[0].trim() : "");
					item.setDefaultValue(
							(itemExeArray[1] != null && itemExeArray[1] != "") ? itemExeArray[1].toString() : "");
					item.setCreateBy(userName);
					item.setCreateTime(new Date());

					ruleManagerDao.insertRlmgRuleDetail(item);

					String[] colsArray = dataArray[i + 1].split(":", -1);
					for (int j = 0; j < colsArray.length; j++) {
						RlmgRuleData ruleData = new RlmgRuleData();
						ruleData.setDataId(CommonUtil.getUUID());
						ruleData.setRuleId(rule.getRuleId());
						ruleData.setDataRowNo(j);
						ruleData.setRuleItemId(item.getRuleItemId());
						ruleData.setDataValue((colsArray[j] != null && colsArray[j] != "") ? colsArray[j].trim() : "");
						ruleData.setCreateBy(userName);
						ruleData.setCreateTime(new Date());
						ruleManagerDao.insertRlmgRuleData(ruleData);
					}
				}
				logUtils.insertLog("00302", "00403003", "编辑规则[{}]", rule.getRuleName());
			}
			
			/**
			 * end
			 * */
		}

			
	}

	public List<RlmgRuleDto> selectExamApproves(String ruleName, String status, String orderBy, SelectOptions options) {
		return ruleManagerDao.selectExamApproves(ruleName, status, orderBy, options);
	}

	public RlmgRule selectRuleByRuleId(String ruleId) {

		return (RlmgRule) ruleManagerDao.selectRlmgRuleById(ruleId);
	}

	/***
	 * 新建和编辑规则
	 */
	@Transactional
	public void insertRule(RuleDto ruleDto) {
		String ruleName = ruleDto.getRuleName();
		String ruleGroupId = ruleDto.getRulegroupId();
		String deptId = ruleDto.getUnitId();

		String modelId = ruleDto.getModelId();
		String memo = ruleDto.getMemo();

		String titles = ruleDto.getTitles();
		String exe = ruleDto.getExe();
		String data = ruleDto.getData();

		RlmgRule rule = new RlmgRule();
		rule.setRuleId(CommonUtil.getUUID());
		rule.setRuleName((ruleName != null && ruleName != "") ? ruleName : "");
		rule.setRulegroupId(ruleGroupId);
		rule.setUnitId(deptId);
		rule.setModelId(modelId);
		rule.setMemo(memo);
		rule.setStatus(0);
		rule.setDeleteFlg(0);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		rule.setCreateBy(userName);
		rule.setCreateTime(new Date());
		rule.setLastUpdateTime(new Date());
		rule.setUpdateCount(0);
		ruleManagerDao.insertRlmgRule(rule);

		String[] titleArray = titles.split("#", -1);
		String[] exeArray = exe.split("#", -1);
		String[] dataArray = data.split("#", -1);
		if (titleArray.length == exeArray.length) {
			RlmgRuleDetail itemDesc = null;
			for (int i = 0; i < titleArray.length; i++) {
				if (i == 0) {
					// 添加描述列
					itemDesc = new RlmgRuleDetail();
					itemDesc.setRuleItemId(CommonUtil.getUUID());
					itemDesc.setRuleId(rule.getRuleId());
					itemDesc.setRuleItemNo(i);
					itemDesc.setRuleItemName("描述");
					itemDesc.setEditType(0);
					itemDesc.setItemType(0);
					// itemDesc.setItemExpression("");
					// itemDesc.setDefaultValue((itemExeArray[1]!=null&&itemExeArray[1]!="")?itemExeArray[1].toString():"");
					itemDesc.setCreateBy(userName);
					itemDesc.setCreateTime(new Date());
					ruleManagerDao.insertRlmgRuleDetail(itemDesc);

					String[] descArray = dataArray[i].split(":", -1);
					for (int j = 0; j < descArray.length; j++) {
						RlmgRuleData ruleData = new RlmgRuleData();
						ruleData.setDataId(CommonUtil.getUUID());
						ruleData.setRuleId(rule.getRuleId());
						ruleData.setDataRowNo(j);
						ruleData.setRuleItemId(itemDesc.getRuleItemId());
						ruleData.setDataValue((descArray[j] != null && descArray[j] != "") ? descArray[j].trim() : "");
						ruleData.setCreateBy(userName);
						ruleData.setCreateTime(new Date());
						ruleManagerDao.insertRlmgRuleData(ruleData);
					}
				}
				RlmgRuleDetail item = new RlmgRuleDetail();
				String[] itemArray = titleArray[i].split(":", -1);
				String[] itemExeArray = exeArray[i].split(":", -1);
				item.setRuleItemId(CommonUtil.getUUID());
				item.setRuleItemNo((i + 1));
				item.setRuleId(rule.getRuleId());
				item.setRuleItemName(
						(itemArray[0] != null && itemArray[0] != "") ? itemArray[0].toString().trim() : "");
				item.setEditType(
						(itemArray[1] != null && itemArray[1] != "") ? Integer.valueOf(itemArray[1].toString()) : 1);
				item.setItemType(
						(itemArray[2] != null && itemArray[2] != "") ? Integer.valueOf(itemArray[2].toString()) : 1);
				item.setItemExpression(
						(itemExeArray[0] != null && itemExeArray[0] != "") ? itemExeArray[0].trim() : "");
				item.setDefaultValue(
						(itemExeArray[1] != null && itemExeArray[1] != "") ? itemExeArray[1].toString() : "");
				item.setCreateBy(userName);
				item.setCreateTime(new Date());

				ruleManagerDao.insertRlmgRuleDetail(item);

				String[] colsArray = dataArray[i + 1].split(":", -1);
				for (int j = 0; j < colsArray.length; j++) {
					RlmgRuleData ruleData = new RlmgRuleData();
					ruleData.setDataId(CommonUtil.getUUID());
					ruleData.setRuleId(rule.getRuleId());
					ruleData.setDataRowNo(j);
					ruleData.setRuleItemId(item.getRuleItemId());
					ruleData.setDataValue((colsArray[j] != null && colsArray[j] != "") ? colsArray[j].trim() : "");
					ruleData.setCreateBy(userName);
					ruleData.setCreateTime(new Date());
					ruleManagerDao.insertRlmgRuleData(ruleData);
				}
			}
		}
	}

	/**
	 * 初始化规则数据
	 * 
	 * @param ruleId
	 * @return
	 */
	public Map<Integer, Object> selectRuleDataByRuleId(String ruleId, String description) {

		List<RlmgRuleData> dataList = (List<RlmgRuleData>) ruleManagerDao.selectDataOfEditRule(ruleId, description);

		Map<Integer, Object> ruleDataMap = new LinkedHashMap<Integer, Object>();
		// List<Map<String,Object>> dataList = new
		// ArrayList<Map<String,Object>>();
		Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
		int rowNo = 0;
		int i = 0;
		for (RlmgRuleData rlmgRuleData : dataList) {
			if (i == 0) {
				rowNo = rlmgRuleData.getDataRowNo();
			}
			if (rowNo == rlmgRuleData.getDataRowNo()) {
				tempMap.put(rlmgRuleData.getRuleItemId(), rlmgRuleData);
				i++;
				continue;
			} else {
				ruleDataMap.put(rowNo, tempMap);
				// dataList.add(tempMap);
				rowNo = rlmgRuleData.getDataRowNo();
				tempMap = new LinkedHashMap<String, Object>();
				tempMap.put(rlmgRuleData.getRuleItemId(), rlmgRuleData);
			}
			i++;
		}
		if (tempMap.size() != 0) {
			// dataList.add(tempMap);
			ruleDataMap.put(rowNo, tempMap);
		}
		tempMap = null;
		return ruleDataMap;
	}

	public List<RuleListDto> selectOneRuleData(String ruleId) {
		return ruleManagerDao.selectOneRuleData(ruleId);
	}

	public List<RlmgRuleData> selectRowNoByValue(String dataValue) {
		return ruleManagerDao.selectRowNoByValue(dataValue);
	}

	// 处理增删改数据
	public int disposeData(String updateIds, String deleteIds, String addIds, String fieldSize) {
		try {
			StringBuffer rowDataAdd = new StringBuffer();
			StringBuffer rowDataEdit = new StringBuffer();
			StringBuffer rowDataDelete = new StringBuffer();
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			IamLog iLog = new IamLog();
			iLog.setLogId(String.valueOf(UUID.randomUUID()));
			iLog.setCreateBy(auth.getName());
			iLog.setCreateTime(new Date());
			iLog.setOperorId(auth.getName());
			iLog.setOperDt(new Date());
			iLog.setUpdateCount(0);
			iLog.setOperModu("00403");
			iLog.setOperObj("00403003");
			StringBuffer operCont = new StringBuffer();
			if ("" != updateIds && null != updateIds) {
				rowDataEdit = this.updateData(updateIds, rowDataEdit);
			}
			if ("" != deleteIds && null != deleteIds) {
				rowDataDelete = this.deleteData(deleteIds, fieldSize, rowDataDelete);
			}
			if ("" != addIds && null != addIds) {
				rowDataAdd = this.insertData(addIds, rowDataAdd, fieldSize);
			}
			if (rowDataAdd.length() > 0) {
				operCont.append(" 添加数据记录:[");
				operCont.append(rowDataAdd.substring(0, rowDataAdd.length() - 1));
				operCont.append("]");
			}
			if (rowDataEdit.length() > 0) {
				operCont.append(" 编辑数据记录:[");
				operCont.append(rowDataEdit.substring(0, rowDataEdit.length() - 1));
				operCont.append("]");
			}
			if (rowDataDelete.length() > 0) {
				operCont.append(" 删除数据记录:[");
				operCont.append(rowDataDelete.substring(0, rowDataDelete.length() - 1));
				operCont.append("]");
			}
			if (operCont.length() > 0) {
				iLog.setOperCont("规则数据编辑，编辑内容包括：" + operCont.toString());
				ruleManagerDao.insertIamLog(iLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ConstantsDef.OPERATION_FAILURE;
		}
		return ConstantsDef.OPERATION_SUCCESS;

	}

	public StringBuffer updateData(String updateIds, StringBuffer rowDataEdit) {

		try {
			Map<String, String> ruleDataMap = new HashMap<String, String>();
			String[] updateDatas = updateIds.split("@");
			for (int i = 0; i < updateDatas.length; i++) {
				String[] updateData = updateDatas[i].split(";", -1);
				ruleDataMap.put(updateData[0], updateData[1]);
				RlmgRuleData entity = (RlmgRuleData) ruleManagerDao.selectById(updateData[0]);
				// log记录——>修改数据记录，定位到具体的行和列。
				String dataValue = entity.getDataValue() == null ? "空" : entity.getDataValue();
				if (!dataValue.equalsIgnoreCase(ruleDataMap.get(updateData[1]))) {
					rowDataEdit.append("第" + updateData[2] + "行" + updateData[3] + "列:\"" + entity.getDataValue()
							+ "\"为\"" + updateData[1] + "\",");
				}
				entity.setDataValue(updateData[1]);
				ruleManagerDao.updateRlmgRuleData(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDataEdit;
	}

	public StringBuffer deleteData(String deleteIds, String fieldSize, StringBuffer rowDataDelete) {
		try {
			String[] updateDatas = deleteIds.split("@");
			for (int i = 0; i < updateDatas.length; i++) {
				String dataId = updateDatas[i];
				RlmgRuleData rlmgRuleData = (RlmgRuleData) ruleManagerDao.selectById(dataId);
				rlmgRuleData.setDeleteFlg(1);
				ruleManagerDao.updateRlmgRuleData(rlmgRuleData);
				// log日志——>记录删除的数据记录
				if (i % (Integer.parseInt(fieldSize) + 1) == 0) {
					rowDataDelete.append(rlmgRuleData.getDataValue());
					rowDataDelete.append(",");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDataDelete;
	}

	public StringBuffer insertData(String addIds, StringBuffer rowDataAdd, String fieldSize) {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			String[] addDatas = addIds.split("@");
			String ruleId = addDatas[0].split(";")[0];
			for (int i = 0; i < addDatas.length; i++) {
				String[] data = addDatas[i].split(";", -1);
				int rowNo = ruleManagerDao.selectMaxDataRowNo(data[1], ruleId);
				RlmgRuleData ruleData = new RlmgRuleData();
				ruleData.setDataId(CommonUtil.getUUID());
				ruleData.setRuleId(ruleId);
				ruleData.setDataRowNo(rowNo + 1);
				ruleData.setRuleItemId(data[1]);
				ruleData.setDataValue(data[2]);
				ruleData.setCreateBy(userName);
				ruleData.setCreateTime(new Date());
				ruleManagerDao.insertRlmgRuleData(ruleData);
				// 记录log日志
				if (i % (Integer.parseInt(fieldSize) + 1) == 0) {
					rowDataAdd.append(data[2]);
					rowDataAdd.append(",");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDataAdd;
	}

	public int updateApproveRule(String ruleIds) {
		try {
			String[] ruleId = ruleIds.split(",");
			for (int i = 0; i < ruleId.length; i++) {
				SecurityContext ctx = SecurityContextHolder.getContext();
				Authentication auth = ctx.getAuthentication();
				RlmgRule entity = (RlmgRule) ruleManagerDao.selectRlmgRuleById(ruleId[i]);
				entity.setStatus(1);
				entity.setApplyBy(auth.getName());
				entity.setApplyTime(new Date());
				ruleManagerDao.updateRlmgRule(entity);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ConstantsDef.OPERATION_FAILURE;
		}
		return ConstantsDef.OPERATION_SUCCESS;
	}

	public int updateApplyRule(String ruleId) {
		try {
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			RlmgRule entity = (RlmgRule) ruleManagerDao.selectRlmgRuleById(ruleId);
			entity.setStatus(0);
			entity.setApplyBy(auth.getName());
			entity.setApplyTime(new Date());
			ruleManagerDao.updateRlmgRule(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ConstantsDef.OPERATION_FAILURE;
		}
		return ConstantsDef.OPERATION_SUCCESS;
	}

	@Override
	public Map<Integer, Object> selectRuleDataByIdForCreatRule(String ruleId) {

		List<RlmgRuleData> rlmgRuleDataList = (List<RlmgRuleData>) ruleManagerDao
				.selectRuleDataByIdForCreatRule(ruleId);
		// RlmgRuleData 有dataTowNo，dataItemId,dataValue三个值
		Map<Integer, Object> ruleDataMap = new HashMap<Integer, Object>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		int rowNo = 0;
		int i = 0;
		for (RlmgRuleData rlmgRuleData : rlmgRuleDataList) {
			if (i == 0) {
				rowNo = rlmgRuleData.getDataRowNo();
			}
			if (rowNo == rlmgRuleData.getDataRowNo()) {
				tempMap.put(rlmgRuleData.getRuleItemId(), rlmgRuleData);
				i++;
				continue;
			} else {
				ruleDataMap.put(rowNo, tempMap);
				rowNo = rlmgRuleData.getDataRowNo();
				tempMap = new HashMap<String, Object>();
				tempMap.put(rlmgRuleData.getRuleItemId(), rlmgRuleData);
			}
			i++;
		}
		ruleDataMap.put(rowNo, tempMap);
		return ruleDataMap;
	}

	// 规则状态
	public List<SystemCode> selectRuleStatus() {
		List<SystemCode> list = ruleManagerDao.selectSystemCodeByCategoryCd("C001");
		return list;
	}

	// 科室控件，根据科室名称查询是否存在科室code
	public List<DictDepartment> selectDeptCodeByDeptName(String deptName) {
		return ruleManagerDao.selectDeptCodeByDeptName(deptName);
	}

	// 提交前验证，只有未提交状态，才能进行规则审批！
	public int checkStatus(String ruleIds) {
		int resultSet = 0;
		String[] ruleId = ruleIds.split(",", -1);
		for (int i = 0; i < ruleId.length; i++) {
			RlmgRule rule = new RlmgRule();
			rule = (RlmgRule) rlmgModelDao.selectRuleByRuleId(ruleId[i]);
			if (rule.getStatus() == 1 || rule.getStatus() == 2 || rule.getStatus() == 3 || rule.getStatus() == 4) {
				resultSet = 1;
			}
		}
		return resultSet;
	}

	// 提交审批
	public int approveRules(String ruleIds, String name) {
		try {
			String[] ruleId = ruleIds.split(",", -1);
			Date CurTime = new Date();
			for (int i = 0; i < ruleId.length; i++) {
				RlmgRule rule = (RlmgRule) rlmgModelDao.selectRuleByRuleId(ruleId[i]);
				rule.setStatus(1);
				rule.setApplyBy(name);
				rule.setApplyTime(CurTime);
				rule.setLastUpdateBy(name);
				rule.setLastUpdateTime(CurTime);
				rlmgModelDao.update(rule);
				logUtils.insertLog("00302", "00404001", "提交审批[{}]", rule.getRuleName());
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	public ArrayList<String> splitString(String str, String str1, String str2){
		ArrayList<String> aaa = new ArrayList<String>();
		String[] strLeng = str.split(str1);
		//int length = strLeng.length;
		/*for(int i = 0; i < strLeng[0].split(str2).length;i++){
			for(int j =0; j< length; j++){
				aaa.add(strLeng[j].split(str2)[i]);
			}
		}*/
		for(int i = 0; i < strLeng.length;i++){
			String[] colsArray = strLeng[i].split(str2);
			for(int j = 0; j < colsArray.length;j++){
				aaa.add(colsArray[j].trim());
			}
		}
		return aaa;
	}

}
