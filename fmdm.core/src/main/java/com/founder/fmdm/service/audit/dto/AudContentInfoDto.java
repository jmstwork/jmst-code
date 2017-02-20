package com.founder.fmdm.service.audit.dto;

import org.seasar.doma.Entity;

@Entity
public class AudContentInfoDto {

	private String audId;
	private String rowNo;
	private String rowAction;
	private String drugName;
	private String packageNo;
	private String drugPriceOld;
	private String drugPriceNew;
	private String effectDate;
	
	
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
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getDrugPriceOld() {
		return drugPriceOld;
	}
	public void setDrugPriceOld(String drugPriceOld) {
		this.drugPriceOld = drugPriceOld;
	}
	public String getDrugPriceNew() {
		return drugPriceNew;
	}
	public void setDrugPriceNew(String drugPriceNew) {
		this.drugPriceNew = drugPriceNew;
	}
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
}
