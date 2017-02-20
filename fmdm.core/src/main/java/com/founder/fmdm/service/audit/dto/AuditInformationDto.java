package com.founder.fmdm.service.audit.dto;

import org.seasar.doma.Entity;

@Entity
public class AuditInformationDto{
	private String audId;
	private String optDt;
	private String optDt1;
	private String optDt2;
	private String sysId;
	private String sysName;
	private String hospitalCode;
	private String eventCode;
	private String eventName;
	private String userNo;
	private String userName;
	private String machineName;
	private String ipAddress;
	private String groupCd;
	private String groupName;
	private String deptCode;
	private String deptName;
	private String content;
	
	public String getAudId()
    {
        return audId;
    }
    public void setAudId(String audId)
    {
        this.audId = audId;
    }
    public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOptDt() {
		return optDt;
	}
	public void setOptDt(String optDt) {
		this.optDt = optDt;
	}
	public String getOptDt1() {
		return optDt1;
	}
	public void setOptDt1(String optDt1) {
		this.optDt1 = optDt1;
	}
	public String getOptDt2() {
		return optDt2;
	}
	public void setOptDt2(String optDt2) {
		this.optDt2 = optDt2;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	/*public String getSys_Id() {
		return sys_Id;
	}
	public void setSys_Id(String sys_Id) {
		this.sys_Id = sys_Id;
	}
	public String getSys_Name() {
		return sys_Name;
	}
	public void setSys_Name(String sys_Name) {
		this.sys_Name = sys_Name;
	}*/
	public String getEventCode() {
		return eventCode;
	}
	
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getUserNo() {
		return userNo;
	}
	/*public String getEvent_Code() {
		return event_Code;
	}
	public void setEvent_Code(String event_Code) {
		this.event_Code = event_Code;
	}
	public String getEvent_Name() {
		return event_Name;
	}
	public void setEvent_Name(String event_Name) {
		this.event_Name = event_Name;
	}*/
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroupCd() {
		return groupCd;
	}
	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	
}
