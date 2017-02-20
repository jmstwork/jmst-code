package com.founder.fmdm.service.sysmgt;

public class MQqmAndqConfig
{
    private String receiveQMName;//接收消息——队列管理器名
    private String receiveQName; //接收消息——队列名
    
    private String sendQMName;       //术语,转发其他系统术语消息——列管理器名
    private String dictSendQName;    //术语,转发其他系统术语消息——队列名
    private String accessControllerSendQName;  //访问控制发消息__队列名
    private String userManagerSendQName;        //账户管理发消息__队列名
    private String warningSetingSendQName;      //警告设定发消息__队列
    private String roleSendQName;               //角色发送消息__队列名
    private String ngSendQName;                 //消息处理NG发送队列名称
    public String getReceiveQMName()
    {
        return receiveQMName;
    }
    public void setReceiveQMName(String receiveQMName)
    {
        this.receiveQMName = receiveQMName;
    }
    public String getReceiveQName()
    {
        return receiveQName;
    }
    public void setReceiveQName(String receiveQName)
    {
        this.receiveQName = receiveQName;
    }
    public String getSendQMName()
    {
        return sendQMName;
    }
    public void setSendQMName(String sendQMName)
    {
        this.sendQMName = sendQMName;
    }
    public String getDictSendQName()
    {
        return dictSendQName;
    }
    public void setDictSendQName(String dictSendQName)
    {
        this.dictSendQName = dictSendQName;
    }
    public String getAccessControllerSendQName()
    {
        return accessControllerSendQName;
    }
    public void setAccessControllerSendQName(String accessControllerSendQName)
    {
        this.accessControllerSendQName = accessControllerSendQName;
    }
    public String getUserManagerSendQName()
    {
        return userManagerSendQName;
    }
    public void setUserManagerSendQName(String userManagerSendQName)
    {
        this.userManagerSendQName = userManagerSendQName;
    }
    
    public String getWarningSetingSendQName()
    {
        return warningSetingSendQName;
    }
    public void setWarningSetingSendQName(String warningSetingSendQName)
    {
        this.warningSetingSendQName = warningSetingSendQName;
    }
    public String getRoleSendQName()
    {
        return roleSendQName;
    }
    public void setRoleSendQName(String roleSendQName)
    {
        this.roleSendQName = roleSendQName;
    }
	public String getNgSendQName() {
		return ngSendQName;
	}
	public void setNgSendQName(String ngSendQName) {
		this.ngSendQName = ngSendQName;
	}
    
}
