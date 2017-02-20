package com.founder.fmdm.service.sysmgt.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "DICT_HOSPITAL")
public class DictMultihospitalInfoDto {
	
	/**
	 * 主键
	 */
    @Id
    @Column(name = "UNI_KEY")
    private String uniKey;
    
    /**
	 * 医疗机构编码
	 */
    @Column(name = "HOSPITAL_CODE")
	private String hospitalCode;
    
    /**
     * 医疗机构名称
     */
    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;
	
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
    
    
    
	public String getUniKey() {
		return uniKey;
	}

	public void setUniKey(String uniKey) {
		this.uniKey = uniKey;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
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

}
