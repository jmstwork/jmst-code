package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class ViewTypeDto {
	
	@Column(name = "cd_div")
	private String viewTypeCode;
	
	@Column(name = "cd_value")
	private String viewTypeName;

	public String getViewTypeCode() {
		return viewTypeCode;
	}

	public void setViewTypeCode(String viewTypeCode) {
		this.viewTypeCode = viewTypeCode;
	}

	public String getViewTypeName() {
		return viewTypeName;
	}

	public void setViewTypeName(String viewTypeName) {
		this.viewTypeName = viewTypeName;
	}
	
	

}
