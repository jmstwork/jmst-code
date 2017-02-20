package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name="IAM_SYS_INFO")
public class IamSysInfo{
	/**
	 *  系统ID
	 */
	@Id
	@Column(name = "sys_id")
	private String sysId;
	/**
	 *  系统名称
	 */
	@Column(name = "sys_name")
	private String sysName;
	/**
	 *  系统分类
	 */
	@Column(name = "cate_code")
	private String cateCode;
	/**
	 *  系统供应商ID
	 */
	@Column(name = "sup_id")
	private String supId;
	/**
	 *  系统URL
	 */
	@Column(name = "sys_url")
	private String sysUrl;
	/**
	 *  系统说明
	 */
	@Column(name="sys_desc")
	private String sysDesc;
	/**
	 *  删除标记
	 */
	@Column(name="delete_flg")
	private int deleteFlg;
	/**
	 *  创建者
	 */
	@Column(name="create_by")
	private String createBy;
	/**
	 *  创建日期
	 */
	@Column(name="create_time")
	private Date createTime;
	/**
	 *  更新者
	 */
	@Column(name="last_update_by")
	private String lastUpdateBy;
	/**
	 *  更新时间
	 */
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	/**
	 *  删除者
	 */
	@Column(name="delete_by")
	private String deleteBy;
	/**
	 *  删除时间
	 */
	@Column(name="delete_time")
	private Date deleteTime;
	/**
	 *  更新次数
	 */
	@Version
	@Column(name="update_count")
	private int updateCount;

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

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}

	public String getSupId() {
		return supId;
	}

	public void setSupId(String supId) {
		this.supId = supId;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}
	
	public String getSysDesc() {
		return sysDesc;
	}

	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
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
