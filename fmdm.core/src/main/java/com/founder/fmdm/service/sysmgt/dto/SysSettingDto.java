package com.founder.fmdm.service.sysmgt.dto;

import org.seasar.doma.Entity;

/**
 * 此实体类用于系统设置页面
 * 获取数据库的实体值
 */
@Entity
public class SysSettingDto
{

    private String parName;

    private int pwdCreateRule;

    private int messageMode;

    private int emailMode;
    
    private String mqExceptionPath;

    public String getParName()
    {
        return parName;
    }

    public void setParName(String parName)
    {
        this.parName = parName;
    }

    public int getPwdCreateRule()
    {
        return pwdCreateRule;
    }

    public void setPwdCreateRule(int pwdCreateRule)
    {
        this.pwdCreateRule = pwdCreateRule;
    }

    public int getMessageMode()
    {
        return messageMode;
    }

    public void setMessageMode(int messageMode)
    {
        this.messageMode = messageMode;
    }

    public int getEmailMode()
    {
        return emailMode;
    }

    public void setEmailMode(int emailMode)
    {
        this.emailMode = emailMode;
    }

	public String getMqExceptionPath() {
		return mqExceptionPath;
	}

	public void setMqExceptionPath(String mqExceptionPath) {
		this.mqExceptionPath = mqExceptionPath;
	}

}
