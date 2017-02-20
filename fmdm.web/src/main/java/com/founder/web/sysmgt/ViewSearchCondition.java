package com.founder.web.sysmgt;

import com.founder.web.dto.PagingDto;

/**
 * @author lishenggen
 * 
 */
public class ViewSearchCondition extends PagingDto {

	private String viewId;
	private String dictId;
	private String viewFieldId;

	/**
	 * 视图名称
	 */
	private String viewName;

	/**
	 * 视图类型
	 */
	private String viewType;

	/**
	 * 术语结构名称
	 */
	private String dictName;

	/**
	 * 术语表名
	 */
	private String tableName;
	/**
	 * 新增操作
	 */
	private String addFlg;
	/**
	 * 删除操作
	 */
	private String deleteFlg;
	/**
	 * 审批操作
	 */
	private String approveFlg;

	/**
	 * 发布操作
	 */
	private String releaseFlg;
	/**
	 * 頁面按鈕操作
	 */
	private String operation;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getViewFieldId() {
		return viewFieldId;
	}

	public void setViewFieldId(String viewFieldId) {
		this.viewFieldId = viewFieldId;
	}

	public String getAddFlg() {
		return addFlg;
	}

	public void setAddFlg(String addFlg) {
		this.addFlg = addFlg;
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getApproveFlg() {
		return approveFlg;
	}

	public void setApproveFlg(String approveFlg) {
		this.approveFlg = approveFlg;
	}

	public String getReleaseFlg() {
		return releaseFlg;
	}

	public void setReleaseFlg(String releaseFlg) {
		this.releaseFlg = releaseFlg;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

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

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

}