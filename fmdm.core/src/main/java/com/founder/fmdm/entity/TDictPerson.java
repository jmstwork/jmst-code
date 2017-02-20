package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

/***
 *  人员字典表
 * @author 
 *
 */
@Entity
@Table(name="dict_person")
public class TDictPerson {

	/**
	 * 代码
	 */
	@Id
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 拼音码
	 */
	private String pyCode;
	/**
	 * 性别码
	 */
	private String sexCode;
	private String genderid;
	/**
	 * 在岗状态码
	 */
	private String employmentStatusCd;
	private String statusid;
	/**
	 * 岗位类别
	 */
	private String jobCategory;
	private String stopflag;
	/**
	 * 科室编码
	 */
	private String departCd;
	private String orgid;
	/**
	 * 科室名称
	 */
	private String departName;
	private String orgname;
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	private String emptelenum;
	/**
	 * 人员类型码
	 */
	private String employeeTypeCd;
	private String emppostseq;
	/**
	 * 邮箱
	 */
	private String mailAddress;
	private String email;
	/**
	 * 入职日期
	 */
	private Date serviceStartDate;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 身份证号
	 */
	private String nationalIdentifier;
	
	/**
	 * 财务科室编码字段
	 */
	private String financialDepartCd;
	private String orgid2;
	
	/**
	 * 财务科室编码字段
	 */
	private String financialDepartName;
	private String orgname2;
	
	/**
	 * 编辑状态
	 */
	private int editStatus;
	/**
	 * 记录版本号
	 */
	@Id
	private int itemVersion;
	/**
	 * 更新次数
	 */
	@Version
	private int updateCount;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者
	 */
	private String lastUpdateBy;
	/**
	 * 更新时间
	 */
	private Date lastUpdateTime;
	/**
	 * 删除者
	 */
	private String deleteBy;
	/**
	 * 删除时间
	 */
	private Date deleteTime;
	/**
	 * 删除标记
	 */
	private int deleteFlg;
	
	/**
	 * 手术分级
	 */
	private String operationGrade;
	/**
	 * 额外手术编码
	 */
	private String additionOperationCode;
	/**
	 * 除外手术编码
	 */
	private String exceptionOperationCode;
	
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
	public String getPyCode() {
		return pyCode;
	}
	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}
	public String getSexCode() {
		return sexCode;
	}
	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}
	public String getEmploymentStatusCd() {
		return employmentStatusCd;
	}
	public void setEmploymentStatusCd(String employmentStatusCd) {
		this.employmentStatusCd = employmentStatusCd;
	}
	public String getJobCategory() {
		return jobCategory;
	}
	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
	public String getDepartCd() {
		return departCd;
	}
	public void setDepartCd(String departCd) {
		this.departCd = departCd;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmployeeTypeCd() {
		return employeeTypeCd;
	}
	public void setEmployeeTypeCd(String employeeTypeCd) {
		this.employeeTypeCd = employeeTypeCd;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public Date getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getNationalIdentifier() {
		return nationalIdentifier;
	}
	public void setNationalIdentifier(String nationalIdentifier) {
		this.nationalIdentifier = nationalIdentifier;
	}
	public String getFinancialDepartCd() {
		return financialDepartCd;
	}
	public void setFinancialDepartCd(String financialDepartCd) {
		this.financialDepartCd = financialDepartCd;
	}
	public String getFinancialDepartName() {
		return financialDepartName;
	}
	public void setFinancialDepartName(String financialDepartName) {
		this.financialDepartName = financialDepartName;
	}
	public int getEditStatus() {
		return editStatus;
	}
	public void setEditStatus(int editStatus) {
		this.editStatus = editStatus;
	}
	
	public int getItemVersion() {
		return itemVersion;
	}
	public void setItemVersion(int itemVersion) {
		this.itemVersion = itemVersion;
	}
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
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
	public int getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	public String getOperationGrade() {
		return operationGrade;
	}
	public void setOperationGrade(String operationGrade) {
		this.operationGrade = operationGrade;
	}
	public String getAdditionOperationCode() {
		return additionOperationCode;
	}
	public void setAdditionOperationCode(String additionOperationCode) {
		this.additionOperationCode = additionOperationCode;
	}
	public String getExceptionOperationCode() {
		return exceptionOperationCode;
	}
	public void setExceptionOperationCode(String exceptionOperationCode) {
		this.exceptionOperationCode = exceptionOperationCode;
	}
	public String getGenderid() {
		return genderid;
	}
	public void setGenderid(String genderid) {
		this.genderid = genderid;
	}
	public String getStatusid() {
		return statusid;
	}
	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}
	public String getStopflag() {
		return stopflag;
	}
	public void setStopflag(String stopflag) {
		this.stopflag = stopflag;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getEmptelenum() {
		return emptelenum;
	}
	public void setEmptelenum(String emptelenum) {
		this.emptelenum = emptelenum;
	}
	public String getEmppostseq() {
		return emppostseq;
	}
	public void setEmppostseq(String emppostseq) {
		this.emppostseq = emppostseq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrgid2() {
		return orgid2;
	}
	public void setOrgid2(String orgid2) {
		this.orgid2 = orgid2;
	}
	public String getOrgname2() {
		return orgname2;
	}
	public void setOrgname2(String orgname2) {
		this.orgname2 = orgname2;
	}
}
