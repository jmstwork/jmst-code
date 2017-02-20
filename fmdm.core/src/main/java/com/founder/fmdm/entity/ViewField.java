package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "VIEW_FIELD")
public class ViewField {
	/**
	 * 视图字段ID
	 */
	@Id
	@Column(name = "VIEW_FIELD_ID")
	String viewFieldId;
	/**
	 * 视图ID
	 */
	@Id
	@Column(name = "VIEW_ID")
	String viewId;

	/**
	 * 字段ID
	 */
	@Column(name = "FIELD_ID")
	String fieldId;

	/**
	 * 编辑标志
	 */
	@Column(name = "EDIT_FLG")
	Integer editFlg;
	/**
	 * 检索标志
	 */
	@Column(name = "RETRIEVAL_FLG")
	Integer retrievalFlg;

	/**
	 * 列表项显示标志
	 */
	@Column(name = "ITEM_FLG")
	Integer itemFlg;

	/**
	 * 列表项顺序
	 */
	@Column(name = "ITEM_ORDER")
	Integer itemOrder;

	/**
	 * 排序标志
	 */
	@Column(name = "ORDER_FLG")
	Integer orderFlg;

	/**
	 * 排序顺序
	 */
	@Column(name = "FIELD_ORDER")
	Integer fieldOrder;
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
	@Column(name = "DELETE_FLG")
	Integer deleteFlg;

	public String getViewFieldId() {
		return viewFieldId;
	}

	public void setViewFieldId(String viewFieldId) {
		this.viewFieldId = viewFieldId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Integer getEditFlg() {
		return editFlg;
	}

	public void setEditFlg(Integer editFlg) {
		this.editFlg = editFlg;
	}

	public Integer getRetrievalFlg() {
		return retrievalFlg;
	}

	public void setRetrievalFlg(Integer retrievalFlg) {
		this.retrievalFlg = retrievalFlg;
	}

	public Integer getItemFlg() {
		return itemFlg;
	}

	public void setItemFlg(Integer itemFlg) {
		this.itemFlg = itemFlg;
	}

	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	public Integer getOrderFlg() {
		return orderFlg;
	}

	public void setOrderFlg(Integer orderFlg) {
		this.orderFlg = orderFlg;
	}

	public Integer getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
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
