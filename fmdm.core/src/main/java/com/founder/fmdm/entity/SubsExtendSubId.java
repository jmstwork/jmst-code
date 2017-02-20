package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "subs_extend_sub_id")
public class SubsExtendSubId {
	@Id
    @Column(name = "UNI_KEY")
	String uniKey;
	
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
	
	@Version
	@Column(name = "UPDATE_COUNT")
	private int updateCount;
	
	@Column(name = "ITEM_VERSION")
	   String itemVersion;
	
	@Column(name = "OPT_STATUS")
	   String optStatus;
	
	@Column(name = "RELEASE_STATUS")
	   String releaseStatus;
	
	@Column(name = "CODE")
	   String code;
	
	@Column(name = "NAME")
	   String name;

	public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
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

	public int getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	public String getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(String itemVersion) {
		this.itemVersion = itemVersion;
	}

	public String getOptStatus() {
		return optStatus;
	}

	public void setOptStatus(String optStatus) {
		this.optStatus = optStatus;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

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

}
