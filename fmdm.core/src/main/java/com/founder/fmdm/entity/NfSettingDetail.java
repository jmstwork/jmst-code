package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name="NF_SETTING_DETAIL")
public class NfSettingDetail
{
	@Id
	@Column(name="setting_detail_id")
    private String settingDetailId;
	
	@Column(name="setting_id")
    private String settingId;
    
	@Column(name="nf_type")
    private String nfType;
    
	@Column(name="setting_value")
    private String settingValue;
    
	@Column(name="flg_mon")
    private String flgMon;
    
	@Column(name="flg_tus")
    private String flgTus;
    
	@Column(name="flg_wed")
    private String flgWed;
	
	@Column(name="flg_thu")
    private String flgThu;
	
	@Column(name="flg_fri")
    private String flgFri;
	
	@Column(name="flg_sat")
    private String flgSat;
	
	@Column(name="flg_sun")
    private String flgSun;
	
	@Column(name="receive_start_time")
    private Date receiveStartTime;
	
	@Column(name="receive_end_time")
    private Date receiveEndTime;
	
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
    
    public String getSettingDetailId()
    {
        return settingDetailId;
    }
    public void setSettingDetailId(String settingDetailId)
    {
        this.settingDetailId = settingDetailId;
    }
    public String getSettingId()
    {
        return settingId;
    }
    public void setSettingId(String settingId)
    {
        this.settingId = settingId;
    }
    public String getNfType()
    {
        return nfType;
    }
    public void setNfType(String nfType)
    {
        this.nfType = nfType;
    }
    public String getSettingValue()
    {
        return settingValue;
    }
    public void setSettingValue(String settingValue)
    {
        this.settingValue = settingValue;
    }
    public String getFlgMon()
    {
        return flgMon;
    }
    public void setFlgMon(String flgMon)
    {
        this.flgMon = flgMon;
    }
    public String getFlgTus()
    {
        return flgTus;
    }
    public void setFlgTus(String flgTus)
    {
        this.flgTus = flgTus;
    }
    public String getFlgWed()
    {
        return flgWed;
    }
    public void setFlgWed(String flgWed)
    {
        this.flgWed = flgWed;
    }
    public String getFlgThu()
    {
        return flgThu;
    }
    public void setFlgThu(String flgThu)
    {
        this.flgThu = flgThu;
    }
    public String getFlgFri()
    {
        return flgFri;
    }
    public void setFlgFri(String flgFri)
    {
        this.flgFri = flgFri;
    }
    public String getFlgSat()
    {
        return flgSat;
    }
    public void setFlgSat(String flgSat)
    {
        this.flgSat = flgSat;
    }
    public String getFlgSun()
    {
        return flgSun;
    }
    public void setFlgSun(String flgSun)
    {
        this.flgSun = flgSun;
    }
    public Date getReceiveStartTime()
    {
        return receiveStartTime;
    }
    public void setReceiveStartTime(Date receiveStartTime)
    {
        this.receiveStartTime = receiveStartTime;
    }
    public Date getReceiveEndTime()
    {
        return receiveEndTime;
    }
    public void setReceiveEndTime(Date receiveEndTime)
    {
        this.receiveEndTime = receiveEndTime;
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
