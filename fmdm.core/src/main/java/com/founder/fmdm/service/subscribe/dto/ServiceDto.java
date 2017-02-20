package com.founder.fmdm.service.subscribe.dto;

import org.seasar.doma.Entity;

@Entity
public class ServiceDto {
	/**
	 * 订阅映射关系ID
	 */
	private String subsId;
	
	/**
	 * 订阅ID
	 */
	private String subscribeId;
	
	/**
	 * 服务ID
	 */
	private String serviceId;

	/**
	 * 服务名称
	 */
	private String serviceName;
	
	/**
	 * 医疗机构ID
	 */
	private String hospitalId;
	
	/**
	 * 医疗机构名称
	 */
	private String hospitalName;
	
	/**
	 * 申请科室ID
	 */
	private String applyUnitGroupId;
	
	/**
	 * 申请科室名称
	 */
	private String applyUnitGroupName;
	
	/**
	 * 执行科室ID
	 */
	private String execUnitGroupId;
	
	/**
	 * 执行科室名称
	 */
	private String execUnitGroupName;
	
	/**
	 * 域名
	 */
	//private String domainName;
	
	/**
	 * 就诊类型ID
	 */
	private String visitTypeId;
	
	/**
	 * 就诊类型名称
	 */
	private String visitTypeName;
	
	/**
	 * 医嘱执行分类编码
	 */
	private String orderExecId;
	
	/**
	 * 医嘱执行分类名称
	 */
	private String orderExecName;
	
	/**
	 * 扩展码
	 */
	private String extendSubId;
	
	/**
	 * 输出队列
	 */
	private String outputQueueName;
	
	
	private String sendSysId;
	
	private String sendSysName;
	/**
	 * 订阅描述
	 */
	private String subsDesc;

	public String getSendSysId() {
		return sendSysId;
	}

	public void setSendSysId(String sendSysId) {
		this.sendSysId = sendSysId;
	}

	public String getSendSysName() {
		return sendSysName;
	}

	public void setSendSysName(String sendSysName) {
		this.sendSysName = sendSysName;
	}

	public String getSubsId() {
		return subsId;
	}

	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}

	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getApplyUnitGroupId() {
		return applyUnitGroupId;
	}

	public void setApplyUnitGroupId(String applyUnitGroupId) {
		this.applyUnitGroupId = applyUnitGroupId;
	}

	public String getApplyUnitGroupName() {
		return applyUnitGroupName;
	}

	public void setApplyUnitGroupName(String applyUnitGroupName) {
		this.applyUnitGroupName = applyUnitGroupName;
	}

	public String getExecUnitGroupId() {
		return execUnitGroupId;
	}

	public void setExecUnitGroupId(String execUnitGroupId) {
		this.execUnitGroupId = execUnitGroupId;
	}

	public String getExecUnitGroupName() {
		return execUnitGroupName;
	}

	public void setExecUnitGroupName(String execUnitGroupName) {
		this.execUnitGroupName = execUnitGroupName;
	}

	public String getOrderExecId() {
		return orderExecId;
	}

	public void setOrderExecId(String orderExecId) {
		this.orderExecId = orderExecId;
	}

	public String getOrderExecName() {
		return orderExecName;
	}

	public void setOrderExecName(String orderExecName) {
		this.orderExecName = orderExecName;
	}

	public String getExtendSubId() {
		return extendSubId;
	}

	public void setExtendSubId(String extendSubId) {
		this.extendSubId = extendSubId;
	}

	public String getOutputQueueName() {
		return outputQueueName;
	}

	public void setOutputQueueName(String outputQueueName) {
		this.outputQueueName = outputQueueName;
	}

	public String getSubsDesc() {
		return subsDesc;
	}

	public void setSubsDesc(String subsDesc) {
		this.subsDesc = subsDesc;
	}

	public String getVisitTypeId() {
		return visitTypeId;
	}

	public void setVisitTypeId(String visitTypeId) {
		this.visitTypeId = visitTypeId;
	}

	public String getVisitTypeName() {
		return visitTypeName;
	}

	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}
}
