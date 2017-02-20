package com.founder.fmdm.service.sysmgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.sysmgt.DataManageDao;
import com.founder.fmdm.entity.SubsServiceSubscribes;
import com.founder.fmdm.entity.SubsSubscribes;
import com.founder.fmdm.entity.SubsUnitGroupInfo;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.sysmgt.dto.DataManageDto;
import com.founder.fmdm.service.sysmgt.dto.DictDepartmentDto;
import com.founder.fmdm.service.sysmgt.dto.DictMultihospitalInfoDto;
import com.founder.fmdm.service.sysmgt.dto.DictPersonDto;
import com.founder.fmdm.service.sysmgt.dto.SubsExtendSubIdDto;
import com.founder.fmdm.service.sysmgt.dto.SubsOrderExecIdDto;
import com.founder.fmdm.service.sysmgt.dto.SubsServiceDto;
import com.founder.fmdm.service.sysmgt.dto.SubsSysDto;
import com.founder.fmdm.service.term.dto.TseUsersDto;

@Service
public class DataManageServiceImpl implements DataManageService {
	@Autowired
	DataManageDao dataManageDao;

	@Override
	public List<DataManageDto> selecManageDatas(String dataName, String tableName ,SelectOptions options) {
		return dataManageDao.selectManageDatas(dataName, tableName,options);
	}

	@Override
	public List<SubsSysDto> selectSubsSysDatas(String sysId,String sysName, String sysApply, String hospitalId,
			SelectOptions options) { 
		return dataManageDao.selectSubsSysDatas(sysId,sysName, sysApply,hospitalId,options);
	}
	
	@Override
	public List<SubsExtendSubIdDto> selectSubsExtendSubIdDatas(String code, String name,
			SelectOptions options) { 
		return dataManageDao.selectSubsExtendSubIdDatas(code, name,options);
	}
	
	@Override
	public List<SubsOrderExecIdDto> selectSubsOrderExecIdDatas(String code, String name,
			SelectOptions options) { 
		return dataManageDao.selectSubsOrderExecIdDatas(code, name,options);
	}
	
	@Override
	public SubsExtendSubIdDto selectExtendDatasById(String uniKey) {
		return dataManageDao.selectExtendDatasById(uniKey);
	}
	
	@Override
	public SubsOrderExecIdDto selectOrderDatasById(String uniKey) {
		return dataManageDao.selectOrderDatasById(uniKey);
	}
	
	@Override
	public SubsServiceDto selectServiceDatasById(String uniKey) {
		return dataManageDao.selectServiceDatasById(uniKey);
	}
	
	@Override
	public DictPersonDto selectPersonDatasById(String uniKey) {
		return dataManageDao.selectPersonDatasById(uniKey);
	}
	
	@Override
	public SubsSysDto selectSysDatasById(String uniKey) {
		return dataManageDao.selectSysDatasById(uniKey);
	}
	
	@Override
	public DictDepartmentDto selectDepartmentDatasById(String uniKey) {
		return dataManageDao.selectDepartmentDatasById(uniKey);
	}
	
	@Override
	public DictMultihospitalInfoDto selectHospitalInfoDatasById(String uniKey) {
		return dataManageDao.selectHospitalInfoDatasById(uniKey);
	}
	 
	@Override
	public int updateExtend(SubsExtendSubIdDto subsExtendSubIdDto) {
		return dataManageDao.updateExtend(subsExtendSubIdDto);
	}
	
	@Override
	public int updateOrder(SubsOrderExecIdDto subsOrderExecIdDto) {
		return dataManageDao.updateOrder(subsOrderExecIdDto);
	}
	
	@Override
	public int updateService(SubsServiceDto subsServiceDto) {
		return dataManageDao.updateService(subsServiceDto);
	}
	
	@Override
	public int updatePerson(DictPersonDto dictPersonDto) {
		return dataManageDao.updatePerson(dictPersonDto);
	}
	
	@Override
	public int updateSys(SubsSysDto subsSysDto) {
		return dataManageDao.updateSys(subsSysDto);
	}
	
	@Override
	public int updateDepartment(DictDepartmentDto dictDepartmentDto) {
		return dataManageDao.updateDepartment(dictDepartmentDto);
	}
	
	@Override
	public int updateHospital(DictMultihospitalInfoDto dictMultihospitalInfoDto) {
		return dataManageDao.updateHospital(dictMultihospitalInfoDto);
	}
	
	@Override
	public List<String> getExtendFieldList() {
		return dataManageDao.getExtendFieldList();
	}
	
	@Override
	public List<String> getOrderFieldList() {
		return dataManageDao.getOrderFieldList();
	}
	
	@Override
	public List<String> getServiceFieldList() {
		return dataManageDao.getServiceFieldList();
	}
	
