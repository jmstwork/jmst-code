package com.founder.fmdm.dao.rule;

import java.math.BigDecimal;
import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.DictDepartment;
import com.founder.fmdm.entity.IamLog;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.RlmgRuleVersion;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.rule.dto.RuleListDto;
@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface RuleManagerDao
{
	 
	@Select
	List<String> selectExistRule(String ruleName,String ruleId);
	
	@Select
	List<RuleListDto> selectReleaseRules(String ruleName,String rulegroupName,String modelName,String status,SelectOptions options);
	
	@Select
	List<RuleListDto> selectRules(String ruleName,String rulegroupName,String modelName,String status,String orderBy,SelectOptions options);
	
	@Select
	List<RlmgRuleDto> selectExamApproves(String ruleName,String status,String orderBy,SelectOptions options);
	
	@Select
	List<RlmgRuleDetail> selectRuleDetailById(String ruleId);
	
	@Select
	List<RlmgRuleData>  selectRuleDataById(String ruleId,String dataValue,SelectOptions options);
	
	@Select
	List<RlmgRuleData> selectDataOfEditRule(String ruleId,String dataValue);
	
	@Select
	int selectMaxDataRowNo(String ruleItemId,String ruleId);
	
	@Select
	int selectMaxItemColNo(String ruleId);
	
	@Select
	List<BigDecimal> selectItemRowNos(String ruleId);
	
	@Select
	List<RlmgRuleData> selectRowNoByValue(String dataValue);
	
	@Select
	List<RlmgRuleData>  selectRuleDataByIdForCreatRule(String ruleId);
	
//	@Select
//	List<RlmgModelDetail>  selectModelDetail(String modelEnName);
	
	@Select
	List<RuleListDto> selectOneRuleData(String ruleId);
	@Select
	List<RlmgRuleVersion> selectNewestDrl();
	
	@Select
	List<RlmgRuleDetail> selectRuleItemNameById(String ruleItemId);
	
	@Select
	List<DictDepartment> selectDeptCodeByDeptName(String deptName);
	
	@Select
	int selectRuleByDept(String ruleType,String visitDept);
	@Select
	RlmgRuleData selectById(String dataId);
	@Update
	int updateRlmgRuleData(RlmgRuleData entity);
	@Update
	int updateRlmgRule(RlmgRule rlmgRule);
	@Select
	RlmgRule selectRlmgRuleById(String ruleId);
	@Select
	RlmgRuleDetail selectRlmgRuleDetailById(String ruleItemId);
	@Select
	List<RlmgRuleData> selectRlmgRuleDataRuleItemId(String ruleItemId);
	@Update
	int updateRlmgRuleDetail(RlmgRuleDetail detail);
	@Insert
	int insertRlmgRuleData(RlmgRuleData ruleData);
	@Insert
	int insertRlmgRuleDetail(RlmgRuleDetail item);
	@Insert
	int insertIamLog(IamLog iLog);
	@Insert
	int insertRlmgRule(RlmgRule rule);
	@Select
	List<SystemCode> selectSystemCodeByCategoryCd(String categoryCd);
	@Delete
	int deleteRlmgRuleDetail(RlmgRuleDetail entity);
	@Delete
	int deleteRlmgRuleData(RlmgRuleData entity);
	
	
	
}
