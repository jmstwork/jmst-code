package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "rlmg_rule_data")
public class RlmgRuleData {

	/**
	 *  数据项目ID
	 */
	@Id
	@Column(name="data_id")
	private String dataId;
	/**
	 *  规则ID
	 */
	@Column(name="RULE_ID")
	private String ruleId;
	/**
	 *  条件项目Id
	 */
	@Column(name="RULE_ITEM_ID")
	private String ruleItemId;
	/**
	 *  数据序号
	 */
	@Column(name="DATA_ROW_NO")
	private int dataRowNo;

	/**
	 *  数据值
	 */
	@Column(name="DATA_VALUE")
	private String dataValue;
	
  
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
	public void setDataRowNo(int dataRowNo){
		this.dataRowNo = dataRowNo;
	}
	
	public int getDataRowNo() {
		return dataRowNo;
	}
	
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
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
