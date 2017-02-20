package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;


@Entity
@Table(name="AUD_EVENT")
public class AudEvent{
	@Id
	@Column(name = "event_Code")
	private String eventCode;
	
	@Column(name = "event_Name")
	private String eventName;
	
	@Column(name="DELETE_FLG")
	private int deleteFlg;
	
	@Column(name="DELETE_BY")
	private String deleteBy;
	
	@Column(name="DELETE_DT")
	private Date deleteDt;
	
	@Column(name = "create_By")
	private String createBy;
	
	@Column(name="create_Dt")
	private Date createDt;
	
	@Column(name = "update_By")
	private String updateBy;
	
	@Column(name="UPDATE_DT")
	private Date updateDt;
	
	 /**
     *  更新次数
     */
	@Version
	@Column(name = "UPDATE_TIMES")
    private int updateTimes;
	
	

	public int getUpdateTimes() {
		return updateTimes;
	}
	public void setUpdateTimes(int updateTimes) {
		this.updateTimes = updateTimes;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
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
	
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public String getDeleteBy() {
		return deleteBy;
	}
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}
	public Date getDeleteDt() {
		return deleteDt;
	}
	public void setDeleteDt(Date deleteDt) {
		this.deleteDt = deleteDt;
	}
	
	
}
