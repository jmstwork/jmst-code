package com.founder.web.sysmgt;

import com.founder.web.dto.PagingDto;

public class DataManageCondition extends PagingDto{
	
	private String dataId;
	private String dataName;
	private String tableName;
	private String dataMomo;
	
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDataMomo() {
		return dataMomo;
	}
	public void setDataMomo(String dataMomo) {
		this.dataMomo = dataMomo;
	}
}
