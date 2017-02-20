package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "DICT_VIEW")
public class DictView {
	/**
	 * 视图ID
	 */
	@Id
	@Column(name = "VIEW_ID")
	String viewId;

	/**
	 * 术语ID
	 */
	@Column(name = "DICT_ID")
	String dictId;

	/**
	 * 视图名称
	 */
	@Column(name = "VIEW_NAME")
	String viewName;
	/**
	 * 视图类型
	 */
	@Column(name = "VIEW_TYPE")
	String viewType;

	/**
	 * 新增操作标志
	 */
	@Column(name = "ADD_FLG")
	Integer addFlg;

	/**
	 * 删除操作标志
	 */
	@Column(name = "DELETE_FLG")
	Integer deleteFlg;

	/**
	 * 审批操作标志
	 */
	@Column(name = "APPROVE_FLG")
	Integer approveFlg;

	/**
	 * 发布操作标志
	 */
	@Column(name = "RELEASE_FLG")
	Integer releaseFlg;
	/**
	 * 更新次数
	 */
	@Version
	@Column(name = "UPDATE_COUNT")
	Integer updateCount;

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
	@Column(name = "DELETE_FLAG")
	Integer deleteFlag;

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public Integer getAddFlg() {
		return addFlg;
	}

	public void setAddFlg(Integer addFlg) {
		this.addFlg = addFlg;
	}

	public Integer getApproveFlg() {
		return approveFlg;
	}

	public void setApproveFlg(Integer approveFlg) {
		this.approveFlg = approveFlg;
	}

	public Integer getReleaseFlg() {
		return releaseFlg;
	}

	public void setReleaseFlg(Integer releaseFlg) {
		this.releaseFlg = releaseFlg;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
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

	public Integer getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Integer deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
