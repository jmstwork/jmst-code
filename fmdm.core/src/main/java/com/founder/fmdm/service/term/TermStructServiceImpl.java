package com.founder.fmdm.service.term;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.core.AppSettings;
import com.founder.core.log.LogUtils;
import com.founder.fmdm.Constants;
import com.founder.fmdm.dao.term.TermStructDao;
import com.founder.fmdm.entity.DictCodeMap;
import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.entity.DictSql;
import com.founder.fmdm.service.term.dto.DictCodeMapDto;
import com.founder.fmdm.service.term.dto.DictFieldDto;
import com.founder.fmdm.service.term.dto.DictMainDto;
import com.founder.fmdm.service.term.dto.TermStructDto;

@Service
public class TermStructServiceImpl implements TermStructService
{
	@Autowired
	LogUtils logUtils;
	
	@Autowired
	TermStructDao termStructDao;
	
    @Override
    public List<TermStructDto> selectTermStructList(String serviceId,String dictName,String typeCd,String isSame,SelectOptions options){
    	return termStructDao.selectTermStructList(serviceId,dictName,typeCd,isSame,options);
    }

    @Override
    public List<Map<String, Object>> selectTermTypeList(){
    	return termStructDao.selectTermTypeList();
    }
    
    @Override
    public List<Map<String, Object>> selectFieldTypeList(){
    	return termStructDao.selectFieldTypeList();
    }
    
    
    @Override
    public List<DictMain> selectTermStructById(String dictId){
    	return termStructDao.selectTermStructById(dictId);
    }
    
    @Override
    public List<DictFieldDto> selectFieldByDictId(String dictId){
    	return termStructDao.selectFieldByDictId(dictId);
    }
    
    @Override
    public List<DictField> selectFieldByFieldId(String fieldId){
    	return termStructDao.selectFieldByFieldId(fieldId);
    }
    
    @Override
    public int saveMainData(DictMain dm){ 	
    	return termStructDao.saveMainData(dm);
    }
    
    @Override
    public int updateMainData(DictMain dm){
    	return termStructDao.updateMainData(dm);
    }
    
    @Override
    public int saveFieldData(DictField df){
    	return termStructDao.saveFieldData(df);
    }
    
    @Override
	public int saveSqlData(DictSql ds) {
		return termStructDao.saveSqlData(ds);
	}
    
    @Override
    public int updateFieldData(DictField df){
    	return termStructDao.updateFieldData(df);
    }
    
    @Override
    public List<Map<String,Object>> selectTermStruct(String dictId){
    	return termStructDao.selectTermStruct(dictId);
    }
    
    @Override
    public List<Map<String,Object>> selectTableStruct(String tableName){
    	return termStructDao.selectTableStruct(tableName);
    }
    
    @Override
    public int selectTableIsExist(String tableName){
    	return termStructDao.selectTableIsExist(tableName);
    }
    
    @Override
    public int selectTermIsMapping(String tableName){
    	return termStructDao.selectTermIsMapping(tableName);
    }
    
    @Override
    public Void executeTable(String sqlText){
    	return termStructDao.executeTable(sqlText);
    }
    
    @Override
    public String selectDictIdByTableName(String tableName){
    	return termStructDao.selectDictIdByTableName(tableName);
    }
    
    @Override
    public int selectValueIsExist(String dictName,String tableName,String serviceId){
    	return termStructDao.selectValueIsExist(dictName,tableName,serviceId);
    }
    
    @Override
    public int selectDataIsExist(String fieldName,String tableName){
    	return termStructDao.selectDataIsExist(fieldName,tableName);
    }
    
    @Override
    public List<DictSql> selectSqlById(String dictId,String fieldId,int status){
    	return termStructDao.selectSqlById(dictId,fieldId,status);
    }
    
    @Override
    public int updateSqlData(DictSql ds){
    	return termStructDao.updateSqlData(ds);
    }
    
    @Override
    public int selectFieldNameIsExist(String fieldName,String dictId){
    	return termStructDao.selectFieldNameIsExist(fieldName,dictId);
    }
    
