package com.founder.fmdm.entity;


import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

/**
 */
@Entity
@Table(name = "DICT_CODE_MAP")
public class DictCodeMap {
	
    /** */
    @Id
    @Column(name = "SOURCE_TABLE")
    String sourceTable;

    /** */
    @Id
    @Column(name = "TARGET_TABLE")
    String targetTable;

    /** */
    @Id
    @Column(name = "SRC_UNI_KEY")
    String srcUniKey;
    
    //UNI_KEY
    @Column(name = "UNI_KEY")
    String uniKey;

    /** */
    @Column(name = "TAR_UNI_KEY")
    String tarUniKey;

    /** */
    @Column(name = "CREATE_BY")
    String createBy;

    /** */
    @Column(name = "CREATE_TIME")
    Date createTime;

    /** */
    @Column(name = "LAST_UPDATE_BY")
    String lastUpdateBy;

    /** */
    @Column(name = "LAST_UPDATE_TIME")
    Date lastUpdateTime;

    /** */
    @Column(name = "DELETE_BY")
    String deleteBy;

    /** */
    @Column(name = "DELETE_TIME")
    Date deleteTime;

    /** */
    @Column(name = "DELETE_FLG")
    String deleteFlg;

    /** */
    @Version
    @Column(name = "UPDATE_COUNT")
    Long updateCount;
    
    @Column(name = "ITEM_VERSION")
	Long itemVersion;
    
    @Column(name = "OPT_STATUS")
    String optStaus;
    
    @Column(name = "RELEASE_STATUS")
    String releaseStatus;

    /** 
     * Returns the sourceTable.
     * 
     * @return the sourceTable
     */
    public String getSourceTable() {
        return sourceTable;
    }

    /** 
     * Sets the sourceTable.
     * 
     * @param sourceTable the sourceTable
     */
    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    /** 
     * Returns the targetTable.
     * 
     * @return the targetTable
     */
    public String getTargetTable() {
        return targetTable;
    }
    
    

    public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
	}

	/** 
     * Sets the targetTable.
     * 
     * @param targetTable the targetTable
     */
    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getSrcUniKey() {
		return srcUniKey;
	}

	public void setSrcUniKey(String srcUniKey) {
		this.srcUniKey = srcUniKey;
	}

	public String getTarUniKey() {
		return tarUniKey;
	}

	public void setTarUniKey(String tarUniKey) {
		this.tarUniKey = tarUniKey;
	}

	/** 
     * Returns the createBy.
     * 
     * @return the createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /** 
     * Sets the createBy.
     * 
     * @param createBy the createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /** 
     * Returns the createTime.
     * 
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * Sets the createTime.
     * 
     * @param createTime the createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * Returns the lastUpdateBy.
     * 
     * @return the lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** 
     * Sets the lastUpdateBy.
     * 
     * @param lastUpdateBy the lastUpdateBy
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /** 
     * Returns the lastUpdateTime.
     * 
     * @return the lastUpdateTime
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /** 
     * Sets the lastUpdateTime.
     * 
     * @param lastUpdateTime the lastUpdateTime
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /** 
     * Returns the deleteBy.
     * 
     * @return the deleteBy
     */
    public String getDeleteBy() {
        return deleteBy;
    }

    /** 
     * Sets the deleteBy.
     * 
     * @param deleteBy the deleteBy
     */
    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    /** 
     * Returns the deleteTime.
     * 
     * @return the deleteTime
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /** 
     * Sets the deleteTime.
     * 
     * @param deleteTime the deleteTime
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /** 
     * Returns the deleteFlg.
     * 
     * @return the deleteFlg
     */
    public String getDeleteFlg() {
        return deleteFlg;
    }

    /** 
     * Sets the deleteFlg.
     * 
     * @param deleteFlg the deleteFlg
     */
    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /** 
     * Returns the updateCount.
     * 
     * @return the updateCount
     */
    public Long getUpdateCount() {
        return updateCount;
    }

    /** 
     * Sets the updateCount.
     * 
     * @param updateCount the updateCount
     */
    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }
    
    public Long getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Long itemVersion) {
		this.itemVersion = itemVersion;
	}

	public String getOptStaus() {
		return optStaus;
	}

	public void setOptStaus(String optStaus) {
		this.optStaus = optStaus;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
}