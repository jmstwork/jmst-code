package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "IAM_LOG")
public class IamLog {
	
	/**
     *  日志ID
     */
	@Id
	@Column(name = "LOG_ID")
    private String logId;
    /**
     *  操作人ID
     */
	@Column(name = "OPEROR_ID")
    private String operorId;
    /**
     *  操作时间
     */
	@Column(name = "OPER_DT")
    private Date operDt;
    /**
     *  操作模块
     */
	@Column(name = "OPER_MODU")
    private String operModu;
    /**
     *  操作对象
     */
	@Column(name = "OPER_OBJ")
    private String operObj;
    /**
     *  操作内容
     */
	@Column(name = "OPER_CONT")
    private String operCont;
    /**
     *  删除标记
     */
	@Column(name = "DELETE_FLG")
    private int deleteFlg;
    /**
     *  创建者
     */
	@Column(name = "CREATE_BY")
    private String createBy;
    /**
     *  创建日期
     */
	@Column(name = "CREATE_TIME")
    private Date createTime;
    /**
     *  更新者
     */
	@Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;
    /**
     *  更新时间
     */
	@Column(name = "LAST_UPDATE_TIME")
    private Date lastUpdateTime;
    /**
     *  删除者
     */
	@Column(name = "DELETE_BY")
    private String deleteBy;
    /**
     *  删除时间
     */
	@Column(name = "DELETE_TIME")
    private Date deleteTime;
    /**
     *  更新次数
     */
	@Version
	@Column(name = "UPDATE_COUNT")
    private int updateCount;
	
	
	
	
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getOperorId() {
		return operorId;
	}
	public void setOperorId(String operorId) {
		this.operorId = operorId;
	}
	public Date getOperDt() {
		return operDt;
	}
	public void setOperDt(Date operDt) {
		this.operDt = operDt;
	}
	public String getOperModu() {
		return operModu;
	}
	public void setOperModu(String operModu) {
		this.operModu = operModu;
	}
	public String getOperObj() {
		return operObj;
	}
	public void setOperObj(String operObj) {
		this.operObj = operObj;
	}
	public String getOperCont() {
		return operCont;
	}
	public void setOperCont(String operCont) {
		this.operCont = operCont;
	}
	public int getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
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
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
    
    
}
