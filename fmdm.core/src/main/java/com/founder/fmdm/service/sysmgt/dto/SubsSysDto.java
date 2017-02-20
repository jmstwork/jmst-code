package com.founder.fmdm.service.sysmgt.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "subs_sys")
public class SubsSysDto {
	
	/**
	 * 唯一主键
	 */
	@Id
	@Column(name = "UNI_KEY")
	private String uniKey;
	

	/**
	 * 字段ID
	 */
    
    @Column(name = "SYS_ID")
	private String sysId;
	
    /**
	 * 系统名称
	 */
    @Column(name = "SYS_NAME")
	private String sysName;
	
    /**
	 * 供应商
	 */
    @Column(name = "SYS_APPLY")
	private String sysApply;
	
    /**
	 * 医院ID
	 */
    @Column(name = "HOSPITAL_ID")
	private String hospitalId;
    
    /**
	 * 创建时间
	 */
    @Column(name = "CREATE_TIME")
	private Date createTime;
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 创建人
	 */
    @Column(name = "CREATE_BY")
	private String createBy;
    
    /**
	 * 最后修改时间
	 */
    @Column(name = "LAST_UPDATE_TIME")
	private Date lastUpdateTime;
    
    /**
	 * 最后修改人
	 */
    @Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;
    
    public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * 删除时间
	 */
    @Column(name = "DELETE_TIME")
	private Date deleteTime;
    
    /**
	 * 删除人
	 */
    @Column(name = "DELETE_BY")
	private String deleteBy;

    
    /**
     * 删除标识
     */
    @Column(name = "DELETE_FLG")
    private String deleteFlg;

    /**
     * 更新次数
     */
    @Column(name = "UPDATE_COUNT")
    private Integer updateCount;
    
    /**
     * 版本号
     */
    @Column(name = "ITEM_VERSION")
    private Integer itemVersion;
    
    /**
     * 操作状态
     */
    @Column(name = "OPT_STATUS")
    
    private String optStatus;
    
    /**
     * 发布状态
     */
    @Column(name = "RELEASE_STATUS")
    private String releaseStatus;

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysApply() {
		return sysApply;
	}

	public void setSysApply(String sysApply) {
		this.sysApply = sysApply;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public Integer getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Integer itemVersion) {
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
	
	public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
	}
}
