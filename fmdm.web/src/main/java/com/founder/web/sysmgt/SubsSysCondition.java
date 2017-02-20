package com.founder.web.sysmgt;

import com.founder.web.dto.PagingDto;

public class SubsSysCondition extends PagingDto{

	private String uniKey;
	
	private String sysId;
	
	private String sysName;
	
	private String sysApply;
	
	private String hospitalId;
	
	private String hospitalName;
	
	private String code;
	
	private String name;
	
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

	public String getSysApply() {
		return sysApply;
	}

	public void setSysApply(String sysApply) {
		this.sysApply = sysApply;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
	}

	
}
