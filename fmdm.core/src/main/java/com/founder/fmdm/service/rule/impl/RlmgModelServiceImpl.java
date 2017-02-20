package com.founder.fmdm.service.rule.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.Constants;
import com.founder.fmdm.dao.rule.RlmgModelDao;
import com.founder.fmdm.entity.RlmgModelDetail;
import com.founder.fmdm.entity.RlmgModelType;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.RlmgRuleVersion;
import com.founder.fmdm.service.rule.CreateDrl;
//import com.founder.fmdm.log.IamLog;
import com.founder.fmdm.service.rule.RlmgModelService;
import com.founder.fmdm.service.rule.dto.RlmgModelInfoDto;
import com.founder.fmdm.service.rule.dto.RlmgRuleDataDto;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.rule.dto.RlmgVersionDto;

@Service
public class RlmgModelServiceImpl implements RlmgModelService
{
    @Autowired
    RlmgModelDao rlmgModelDao;
    @Autowired
    CreateDrl createDrl;
    @Autowired
	LogUtils logUtils;
    
    public List<RlmgModelType> selectInitModels()
    {
        List<RlmgModelType> list = rlmgModelDao.selectInitModels();
        return list;
    }

    public List<RlmgModelDetail> selectInitItems(String modelId)
    {
        List<RlmgModelDetail> list = rlmgModelDao.selectInitItems(modelId);
        return list;
    }
    /***
     * 保存模型信息
     */
    @Transactional
    public int saveModel(RlmgModelInfoDto rlmgModelInfoDto, String opt)
    {
        try
        {
            RlmgModelType rlmgModelType = new RlmgModelType();
            if (Constants.OPERATION_ADD.equalsIgnoreCase(opt))
            {
                RlmgModelDetail rlmgModelDetail = new RlmgModelDetail();
            	rlmgModelType.setModelName(rlmgModelInfoDto.getModelName());
                rlmgModelType.setModelEnName(rlmgModelInfoDto.getModelEnName());
                String id = UUID.randomUUID().toString();
                // 插入rlmg_model_type表
                rlmgModelType.setModelId(id);
                rlmgModelType.setCreateTime(new Date());
                rlmgModelType.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
                rlmgModelType.setUpdateCount(0);
                rlmgModelDao.insert(rlmgModelType);
                // 插入rlmg_model_detail
                String[] fieldNameArr = rlmgModelInfoDto.getFieldName().split(
                        ",");
                for (int i = 0; i < fieldNameArr.length; i++)
                {
                    rlmgModelDetail.setFieldId(UUID.randomUUID().toString());
                    rlmgModelDetail.setModelId(id);
                    rlmgModelDetail.setFieldName(fieldNameArr[i]);
                    rlmgModelDetail.setFieldEnName(rlmgModelInfoDto.getFieldEnName().split(
                            ",")[i]);
                    rlmgModelDetail.setFieldType(rlmgModelInfoDto.getFieldType().split(
                            ",")[i]);
                    rlmgModelDetail.setCreateTime(new Date());
                    rlmgModelDao.insert(rlmgModelDetail);
                }
            }else if(Constants.OPERATION_EDIT.equalsIgnoreCase(opt)){
            	
            	rlmgModelType = (RlmgModelType) rlmgModelDao.selectModelById(rlmgModelInfoDto.getModelId());
                rlmgModelType.setModelName(rlmgModelInfoDto.getModelName());
                rlmgModelType.setModelEnName(rlmgModelInfoDto.getModelEnName());
                rlmgModelType.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
                rlmgModelType.setLastUpdateTime(new Date());
                //根据ID更新RlmgModelType表中数据
                rlmgModelDao.update(rlmgModelType);
                // 查询modelId所对应的数据表中所有fieldId，存入map，用于对字段的删除操作
                List<RlmgModelDetail> list = rlmgModelDao.selectRlmgModelDetailByFk(rlmgModelInfoDto.getModelId());

                //页面获得数据 
                String[] fieldIdArr = rlmgModelInfoDto.getFieldId().split(",");
                String[] fieldNameArr = rlmgModelInfoDto.getFieldName().split(",");
                String[] fieldEnNameArr = rlmgModelInfoDto.getFieldEnName().split(",");
                String[] fieldTypeArr = rlmgModelInfoDto.getFieldType().split(",");
                //add
                if(fieldNameArr.length > 0){
                	for(int i=0;i<fieldNameArr.length;i++){
                		try{
                			String fieldId = fieldIdArr[i];
                		}catch(ArrayIndexOutOfBoundsException ab){
                			RlmgModelDetail rlmgModelDetail = new RlmgModelDetail();
               			 	rlmgModelDetail.setFieldId(UUID.randomUUID().toString());
                            rlmgModelDetail.setModelId(rlmgModelInfoDto.getModelId());
                            rlmgModelDetail.setFieldName(fieldNameArr[i]);
                            rlmgModelDetail.setFieldEnName(fieldEnNameArr[i]);
                            rlmgModelDetail.setFieldType(fieldTypeArr[i]);
                            rlmgModelDetail.setCreateTime(new Date());
                            rlmgModelDao.insert(rlmgModelDetail);
                		}
                	}
                }
                if(list.size() > 0){
                	for(int i=0;i<list.size();i++){
                		RlmgModelDetail rmlgd = list.get(i);
                		//del
                		if(!Arrays.asList(fieldIdArr).contains(rmlgd.getFieldId())){
                			rmlgd.setDeleteFlg(1);
                			rlmgModelDao.update(rmlgd);
                		}
                		//update
                		if(Arrays.asList(fieldIdArr).contains(rmlgd.getFieldId())){
                			for(int j=0;j<fieldIdArr.length;j++){
                				if(rmlgd.getFieldId().equals(fieldIdArr[j])){
                                	rmlgd.setFieldName(fieldNameArr[j]);
                                	rmlgd.setFieldEnName(fieldEnNameArr[j]);
                                	rmlgd.setFieldType(fieldTypeArr[j]);
                            		rmlgd.setLastUpdateTime(new Date());
                            		rlmgModelDao.update(rmlgd);
                				}
                            }
                		}
                	}
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Constants.OPERATION_FAILURE;
        }
        return Constants.OPERATION_SUCCESS;
    }
	/**
     * 模型列表
     */
	@Override
	public List<RlmgModelType> selectModels(String modelName,SelectOptions options) {
		// TODO Auto-generated method stub
		List<RlmgModelType> rlmgModelType= rlmgModelDao.selectModels(modelName,options);
		return rlmgModelType;
	}
	@Override
	public List<RlmgRuleDto> selectRules(String ruleName, String status, SelectOptions options) {
		// TODO Auto-generated method stub
		List<RlmgRuleDto> rlmgRule= rlmgModelDao.selectRules(ruleName,status,options);
		return rlmgRule;
	}
	
	@Override
	public List<RlmgVersionDto> selectVersions(String versionNo, SelectOptions options) {
		// TODO Auto-generated method stub
		List<RlmgVersionDto> rlmgVersion= rlmgModelDao.selectVersions(versionNo,options);
		return rlmgVersion;
	}
	
	@Override
    public RlmgRule selectRuleByRuleId(String ruleId)
    {
        return (RlmgRule) rlmgModelDao.selectRuleByRuleId( ruleId);
    }
    @Override
    public List<RlmgRuleData> selectRowNoByValue(String ruleId,String dataValue)
    {
        return rlmgModelDao.selectRowNoByValue(ruleId,dataValue);
    }
    @Override
    public List<RlmgRuleDetail> selectRuleDataDetailCol(String ruleId)
    {
        return rlmgModelDao.selectRuleDataDetailCol(ruleId);
    }
	/**
	 * 规则是否引用模型
	 */
	@Override
	public int selectRuleByModelId(String modelId) {
		 int result = rlmgModelDao.selectRuleByModelId(modelId);
	     return result;
	}
	/**
	 * 删除模型
	 */
	@Transactional
	@Override
	public int deleteModel(String modelId) {
        try
        {
            // 将rlmg_model_type表中记录delete_flg设值为1
            RlmgModelType rlmgModelType = (RlmgModelType) rlmgModelDao.selectModelById(modelId);
            rlmgModelType.setDeleteFlg(1);
            rlmgModelDao.update(rlmgModelType);
            // 将rlmg_model_detail表中所对应的所有记录delte_flg设值为1
            List<RlmgModelDetail> fieldList = rlmgModelDao.selectRlmgModelDetailByFk(modelId);
            RlmgModelDetail rlmgModelDetail = new RlmgModelDetail();
            for (int i = 0; i < fieldList.size(); i++)
            {
                rlmgModelDetail = (RlmgModelDetail) fieldList.get(i);
                rlmgModelDetail.setDeleteFlg(1);
                rlmgModelDao.update(rlmgModelDetail);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Constants.OPERATION_FAILURE;
        }
        return Constants.OPERATION_SUCCESS;
	}
	 public List<RlmgModelType> keepModelType(RlmgModelInfoDto rlmgModelInfoDto)
	    {
	        List<RlmgModelType> modelList = new ArrayList<RlmgModelType>();
	        RlmgModelType rlmgModelType = rlmgModelDao.selectModelById(rlmgModelInfoDto.getModelId());
	        modelList.add(rlmgModelType);
	        return modelList;
	    }
	@Override
	public List<RlmgModelInfoDto> keepModelDetail(RlmgModelInfoDto rlmgModelInfoDto) {
		List<RlmgModelInfoDto> dlist = new ArrayList<RlmgModelInfoDto>();
		List<RlmgModelDetail> rlist = rlmgModelDao.selectRlmgModelDetailByFk(rlmgModelInfoDto.getModelId());
		if(rlist.size()>0){
			for(int i=0;i<rlist.size();i++){
				RlmgModelInfoDto rlmg = new RlmgModelInfoDto();
				rlmg.setFieldId(rlist.get(i).getFieldId());
				rlmg.setFieldName(rlist.get(i).getFieldName());
				rlmg.setFieldEnName(rlist.get(i).getFieldEnName());
				rlmg.setFieldType(rlist.get(i).getFieldType());
				dlist.add(rlmg);
			}
		}
		return dlist;
	}
	@Override
	public int selectFieldFromRules(String fieldId) {
		int result = rlmgModelDao.selectFieldFromRules(fieldId);
        return result;
	}
	
	@Override
    public int checkStatus(String ruleIds){
    	int resultSet = 0 ;
    	String[] ruleId = ruleIds.split(",",-1);
    	for(int i=0 ;i<ruleId.length; i++){
    		RlmgRule rule =  new RlmgRule();
    		rule = (RlmgRule)rlmgModelDao.selectRuleByRuleId(ruleId[i]);
    		if(rule.getStatus() == 1 || rule.getStatus()==3){
    			resultSet = 1;
    		}
    	}
    	return resultSet;
    }

	
	@Override
    public int checkStatus1(String ruleIds){
    	int resultSet = 0 ;
    	String[] ruleId = ruleIds.split(",",-1);
    	for(int i=0 ;i<ruleId.length; i++){
    		RlmgRule rule =  new RlmgRule();
    		rule = (RlmgRule)rlmgModelDao.selectRuleByRuleId(ruleId[i]);
    		if(rule.getStatus() == 2 || rule.getStatus()==3 || rule.getStatus()==4){
    			resultSet = 1;
    		}
    	}
    	return resultSet;
    }

	@Override
	public int checkStatus2(String ruleIds) {
		int resultSet = 0 ;
    	String[] ruleId = ruleIds.split(",",-1);
    	for(int i=0 ;i<ruleId.length; i++){
    		RlmgRule rule =  new RlmgRule();
    		rule = (RlmgRule)rlmgModelDao.selectRuleByRuleId(ruleId[i]);
    		if(rule.getStatus()==3 || rule.getStatus()==4){
    			resultSet = 1;
    		}
    	}
    	return resultSet;
	}
	
	@Override
	public int insertRuleVersion(String ruleIds, String content,String name) {
		try {
			String[] ruleId = ruleIds.split(",", -1);
			for (int i = 0; i < ruleId.length; i++) {
				RlmgRule rule = (RlmgRule)rlmgModelDao.selectRuleByRuleId(ruleId[i]);
				logUtils.insertLog("00401", "00404004", "发布规则[{}],发布说明:" + content, rule.getRuleName());
				rule.setStatus(4);
				rule.setLastUpdateBy(name);
				rule.setLastUpdateTime(new Date());
				rule.setOpinions(content);
				rlmgModelDao.update(rule);
			}
			RlmgRuleVersion ruleVersion = new RlmgRuleVersion();
			ruleVersion.setVersionId(String.valueOf(UUID.randomUUID()));
			// 查询目前最大的版本号RLMG_RULE_VERSI_NO
			ruleVersion.setVersionNo(String.valueOf(rlmgModelDao.selectMaxVersionNo() + 1));
			ruleVersion.setVersionMemo(content);
//			ruleVersion.setVersionDrl("<CLOB>");
			// 获取DRL数据
	        ruleVersion.setVersionDrl(createDrl.createRools(ruleIds).toString());
			ruleVersion.setCreateBy(name);
			ruleVersion.setCreateTime(new Date());
			ruleVersion.setUpdateCount(0);
			ruleVersion.setDeleteFlg(0);
			rlmgModelDao.insert(ruleVersion);
			logUtils.insertLog("00402", "00404005", "新建版本[V{}],发布说明:" + content, ruleVersion.getVersionNo());
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	@Override
	public int changeRuleStatus(String ruleId, String content, String name) {
		try {
				RlmgRule rule = rlmgModelDao.selectRuleByRuleId(ruleId);
				logUtils.insertLog("00401", "00404002", "审批同意[{}],审批同意意见：" + content, rule.getRuleName());
				rule.setOpinions(content);
				rule.setPermitBy(name);
				rule.setPermitTime(new Date());
				rule.setLastUpdateBy(name);
				rule.setLastUpdateTime(new Date());
				rule.setStatus(2);
				int i = rlmgModelDao.update(rule);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int changeRuleStatus1(String ruleId, String content, String name) {
		try {
			RlmgRule rule = rlmgModelDao.selectRuleByRuleId(ruleId);
			logUtils.insertLog("00401", "00404003", "审批驳回[{}],审批驳回意见：" + content, rule.getRuleName());
			rule.setOpinions(content);
			rule.setPermitBy(name);
			rule.setPermitTime(new Date());
			rule.setLastUpdateBy(name);
			rule.setLastUpdateTime(new Date());
			rule.setStatus(3);
			int i = rlmgModelDao.update(rule);
	} catch (Exception e) {
		e.printStackTrace();
		return 0;
	}
	return 1;
	}
    @Override
    public int selectMaxDataRowNo(String ruleId)
    {
        return rlmgModelDao.selectMaxDataRowNo(ruleId);
    }
    
    @Override
    public List<RlmgRuleData> selectRuleDataMap(String ruleId,
            String dataRowNo,int currentPage, int colSize)
    {
    	List<String> dataRowNos = Arrays.asList(dataRowNo.split(","));
        return rlmgModelDao.selectRuleDataMap(ruleId, dataRowNos,currentPage,colSize);
    }


	
	
	public RlmgModelType selectModelById(String modelId){
		return rlmgModelDao.selectModelById(modelId);
	}

	@Override
	public List<RlmgRuleDataDto> selectRuleDataById(String ruleId) {
		// TODO Auto-generated method stub
		return rlmgModelDao.selectRuleDataById(ruleId);
	}
	
	/**
     * 根据模型中文名或英文名查询模型
     * */
	public List<RlmgModelType> selectModelByName(String modelName,String modelEnName){
		return rlmgModelDao.selectModelByName(modelName, modelEnName);
	}

}
