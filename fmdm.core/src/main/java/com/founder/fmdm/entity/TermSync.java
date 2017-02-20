package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name="sync_dict")
public class TermSync {

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "SYNC_ID")
	private String syncId;
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
	 * 术语表名
	 */
	@Column(name = "DICT_TABLE_NAME")
	private String dictTableName;
	/**
	 * 字典状态 0：未启用；1：已启用
	 */
	@Column(name = "DICT_STATUS")
	private Integer dictStatus;
	/**
	 * 同步状态 0：未开始；1：等待中；2：执行中
	 */
	@Column(name = "SYNC_STATUS")
	private Integer syncStatus;
	/**
	 * 同步时间间隔
	 */
	@Column(name = "SYNC_INTERVAL")
	private Integer syncInterval;
	/**
	 * 数据源Id
	 */
	@Column(name = "DATASOURCE_ID")
	private String datasourceId;
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
	 * 开始时间
	 */
	@Column(name = "START_TIME")
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	@Column(name = "END_TIME")
	private Date endtime;
	
	/**
	 * 同步结果
	 */
	@Column(name = "SYNC_RESULT")
	private Integer syncResult;
	
	/**
	 * 同步结果
	 */
	@Column(name = "LOG_TXT")
	private String logTxt;
	
	@Version
	@Column(name = "UPDATE_COUNT")
	private Integer updateCount;
	
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
	private Integer deleteFlg;
	
	
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
	public Integer getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(Integer dictStatus) {
		this.dictStatus = dictStatus;
	}
	public Integer getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}
	public Integer getSyncInterval() {
		return syncInterval;
	}
	public void setSyncInterval(Integer syncInterval) {
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
	public Integer getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(Integer updateCount) {
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
	public Integer getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(Integer deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Integer getSyncResult() {
		return syncResult;
	}
	public void setSyncResult(Integer syncResult) {
		this.syncResult = syncResult;
	}
	public String getLogTxt() {
		return logTxt;
	}
	public void setLogTxt(String logTxt) {
		this.logTxt = logTxt;
	}
	public String getSyncId() {
		return syncId;
	}
	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}
	
}
