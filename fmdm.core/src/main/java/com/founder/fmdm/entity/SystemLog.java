package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.seasar.doma.Version;


@Entity
@Table(name="system_log")
public class SystemLog
{
    /**
     *  日志ID
     */
    private String logId;
    /**
     *  操作人ID
     */
    private String operorId;
    /**
     *  操作时间
     */
    private Date operDt;
    /**
     *  操作模块
     */
    private String operModu;
    /**
     *  操作对象
     */
    private String operObj;
    /**
     *  操作内容
     */
    private String operCont;
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
    @Version
    private int updateCount;
    public String getLogId()
    {
        return logId;
    }
    public void setLogId(String logId)
    {
        this.logId = logId;
    }
    public String getOperorId()
    {
        return operorId;
    }
    public void setOperorId(String operorId)
    {
        this.operorId = operorId;
    }
    public Date getOperDt()
    {
        return operDt;
    }
    public void setOperDt(Date operDt)
    {
        this.operDt = operDt;
    }
    public String getOperModu()
    {
        return operModu;
    }
    public void setOperModu(String operModu)
    {
        this.operModu = operModu;
    }
    public String getOperObj()
    {
        return operObj;
    }
    public void setOperObj(String operObj)
    {
        this.operObj = operObj;
    }
    public String getOperCont()
    {
        return operCont;
    }
    public void setOperCont(String operCont)
    {
        this.operCont = operCont;
    }
    public int getDeleteFlg()
    {
        return deleteFlg;
    }
    public void setDeleteFlg(int deleteFlg)
    {
        this.deleteFlg = deleteFlg;
    }
    public String getCreateBy()
    {
        return createBy;
    }
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    public String getLastUpdateBy()
    {
        return lastUpdateBy;
    }
    public void setLastUpdateBy(String lastUpdateBy)
    {
        this.lastUpdateBy = lastUpdateBy;
    }
    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }
    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }
    public String getDeleteBy()
    {
        return deleteBy;
    }
    public void setDeleteBy(String deleteBy)
    {
        this.deleteBy = deleteBy;
    }
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    public int getUpdateCount()
    {
        return updateCount;
    }
    public void setUpdateCount(int updateCount)
    {
        this.updateCount = updateCount;
    }
}
