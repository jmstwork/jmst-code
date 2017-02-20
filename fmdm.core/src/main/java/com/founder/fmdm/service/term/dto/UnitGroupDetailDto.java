package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Entity;

@Entity
public class UnitGroupDetailDto {
	private String unitGroupId;
	
	private String unitId;
	
	private String unitName;
	
	private String pyCode;
	
	
	private String hospitalCode;

	public String getUnitGroupId() {
		return unitGroupId;
	}

	public void setUnitGroupId(String unitGroupId) {
		this.unitGroupId = unitGroupId;
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

	public String getPyCode() {
		return pyCode;
	}

	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}


	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

}
