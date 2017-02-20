package com.founder.web.rule.dto;
import java.util.Date;

import com.founder.web.dto.PagingDto;

public class RlmgVersionCondition extends PagingDto{
	
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getVersionMemo() {
		return versionMemo;
	}
	public void setVersionMemo(String versionMemo) {
		this.versionMemo = versionMemo;
	}
	public String getVersionDrl() {
		return versionDrl;
	}
	public void setVersionDrl(String versionDrl) {
		this.versionDrl = versionDrl;
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
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
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
	private String versionId;// 版本ID
	private String versionNo;// 版本号
	private String versionMemo;// 版本说明
	private String versionDrl;// 源代码
	private String createBy;// 创建者
	private Date createTime;// 创建时间
	private String lastUpdateBy;// 更新者
	private Date lastUpdateTime;// 更新时间
	private int updateCount;// 更新次数
	private String deleteBy;// 删除者
	private Date deleteTime;// 删除时间
	private int deleteFlg;// 删除标记
}
