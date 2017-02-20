package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "rlmg_rule")
public class RlmgRule {

	/**
	 *  规则ID
	 */
	@Id
	@Column(name="RULE_ID")
	private String ruleId;
	/**
	 *  规则名称
	 */
	@Column(name="RULE_NAME")
	private String ruleName;
	/**
	 *  规则组ID
	 */
	@Column(name="RULE_GROUP_ID")
	private String rulegroupId;
	/**
	 *  科室ID
	 */
	@Column(name="UNIT_ID")
	private String unitId;
	/**
	 *  模型ID
	 */
	@Column(name="MODEL_ID")
	private String modelId;
	/**
	 *  规则状态
	 */
	@Column(name="STATUS")
	private int status;
	/**
	 *  描述 
	 */
	@Column(name="MEMO")
	private String memo;

    /**
     *  提交人
     */
	@Column(name="APPLY_BY")
	private String applyBy;

    /**
     *  提交时间
     */
	@Column(name="APPLY_TIME")
	private Date applyTime;
    /**
     *  审批人
     */
	@Column(name="PERMIT_BY")
	private String permitBy;
    /**
     *  审批时间
     */
	@Column(name="PERMIT_TIME")
	private Date permitTime;
    /**
     *  意见
     */
	@Column(name="OPINIONS")
	private String opinions;
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
	

	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	
	public String getRulegroupId() {
		return rulegroupId;
	}

	public void setRulegroupId(String rulegroupId) {
		this.rulegroupId = rulegroupId;
	}
	
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

		
	public String getApplyBy()
    {
        return applyBy;
    }

    public void setApplyBy(String applyBy)
    {
        this.applyBy = applyBy;
    }

    
    public Date getApplyTime()
    {
        return applyTime;
    }

    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }

    
    public String getPermitBy()
    {
        return permitBy;
    }

    public void setPermitBy(String permitBy)
    {
        this.permitBy = permitBy;
    }

    
    public Date getPermitTime()
    {
        return permitTime;
    }

    public void setPermitTime(Date permitTime)
    {
        this.permitTime = permitTime;
    }

    
    public String getOpinions()
    {
        return opinions;
    }

    public void setOpinions(String opinions)
    {
        this.opinions = opinions;
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
