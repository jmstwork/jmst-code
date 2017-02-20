package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name="iam_parm")
public class IamParm
{
	@Id
	@Column(name="par_name")
	private String parName;
	
	@Column(name="par_value")
	private String parValue;
	
	@Column(name="par_desc")
	private String parDesc;
	
	@Column(name="delete_flg")
	private int deleteFlg;
	
	@Column(name="create_by")
	private String createBy;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="last_update_by")
	private String lastUpdateBy;
	
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	
	@Column(name="delete_by")
	private String deleteBy;
	
	@Column(name="delete_time")
	private Date deleteTime;
	
	@Version
	@Column(name="update_count")
	private int updateCount;
	
	
	public String getParName() {
		return parName;
	}
	public void setParName(String parName) {
		this.parName = parName;
	}
	
	public String getParValue() {
		return parValue;
	}
	public void setParValue(String parValue) {
		this.parValue = parValue;
	}
	
	public String getParDesc() {
		return parDesc;
	}
	public void setParDesc(String parDesc) {
		this.parDesc = parDesc;
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
