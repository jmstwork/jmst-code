package com.founder.fmdm.entity;
import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "rmt_user_access_auth")
public class RmtUserAccessAuth 
{
	/**
	 *  资源Id
	 */
	@Id
	@Column(name = "RESOURCE_ID")
	private String resourceId;
	/**
	 *  人员Id
	 */
	@Column(name = "USER_ID")
	private String userId;

	/**
	 * 创建者
	 */
	@Column(name = "CREATE_BY")
	String createBy;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	Date createTime;

	/**
	 * 更新者
	 */
	@Column(name = "LAST_UPDATE_BY")
	String lastUpdateBy;

	/**
	 * 更新时间
	 */
	@Column(name = "LAST_UPDATE_TIME")
	Date lastUpdateTime;

	/**
	 * 更新次数
	 */
	@Version
	@Column(name = "UPDATE_COUNT")
	Integer updateCount;

	/**
	 * 删除者
	 */
	@Column(name = "DELETE_BY")
	String deleteBy;

	/**
	 * 删除时间
	 */
	@Column(name = "DELETE_TIME")
	Date deleteTime;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETE_FLG")
	Integer deleteFlg;
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
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
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
	
	
}
