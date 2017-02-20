package com.founder.fmdm.service.rule;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.entity.DictDepartment;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.rule.dto.RuleDto;
import com.founder.fmdm.service.rule.dto.RuleListDto;
@Service
public interface RuleManagerService 
{  
	//规则名称校验
	public int checkExistRule(String ruleName,String ruleId);
	//已发布规则列表
	List<RuleListDto> selectReleaseRules(RuleListDto ruleListDto, SelectOptions options);
	
	//规则一览
	List<RuleListDto> selectRules(RuleListDto ruleListDto,String orderBy,
			SelectOptions options);	
	
	int deleteRule(String ruleId);
	
	//根据ruleId取规则详细里的项目名称ruleItemName
	List<RlmgRuleDetail> selectById(String ruleId);
	
	//规则数据编辑，数据处理
	Map<String, Object>[] selectRuleDataById(String ruleId,String dataValue,SelectOptions options);
	
	List<RlmgRuleDto> selectExamApproves(String ruleName,String status,String orderBy,SelectOptions options);
	
	List<RlmgRuleData> selectRowNoByValue(String dataValue);
	
	//处理增删改数据
	@Transactional
	int disposeData(String updateIds,String deleteIds,String addIds,String fieldSize) ;
	
	int updateApproveRule(String ruleIds);
	
	//规则状态改为未提交
	int updateApplyRule(String ruleId);
	/***
	 *  新建规则和编辑规则
	 */
	void insertRule(RuleDto ruleDto);
	/**
	 *  初始化规则数据
	 * @param ruleId
	 * @param description 
	 * @return
	 */
	Map<Integer, Object> selectRuleDataByRuleId(String ruleId, String description);
	
	/**
	 * 编辑规则
	 * @param ruleDto
	 */
	void updateRule(RuleDto ruleDto);
	/***
	 * @param ruleId
	 * @return
	 */
	//根据ruleId取规则RlmgRule
	RlmgRule selectRuleByRuleId(String ruleId);
	
	List<RuleListDto> selectOneRuleData(String ruleId);
	
	Map<Integer, Object> selectRuleDataByIdForCreatRule(String ruleId);
	
	//规则状态
	List<SystemCode> selectRuleStatus();
	
	//科室控件，根据科室名称查询是否存在科室code
	List<DictDepartment> selectDeptCodeByDeptName(String deptName);
	
	//	提交前验证，只有未提交状态，才能进行规则审批！
	int checkStatus(String ruleIds);
	
	//提交审批
	int approveRules(String ruleIds, String name);
}
