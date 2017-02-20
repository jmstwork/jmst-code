package com.founder.fmdm.service.sysmgt.dto;

import org.seasar.doma.Entity;

/**
 * 此实体类用于系统设置页面
 * 更新数据库相关信息的值
 */
@Entity
public class SysSettingDetailDto
{

    private String parName;

    private String parValue;

    private String parDesc;

    private String createBy;

    private String createDt;

    private String updateBy;

    private String updateDt;

    public String getParName()
    {
        return parName;
    }

    public void setParName(String parName)
    {
        this.parName = parName;
    }

    public String getParValue()
    {
        return parValue;
    }

    public void setParValue(String parValue)
    {
        this.parValue = parValue;
    }

    public String getParDesc()
    {
        return parDesc;
    }

    public void setParDesc(String parDesc)
    {
        this.parDesc = parDesc;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getCreateDt()
    {
        return createDt;
    }

    public void setCreateDt(String createDt)
    {
        this.createDt = createDt;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public String getUpdateDt()
    {
        return updateDt;
    }

    public void setUpdateDt(String updateDt)
    {
        this.updateDt = updateDt;
    }

}
