package com.founder.fmdm.dao.rule;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.RlmgModelDetail;
import com.founder.fmdm.entity.RlmgModelType;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.RlmgRuleVersion;
import com.founder.fmdm.service.rule.dto.RlmgRuleDataDto;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.rule.dto.RlmgVersionDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface RlmgModelDao
{
    @Insert
    int insert(RlmgModelType rlmgModelType);
    @Insert
    int insert(RlmgModelDetail rlmgModelDetail);
    @Insert
    int insert(RlmgRuleVersion ruleVersion);
    @Select
    List<RlmgModelType> selectModels(String modelName, SelectOptions options);
    @Select
    List<RlmgRuleDto> selectRules(String ruleName, String status, SelectOptions options);
    @Select
    List<RlmgVersionDto> selectVersions(String versionNo, SelectOptions options);
    @Select
    List<RlmgRuleDataDto> selectRuleDataById(String ruleId);
    @Select
    int selectMaxVersionNo();
    @Select
    List<RlmgRuleDetail> selectRuleDataDetailCol(String ruleId);
    @Select
	int selectRuleByModelId(String modelId);
    @Update
    int update(RlmgModelType rlmgModelType);
    @Update
    int update(RlmgModelDetail rlmgModelDetail);
    @Update
    int update(RlmgRule rule);
    @Select
	RlmgModelType selectModelById(String modelId);
    @Select
	List<RlmgModelDetail> selectRlmgModelDetailByFk(String modelId);
    @Select
	int selectFieldFromRules(String fieldId);
    @Select
    List<RlmgModelType> selectModelNameById(String modelId);
    @Select
    List<RlmgModelDetail> selectInitItems(String modelId);
    @Select
	List<RlmgModelType> selectInitModels();
    @Select
	RlmgRule selectRuleByRuleId(String ruleId);
    @Select
	List<RlmgRuleData> selectRowNoByValue(String ruleId,String dataValue);
    @Select
	int selectMaxDataRowNo(String ruleId);
    @Select
	List<RlmgRuleData> selectRuleDataMap(String ruleId, List<String> dataRowNos, int currentPage, int colSize);
    
    /**
     * 根据模型中文名或英文名查询模型
     * */
    @Select
	List<RlmgModelType> selectModelByName(String modelName,String modelEnName);
}
