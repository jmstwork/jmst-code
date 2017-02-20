package com.founder.fmdm.dao.term;

import java.util.List;
import java.util.Map;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Delegate;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.DictCodeMap;
import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.entity.DictSql;
import com.founder.fmdm.service.term.dto.DictCodeMapDto;
import com.founder.fmdm.service.term.dto.DictFieldDto;
import com.founder.fmdm.service.term.dto.DictMainDto;
import com.founder.fmdm.service.term.dto.TermStructDto;

@Dao
@AnnotateWith(annotations = {
		@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface TermStructDao {

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	DictMain queryDictMainByServiceId(String serviceId);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<TermStructDto> selectTermStructList(String serviceId, String dictName,
			String typeCd, String isSame, SelectOptions options);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectTermTypeList();

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectFieldTypeList();

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<DictMain> selectTermStructById(String dictId);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<DictFieldDto> selectFieldByDictId(String dictId);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<DictField> selectFieldByFieldId(String fieldId);

	@Insert
	int saveMainData(DictMain dm);

	@Insert
	int saveFieldData(DictField df);

	@Insert
	int saveSqlData(DictSql ds);

	@Update
	int updateMainData(DictMain dm);

	@Update
	int updateFieldData(DictField df);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectTermStruct(String dictId);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectTableStruct(String tableName);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectTableIsExist(String tableName);
	
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectTermIsMapping(String tableName);

	@Delegate(to = TermStructDaoDelegate.class)
	Void executeTable(String sqlText);

	@Delegate(to = TermStructDaoDelegate.class)
	int executeDrop(String sqlText);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	String selectDictIdByTableName(String tableName);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectValueIsExist(String dictName, String tableName, String serviceId);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectDataIsExist(String fieldName, String tableName);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<DictSql> selectSqlById(String dictId, String fieldId, int status);

	@Update
	int updateSqlData(DictSql ds);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectFieldNameIsExist(String fieldName, String dictId);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectIsOracleKeyWord(String fieldName, List<String> keyWords);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<DictField> selectFieldInfoByDictId(String dictId);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectFieldIsExist(String dictId, String fieldName);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectIndexInfo(String tableName);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectColumnIsExistInTable(String fieldName, String tableName);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectIndexNameInfo(String tableName);

	@Select
	List<DictCodeMapDto> selectMappingCodeList(String sourceTable,
			String sourceName, String targetTable, String targetName,SelectOptions options);

	@Select
	List<DictCodeMapDto> selectMappingCodeAllList(String sourceTable,
			String sourceName, String targetTable, String targetName,SelectOptions options);

	@Update(sqlFile = true)
	int updateDictCodeMap(String sourceTable, String targetTable,
			String sourceCode_y, String targetCode_y, String targetCode);

	@Insert
	int insertDictCodeMap(DictCodeMap dictCodeMap);

	@Select
	List<DictMainDto> selectOptionInfo(String sourceTable);

	@Delete(sqlFile = true)
	int updateDictCodeMapDel(String sourceTable, String targetTable,
			String sourceCode, String targetCode);

	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectCodeOptionInfo(String table, String code);
	
	@Select
	int selectTableName(String tableName);
	
	@Select
	int selectCodeName(String tableName,String codeName,String codeContent);
	
	@Select
	int selectDataIsHaveExist(String sourceTable, String targetTable,String sourceCode);
	
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	String selectDictNameByTableName(String tableName);
	
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	String selectCodeNameByTableNameAndUnikey(String tableName, String unikey);
	
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	String selectSysNameById(String sysId);
	
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	String selectTypeNameByCd(String typeCd);
	
}
