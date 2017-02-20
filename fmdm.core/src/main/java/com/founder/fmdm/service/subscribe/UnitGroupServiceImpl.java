package com.founder.fmdm.service.subscribe;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.dao.subscribe.UnitGroupDao;
import com.founder.fmdm.entity.SubsUnitGroup;
import com.founder.fmdm.entity.SubsUnitGroupInfo;
import com.founder.fmdm.service.term.dto.SubsUnitGroupDto;
import com.founder.fmdm.service.term.dto.UnitGroupDetailDto;
import com.founder.util.StringUtil;

@Service
public class UnitGroupServiceImpl implements UnitGroupService {

	@Autowired
	UnitGroupDao unitGroupDao;
	@Override
	public List<SubsUnitGroupDto> selectunitGroupTable(String unitGroupId,
			String unitGroupName,String hospitalCode, SelectOptions options) {
		return unitGroupDao.selectunitGroupTable(unitGroupId, unitGroupName,hospitalCode, options);
	}
	
	@Override
	public List<UnitGroupDetailDto> selectUnitByGroupTable(String unitGroupId,String unitId,String unitName,String tableName,SelectOptions options) {
		return unitGroupDao.selectUnitByGroupTable(unitGroupId, unitId, unitName,tableName,options);
	}
	
	@Override
	public List<UnitGroupDetailDto> selectUnitByGroup(String unitGroupId,String tableName,SelectOptions options) {
		return unitGroupDao.selectUnitByGroup(unitGroupId, tableName,options);
	}
	
	@Override
	public SubsUnitGroup selectUnitGroupById(String unitGroupId) {
		return unitGroupDao.selectUnitGroupById(unitGroupId);
	}
	
	@Override
	public List<Map<String, Object>> getHospitalList(String hospitalCode) {
		return unitGroupDao.getHospitalList(hospitalCode);
	}
	
	@Override
	public List<Map<String, Object>> getUnitGroupTypeList() {
		return unitGroupDao.getUnitGroupTypeList();
	}
	
	@Override
	@Transactional
	public int updateUnitGroup(SubsUnitGroup unitGroup) {
		return unitGroupDao.updateUnitGroup(unitGroup);
	}
	
	@Override
	public List<SubsUnitGroupInfo> selectUnitGroupInfoById(String unitGroupId) {
		return unitGroupDao.selectUnitGroupInfoById(unitGroupId);
	}
	
	@Override
	@Transactional
	public int updateUnitGroupInfo(SubsUnitGroupInfo unitGroupInfo) {
		return unitGroupDao.updateUnitGroupInfo(unitGroupInfo);
	}
	
	@Override
	public List<UnitGroupDetailDto> selectUnit(String tableName,String unitId,String unitName,SelectOptions options) {
		return unitGroupDao.selectUnit(tableName,unitId,unitName, options);
	}
	
	@Override
	public List<UnitGroupDetailDto> selectUnits(String tableName,List<String> unitIdList) {
		return unitGroupDao.selectUnits( tableName, unitIdList);
	}
	
	@Override
	public int addUnitGroup(SubsUnitGroup unitGroup) {
		return unitGroupDao.insertUnitGroup(unitGroup);
	}
	
	@Override
	@Transactional
	public int addUnitGroupInfo(String unitIds,String unitGroupId) {
		int result = 0;
		String[] unitIdArry=unitIds.split(",");
		for(String unitId : unitIdArry){
			//根据科室组Id检索科室组信息
			List<SubsUnitGroupInfo> unitGroupInfos = selectUnitGroupInfoByCondition1(unitId,unitGroupId);
			if(unitGroupInfos.size() > 0){
				result = 2;
				return result;
			}
			SubsUnitGroupInfo unitGroupInfo = new SubsUnitGroupInfo();
			unitGroupInfo.setUnitGroupId(unitGroupId);
			unitGroupInfo.setUnitId(unitId);
			unitGroupInfo.setUuid(StringUtil.getUUID());
			unitGroupInfo.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
			unitGroupInfo.setCreateTime(new Date());
			unitGroupInfo.setDeleteFlg(0);
			result=	unitGroupDao.insertUnitGroupInfo(unitGroupInfo);
		}
		return result;
	}
	
	@Override
	public int deleteUnitGroupInfo(SubsUnitGroupInfo unitGroupInfo) {
		return unitGroupDao.deleteUnitGroupInfo(unitGroupInfo);
	}
	
	@Override
	public String selectTableName(String hospitalCode, String classifyCode) {
		return unitGroupDao.selectTableName(hospitalCode,classifyCode);
	}
	
	@Override
	public int selectSubscribesByGroupId(String id) {
		return unitGroupDao.selectSubscribesByGroupId(id);
	}
	
	@Override
	public SubsUnitGroupInfo selectUnitGroupInfoByCondition(String unitId, String unitGroupId) {
		// TODO Auto-generated method stub
		return unitGroupDao.selectUnitGroupInfoByCondition(unitId, unitGroupId);
	}
	
	@Override
	public List<SubsUnitGroupInfo> selectUnitGroupInfoByCondition1(String unitId, String unitGroupId) {
		// TODO Auto-generated method stub
		return unitGroupDao.selectUnitGroupInfoByCondition1(unitId, unitGroupId);
	}
}
