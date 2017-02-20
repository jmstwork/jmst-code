package com.founder.msg.utils;
import java.util.Date;

public class MessageHead
{
  private String msgId;
  private String msgName;
  private String sourceSysCode;
  private String targetSysCode;
  private Date createTime;
  private long version;

  public String getMsgId()
  {
    return this.msgId;
  }

  public void setMsgId(String msgId)
  {
    this.msgId = msgId;
  }

  public String getMsgName()
  {
    return this.msgName;
  }

  public void setMsgName(String msgName)
  {
    this.msgName = msgName;
  }

  public String getSourceSysCode()
  {
    return this.sourceSysCode;
  }

  public void setSourceSysCode(String sourceSysCode)
  {
    this.sourceSysCode = sourceSysCode;
  }

  public String getTargetSysCode()
  {
    return this.targetSysCode;
  }

  public void setTargetSysCode(String targetSysCode)
  {
    this.targetSysCode = targetSysCode;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public long getVersion()
  {
    return this.version;
  }

  public void setVersion(long version)
  {
    this.version = version;
  }
}