package com.founder.fmdm.service.term.dto;


import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
@Entity
public class ViewsDto {
	/**
	 * 视图ID
	 */
	@Id
	@Column(name = "VIEW_ID")
	private String viewId;
	/**
	 * 视图名称
	 */
	@Column(name = "VIEW_NAME")	
	private String viewName;
	/**
	 * 视图类型
	 */
	@Column(name = "VIEW_TYPE")	
	private String viewType;
	/**
	 * 视图类型描述
	 */
	@Column(name = "VIEW_TYPE_CN")	
	private String viewTypeCN;


	/**
	 * 术语ID
	 */
	@Column(name = "DICT_ID")	
	private String dictId;
	
	/**
	 * 术语名称
	 */
	@Column(name = "DICT_NAME")
	private String dictName;	
	/**
	 * 对应表
	 */
	@Column(name = "TABLE_NAME")
	private String tableName;
	/**
	 * 新增操作
	 */
	@Column(name = "ADD_FLG")	
	private String addFlg;
	/**
	 * 删除操作
	 */
	@Column(name = "DELETE_FLG")
	private String deleteFlg;	
	/**
	 * 审批操作
	 */
	@Column(name = "APPROVE_FLG")
	private String approveFlg;	
	/**
	 * 发布操作
	 */
	@Column(name = "RELEASE_FLG")
	private String releaseFlg;
	
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

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getViewTypeCN() {
		return viewTypeCN;
	}

	public void setViewTypeCN(String viewTypeCN) {
		this.viewTypeCN = viewTypeCN;
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

}
