package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "SYSTEM_CODE")
public class SystemCode 
{
	/**
	 *分类
	 */
	@Id
	@Column(name = "CATEGORY_CD")
	 String categoryCd;
	
	/**
	 * code
	 */
	@Column(name = "CD_DIV")
	 String cdDiv;
	/**
	 * code值
	 */
	@Column(name = "CD_VALUE")
	 String cdValue;
	
	/**
	 * code英文值 
	 */
	@Column(name = "CD_EN_VALUE")
	 String cdEnValue;
	
	/**
	 * 更新次数
	 */
	@Version
    @Column(name = "UPDATE_COUNT")
    Integer updateCount;
    
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
	 * 序列
	 */
    @Column(name = "DISP_ORDER")
    Integer dispOrder;
    

	public Integer getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(Integer dispOrder) {
		this.dispOrder = dispOrder;
	}

	public String getCategoryCd() {
		return categoryCd;
	}

	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}

	public String getCdDiv() {
		return cdDiv;
	}

	public void setCdDiv(String cdDiv) {
		this.cdDiv = cdDiv;
	}

	public String getCdValue() {
		return cdValue;
	}

	public void setCdValue(String cdValue) {
		this.cdValue = cdValue;
	}

	public String getCdEnValue() {
		return cdEnValue;
	}

	public void setCdEnValue(String cdEnValue) {
		this.cdEnValue = cdEnValue;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
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
