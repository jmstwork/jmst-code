package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(listener = SysMenuButtonListener.class,naming=NamingType.SNAKE_UPPER_CASE)
@Table(name = "SYS_MENU_BUTTON")
public class SysMenuButton {

	@Id
    @Column(name = "SMB_ID")
    String smbId;

    @Column(name = "SMB_NAME")
    String smbName;

    @Column(name = "TYPE") 
    Integer type;
    
    @Column(name = "PARENT_NODE") 
    String parentNode;
    
    @Column(name = "SMB_LINK")
    String smbLink;

    
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

	public String getSmbId() {
		return smbId;
	}

	public void setSmbId(String smbId) {
		this.smbId = smbId;
	}

	public String getSmbName() {
		return smbName;
	}

	public void setSmbName(String smbName) {
		this.smbName = smbName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentNode() {
		return parentNode;
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}

	public String getSmbLink() {
		return smbLink;
	}

	public void setSmbLink(String smbLink) {
		this.smbLink = smbLink;
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
