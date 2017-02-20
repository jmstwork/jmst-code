package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name="dict_department")
public class DictDepartment {

	@Id
	private String code;
	
	private String name;
	
	private String otherName;
	
	private String openFlag;
	
	private String mzFlag;
	
	private String erpCode;
	
	private String address;
	
	private String incomeType;
	
	private String pyCode;
	
	private int editStatus;
	
	private int itemVersion;
	
	@Version
	private int updateCount;
	
	private String createBy;
	
	private Date createTime;
	
	private String lastUpdateBy;
	
	private Date lastUpdateTime;
	
	private String deleteBy;
	
	private Date deleteTime;
	
	private int deleteFlg;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getMzFlag() {
		return mzFlag;
	}

	public void setMzFlag(String mzFlag) {
		this.mzFlag = mzFlag;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getPyCode() {
		return pyCode;
	}
	
	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}
	
	public int getEditStatus() {
		return editStatus;
	}
	
	public void setEditStatus(int editStatus) {
		this.editStatus = editStatus;
	}
	
	public int getItemVersion() {
		return itemVersion;
	}
	
	public void setItemVersion(int itemVersion) {
		this.itemVersion = itemVersion;
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
