package com.founder.fmdm.service.sysmgt.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class WarningSettingDto
{
	@Column(name="setting_id")
    private String settingId;
	
	@Column(name="user_no")
    private String userNo;
    
	@Column(name="user_name")
    private String userName;
	
	@Column(name="user_type")
    private String userType;
	
	@Column(name="user_type_name")
    private String userTypeName;
	
	@Column(name="unit_id")
    private String unitId;
	
	@Column(name="unit_name")
    private String unitName;
	
	@Column(name="tel")
    private String tel;
	
	@Column(name="rulegroup_name")
    private String rulegroupName;
	
	@Column(name="group_code")
    private String groupCode;
	
	@Column(name="email")
    private String email;
	
	@Column(name="sys_ids")
    private String sysIds;
	
	@Column(name="sys_names")
    private String sysNames;
	
	@Column(name="receive_days")
    private String receiveDays;
	
	@Column(name="monday")
    private boolean monday;
	
	@Column(name="tuesday")
    private boolean tuesday;
	
	@Column(name="wednesday")
    private boolean wednesday;
	
	@Column(name="thursday")
    private boolean thursday;
	
	@Column(name="friday")
    private boolean friday;
	
	@Column(name="saturday")
    private boolean saturday;
	
	@Column(name="sunday")
    private boolean sunday;
    public String getSettingId()
    {
        return settingId;
    }
    public void setSettingId(String settingId)
    {
        this.settingId = settingId;
    }
    public String getUserNo()
    {
        return userNo;
    }
    public void setUserNo(String userNo)
    {
        this.userNo = userNo;
    }
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public String getUserType()
    {
        return userType;
    }
    public void setUserType(String userType)
    {
        this.userType = userType;
    }
    
    public String getUserTypeName()
    {
        return userTypeName;
    }
    public void setUserTypeName(String userTypeName)
    {
        this.userTypeName = userTypeName;
    }
    public String getUnitId()
    {
        return unitId;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
    
    public String getUnitName()
    {
        return unitName;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }
    public String getTel()
    {
        return tel;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    public String getRulegroupName()
    {
        return rulegroupName;
    }
    public void setRulegroupName(String rulegroupName)
    {
        this.rulegroupName = rulegroupName;
    }
    public String getGroupCode()
    {
        return groupCode;
    }
    public void setGroupCode(String groupCode)
    {
        this.groupCode = groupCode;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getSysIds()
    {
        return sysIds;
    }
    public void setSysIds(String sysIds)
    {
        this.sysIds = sysIds;
    }
    public String getSysNames()
    {
        return sysNames;
    }
    public void setSysNames(String sysNames)
    {
        this.sysNames = sysNames;
    }
    public String getReceiveDays()
    {
        return receiveDays;
    }
    public void setReceiveDays(String receiveDays)
    {
        this.receiveDays = receiveDays;
    }
    public boolean isMonday()
    {
        return monday;
    }
    public void setMonday(boolean monday)
    {
        this.monday = monday;
    }
    public boolean isTuesday()
    {
        return tuesday;
    }
    public void setTuesday(boolean tuesday)
    {
        this.tuesday = tuesday;
    }
    public boolean isWednesday()
    {
        return wednesday;
    }
    public void setWednesday(boolean wednesday)
    {
        this.wednesday = wednesday;
    }
    public boolean isThursday()
    {
        return thursday;
    }
    public void setThursday(boolean thursday)
    {
        this.thursday = thursday;
    }
    public boolean isFriday()
    {
        return friday;
    }
    public void setFriday(boolean friday)
    {
        this.friday = friday;
    }
    public boolean isSaturday()
    {
        return saturday;
    }
    public void setSaturday(boolean saturday)
    {
        this.saturday = saturday;
    }
    public boolean isSunday()
    {
        return sunday;
    }
    public void setSunday(boolean sunday)
    {
        this.sunday = sunday;
    }
}
