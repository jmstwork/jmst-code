package com.founder.fmdm.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity
@Table(name = "DICT_SQL")
public class DictSql 
{
	/**
	 * id标识主键
	 */
	@Id
    @Column(name = "SQL_ID")
    String sqlId;
	
	/**
	 * 术语结构ID
	 */
    @Column(name = "DICT_ID")
    String dictId;

    /**
	 * 字段ID
	 */
    @Column(name = "FIELD_ID")
    String fieldId;

    /**
	 * sql内容
	 */
    @Column(name = "DICT_SQL") 
    String dictSql;

    /**
	 * 错误信息
	 */
    @Column(name = "ERROR_MSG") 
    String errorMsg;
    
    /**
	 * 修改的原始数据
	 */
    @Column(name = "ORIGINAL_DATA") 
    String originalData;
    
    /**
	 * 操作确认
	 */
    @Column(name = "OPT_STATUS") 
    Integer optStatus;
    
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

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getDictSql() {
		return dictSql;
	}

	public void setDictSql(String dictSql) {
		this.dictSql = dictSql;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOriginalData() {
		return originalData;
	}

	public void setOriginalData(String originalData) {
		this.originalData = originalData;
	}

	public Integer getOptStatus() {
		return optStatus;
	}

	public void setOptStatus(Integer optStatus) {
		this.optStatus = optStatus;
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
