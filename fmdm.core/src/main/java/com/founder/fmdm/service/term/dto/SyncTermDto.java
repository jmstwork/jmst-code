package com.founder.fmdm.service.term.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
public class SyncTermDto {

	
	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "SYNC_ID")
	private String syncId;
	/**
	 * 术语ID
	 */
	@Id
	@Column(name = "DICT_ID")
	private String dictId;
	/**
	 * 术语名称
	 */ 
	@Column(name = "DICT_NAME")
	private String dictName;
	/**
	 * 术语表名
	 */
	@Column(name = "DICT_TABLE_NAME")
	private String dictTableName;
	/**
	 * 字典状态 0：未启用；1：已启用
	 */
	@Column(name = "DICT_STATUS")
	private int dictStatus;
	/**
	 * 字典状态中文
	 */
	private String dictStatusCh;
	/**
	 * 同步状态 0：未开始；1：等待中；2：执行中
	 */
	@Column(name = "SYNC_STATUS")
	private int syncStatus;
	/**
	 * 同步时间间隔
	 */
	@Column(name = "SYNC_INTERVAL")
	private int syncInterval;
	/**
	 * 数据源Id
	 */
	@Column(name = "DATASOURCE_ID")
	private String datasourceId;
	/**
	 * 数据源名称
	 */
	@Column(name = "DATASOURCE_name")
	private String datasourceName;
	/**
	 * 源数据源SQL
	 */
	@Column(name = "FROM_DATASOURCE_SQL")
	private String fromDatasourceSql;
	/**
	 * 目标数据源SQL
	 */
	@Column(name = "TO_DATASOURCE_SQL")
	private String toDatasourceSql;
	
	/**
	 * 同步结果 0：失败；1；成功
	 */
	@Column(name = "SYNC_RESULT")
	private int syncResult;
	/**
	 * 同步结果中文
	 */
	private String syncResultCh;
	/**
	 * 同步结果
	 */
	@Column(name = "LOG_TXT")
	private String logTxt;
	
	@Column(name = "UPDATE_COUNT")
	private int updateCount;
	@Column(name = "CREATE_BY")
	private String createBy;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;
	@Column(name = "LAST_UPDATE_TIME")
	private Date lastUpdateTime;
	@Column(name = "DELETE_BY")
	private String deleteBy;
	@Column(name = "DELETE_TIME")
	private Date deleteTime;
	@Column(name = "DELETE_FLG")
	private int deleteFlg;
	
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
	public String getDictTableName() {
		return dictTableName;
	}
	public void setDictTableName(String dictTableName) {
		this.dictTableName = dictTableName;
	}
	public int getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(int dictStatus) {
		this.dictStatus = dictStatus;
	}
	public int getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}
	public int getSyncInterval() {
		return syncInterval;
	}
	public void setSyncInterval(int syncInterval) {
		this.syncInterval = syncInterval;
	}
	public String getDatasourceId() {
		return datasourceId;
	}
	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}
	public String getFromDatasourceSql() {
		return fromDatasourceSql;
	}
	public void setFromDatasourceSql(String fromDatasourceSql) {
		this.fromDatasourceSql = fromDatasourceSql;
	}
	public String getToDatasourceSql() {
		return toDatasourceSql;
	}
	public void setToDatasourceSql(String toDatasourceSql) {
		this.toDatasourceSql = toDatasourceSql;
	}
	
	public int getSyncResult() {
		return syncResult;
	}
	public void setSyncResult(int syncResult) {
		this.syncResult = syncResult;
	}
	public String getDictStatusCh() {
		return dictStatusCh;
	}
	public void setDictStatusCh(String dictStatusCh) {
		this.dictStatusCh = dictStatusCh;
	}
	public String getSyncResultCh() {
		return syncResultCh;
	}
	public void setSyncResultCh(String syncResultCh) {
		this.syncResultCh = syncResultCh;
	}
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getDeleteBy() {
		return deleteBy;
	}
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public int getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	public String getLogTxt() {
		return logTxt;
	}
	public void setLogTxt(String logTxt) {
		this.logTxt = logTxt;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public String getSyncId() {
		return syncId;
	}
	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}
	
}
