package com.founder.fmdm.service.term.dto;


import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
public class ViewsFieldsDto {
	/**
	 * 视图字段ID
	 */
	@Id
	@Column(name = "VIEW_FIELD_ID")
	private String viewFieldId;
	/**
	 * 视图ID
	 */
	@Column(name = "VIEW_ID")
	private String viewId;

	/**
	 * 字段ID
	 */
	@Column(name = "FIELD_ID")
	private String fieldId;
	/**
	 * 字段名称
	 */
	@Column(name = "FIELD_NAME")
	private String fieldName;
	/**
	 * 字段描述
	 */
	@Column(name = "FIELD_DESC")
	private String fieldDesc;

	/**
	 * 编辑标志
	 */
	@Column(name = "EDIT_FLG")
	private String editFlg;
	/**
	 * 检索标志
	 */
	@Column(name = "RETRIEVAL_FLG")
	private String retrievalFlg;

	/**
	 * 列表项显示标志
	 */
	@Column(name = "ITEM_FLG")
	private String itemFlg;

	/**
	 * 列表项顺序
	 */
	@Column(name = "ITEM_ORDER")
	private String itemOrder;

	/**
	 * 排序标志
	 */
	@Column(name = "ORDER_FLG")
	private String orderFlg;

	/**
	 * 排序顺序
	 */
	@Column(name = "FIELD_ORDER")
	private String fieldOrder;

	public String getViewFieldId() {
		return viewFieldId;
	}

	public void setViewFieldId(String viewFieldId) {
		this.viewFieldId = viewFieldId;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getEditFlg() {
		return editFlg;
	}

	public void setEditFlg(String editFlg) {
		this.editFlg = editFlg;
	}

	public String getRetrievalFlg() {
		return retrievalFlg;
	}

	public void setRetrievalFlg(String retrievalFlg) {
		this.retrievalFlg = retrievalFlg;
	}

	public String getItemFlg() {
		return itemFlg;
	}

	public void setItemFlg(String itemFlg) {
		this.itemFlg = itemFlg;
	}

	public String getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(String itemOrder) {
		this.itemOrder = itemOrder;
	}

	public String getOrderFlg() {
		return orderFlg;
	}

	public void setOrderFlg(String orderFlg) {
		this.orderFlg = orderFlg;
	}

	public String getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(String fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

}
