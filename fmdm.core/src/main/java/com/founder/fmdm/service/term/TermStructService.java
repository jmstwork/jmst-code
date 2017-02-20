package com.founder.fmdm.service.term;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.DictCodeMap;
import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.entity.DictSql;
import com.founder.fmdm.service.term.dto.DictCodeMapDto;
import com.founder.fmdm.service.term.dto.DictFieldDto;
import com.founder.fmdm.service.term.dto.DictMainDto;
import com.founder.fmdm.service.term.dto.TermStructDto;

public interface TermStructService 
{
	DictMain queryDictMainByServiceId(String serviceId);
	
	List<TermStructDto> selectTermStructList(String serviceId,String dictName,String typeCd,String isSame,SelectOptions options);
	
	List<DictCodeMapDto> selectMappingCodeList(String sourceTable,String sourceName,String targetTable,String targetName,SelectOptions options);
	
	List<DictCodeMapDto> selectMappingCodeAllList(String sourceTable,String sourceName,String targetTable,String targetName,SelectOptions options);
	
	List<Map<String, Object>> selectTermTypeList();
	
	List<Map<String, Object>> selectFieldTypeList();
	
	
	List<DictMain> selectTermStructById(String dictId);
	
	List<DictFieldDto> selectFieldByDictId(String dictId);
	
	List<DictField> selectFieldByFieldId(String fieldId);
	
	List<DictField> selectFieldInfoByDictId(String dictId);
	
	int saveMainData(DictMain dm);
	
	int saveFieldData(DictField df);
	
	int saveSqlData(DictSql ds);
	
	int updateMainData(DictMain dm);
	
	int updateFieldData(DictField df);
	
	List<Map<String,Object>> selectTermStruct(String dictId);
	
	List<Map<String,Object>> selectTableStruct(String tableName);
	
	int selectTableIsExist(String tableName);
	
	int selectTermIsMapping(String tableName);
	
	Void executeTable(String sqlText);
	
	String selectDictIdByTableName(String tableName);
	
	int selectValueIsExist(String dictName,String tableName,String serviceId);
	
	int selectDataIsExist(String fieldName,String tableName);
	/**
	 * 
	 * @param sqlId   sqlID标识
	 * @param dictId  术语结构ID标识
	 * @param status  操作状态(0：未确认,1:已确认,-1:执行sql异常,2:归档)
	 * @return
	 */
	List<DictSql> selectSqlById(String dictId,String fieldId,int status);
	
	int updateSqlData(DictSql ds);
	
	int selectFieldNameIsExist(String dictName,String dictId);
	/**
	 * 根据fieldName判断是否为oracle关键字
	 * @param fieldName
	 * @return
	 */
	int selectIsOracleKeyWord(String fieldName);
	
	int selectFieldIsExist(String dictId,String fieldName);
	
	List<Map<String,Object>> selectIndexInfo(String tableName);
	
	/**
	 * 根据列名和表名，判断该列是否存在表中
	 */
	int selectColumnIsExistInTable(String fieldName,String tableName);

	List<Map<String, Object>> selectIndexNameInfo(String tableName);
	
	int updateDictCodeMap(String sourceTable,String targetTable,String sourceCode_y,String targetCode_y,String targetCode);
	
	/**
	 * 插入映射关系，最四个参数用于详细的记录日志
	 * @param dictCodeMap
	 * @param 源表明
	 * @param 映射表明
	 * @param 源名称
	 * @param 映射名称
	 * @return
	 */
	int insertDictCodeMap(DictCodeMap dictCodeMap);
	
	List<DictMainDto> selectOptionInfo(String sourceTable);
	
	int updateDictCodeMapDel(String sourceTable,String targetTable,String sourceCode,String targetCode);
	
	List<Map<String, Object>> selectCodeOptionInfo(String table,String code);
	
	int selectTableName(String tableName);
	
	int selectCodeName(String tableName,String codeName,String codeContent);
	
	int selectDataIsHaveExist(String sourceTable,String targetTable,String sourceCode);
	
	//映射模块：根据tableName查询字典名称
	String selectDictNameByTableName(String tableName);
	
	//映射模块：根据tableName和unikey查询字典表中对应的字段名称
	String selectCodeNameByTableNameAndUnikey(String tableName, String unikey);
	
	//术语结构管理模块，根据sysid，查询主数据源，
	String selectSysNameById(String sysId);
	
	//术语结构管理模块，根据typeid，查询结构类型
	String selectTypeNameByCd(String typeCd);
}
