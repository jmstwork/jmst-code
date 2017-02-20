package com.founder.fmdm.service.audit.dto;

import org.seasar.doma.Entity;

@Entity
public class AudContentInfo004Dto {

	private String audId;
	private String rowNo;
	private String rowAction;
	private String patientId;
	private String domainId;
	private String clinicNo;
	private String hospitalNo;
	private String clinicDataCategory;
	private String clinicDataId;
	private String examineName;
	
	
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
	public String getClinicNo() {
		return clinicNo;
	}
	public void setClinicNo(String clinicNo) {
		this.clinicNo = clinicNo;
	}
	public String getHospitalNo() {
		return hospitalNo;
	}
	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}
	public String getClinicDataCategory() {
		return clinicDataCategory;
	}
	public void setClinicDataCategory(String clinicDataCategory) {
		this.clinicDataCategory = clinicDataCategory;
	}
	public String getClinicDataId() {
		return clinicDataId;
	}
	public void setClinicDataId(String clinicDataId) {
		this.clinicDataId = clinicDataId;
	}
	public String getExamineName() {
		return examineName;
	}
	public void setExamineName(String examineName) {
		this.examineName = examineName;
	}
}
