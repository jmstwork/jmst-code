package com.founder.fmdm.service.rule.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
@Entity
public class RuleListDto
{
    private String ruleId;
	
    private String ruleName;

    private int status;

    private String memo;

    private String applyBy;

    private Date applyTime;

    private String permitBy;

    private Date permitTime;

    private String opinions;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private int updateCount;

    private String deleteBy;

    private Date deleteTime;

    private int deleteFlg;

    private String rulegroupId;

    private String rulegroupName;

    private String rulegroupEnName;

    private String departName;

    private String departCd;

    private String modelId;

    private String modelName;

    private String modelEnName;

    private String statusText;

    /*
     * 条件
     */
    private String condRuleName;

    private String condRuleGroName;

    private String condModelName;

    private String condStatus;

    private String operFlg;

    private String selectId;
    
    private int pageNo=1;
    
    private String ruleGroupId;
    
	
	public String getRuleGroupId() {
		return ruleGroupId;
	}
	public void setRuleGroupId(String ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

    /**	
     * 结果
    */

    private String unitId;

    public String getRuleId()
    {
        return ruleId;
    }

    public void setRuleId(String ruleId)
    {
        this.ruleId = ruleId;
    }

    public String getRuleName()
    {
        return ruleName;
    }

    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMemo()
    {
        return memo;
    }

    public void setMemo(String memo)
    {
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

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getLastUpdateBy()
    {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy)
    {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(int updateCount)
    {
        this.updateCount = updateCount;
    }

    public String getDeleteBy()
    {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy)
    {
        this.deleteBy = deleteBy;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public int getDeleteFlg()
    {
        return deleteFlg;
    }

    public void setDeleteFlg(int deleteFlg)
    {
        this.deleteFlg = deleteFlg;
    }

    public String getRulegroupId()
    {
        return rulegroupId;
    }

    public void setRulegroupId(String rulegroupId)
    {
        this.rulegroupId = rulegroupId;
    }

    public String getRulegroupName()
    {
        return rulegroupName;
    }

    public void setRulegroupName(String rulegroupName)
    {
        this.rulegroupName = rulegroupName;
    }

    public String getRulegroupEnName()
    {
        return rulegroupEnName;
    }

    public void setRulegroupEnName(String rulegroupEnName)
    {
        this.rulegroupEnName = rulegroupEnName;
    }

    public String getDepartName()
    {
        return departName;
    }

    public void setDepartName(String departName)
    {
        this.departName = departName;
    }

    public String getDepartCd()
    {
        return departCd;
    }

    public void setDepartCd(String departCd)
    {
        this.departCd = departCd;
    }

    public String getModelId()
    {
        return modelId;
    }

    public void setModelId(String modelId)
    {
        this.modelId = modelId;
    }

    public String getModelName()
    {
        return modelName;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public String getModelEnName()
    {
        return modelEnName;
    }

    public void setModelEnName(String modelEnName)
    {
        this.modelEnName = modelEnName;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }

    public String getCondRuleName()
    {
        return condRuleName;
    }

    public void setCondRuleName(String condRuleName)
    {
        this.condRuleName = condRuleName;
    }

    public String getCondRuleGroName()
    {
        return condRuleGroName;
    }

    public void setCondRuleGroName(String condRuleGroName)
    {
        this.condRuleGroName = condRuleGroName;
    }

    public String getCondModelName()
    {
        return condModelName;
    }

    public void setCondModelName(String condModelName)
    {
        this.condModelName = condModelName;
    }

    public String getCondStatus()
    {
        return condStatus;
    }

    public void setCondStatus(String condStatus)
    {
        this.condStatus = condStatus;
    }

    public String getOperFlg()
    {
        return operFlg;
    }

    public void setOperFlg(String operFlg)
    {
        this.operFlg = operFlg;
    }

    public String getSelectId()
    {
        return selectId;
    }

    public void setSelectId(String selectId)
    {
        this.selectId = selectId;
    }

    public String getUnitId()
    {
        return unitId;
    }

    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }

}
