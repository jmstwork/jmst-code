package com.founder.fmdm.dao.term;

import java.util.List;
import java.util.Map;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.DatasourceSet;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.entity.SyncLog;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.TermSync;
import com.founder.fmdm.service.term.dto.DatasourceDto;
import com.founder.fmdm.service.term.dto.SyncLogDto;
import com.founder.fmdm.service.term.dto.SyncTermDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface TermSyncDao { 

	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<Map<String, Object>> getSysList(); 
	 
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	String selectSysNameById(String sysId);
	
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<SystemCode> getDataTypeList(); 
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<SyncTermDto> selectSyncTermTable(String dictId ,String dictName,SelectOptions options);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<SyncTermDto> selectSyncTermList(String syncId ,String dictName);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 String  selectDriverClass(String cdDiv); 
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<DatasourceDto> selectDatasourceList(String systemId,String datasourceId );
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<DatasourceDto> selectDatasourceTable(String systemId,String datasourceId ,SelectOptions options);
	 
	 @Update(excludeNull=true)
	 int updateSyncDict(TermSync entity);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<String> selectSyncJoinKeys(String serviceId);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<SyncLogDto> selectSyncLogList(String dictId);
	 
	 @Insert
	 int insertSyncLog(SyncLog entity);
	 
	 @Insert
	 int insertDatasourceSet(DatasourceSet entity);
	 
	 @Update
	 int updateDatasourceSet(DatasourceSet entity);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 DatasourceSet selectDatasourceSetById(String datasourceId);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<TermSync> selectSyncTermForBatList(String dictId);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 TermSync selectSyncTerm(String syncId);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<Map<String, Object>> getDictList(); 
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<Map<String, Object>> getAllDictList(); 
	 
	 @Insert
	 int insertTermSync(TermSync entity);
	 
	 @Update
	 int updateDataSource(DatasourceSet datasourceSet);
	 
	 @Update
	 int updateTermSync(TermSync entity);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 int checkDataFromSync(String datasourceId);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 int checkDataName(String datasourceName ,String datasourceId);
	 
	 @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 DictMain selectDictMainByServiceId(String serviceId);
	 
	 @Update
	 int updateDictMain(DictMain entity);
	 
}
