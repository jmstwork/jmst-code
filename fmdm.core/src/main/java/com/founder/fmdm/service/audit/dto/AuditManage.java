package com.founder.fmdm.service.audit.dto;

import org.seasar.doma.Entity;

@Entity
public class AuditManage {
	
	private String evenCode;
	
	private String evneName;
	
	private String rownum;

	public String getEvenCode() {
		return evenCode;
	}

	public void setEvenCode(String evenCode) {
		this.evenCode = evenCode;
	}

	public String getEvneName() {
		return evneName;
	}

	public void setEvneName(String evneName) {
		this.evneName = evneName;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
	
	

}
