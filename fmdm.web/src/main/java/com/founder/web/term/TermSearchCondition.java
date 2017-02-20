package com.founder.web.term;

import com.founder.web.dto.PagingDto;

public class TermSearchCondition extends PagingDto {
	private String dictId;
	
	private String serviceId;
	private String dictName;
	private String typeCd;
	
	private String tableName;
	private String sysId;
	
	private String paramData;
	//术语同步搜索条件专用
	private String systemId;
	
	private String operation;
	
	private String autoOpen;
	
	private String sqlIdArr;
	
	private String sqlContentArr;
	
	private String isSame;
	
	private String fieldId;
	
	private String fieldName;
	
	private String fieldDesc;
	
	private String fieldType;
	
	private String length;
	
	private String precision;
	
	private String defaultValue;
	
	private String isMust;
	
	private String isPrimary;
	
	private String dropColumn;

	//用户管理
	/**
	 * 用户ID
	 */
	private String userNo;
	/**
	 *  用户名
	 */
	private String userName;
	/**
	 *  账户状态
	 */
	private int status;
	
	private String statusName;
	
	private String indexNames;
	
	//订阅管理搜索条件
	String id;
	/**
	 * 科室组Id
	 */
	private String unitGroupId;
	
	/**
	 * 科室组名称 
	 */
	private String unitGroupName;
	
	private String hospitalCode;
	
	private String unitId;
	
	private String unitName;
	
	private String unitGroupType;
	
	private String unitGroupDesc;
	
	private int flag;
	
	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
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

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getAutoOpen() {
		return autoOpen;
	}

	public void setAutoOpen(String autoOpen) {
		this.autoOpen = autoOpen;
	}

	public String getSqlIdArr() {
		return sqlIdArr;
	}

	public void setSqlIdArr(String sqlIdArr) {
		this.sqlIdArr = sqlIdArr;
	}

	public String getSqlContentArr() {
		return sqlContentArr;
	}

	public void setSqlContentArr(String sqlContentArr) {
		this.sqlContentArr = sqlContentArr;
	}

	public String getIsSame() {
		return isSame;
	}

	public void setIsSame(String isSame) {
		this.isSame = isSame;
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

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getDropColumn() {
		return dropColumn;
	}

	public void setDropColumn(String dropColumn) {
		this.dropColumn = dropColumn;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getIndexNames() {
		return indexNames;
	}

	public void setIndexNames(String indexNames) {
		this.indexNames = indexNames;
	}

	public String getUnitGroupId() {
		return unitGroupId;
	}

	public void setUnitGroupId(String unitGroupId) {
		this.unitGroupId = unitGroupId;
	}

	public String getUnitGroupName() {
		return unitGroupName;
	}

	public void setUnitGroupName(String unitGroupName) {
		this.unitGroupName = unitGroupName;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitGroupType() {
		return unitGroupType;
	}

	public void setUnitGroupType(String unitGroupType) {
		this.unitGroupType = unitGroupType;
	}

	public String getUnitGroupDesc() {
		return unitGroupDesc;
	}

	public void setUnitGroupDesc(String unitGroupDesc) {
		this.unitGroupDesc = unitGroupDesc;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
