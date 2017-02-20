package com.founder.fmdm.service.sysmgt.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "DICT_DEPARTMENT")
public class DictDepartmentDto {
	
	/**
	 * 主键
	 */
    @Id
    @Column(name = "UNI_KEY")
	private String uniKey;
	
   

	/**
	 * 科室编码
	 */
    @Column(name = "CODE")
	private String code;
	
    /**
	 * 科室名称
	 */
    @Column(name = "NAME")
	private String name;
    
    /**
     * 拼音码
     */
    @Column(name = "PY_CODE")
    private String pyCode;
	
    /**
	 * 创建人
	 */
    @Column(name = "CREATE_BY")
	private String createBy;
    
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    /**
     * 删除人
     */
    @Column(name = "DELETE_BY")
    private String deleteBy;
    
    /**
     * 删除时间
     */
    @Column(name = "DELETE_TIME")
    private Date deleteTime;
    
    /**
     * 删除标识
     */
    @Column(name = "DELETE_FLG")
    private String deleteFlg;
    
    /**
     * 最后修改人
     */
    @Column(name = "LAST_UPDATE_BY")
    private String LastUpdateBy;

    /**
     * 最后修改时间
     */
    @Column(name = "LAST_UPDATE_TIME")
    private Date LastUpdateTime;
    
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
    
    public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
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

	public String getLastUpdateBy() {
		return LastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		LastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateTime() {
		return LastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		LastUpdateTime = lastUpdateTime;
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

	public String getPyCode() {
		return pyCode;
	}

	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}
	
}
