package com.founder.fmdm.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name="ROLE_AUTHORITY")
public class RoleAuthority {
	/**
	 * 菜单所有的按钮集合，暂时废弃
	 */
	@Column(name="BUTTONS")
	private String buttons;
	
	/**
	 * 每条角色权限记录的id
	 */
	@Id
	@Column(name="ROLE_AUTHORITY_ID")
	private String roleAuthorityId;
	
	/**
	 * 角色id
	 */
	@Column(name="ROLE_ID")
	private String roleId;
	
	/**
	 * 菜单id
	 */
	@Column(name="SMB_ID")
	private String smbId;
	
	
	public String getButtons() {
		return buttons;
	}
	public String getRoleAuthorityId() {
		return roleAuthorityId;
	}
	public String getRoleId() {
		return roleId;
	}
	public String getSmbId() {
		return smbId;
	}
	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
	public void setRoleAuthorityId(String roleAuthorityId) {
		this.roleAuthorityId = roleAuthorityId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public void setSmbId(String smbId) {
		this.smbId = smbId;
	}
}
