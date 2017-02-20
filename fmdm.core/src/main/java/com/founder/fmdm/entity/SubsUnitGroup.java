package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "subs_unit_group")
public class SubsUnitGroup {
	@Id
    @Column(name = "id")
	String id;
	
    @Column(name = "unit_group_id")
    String unitGroupId;

    @Column(name = "unit_group_name")
    String unitGroupName;

    @Column(name = "unit_group_type")
    String unitGroupType;

    @Column(name = "unit_group_desc")
    String unitGroupDesc;
    
    @Column(name = "hospital_code")
    String hospitalCode;
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
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitGroupId() {
		return unitGroupId;
	}
	public void setUnitGroupId(String unitGroupId) {
		this.unitGroupId = unitGroupId;
	}
	public String getUnitGroupName() {
		return unitGroupName;
	}
	public void setUnitGroupName(String unitGroupName) {
		this.unitGroupName = unitGroupName;
	}
	public String getUnitGroupType() {
		return unitGroupType;
	}
	public void setUnitGroupType(String unitGroupType) {
		this.unitGroupType = unitGroupType;
	}
	public String getUnitGroupDesc() {
		return unitGroupDesc;
	}
	public void setUnitGroupDesc(String unitGroupDesc) {
		this.unitGroupDesc = unitGroupDesc;
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
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
}
