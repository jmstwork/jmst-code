package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "tse_users")
public class TseUsers 
{	
    @Column(name = "user_id")
    String userId;
    
    @Id
    @Column(name = "user_account")
    String userAccount;

    @Column(name = "user_name")
    String userName;

    @Column(name = "user_passwd")
    String userPasswd;

    @Column(name = "enabled")
    Integer enabled;

    @Column(name = "super_user")
    Integer superUser;
    
    @Column(name = "user_mobile")
    String userMobile;
    
    @Column(name = "user_mail")
    String userMail;
    
    @Column(name = "enable_flag")
    Integer enableFlag;

    @Column(name = "memo")
    String memo;
    
    @Column(name = "create_by")
    String createBy;
    
    @Column(name = "create_time")
    Date createTime;
    
    @Column(name = "last_update_by")
    String lastUpdateBy;
    
    @Column(name = "last_update_time")
    Date lastUpdateTime;
    
    @Version
    @Column(name = "update_count")
    Integer updateCount;
    
    @Column(name = "delete_by")
    String deleteBy;
    
    @Column(name = "delete_time")
    Date deleteTime;
    
    @Column(name = "delete_flg")
    Integer deleteFlg;
    
    @Column(name = "user_desc")
    String userDesc;
    
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

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getSuperUser() {
		return superUser;
	}

	public void setSuperUser(Integer superUser) {
		this.superUser = superUser;
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

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Integer deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	
}
