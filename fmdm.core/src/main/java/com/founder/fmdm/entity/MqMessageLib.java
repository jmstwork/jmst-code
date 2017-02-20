package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "MQ_MESSAGE_LIB")
public class MqMessageLib 
{
	public String getMqmsgId() {
		return mqmsgId;
	}

	public void setMqmsgId(String mqmsgId) {
		this.mqmsgId = mqmsgId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getOrderdispatchtypeCode() {
		return orderdispatchtypeCode;
	}

	public void setOrderdispatchtypeCode(String orderdispatchtypeCode) {
		this.orderdispatchtypeCode = orderdispatchtypeCode;
	}

	public String getExecunitId() {
		return execunitId;
	}

	public void setExecunitId(String execunitId) {
		this.execunitId = execunitId;
	}

	public String getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getSendFlg() {
		return sendFlg;
	}

	public void setSendFlg(Integer sendFlg) {
		this.sendFlg = sendFlg;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	/**
	 * MsgID
	 */
    @Id
    @Column(name = "mqmsg_id")
    String mqmsgId;

    /**
	 * 服务名称
	 */
    @Column(name = "service_id")
    String serviceId;

    /**
	 * 域id
	 */
    @Column(name = "domain_id") 
    String domainId;

    /**
	 * 医嘱小分类
	 */
    @Column(name = "orderdispatchtype_code") 
    String orderdispatchtypeCode;
    
    /**
	 * 执行科室
	 */
    @Column(name = "execunit_id") 
    String execunitId;

    /**
	 * 消息内容
	 */
    @Column(name = "message_detail")
    String messageDetail;

    /**
	 * 接收时间
	 */
    @Column(name = "arrive_time")
    Date arriveTime;
    
    /**
	 * 转发时间
	 */
    @Column(name = "send_time") 
    Date sendTime;
    
    /**
	 * 是否已发送
	 */
    @Column(name = "send_flg")
    Integer sendFlg;
    
    /**
	 * 处理错误
	 */
    @Column(name = "error_info")
    String errorInfo;
}
