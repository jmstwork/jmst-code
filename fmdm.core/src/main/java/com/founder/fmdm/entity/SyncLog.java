package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "sync_log")
public class SyncLog {

	/**
	 * 日志ID
	 */
	@Id
	@Column(name = "LOG_ID")
	private String logId;
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
	 * 开始时间
	 */
	@Column(name = "START_TIME")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@Column(name = "END_TIME")
	private Date endTime;
	/**
	 * 同步结果
	 */
	@Column(name = "SYNC_RESULT")
	private int syncResult;
	/**
	 * 日志明细
	 */
	@Column(name = "LOG_TXT")
	private String logTxt;
	
	@Version
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
	
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getSyncResult() {
		return syncResult;
	}
	public void setSyncResult(int syncResult) {
		this.syncResult = syncResult;
	}
	public String getLogTxt() {
		return logTxt;
	}
	public void setLogTxt(String logTxt) {
		this.logTxt = logTxt;
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
}
