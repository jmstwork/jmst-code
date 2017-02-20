package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name="RLMG_RULE_VERSION")
public class RlmgRuleVersion 
{
    /**
     *  版本ID
     */
	@Id
	@Column(name="VERSION_ID")
    private String versionId;
     /**
     *  版本号
     */
	@Column(name="VERSION_NO")
    private String versionNo;
    /**
     *  版本说明
     */
	@Column(name="VERSION_MEMO")
    private String versionMemo;
    /**
     *  源代码
     */
	@Column(name="VERSION_DRL")
    private String versionDrl;
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
    /**
     *  删除标记
     */
	@Column(name="DELETE_FLG")
    private int deleteFlg;
    
    
    public String getVersionId()
    {
        return versionId;
    }
    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }
    
    
    public String getVersionNo()
    {
        return versionNo;
    }
    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }
    
    
    public String getVersionMemo()
    {
        return versionMemo;
    }
    public void setVersionMemo(String versionMemo)
    {
        this.versionMemo = versionMemo;
    }
    
    
    public String getVersionDrl()
    {
        return versionDrl;
    }
    public void setVersionDrl(String versionDrl)
    {
        this.versionDrl = versionDrl;
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
    
    
    public int getUpdateCount()
    {
        return updateCount;
    }
    public void setUpdateCount(int updateCount)
    {
        this.updateCount = updateCount;
    }
    
    
    public int getDeleteFlg()
    {
        return deleteFlg;
    }
    public void setDeleteFlg(int deleteFlg)
    {
        this.deleteFlg = deleteFlg;
    }
}