package com.founder.fmdm.service.sysmgt.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "SUBS_SERVICE")
public class SubsServiceDto {
	/**
	 * 主键
	 */
    @Id
    @Column(name = "UNI_KEY")
	private String uniKey;
	
   

    /**
	 * 服务ID
	 */
    @Column(name = "SERVICE_ID")
	private String serviceId;
	
    /**
	 * 服务名称
	 */
    @Column(name = "SERVICE_NAME")
	private String serviceName;
	
    /**
	 * 消息类型
	 */
    @Column(name = "MSG_TYPE")
	private String msgType;
    
    /**
     * 服务类型
     */
    @Column(name = "SERVICE_TYPE")
    private String serviceType;
    
    /**
     * 服务备注
     */
    @Column(name = "SERVICE_DESC")
    private String serviceDesc;
	
    /**
	 * 创建人
	 */
    @Column(name = "CREATE_BY")
	private String createBy;
    
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    /**
     * 删除人
     */
    @Column(name = "DELETE_BY")
    private String deleteBy;
    
    /**
     * 删除时间
     */
    @Column(name = "DELETE_TIME")
    private Date deleteTime;
    
    /**
     * 删除标识
     */
    @Column(name = "DELETE_FLG")
    private String deleteFlg;
    
    /**
     * 最后修改人
     */
    @Column(name = "LAST_UPDATE_BY")
    private String LastUpdateBy;

    /**
     * 最后修改时间
     */
    @Column(name = "LAST_UPDATE_TIME")
    private Date LastUpdateTime;
    
    /**
     * 更新次数
     */
    @Column(name = "UPDATE_COUNT")
    private Integer updateCount;
    
    /**
     * 版本号
     */
    @Column(name = "ITEM_VERSION")
    private Integer itemVersion;
    
    /**
     * 操作状态
     */
    @Column(name = "OPT_STATUS")
    
    private String optStatus;
    
    /**
     * 发布状态
     */
    @Column(name = "RELEASE_STATUS")
    private String releaseStatus;
    
    public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
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

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getLastUpdateBy() {
		return LastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		LastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateTime() {
		return LastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		LastUpdateTime = lastUpdateTime;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public Integer getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Integer itemVersion) {
		this.itemVersion = itemVersion;
	}

	public String getOptStatus() {
		return optStatus;
	}

	public void setOptStatus(String optStatus) {
		this.optStatus = optStatus;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
}
