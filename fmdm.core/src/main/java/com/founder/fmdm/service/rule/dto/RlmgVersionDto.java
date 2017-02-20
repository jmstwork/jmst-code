package com.founder.fmdm.service.rule.dto;

import java.util.Date;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming=NamingType.SNAKE_UPPER_CASE) 
public class RlmgVersionDto {

    /**
     *  版本ID
     */
    private String versionId;
    /**
     *  版本号
     */
    private String versionNo;
    /**
     *  规则组ID
     */
    private String versionMemo;
    /**
     *  科室ID
     */
    private String versionDrl;
    /**
     *  删除标记
     */
    private int deleteFlg;
    /**
     *  创建者
     */
    private String createBy;
    /**
     *  创建日期
     */
    private Date createTime;
    /**
     *  更新者
     */
    private String lastUpdateBy;
    /**
     *  更新时间
     */
    private Date lastUpdateTime;
    /**
     *  删除者
     */
    private String deleteBy;
    /**
     *  删除时间
     */
    private Date deleteTime;
    /**
     *  更新次数
     */
    private int updateCount;
    
 
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

    public void setVersionMemo(String VersionMemo) {
        this.versionMemo = VersionMemo;
    }
    
    public String getVersionDrl() {
        return versionDrl;
    }

    public void setVersionDrl(String versionDrl) {
        this.versionDrl = versionDrl;
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