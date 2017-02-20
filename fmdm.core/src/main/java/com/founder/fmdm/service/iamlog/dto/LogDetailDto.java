package com.founder.fmdm.service.iamlog.dto;

import org.seasar.doma.Entity;

@Entity
public class LogDetailDto {
	
	private String logId;
	
	private String operorId;
	
	private String operDt;
	
	private String operModu;
	
	private String resrName;
	
	private String operObj;
	
	private String optName;
	
	private String operCont;
	
	private String userName;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getOperorId() {
		return operorId;
	}

	public void setOperorId(String operorId) {
		this.operorId = operorId;
	}

	public String getOperDt() {
		return operDt;
	}

	public void setOperDt(String operDt) {
		this.operDt = operDt;
	}

	public String getOperModu() {
		return operModu;
	}

	public void setOperModu(String operModu) {
		this.operModu = operModu;
	}

	public String getResrName() {
		return resrName;
	}

	public void setResrName(String resrName) {
		this.resrName = resrName;
	}

	public String getOperObj() {
		return operObj;
	}

	public void setOperObj(String operObj) {
		this.operObj = operObj;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOperCont() {
		return operCont;
	}

	public void setOperCont(String operCont) {
		this.operCont = operCont;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
