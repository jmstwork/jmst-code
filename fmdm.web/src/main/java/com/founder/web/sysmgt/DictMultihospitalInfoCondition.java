package com.founder.web.sysmgt;

import com.founder.web.dto.PagingDto;

public class DictMultihospitalInfoCondition extends PagingDto{
	
	private String uniKey;

	private String hospitalCode;
	
	private String hospitalName;
	
	public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
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
	
}
