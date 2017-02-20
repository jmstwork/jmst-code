package com.founder.fmdm.service.rule;


import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.RlmgModelDetail;
import com.founder.fmdm.entity.RlmgModelType;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.service.rule.dto.RlmgModelInfoDto;
import com.founder.fmdm.service.rule.dto.RlmgRuleDataDto;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.rule.dto.RlmgVersionDto;

public interface RlmgModelService
{

	/**
	 * 初始化模型事实数据
	 * @return
	 */
	List<RlmgModelType> selectInitModels();
	
	/**
	 * 初始化字段数据
	 * @return
	 */
	List<RlmgModelDetail> selectInitItems(String modelId);
    int saveModel(RlmgModelInfoDto rlmgModelInfoDto,String opt);

	List<RlmgModelType> selectModels(String modelName,SelectOptions options);
	
	List<RlmgRuleDto> selectRules(String ruleName,String status, SelectOptions options);
	
	//根据ruleId取规则RlmgRule
	RlmgRule selectRuleByRuleId(String ruleId);
	List<RlmgRuleData> selectRowNoByValue(String dataValue, String description);
	int insertRuleVersion(String ruleIds, String content,String name);
	
	List<RlmgRuleDetail> selectRuleDataDetailCol(String ruleId);

	int selectRuleByModelId(String selectId);

	int deleteModel(String selectId);

	List<RlmgModelType> keepModelType(RlmgModelInfoDto rlmgModelInfoDto);

	List<RlmgModelInfoDto> keepModelDetail(RlmgModelInfoDto rlmgModelInfoDto);

	int selectFieldFromRules(String fieldId);

	int checkStatus(String ruleIds);

	
	int checkStatus1(String ruleIds);
	
	int checkStatus2(String ruleIds);
	
	int selectMaxDataRowNo(String ruleId);
	
	List<RlmgRuleData> selectRuleDataMap(String ruleId, String dataRowNo, int currentPage, int colSize);

	
	RlmgModelType selectModelById(String modelId);


	int changeRuleStatus(String ruleIds, String content, String name);

	int changeRuleStatus1(String ruleIds, String content, String name);

	List<RlmgVersionDto> selectVersions(String versionNo, SelectOptions options);

	List<RlmgRuleDataDto> selectRuleDataById(String ruleId);
	
	/**
     * 根据模型中文名或英文名查询模型
     * */
	public List<RlmgModelType> selectModelByName(String modelName,String modelEnName);
}
