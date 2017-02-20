package com.founder.fmdm.service.sysmgt.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "sys_data_manage")
public class DataManageDto {
	
	/**
	 * 字段ID
	 */
    @Id
    @Column(name = "DATA_ID")
	private String dataId;
    
    /**
	 * 数据源名称
	 */
    @Column(name = "DATA_NAME")
	private String dataName;
    
    /**
	 *  对应表名
	 */
    @Column(name = "TABLE_NAME")
	private String tableName;
    
    /**
	 * 备注
	 */
    @Column(name = "DATA_MOMO")
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
