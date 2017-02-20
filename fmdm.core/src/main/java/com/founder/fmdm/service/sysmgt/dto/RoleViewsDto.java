package com.founder.fmdm.service.sysmgt.dto;

import java.util.List;

import com.founder.fmdm.service.term.dto.ViewsDto;

public class RoleViewsDto {

	private String roleId;
	
	private String roleName;
	
	private String memo;
	
	private List<ViewsDto> views;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<ViewsDto> getViews() {
		return views;
	}

	public void setViews(List<ViewsDto> views) {
		this.views = views;
	}

	
	
}