	@Override
	public List<String> getPersonFieldList() {
		return dataManageDao.getPersonFieldList();
	}
	
	@Override
	public List<String> getSysfieldList() {
		return dataManageDao.getSysfieldList();
	}
	
	@Override
	public List<String> getDepartmentfieldList() {
		return dataManageDao.getDepartmentfieldList();
	}
	
	@Override
	public List<String> getHospitalfieldList() {
		return dataManageDao.getHospitalfieldList();
	}
	
	private Object typeOf(String uniKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubsServiceDto> selectSubsServiceDatas(String serviceId, String serviceName, SelectOptions options) {
		return dataManageDao.selectSubsServiceDatas(serviceId, serviceName,options);
	}

	@Override
	public int addExtend(SubsExtendSubIdDto data) {
		return dataManageDao.addExtend(data);
	}
	
	@Override
	public int addService(SubsServiceDto data) {
		return dataManageDao.addService(data);
	}
	
	@Override
	public int addOrder(SubsOrderExecIdDto data) {
		return dataManageDao.addOrder(data);
	}
	
	@Override
	public int addPerson(DictPersonDto data) {
		return dataManageDao.addPerson(data);
	}
	
	@Override
	public int addSys(SubsSysDto data) {
		return dataManageDao.addSys(data);
	}
	
	@Override
	public int addDepartment(DictDepartmentDto data) {
		return dataManageDao.addDepartment(data);
	}
	
	@Override
	public int addHospital(DictMultihospitalInfoDto data) {
		return dataManageDao.addHospital(data);
	}
	
	@Override
	public String getMaxId(String tableName) {
		return dataManageDao.getMaxId(tableName);
	}
	
	@Override
	public Boolean checkRuleUnit(String code) {
		List<RlmgRuleDto> list = dataManageDao.checkRuleUnit(code);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean checkGroupUnit(String code) {
		List<SubsUnitGroupInfo> list = dataManageDao.checkGroupUnit(code);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean checkServiceSubscribes(String sysId) {
		List<SubsServiceSubscribes> list = dataManageDao.checkServiceSubscribes(sysId);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean checkSubscribes(String sysId) {
		List<SubsSubscribes> list = dataManageDao.checkSubscribes(sysId);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean checkExtend(String code) {
		List<SubsSubscribes> list = dataManageDao.checkExtend(code);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean checkService(String serviceId) {
		List<SubsSubscribes> list = dataManageDao.checkService(serviceId);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean checkOrder(String orderCode) {
		List<SubsSubscribes> list = dataManageDao.checkOrder(orderCode);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean checkHospiatl(String hospiatlId) {
		List<SubsSubscribes> list = dataManageDao.checkHospiatl(hospiatlId);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean checkUniOne(String code,String tableName) {
		Boolean status = false;
		if("SUBS_SYS".equals(tableName)){
			List<SubsSysDto> a = dataManageDao.selectSysDatasByCode(code);
			if(a.size()>0){
				status = true;
			}
		}else if("DICT_HOSPITAL".equals(tableName)){
			List<DictMultihospitalInfoDto> a = dataManageDao.selectHospitalInfoDatasByCode(code);
			if(a.size()>0){
				status = true;
			}
		}
		return status;
	}

	@Override
	public List<DictMultihospitalInfoDto> selectHospitalInfoDatasByCode(String code) {
		List<DictMultihospitalInfoDto> a = dataManageDao.selectHospitalInfoDatasByCode(code);
		return a;
	}

	@Override
	public List<SubsSysDto> selectSysDatasByCode(String code) {
		List<SubsSysDto> a = dataManageDao.selectSysDatasByCode(code);
		return a;
	}

	@Override
	public List<DictDepartmentDto> selectDictDepartmentDatas(String code, String name, String hospitalCode,
			String hospitalName, SelectOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DictPersonDto> selectDictPersonDatas(String code, String name, String genderId, String orgId,
			String hospitalId, SelectOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DictMultihospitalInfoDto> selectHospitalInfoDatas(String hospitalCode, String hospitalName,
			SelectOptions options) {
		return dataManageDao.selectHospitalInfoDatas(hospitalCode,hospitalName,options);
	}

	@Override
	public List<String> getWebservicefieldList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getDataListMapByUniKey(List<String> fieldList, String uniKey,String tableName) {
		Map<String, String> dataMap = new HashMap<String, String>();
	    for(int i=0;i<fieldList.size();i++){
	   	   String data = dataManageDao.getFieldData(fieldList.get(i),uniKey,tableName);
	   	   dataMap.put(fieldList.get(i), data);
	    }
		return dataMap;
	}

	@Override
	public List<DictDepartmentDto> selectDepartmentDatasByCode(String code, String hospitalCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
