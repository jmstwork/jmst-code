package com.founder.web.sysmgt;

import com.founder.web.dto.PagingDto;


public class UserSearchCondition extends PagingDto{

	private String userId;
	private String userAccount;
	private String userName;
	private String userPasswd;
	private String userMobile;
	private String userMail;
	private Integer enableFlg;
	private Integer superUser;
	private String memo;
	private String roleName;
	private String operateFlg;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPasswd() {
		return userPasswd;
	}
	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public Integer getEnableFlg() {
		return enableFlg;
	}
	public void setEnableFlg(Integer enableFlg) {
		this.enableFlg = enableFlg;
	}
	public Integer getSuperUser() {
		return superUser;
	}
	public void setSuperUser(Integer superUser) {
		this.superUser = superUser;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOperateFlg() {
		return operateFlg;
	}
	public void setOperateFlg(String operateFlg) {
		this.operateFlg = operateFlg;
	}
}
