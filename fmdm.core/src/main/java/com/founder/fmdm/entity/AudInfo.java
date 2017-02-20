package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "AUD_INFO")
public class AudInfo {
	@Id
	@Column(name = "AUD_ID")
	private String audId;
	
	@Column(name = "SYS_ID")
	private String sysId;
	
	@Column(name = "OPT_DT")
	private Date optDt;
	
	@Column(name = "EVENT_CODE")
	private String eventCode;
	
	@Column(name = "HOSPITAL_CODE")
	private String hospitalCode;
	
	@Column(name = "HOSPITAL_NAME")
	private String hospitalName;
	
	@Column(name = "DEPT_CODE")
	private String deptCode;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "MACHINE_NAME")
	private String machineName;
	
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	@Column(name = "REMARK1")
	private String remark1;
	
	@Column(name = "REMARK2")
	private String remark2;
	
	@Column(name = "REMARK3")
	private String remark3;
	
	@Column(name = "CREATE_BY")
	private String createBy;

	@Column(name = "CREATE_DT")
	private Date createDt;
	
	@Column(name = "UPDATE_BY")
	private String updateBy;
	
	@Column(name = "UPDATE_DT")
	private Date updateDt;
	
	/**
     *  删除标记
     */
	@Column(name = "DELETE_FLG")
    private int deleteFlg;
	
	/**
     *  删除者
     */
	@Column(name = "DELETE_BY")
    private String deleteBy;
    /**
     *  删除时间
     */
	@Column(name = "DELETE_DT")
    private Date deleteDt;
    /**
     *  更新次数
     */
	@Version
	@Column(name = "UPDATE_TIMES")
    private int updateTimes;
	
	public String getAudId() {
		return audId;
	}

	public void setAudId(String audId) {
		this.audId = audId;
	}
	
	public String getSysId()
    {
        return sysId;
    }

    public void setSysId(String sysId)
    {
        this.sysId = sysId;
    }

	public Date getOptDt() {
		return optDt;
	}

	public void setOptDt(Date optDt) {
		this.optDt = optDt;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public int getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteDt() {
		return deleteDt;
	}

	public void setDeleteDt(Date deleteDt) {
		this.deleteDt = deleteDt;
	}

	public int getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(int updateTimes) {
		this.updateTimes = updateTimes;
	}
	
	
	

}
