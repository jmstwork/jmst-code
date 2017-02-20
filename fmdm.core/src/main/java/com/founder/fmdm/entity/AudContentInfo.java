package com.founder.fmdm.entity;


import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "AUD_CONTENT_INFO")
public class AudContentInfo{
	@Id
	@Column(name = "ITEM_ID")
	private String itemId;
	
	@Column(name = "ROW_NO")
	private String rowNo;
	
	@Column(name = "CONTENT_NO")
	private String contentNo;
	
	@Column(name = "AUD_ID")
	private String audId;
	
	@Column(name = "ROW_NAME")
	private String rowName;
	
	@Column(name = "ROW_ACTION")
	private String rowAction;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "OLD_VALUE")
	private String oldValue;
	
	@Column(name = "NEW_VALUE")
	private String newValue;
	
	
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
	@Column(name = "CREATE_DT")
    private Date createDt;
    /**
     *  更新者
     */
	@Column(name = "UPDATE_BY")
    private String updateBy;
	
	/**
     *  更新时间
     */
	@Column(name = "UPDATE_DT")
    private Date updateDt;
    /**
     *  删除者
     */
	@Column(name = "DELETE_BY")
    private String deleteBy;
    /**
     *  删除时间
     */
	@Column(name = "DELETE_DT")
    private Date deleteDt;
    /**
     *  更新次数
     */
	@Version
	@Column(name = "UPDATE_TIMES")
    private int updateTimes;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getAudId() {
		return audId;
	}
	public void setAudId(String audId) {
		this.audId = audId;
	}
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public String getRowAction() {
		return rowAction;
	}
	public void setRowAction(String rowAction) {
		this.rowAction = rowAction;
	}
	public String getContentNo() {
		return contentNo;
	}
	public void setContentNo(String contentNo) {
		this.contentNo = contentNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
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
	public int getUpdateTimes() {
		return updateTimes;
	}
	public void setUpdateTimes(int updateTimes) {
		this.updateTimes = updateTimes;
	}
	
	

}
