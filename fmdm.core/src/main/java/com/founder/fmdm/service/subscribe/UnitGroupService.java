package com.founder.fmdm.service.subscribe;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.SubsUnitGroup;
import com.founder.fmdm.entity.SubsUnitGroupInfo;
import com.founder.fmdm.service.term.dto.SubsUnitGroupDto;
import com.founder.fmdm.service.term.dto.UnitGroupDetailDto;

public interface UnitGroupService {

	List<SubsUnitGroupDto> selectunitGroupTable (String unitGroupId,String unitGroupName,
			String hospitalCode,SelectOptions options);
	
	//分页检索科室组信息
	List<UnitGroupDetailDto> selectUnitByGroupTable(String unitGroupId,String unitId,String unitName,String tableName,SelectOptions options);
	
	//检索科室组详细信息
	List<UnitGroupDetailDto> selectUnitByGroup(String unitGroupId,String tableName,SelectOptions options);
	
	//根据科室组Id或id选择科室组信息
	SubsUnitGroup  selectUnitGroupById(String unitGroupId);
	
	//医疗机构列表
	List<Map<String, Object>> getHospitalList(String hospitalCode);
	
	
	
	//科室类别列表
	List<Map<String, Object>> getUnitGroupTypeList();
	
	//更新科室组信息
	int updateUnitGroup(SubsUnitGroup unitGroup);
	
	//根据科室组ID检索科室分组关联表信息
	List<SubsUnitGroupInfo> selectUnitGroupInfoById(String unitGroupId);
	
	//根据科室组ID和科室ID检索科室分组关联表信息
	SubsUnitGroupInfo selectUnitGroupInfoByCondition(String unitId,String unitGroupId);
	
	//根据科室组ID和科室ID检索科室分组关联表信息
	List<SubsUnitGroupInfo> selectUnitGroupInfoByCondition1(String unitId,String unitGroupId);
	
	//更新科室分组关联表信息
	int updateUnitGroupInfo(SubsUnitGroupInfo unitGroupInfo);
	
	//科室选择列表 
	List<UnitGroupDetailDto> selectUnit(String tableName,String unitId,String unitName,SelectOptions options);
	
	//多科室选择列表 
	List<UnitGroupDetailDto> selectUnits(String tableName,List<String> unitIdList);
	
	//添加科室组信息
	int addUnitGroup(SubsUnitGroup unitGroup);
	
	//添加科室分组关联表信息
	int addUnitGroupInfo(String unitIds,String unitGroupId);
	
	//删除科室分组关联表信息
	int deleteUnitGroupInfo(SubsUnitGroupInfo unitGroupInfo);
	
	//根据医疗机构和术语分类取相应多院区表名
	String selectTableName(String hospitalCode,String classifyCode);
	
	//根据科室组Id检索订阅关系中引用的科室组
	int selectSubscribesByGroupId(String id);
}
