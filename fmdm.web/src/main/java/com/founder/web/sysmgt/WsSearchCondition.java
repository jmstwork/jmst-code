package com.founder.web.sysmgt;

import org.seasar.doma.Entity;

import com.founder.web.dto.PagingDto;

/**
 * @author yang_jianbo
 * 
 */

public class WsSearchCondition extends PagingDto {
    private String settingId;
    private String userNo;
    private String userName;
    private String userType;
    private String userTypeName;
    private String unitId;
    private String unitName;
    private String tel;
    private String departName;
    private String rulegroupName;
    private String groupCode;
    private String email;
    private String sysIds;
    private String sysNames;
    private String receiveDays;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private String noticeMethod;
	public String getSettingId() {
		return settingId;
	}
	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getRulegroupName() {
		return rulegroupName;
	}
	public void setRulegroupName(String rulegroupName) {
		this.rulegroupName = rulegroupName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSysIds() {
		return sysIds;
	}
	public void setSysIds(String sysIds) {
		this.sysIds = sysIds;
	}
	public String getSysNames() {
		return sysNames;
	}
	public void setSysNames(String sysNames) {
		this.sysNames = sysNames;
	}
	public String getReceiveDays() {
		return receiveDays;
	}
	public void setReceiveDays(String receiveDays) {
		this.receiveDays = receiveDays;
	}
	public boolean isMonday() {
		return monday;
	}
	public void setMonday(boolean monday) {
		this.monday = monday;
	}
	public boolean isTuesday() {
		return tuesday;
	}
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}
	public boolean isWednesday() {
		return wednesday;
	}
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}
	public boolean isThursday() {
		return thursday;
	}
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}
	public boolean isFriday() {
		return friday;
	}
	public void setFriday(boolean friday) {
		this.friday = friday;
	}
	public boolean isSaturday() {
		return saturday;
	}
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}
	public boolean isSunday() {
		return sunday;
	}
	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}
	public String getNoticeMethod() {
		return noticeMethod;
	}
	public void setNoticeMethod(String noticeMethod) {
		this.noticeMethod = noticeMethod;
	}

}