    @Override
    public int selectIsOracleKeyWord(String fieldName){
    	String keyWords = AppSettings.getConfig(Constants.STR_ORACLE_KEY_WORD).toUpperCase();
    	List<String> keyWordList = Arrays.asList(keyWords.split(","));
    	return termStructDao.selectIsOracleKeyWord(fieldName, keyWordList);
    }
    
    @Override
    public List<DictField> selectFieldInfoByDictId(String dictId){
    	return termStructDao.selectFieldInfoByDictId(dictId);
    }

	@Override
	public DictMain queryDictMainByServiceId(String serviceId) {
		return termStructDao.queryDictMainByServiceId(serviceId);
	}
    
	@Override
	public int selectFieldIsExist(String dictId, String fieldName) {
		return termStructDao.selectFieldIsExist(dictId,fieldName);
	}

	@Override
	public List<Map<String,Object>> selectIndexInfo(String tableName) {
		return termStructDao.selectIndexInfo(tableName);
	}

	@Override
	public int selectColumnIsExistInTable(String fieldName, String tableName) {
		return termStructDao.selectColumnIsExistInTable(fieldName,tableName);
	}

	@Override
	public List<Map<String, Object>> selectIndexNameInfo(String tableName) {
		return termStructDao.selectIndexNameInfo(tableName);
	}

	@Override
	public List<DictCodeMapDto> selectMappingCodeList(String sourceTable,
			String sourceName, String targetTable, String targetName,SelectOptions options) {
		return termStructDao.selectMappingCodeList(sourceTable,sourceName,targetTable,targetName,options);
	}

	@Override
	public List<DictCodeMapDto> selectMappingCodeAllList(String sourceTable,
			String sourceName, String targetTable, String targetName,SelectOptions options) {
		return termStructDao.selectMappingCodeAllList(sourceTable,sourceName,targetTable,targetName,options);
	}

	@Override
	@Transactional
	public int updateDictCodeMap(String sourceTable, String targetTable,
			String sourceCode_y, String targetCode_y,String targetCode) {
//		logUtils.insertLog("00704", "00704002", "编辑术语映射列[{}]-[{}]",sourceTable,targetTable);
		return termStructDao.updateDictCodeMap(sourceTable,targetTable,sourceCode_y,targetCode_y,targetCode);
	}

	@Override
	@Transactional
	public int insertDictCodeMap(DictCodeMap dictCodeMap) {
		return termStructDao.insertDictCodeMap(dictCodeMap);
	}

	@Override
	public List<DictMainDto> selectOptionInfo(String sourceTable) {
		return termStructDao.selectOptionInfo(sourceTable);
	}

	@Override
	@Transactional
	public int updateDictCodeMapDel(String sourceTable, String targetTable,String sourceCode, String targetCode) {
		return termStructDao.updateDictCodeMapDel(sourceTable,targetTable,sourceCode,targetCode);
	}

	@Override
	public List<Map<String, Object>> selectCodeOptionInfo(String table,
			String code) {
		return termStructDao.selectCodeOptionInfo(table,code);
	}

	@Override
	public int selectTableName(String tableName) {
		// TODO Auto-generated method stub
		return termStructDao.selectTableName(tableName);
	}

	@Override
	public int selectCodeName(String tableName, String codeName,
			String codeContent) {
		return termStructDao.selectCodeName(tableName,codeName,codeContent);
	}

	@Override
	public int selectDataIsHaveExist(String sourceTable, String targetTable,
			String sourceCode) {
		return termStructDao.selectDataIsHaveExist(sourceTable,targetTable,sourceCode);
	}
	
	@Override
	public String selectDictNameByTableName(String tableName){
		return termStructDao.selectDictNameByTableName(tableName);
	}
	
	@Override
	public String selectCodeNameByTableNameAndUnikey(String tableName, String unikey){
		return termStructDao.selectCodeNameByTableNameAndUnikey(tableName, unikey);
	}
	
	@Override
	public String selectSysNameById(String sysId){
		return termStructDao.selectSysNameById(sysId);
	}
	
	@Override
	public String selectTypeNameByCd(String typeCd){
		return termStructDao.selectTypeNameByCd(typeCd);
	}
}
