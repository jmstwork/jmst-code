package com.founder.fmdm.service.rule.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
 

@Entity
public class RlmgRuleDataDto {
	@Column(name="DATA_ID")
    private String dataId;
	
	@Column(name="RULE_ID")
    private String ruleId;
	
	@Column(name="RULE_ITEM_ID")
    private String ruleItemId;
	
	@Column(name="DATA_ROW_NO")
    private String dataRowNo;
	
	@Column(name="DATA_VALUE")
    private String dataValue;
	
	@Column(name="CREATE_BY")
    private String createBy;
	
	@Column(name="CREATE_TIME")
    private String createTime;
	
	@Column(name="LAST_UPDATE_BY")
    private String lastUpdateBy;
	
	@Column(name="LAST_UPDATE_TIME")
    private String lastUpdateTime;
	
	@Column(name="UPDATE_COUNT")
    private String updateCount;
	
	@Column(name="DELETE_BY")
    private String deleteBy;
	
	@Column(name="DELETE_TIME")
    private String deleteTime;
	
	@Column(name="DELETE_FLG")
    private String deleteFlg;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleItemId() {
		return ruleItemId;
	}

	public void setRuleItemId(String ruleItemId) {
		this.ruleItemId = ruleItemId;
	}

	public String getDataRowNo() {
		return dataRowNo;
	}

	public void setDataRowNo(String dataRowNo) {
		this.dataRowNo = dataRowNo;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(String updateCount) {
		this.updateCount = updateCount;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
}