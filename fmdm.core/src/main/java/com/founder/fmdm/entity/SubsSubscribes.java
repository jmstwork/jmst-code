package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "SUBS_SUBSCRIBES")
public class SubsSubscribes 
{
	/**
	 * ID
	 */
	@Id
    @Column(name = "SUBSCRIBE_ID")
    String subscribeId;

    /**
   	 * 医疗结构ID
   	 */
    @Column(name = "HOSPITAL_ID")
    String hospitalId;
       
    /**
	 * 服务ID
	 */
    @Column(name = "SERVICE_ID") 
    String serviceId;
    
    /**
	 * 域ID
	 */
    @Column(name = "DOMAIN_ID") 
    String domainId;
    
    /**
	 * 申请科室组ID
	 */
    @Column(name = "APPLY_UNIT_GROUP_ID") 
    String applyUnitGroupId;
    
    /**
	 * 执行科室组ID
	 */
    @Column(name = "EXEC_UNIT_GROUP_ID") 
    String execUnitGroupId;

    /**
   	 * 发送方系统ID
   	 */
    @Column(name = "SEND_SYS_ID") 
    String sendSysId;
    
    /**
   	 * 扩展码
   	 */
    @Column(name = "EXTEND_SUB_ID") 
    String extendSubId;
    
    /**
   	 * 订阅描述
   	 */
    @Column(name = "SUBS_DESC") 
    String subsDesc;
    
    /**
	 * 创建者
	 */
    @Column(name = "CREATE_BY") 
    String createBy;
    
    /**
	 * 创建时间
	 */
    @Column(name = "CREATE_TIME")
    Date createTime;
    
    /**
	 * 更新者
	 */
    @Column(name = "LAST_UPDATE_BY")
    String lastUpdateBy;
    
    /**
	 * 更新时间
	 */
    @Column(name = "LAST_UPDATE_TIME")
    Date lastUpdateTime;
    
    /**
	 * 更新次数
	 */
    @Version
    @Column(name = "UPDATE_COUNT")
    Integer updateCount;
    
    /**
	 * 删除者
	 */
    @Column(name = "DELETE_BY")
    String deleteBy;
    
    /**
	 * 删除时间
	 */
    @Column(name = "DELETE_TIME")
    Date deleteTime;
    
    /**
	 * 删除标记
	 */
    @Column(name = "DELETE_FLG")
    Integer deleteFlg;
    
    /**
   	 * 医嘱执行分类编码
   	 */
    @Column(name = "ORDER_EXEC_ID") 
    String orderExecId;
	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
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

	public String getApplyUnitGroupId() {
		return applyUnitGroupId;
	}

	public void setApplyUnitGroupId(String applyUnitGroupId) {
		this.applyUnitGroupId = applyUnitGroupId;
	}

	public String getExecUnitGroupId() {
		return execUnitGroupId;
	}

	public void setExecUnitGroupId(String execUnitGroupId) {
		this.execUnitGroupId = execUnitGroupId;
	}

	public String getSendSysId() {
		return sendSysId;
	}

	public void setSendSysId(String sendSysId) {
		this.sendSysId = sendSysId;
	}

	public String getExtendSubId() {
		return extendSubId;
	}

	public void setExtendSubId(String extendSubId) {
		this.extendSubId = extendSubId;
	}

	public String getSubsDesc() {
		return subsDesc;
	}

	public void setSubsDesc(String subsDesc) {
		this.subsDesc = subsDesc;
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

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
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

	public Integer getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Integer deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getOrderExecId() {
		return orderExecId;
	}

	public void setOrderExecId(String orderExecId) {
		this.orderExecId = orderExecId;
	}

}
