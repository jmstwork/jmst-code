package com.founder.fmdm.dao.subscribe;

import java.util.List;
import java.util.Map;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.SubsUnitGroup;
import com.founder.fmdm.entity.SubsUnitGroupInfo;
import com.founder.fmdm.service.term.dto.SubsUnitGroupDto;
import com.founder.fmdm.service.term.dto.UnitGroupDetailDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface UnitGroupDao {
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<SubsUnitGroupDto> selectunitGroupTable(String unitGroupId,
			String unitGroupName, String hospitalCode, SelectOptions options); 
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<UnitGroupDetailDto> selectUnitByGroupTable(String unitGroupId,String unitId,String unitName,String tableName,SelectOptions options);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<UnitGroupDetailDto> selectUnitByGroup(String unitGroupId,String tableName,SelectOptions options);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	SubsUnitGroup selectUnitGroupById(String unitGroupId);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> getHospitalList(String hospitalCode);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> getUnitGroupTypeList();
	
	@Update
	int updateUnitGroup(SubsUnitGroup unitGroup);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<SubsUnitGroupInfo> selectUnitGroupInfoById(String unitGroupId);
	
	@Update
	int updateUnitGroupInfo(SubsUnitGroupInfo unitGroupInfo);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<UnitGroupDetailDto> selectUnit(String tableName,String unitId,String unitName,SelectOptions options);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<UnitGroupDetailDto> selectUnits(String tableName,List<String> unitIds);
	
	@Insert
	int insertUnitGroup(SubsUnitGroup unitGroup);
	
	@Insert
	
	int insertUnitGroupInfo(SubsUnitGroupInfo unitGroupInfo);
	
	@Delete
	int deleteUnitGroupInfo(SubsUnitGroupInfo unitGroupInfo);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	String selectTableName(String hospitalCode, String classifyCode);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	int selectSubscribesByGroupId(String id);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	SubsUnitGroupInfo selectUnitGroupInfoByCondition(String unitId,String unitGroupId);

	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<SubsUnitGroupInfo> selectUnitGroupInfoByCondition1(String unitId, String unitGroupId);
	
}
