package com.founder.web.term;

import java.util.Map;

import com.founder.web.dto.PagingDto;

public class TermMainCondition extends PagingDto {
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
	 * 结构状态
	 */
	private String isDb;

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
    
    /**
     * 是否查询所有数据
     */
    private String searchAll;
    
    private Map<String,Object> mapSearch;
    
    
    
    private String title;
    
    private String titleName;
    
    private String jumpToItem;

    private String fieldId;
    
    private String fieldName;
    
    private String fieldType;
    
    private Integer fieldLength; 
    
    private String  isShow;
	
	private String  isPrimary;

	private String isMust;
	
    private String keyPrimary;
    
    private String uni_key;
    
	public Map<String, Object> getMapSearch() {
		return mapSearch;
	}

	public void setMapSearch(Map<String, Object> mapSearch) {
		this.mapSearch = mapSearch;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

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



	public String getIsDb() {
		return isDb;
	}

	public void setIsDb(String isDb) {
		this.isDb = isDb;
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

	public String getJumpToItem() {
		return jumpToItem;
	}

	public void setJumpToItem(String jumpToItem) {
		this.jumpToItem = jumpToItem;
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

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getKeyPrimary() {
		return keyPrimary;
	}

	public void setKeyPrimary(String keyPrimary) {
		this.keyPrimary = keyPrimary;
	}

	public String getUni_key() {
		return uni_key;
	}

	public void setUni_key(String uni_key) {
		this.uni_key = uni_key;
	}

	

	

	
    
    
}
