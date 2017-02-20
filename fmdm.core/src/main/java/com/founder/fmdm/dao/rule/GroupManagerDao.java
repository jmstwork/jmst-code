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

import com.founder.fmdm.entity.RlmgRulegroup;

@Dao
@AnnotateWith(annotations = {
		@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface GroupManagerDao
{
	@Select
	public List<RlmgRulegroup> selectGros(String rulegroupName,String rulegroupEnName,String operFlg,SelectOptions options);
	
	
	
	
	@Insert
	public int saveGros(RlmgRulegroup ruleGroup);
	
	
	@Select
	int selectCheckRuleIsUsed(String rulegroupId);
	
	@Select
	RlmgRulegroup selectGrosById(String rulegroupId);
	
	@Update
	int deleteGros(RlmgRulegroup ruleGroup);
	
	
	@Select
	RlmgRulegroup checkGroName(String groName);
	
	@Select
	RlmgRulegroup checkGroNameEn(String groEnName);
	
	
	@Update
	int updateGros(RlmgRulegroup ruleGroup);
	
	@Select
    List<RlmgRulegroup> selectGroupNameById(String rulegroupId);



	@Select
	List<RlmgRulegroup> selectAllGros();
	
	/*@Select
	int selectCheckGroName(String rulegroupId,String rulegroupName);
	
	@Select
	int selectCheckGroNameEn(String rulegroupId,String rulegroupEnName);
	
	@Select
	RlmgRulegroup selectMaxId();
	
	@Select
	int selectCheckRuleIsUsed(String rulegroupId);
	
	@Select
    List<RlmgRulegroup> selectGroupNameById(String rulegroupId);*/
}
