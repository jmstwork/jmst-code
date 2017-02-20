package com.founder.fmdm.service.rule;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.service.rule.dto.GroListDto;

public interface GroupManagerService {
	 List<RlmgRulegroup> selectInitGros();

	/**
	 * 查询规则组
	 * 
	 * @param groListDto
	 * @return RlmgRulegroup
	 * @author cao_rui
	 */
	List<RlmgRulegroup> selectGros(GroListDto groListDto, String operFlg, SelectOptions options);

	/**
	 * 存储规则组
	 */
	int saveGro(RlmgRulegroup ruleGroup);

	/**
	 * 校验此规则组是否有规则引用
	 */
	int checkRuleIsUsed(String groId);

	/**
	 * 删除规则组
	 */
	int deleteGro(String groId, String userName);

	/**
	 * 根据ID查规则组
	 */
	RlmgRulegroup selectGroById(String rulegroupId);

	
	/**
	 * 校验规则组名是否重复
	 */
	RlmgRulegroup checkGroName(String groName);

	/**
	 * 校验规则组英文名是否重复
	 */
	RlmgRulegroup checkGroNameEn(String groEnName);
	
	
	/**
	 * 更新规则组
	 */
	int updateGro(RlmgRulegroup ruleGroup);

}
