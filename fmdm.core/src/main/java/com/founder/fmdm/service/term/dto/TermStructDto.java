package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
public class TermStructDto {
	
	@Column(name = "DOMA_ROWNUMBER_")
	String rownum;
	
	/**
	 * 术语结构ID
	 */
	@Id
	@Column(name = "DICT_ID")
	private String dictId;

    /**
	 * 术语结构名称
	 */
	@Column(name = "DICT_NAME")
	private String dictName;

    /**
	 * 是否可编辑
	 */
	@Column(name = "IS_EDIT")
	private String isEdit;

    /**
	 * 结构状态
	 */
	@Column(name = "IS_SAME")
	private String isSame;

	private String isSameText;
    /**
	 * 术语状态
	 */
	@Column(name = "STATUS")
	private String status;

    /**
	 * 术语结构类型ID
	 */
	@Column(name = "TYPE_CD")
	private String typeCd;
    
	
	@Column(name = "TYPE_NAME")
    String typeName;
	
    /**
	 * 对应表名
	 */
	@Column(name = "TABLE_NAME")
	private String tableName;
    
    /**
	 * 版本号
	 */
	@Column(name = "ITEM_VERSION")
	private Integer itemVersion;
    
    /**
	 * 服务ID
	 */
	@Column(name = "SERVICE_ID")
	private String serviceId;
    
    /**
	 * 提供系统
	 */
	@Column(name = "SYS_ID")
	private String sysId;
    
    /**
	 * 更新次数
	 */
	@Column(name = "UPDATE_COUNT")
	private Integer updateCount;

    /**
     * 是否可编辑
     */
	private String isEditValue;
    
	
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
	
	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public String getSysId() {
		return sysId;
	}
	
	public void setSysId(String sysId) {
		this.sysId = sysId;
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

	public void setIsSameText(String isSameText) {
		this.isSameText = isSameText;
	}

	public String getIsSameText() {
		String result = "";
		if("N".equalsIgnoreCase(this.isSame)){
			result = "不一致";
		}else if("Y".equalsIgnoreCase(this.isSame)){
			result = "一致";
		}
		return result;
	}
}
