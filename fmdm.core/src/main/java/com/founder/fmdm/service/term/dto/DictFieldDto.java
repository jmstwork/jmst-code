package com.founder.fmdm.service.term.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "dict_field")
public class DictFieldDto 
{
	/**
	 * 字段ID
	 */
    @Id
    @Column(name = "FIELD_ID")
    String fieldId;

    /**
	 * 术语结构ID
	 */
    @Column(name = "DICT_ID")
    String dictId;

    /**
   	 * 字段列名
   	 */
    @Column(name = "FIELD_NAME")
    String fieldName;
       
    /**
	 * 注释
	 */
    @Column(name = "FIELD_DESC") 
    String fieldDesc;

    /**
	 * 结构类型
	 */
    @Column(name = "FIELD_TYPE") 
    String fieldType;
    
    /**
   	 * 结构类型
   	 */
    @Column(name = "FIELD_TYPE_NAME") 
    String fieldTypeName;
    
    /**
	 * 长度
	 */
    @Column(name = "LENGTH") 
    String length;

    /**
	 * 精度
	 */
    @Column(name = "PRECISION")
    String precision;

    /**
	 * 缺省值
	 */
    @Column(name = "DEFAULT_VALUE")
    String defaultValue;
    
    /**
	 * 是否为空
	 */
    @Column(name = "IS_MUST")
    String isMust;
    
    /**
	 * 是否为主键
	 */
    @Column(name = "IS_PRIMARY")
    String isPrimary;
    
    /**
	 * 是否列表显示
	 */
    @Column(name = "IS_SHOW")
    String isShow;
    
    /**
   	 * 是否用于过滤
   	 */
    @Column(name = "IS_FILTER")
    String isFilter;
   
    /**
  	 * 是否为默认列
  	 */
    @Column(name = "IS_DEFAULT")
    String isDefault;
    
    /**
	 * 更新次数
	 */
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
	 * 界面显示顺序
	 */
    @Column(name = "DISP_ORDER")
    String dispOrder;

    /**
     * 各字段背后是否有预留SQL未执行
     */
    @Column(name = "DICT_SQL")
    String dictSql;
    
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldTypeName() {
		return fieldTypeName;
	}

	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsFilter() {
		return isFilter;
	}

	public void setIsFilter(String isFilter) {
		this.isFilter = isFilter;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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

	public String getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(String dispOrder) {
		this.dispOrder = dispOrder;
	}

	public String getDictSql() {
		return dictSql;
	}

	public void setDictSql(String dictSql) {
		this.dictSql = dictSql;
	}

}
