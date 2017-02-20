package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "rlmg_rule_detail")
public class RlmgRuleDetail {

	/**
	 *  条件项目ID
	 */
	@Id
	@Column(name = "rule_item_id")
	private String ruleItemId;
	/**
	 *  项目序号
	 */
	@Column(name="rule_item_no")
	private int ruleItemNo;
	/**
	 *  项目名称
	 */
	@Column(name="rule_item_name")
	private String ruleItemName;
	/**
	 *  规则ID
	 */
	@Column(name="RULE_ID")
	private String ruleId;
	/**
	 *  编辑类型
	 */
	@Column(name="EDIT_TYPE")
	private int editType;
	/**
	 *  项目类型
	 */
	@Column(name="ITEM_TYPE")
	private int itemType;
	/**
	 *  项目表达式 
	 */
	@Column(name="ITEM_EXPRESSION")
	private String itemExpression;

    /**
     *  缺省值
     */
	@Column(name="DEFAULT_VALUE")
	private String defaultValue;

  
	/**
	 *  删除标记
	 */
	@Column(name="DELETE_FLG")
	private int deleteFlg;
	/**
	 *  创建者
	 */
	@Column(name="CREATE_BY")
	private String createBy;
	/**
	 *  创建日期
	 */
	@Column(name="CREATE_TIME")
	private Date createTime;
	/**
	 *  更新者
	 */
	@Column(name="LAST_UPDATE_BY")
	private String lastUpdateBy;
	/**
	 *  更新时间
	 */
	@Column(name="LAST_UPDATE_TIME")
	private Date lastUpdateTime;
	/**
	 *  删除者
	 */
	@Column(name="DELETE_BY")
	private String deleteBy;
	/**
	 *  删除时间
	 */
	@Column(name="DELETE_TIME")
	private Date deleteTime;
	/**
	 *  更新次数
	 */
	@Version
	@Column(name="UPDATE_COUNT")
	private int updateCount;
	


	
    public String getRuleItemId() {
		return ruleItemId;
	}
	public void setRuleItemId(String ruleItemId) {
		this.ruleItemId = ruleItemId;
	}
	
	
	public int getRuleItemNo() {
		return ruleItemNo;
	}
	public void setRuleItemNo(int ruleItemNo) {
		this.ruleItemNo = ruleItemNo;
	}
	
	
	public String getRuleItemName() {
		return ruleItemName;
	}
	public void setRuleItemName(String ruleItemName) {
		this.ruleItemName = ruleItemName;
	}
	
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	
	public int getEditType() {
		return editType;
	}
	public void setEditType(int editType) {
		this.editType = editType;
	}
	
	
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	
	public String getItemExpression() {
		return itemExpression;
	}
	public void setItemExpression(String itemExpression) {
		this.itemExpression = itemExpression;
	}
	
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public int getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getDeleteBy() {
		return deleteBy;
	}
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}
	
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
}
