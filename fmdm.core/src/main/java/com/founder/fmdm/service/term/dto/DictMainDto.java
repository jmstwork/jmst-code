package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
public class DictMainDto {
	/**
	 * 术语结构ID
	 */
	private String dictId;

    /**
	 * 术语结构名称
	 */
	private String dictName;

    /**
	 * 是否可编辑
	 */
	private String isEdit;

    /**
	 * 术语结构状态(一致不一致)
	 */
	private String isSame;

    /**
	 * 术语状态
	 */
	private String status;

    /**
	 * 术语结构类型ID
	 */
	private String typeCd;
    
    /**
	 * 对应表名
	 */
	private String tableName;
    
    /**
	 * 版本号
	 */
	private Integer itemVersion;
    
    /**
	 * 服务ID
	 */
	private String serviceId;
    
    /**
	 * 提供系统
	 */
	private String supplySys;
    
    /**
	 * 更新次数
	 */
	private Integer updateCount;

    /**
     * 是否可编辑
     */
	private String isEditValue;
    
    /**
     * 结构状态
     */
	private String isDbValue;
    
    /**
     * 术语状态
     */
	private String statusValue;
    
    /**
     * 提供系统名称
     */
	private String supplySysName;
    
	private String title;
    
	private String titleName;
	
	private String viewTypeId;
	
	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsSame() {
		return isSame;
	}

	public void setIsSame(String isSame) {
		this.isSame = isSame;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Integer itemVersion) {
		this.itemVersion = itemVersion;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getSupplySys() {
		return supplySys;
	}

	public void setSupplySys(String supplySys) {
		this.supplySys = supplySys;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public String getIsEditValue() {
		return isEditValue;
	}

	public void setIsEditValue(String isEditValue) {
		this.isEditValue = isEditValue;
	}

	public String getIsDbValue() {
		return isDbValue;
	}

	public void setIsDbValue(String isDbValue) {
		this.isDbValue = isDbValue;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getSupplySysName() {
		return supplySysName;
	}

	public void setSupplySysName(String supplySysName) {
		this.supplySysName = supplySysName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getViewTypeId() {
		return viewTypeId;
	}

	public void setViewTypeId(String viewTypeId) {
		this.viewTypeId = viewTypeId;
	}
}
