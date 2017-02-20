package com.founder.fmdm.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "iam_account_info")
public class TsysAccountInfo 
{
    @Id
    @Column(name = "user_no")
    String userNo;

    @Column(name = "user_name")
    String userName;

    @Column(name = "passwd")
    String passwd;
    
    @Column(name = "mobile")
    String mobile;
    
    @Column(name = "email")
    String email;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void sePasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
