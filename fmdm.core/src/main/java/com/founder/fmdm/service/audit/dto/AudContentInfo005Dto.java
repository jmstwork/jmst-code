package com.founder.fmdm.service.audit.dto;

import org.seasar.doma.Entity;

@Entity
public class AudContentInfo005Dto {

	private String audId;
	private String rowNo;
	private String rowAction;
	private String docotorNo;
	private String clinicPathRecordId;
	private String clinicPathId;
	private String clinicPathName;
	private String exitTxt;
	private String patientId;
	private String domainId;
	private String patientTimes;
	private String hospitalNo;
	
	
	public String getAudId() {
		return audId;
	}
	public void setAudId(String audId) {
		this.audId = audId;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getRowAction() {
		return rowAction;
	}
	public void setRowAction(String rowAction) {
		this.rowAction = rowAction;
	}
	public String getDocotorNo() {
		return docotorNo;
	}
	public void setDocotorNo(String docotorNo) {
		this.docotorNo = docotorNo;
	}
	public String getClinicPathRecordId() {
		return clinicPathRecordId;
	}
	public void setClinicPathRecordId(String clinicPathRecordId) {
		this.clinicPathRecordId = clinicPathRecordId;
	}
	public String getClinicPathId() {
		return clinicPathId;
	}
	public void setClinicPathId(String clinicPathId) {
		this.clinicPathId = clinicPathId;
	}
	public String getClinicPathName() {
		return clinicPathName;
	}
	public void setClinicPathName(String clinicPathName) {
		this.clinicPathName = clinicPathName;
	}
	public String getExitTxt() {
		return exitTxt;
	}
	public void setExitTxt(String exitTxt) {
		this.exitTxt = exitTxt;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getPatientTimes() {
		return patientTimes;
	}
	public void setPatientTimes(String patientTimes) {
		this.patientTimes = patientTimes;
	}
	public String getHospitalNo() {
		return hospitalNo;
	}
	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}
}
