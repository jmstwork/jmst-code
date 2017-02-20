package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "SUBS_SERVICE")
public class SubsService 
{
	/**
	 * 服务ID
	 */
	@Id
    @Column(name = "SERVICE_ID")
    String serviceId;

    /**
   	 * 服务名称
   	 */
    @Column(name = "SERVICE_NAME")
    String serviceName;
       
    /**
	 * 消息类型
	 */
    @Column(name = "MSG_TYPE") 
    String msgType;
    
    /**
	 * 服务类型
	 */
    @Column(name = "SERVICE_TYPE") 
    String serviceType;
    
    /**
	 * 服务描述
	 */
    @Column(name = "SERVICE_DESC") 
    String serviceDesc;
    
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

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
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
}
