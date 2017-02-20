package com.founder.fmdm.service.rule.impl;

import java.util.Date;
import java.util.List;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.rule.GroupManagerDao;
import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.service.rule.GroupManagerService;
import com.founder.fmdm.service.rule.dto.GroListDto;
import com.founder.util.StringUtil;

@Service
public class GroupManagerServiceImpl implements GroupManagerService {

	@Autowired
	GroupManagerDao groupManagerDao;

	public List<RlmgRulegroup> selectInitGros() {
		List<RlmgRulegroup>  list = groupManagerDao.selectAllGros();
		return list;
	}

	/**
	 * 查询规则组
	 * 
	 * @param groListDto
	 * @return RlmgRulegroup
	 * @author cao_rui
	 */
	public List<RlmgRulegroup> selectGros(GroListDto groListDto, String operFlg, SelectOptions options) {
		String rulegroupNameValue = groListDto.getCondGroName() == null ? "" : groListDto.getCondGroName();
		String rulegroupName = rulegroupNameValue.trim();
		String rulegroupEnNameValue = groListDto.getCondGroNameEn() == null ? "" : groListDto.getCondGroNameEn();
		String rulegroupEnName = rulegroupEnNameValue.trim();
		return groupManagerDao.selectGros(rulegroupName, rulegroupEnName, operFlg, options);
	}

	@Override
	public int saveGro(RlmgRulegroup ruleGroup) {
		ruleGroup.setRulegroupId(StringUtil.getUUID());
		ruleGroup.setUpdateCount(0);
		ruleGroup.setCreateTime(new Date());
		ruleGroup.setLastUpdateTime(new Date());
		ruleGroup.setDeleteFlg(0);
		ruleGroup.setLastUpdateBy(ruleGroup.getCreateBy());
		return groupManagerDao.saveGros(ruleGroup);
	}

	@Override
	public int checkRuleIsUsed(String rulegroupId) {
		return groupManagerDao.selectCheckRuleIsUsed(rulegroupId);
	}

	public int deleteGro(String groId, String userName) {
		RlmgRulegroup rlmgRulegroup = groupManagerDao.selectGrosById(groId);
		if (null == rlmgRulegroup) {
			return 0;
		}
		rlmgRulegroup.setDeleteBy(userName);
		rlmgRulegroup.setDeleteFlg(1);
		rlmgRulegroup.setDeleteTime(new Date());
		return groupManagerDao.deleteGros(rlmgRulegroup);
	}

	public RlmgRulegroup selectGroById(String rulegroupId) {
		return groupManagerDao.selectGrosById(rulegroupId);
	}

	/**
	 * 校验规则组名是否重复
	 */
	public RlmgRulegroup checkGroName(String groName) {
		return groupManagerDao.checkGroName(groName);
	}

	/**
	 * 校验规则组英文名是否重复
	 */
	public RlmgRulegroup checkGroNameEn(String groEnName) {
		return groupManagerDao.checkGroNameEn(groEnName);
	}

	/**
	 * 更新规则组
	 */
	public int updateGro(RlmgRulegroup ruleGroup) {
		RlmgRulegroup oldRlmgRulegroup = groupManagerDao.selectGrosById(ruleGroup.getRulegroupId());
		if (null != ruleGroup.getRulegroupName()) {
			oldRlmgRulegroup.setRulegroupName(ruleGroup.getRulegroupName());
		}
		if (null != ruleGroup.getRulegroupEnName()) {
			oldRlmgRulegroup.setRulegroupEnName(ruleGroup.getRulegroupEnName());
		}
		oldRlmgRulegroup.setLastUpdateBy(ruleGroup.getLastUpdateBy());
		oldRlmgRulegroup.setLastUpdateTime(new Date());
		return groupManagerDao.updateGros(oldRlmgRulegroup);
	}

}
