package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Entity;

@Entity
public class UserInfoDto {
	/**
	 * 用户ID
	 */
	private String userNo;
	/**
	 * 密码
	 */
	private String passWD;
	/**
	 *  用户名
	 */
	private String userName;
	/**
	 *  性别编码
	 */
	private int sex;
	/**
	 *  性别描述
	 */
	private String sexName;
	/**
	 *  电子邮件
	 */
	private String email;
	/**
	 *  手机
	 */
	private String mobile;
	/**
	 *  部门编码
	 */
	private String deptCode;
	/**
	 *  部门名称
	 */
	private String deptName;
	/**
	 *  职务编码
	 */
	private String groupCd;
	/**
	 *  职务名称
	 */
	private String postName;
	/**
	 *  状态
	 */
	private int status;
	/**
	 *  状态名称
	 */
	private String statusName;
	/***
	 *  在岗状态
	 */
	private String employmentStatusCd;
	/***
	 *  在岗状态字典name
	 */
	private String inPostStatus;
	/***
	 *  人员类型
	 */
	private String employeeTypeCd;
	/***
	 *  人员类型name
	 */
	private String personType;
	/***
	 *  入职日期
	 */
	private String entryDate;
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getPassWD() {
		return passWD;
	}
	public void setPassWD(String passWD) {
		this.passWD = passWD;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getGroupCd() {
		return groupCd;
	}
	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getEmploymentStatusCd() {
		return employmentStatusCd;
	}
	public void setEmploymentStatusCd(String employmentStatusCd) {
		this.employmentStatusCd = employmentStatusCd;
	}
	public String getInPostStatus() {
		return inPostStatus;
	}
	public void setInPostStatus(String inPostStatus) {
		this.inPostStatus = inPostStatus;
	}
	public String getEmployeeTypeCd() {
		return employeeTypeCd;
	}
	public void setEmployeeTypeCd(String employeeTypeCd) {
		this.employeeTypeCd = employeeTypeCd;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	
	
}
