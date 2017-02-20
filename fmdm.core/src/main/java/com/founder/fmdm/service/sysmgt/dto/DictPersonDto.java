package com.founder.fmdm.service.sysmgt.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "DICT_PERSON")
public class DictPersonDto {
	/**
	 * 主键
	 */
    @Id
    @Column(name = "UNI_KEY")
	private String uniKey;
	
   

    /**
	 * 编码
	 */
    @Column(name = "CODE")
	private String code;
	
    /**
	 * 名字
	 */
    @Column(name = "NAME")
	private String name;
	
    /**
	 * 出生日期
	 */
    @Column(name = "BIRTHDAY")
	private Date birthDay;
    
    /**
     * 性别
     */
    @Column(name = "GENDERID")
    private String genderId;
    
    /**
     * 身份证
     */
    @Column(name = "NATIONAL_IDENTIFIER")
    private String nationalIdentifier;
    
    /**
     * 部门ID
     */
    @Column(name = "ORGID")
    private String orgId;
    
    /**
     * 部门名称
     */
    @Column(name = "ORGNAME")
    private String orgName;
    
    /**
     * 邮件
     */
    @Column(name = "EMAIL")
    private String email;
    
    /**
     * 电话
     */
    @Column(name = "EMPTELENUM")
    private String empteleNum;
    
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getNationalIdentifier() {
		return nationalIdentifier;
	}

	public void setNationalIdentifier(String nationalIdentifier) {
		this.nationalIdentifier = nationalIdentifier;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpteleNum() {
		return empteleNum;
	}

	public void setEmpteleNum(String empteleNum) {
		this.empteleNum = empteleNum;
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
