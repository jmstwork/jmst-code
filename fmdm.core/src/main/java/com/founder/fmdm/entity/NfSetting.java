package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;


@Entity
@Table(name="NF_SETTING")
public class NfSetting
{
	@Id
	@Column(name="setting_id")
    private String settingId;
	
	@Column(name="unit_id")
    private String unitId;
	
	@Column(name="user_no")
    private String userNo;
	
	@Column(name="rulegroup_id")
    private String rulegroupId;
	
	@Column(name="user_type")
    private String userType;
	
	@Column(name="create_by")
    private String createBy;
	
	@Column(name="create_time")
    private Date createTime;
	
	@Column(name="last_update_by")
    private String lastUpdateBy;
	
	@Column(name="last_update_time")
    private Date lastUpdateTime;
	
	@Column(name="delete_by")
    private String deleteBy;
	
	@Column(name="delete_time")
    private Date deleteTime;
	
	@Version
	@Column(name="update_count")
    private int updateCount;
	
	@Column(name="delete_flg")
    private int deleteFlg;
    
    public String getSettingId()
    {
        return settingId;
    }
    public void setSettingId(String settingId)
    {
        this.settingId = settingId;
    }
    public String getUnitId()
    {
        return unitId;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
    public String getUserNo()
    {
        return userNo;
    }
    public void setUserNo(String userNo)
    {
        this.userNo = userNo;
    }
    public String getRulegroupId()
    {
        return rulegroupId;
    }
    public void setRulegroupId(String rulegroupId)
    {
        this.rulegroupId = rulegroupId;
    }
    public String getUserType()
    {
        return userType;
    }
    public void setUserType(String userType)
    {
        this.userType = userType;
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
