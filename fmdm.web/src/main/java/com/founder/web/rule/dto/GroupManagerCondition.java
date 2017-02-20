package com.founder.web.rule.dto;

import com.founder.web.dto.PagingDto;

public class GroupManagerCondition extends PagingDto {
	
	private String condGroName;
	
	private String condGroNameEn;
	
	private String groupId;
	
	private String operFlg;
	
	private String selectId;

	public String getCondGroName() {
		return condGroName;
	}

	public void setCondGroName(String condGroName) {
		this.condGroName = condGroName;
	}

	public String getCondGroNameEn() {
		return condGroNameEn;
	}

	public void setCondGroNameEn(String condGroNameEn) {
		this.condGroNameEn = condGroNameEn;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOperFlg() {
		return operFlg;
	}

	public void setOperFlg(String operFlg) {
		this.operFlg = operFlg;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

}
