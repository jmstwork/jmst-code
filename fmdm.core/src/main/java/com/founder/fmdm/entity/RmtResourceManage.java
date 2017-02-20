package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "rmt_resource_manage")
public class RmtResourceManage {
	/**
	 * 资源Id
	 */
	@Id
	@Column(name = "RESOURCE_ID")
	private String resourceId;
	/**
	 * 资源名称
	 */
	@Column(name = "RESOURCE_NAME")
	private String resourceName;
	/**
	 * 系统Id
	 */
	@Column(name = "SYSTEM_ID")
	private String systemId;
	
	/**
	 * 排序
	 */
	@Column(name = "DISP_ORDER")
	private String dispOrder;
	/**
	 * 系统名称
	 */
	@Column(name = "SYSTEM_NAME")
	private String systemName;
	/**
	 * 链接
	 */
	@Column(name = "URL")
	private String url;

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

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(String dispOrder) {
		this.dispOrder = dispOrder;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public void setDeleteFlg(Integer deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
