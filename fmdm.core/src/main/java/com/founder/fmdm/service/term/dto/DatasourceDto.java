package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
public class DatasourceDto {
	/**
	 * 数据源Id
	 */
	@Id
	@Column(name = "DATASOURCE_ID")
	String datasourceId;

	/**
	 * 数据源名称
	 */
	@Column(name = "DATASOURCE_NAME")
	String datasourceName;
	/**
	 * 数据库类型
	 */
	@Column(name = "JDBC_SPECIFIC")
	String jdbcSpecific;
	
	/**
	 * 数据库类型
	 */
	String jdbcSpecificName;


	/**
	 * 数据库连接
	 */
	@Column(name = "JDBC_URL")
	String jdbcUrl;

	/**
	 * 用户名
	 */
	@Column(name = "JDBC_USER_NAME")
	String jdbcUserName;

	/**
	 * 密码
	 */
	@Column(name = "JDBC_PASSWORD")
	String jdbcPassword;

	@Column(name = "SYSTEM_ID")
	String systemId;

	/**
	 * 提供系统名称
	 */
	@Column(name = "SYS_NAME")
	String sysName;
	/**
	 *  更新次数
	 */
	@Column(name = "update_count")
	String updateCount;

	public String getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getJdbcSpecific() {
		return jdbcSpecific;
	}

	public void setJdbcSpecific(String jdbcSpecific) {
		this.jdbcSpecific = jdbcSpecific;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUserName() {
		return jdbcUserName;
	}

	public void setJdbcUserName(String jdbcUserName) {
		this.jdbcUserName = jdbcUserName;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(String updateCount) {
		this.updateCount = updateCount;
	}

	public String getJdbcSpecificName() {
		return jdbcSpecificName;
	}

	public void setJdbcSpecificName(String jdbcSpecificName) {
		this.jdbcSpecificName = jdbcSpecificName;
	}

}
