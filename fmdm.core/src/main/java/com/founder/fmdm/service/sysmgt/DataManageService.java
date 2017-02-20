package com.founder.fmdm.service.sysmgt;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.service.sysmgt.dto.DataManageDto;
import com.founder.fmdm.service.sysmgt.dto.DictDepartmentDto;
import com.founder.fmdm.service.sysmgt.dto.DictMultihospitalInfoDto;
import com.founder.fmdm.service.sysmgt.dto.DictPersonDto;
import com.founder.fmdm.service.sysmgt.dto.SubsExtendSubIdDto;
import com.founder.fmdm.service.sysmgt.dto.SubsOrderExecIdDto;
import com.founder.fmdm.service.sysmgt.dto.SubsServiceDto;
import com.founder.fmdm.service.sysmgt.dto.SubsSysDto;

public interface DataManageService {

	List<DataManageDto> selecManageDatas(String dataName , String tableName,SelectOptions options);

	List<SubsSysDto> selectSubsSysDatas(String sysId,String sysName, String sysApply, String hospitalId, SelectOptions options);
	
	List<SubsExtendSubIdDto> selectSubsExtendSubIdDatas(String code, String name, SelectOptions options);
	
	List<DictDepartmentDto> selectDictDepartmentDatas(String code, String name,String hospitalCode,String hospitalName, SelectOptions options);
	
	List<SubsServiceDto> selectSubsServiceDatas(String serviceId, String serviceName, SelectOptions options);

	List<SubsOrderExecIdDto> selectSubsOrderExecIdDatas(String code, String name, SelectOptions options);
	
	List<DictPersonDto> selectDictPersonDatas(String code, String name, String genderId,String orgId,String hospitalId,SelectOptions options);
	
	List<DictMultihospitalInfoDto> selectHospitalInfoDatas(String hospitalCode, String hospitalName,SelectOptions options);
	
	SubsExtendSubIdDto selectExtendDatasById(String uniKey);
	
	SubsOrderExecIdDto selectOrderDatasById(String uniKey);
	
	SubsServiceDto selectServiceDatasById(String uniKey);
	
	DictPersonDto selectPersonDatasById(String uniKey);
	
	SubsSysDto selectSysDatasById(String uniKey);
	
	DictDepartmentDto selectDepartmentDatasById(String uniKey);
	
	DictMultihospitalInfoDto selectHospitalInfoDatasById(String uniKey);
	
	int updateExtend(SubsExtendSubIdDto subsExtendSubIdDto);
	
	int updateOrder(SubsOrderExecIdDto subsOrderExecIdDto);
	
	int updateService(SubsServiceDto subsServiceDto);
	
	int updatePerson(DictPersonDto dictPersonDto);
	
	int updateSys(SubsSysDto subsSysDto);
	
	int updateDepartment(DictDepartmentDto dictDepartmentDto);
	
	int updateHospital(DictMultihospitalInfoDto dictMultihospitalInfoDto);
	
	List<String> getExtendFieldList();
	
	List<String> getOrderFieldList();
	
	List<String> getServiceFieldList();
	
	List<String> getPersonFieldList();
	
	List<String> getSysfieldList();
	
	List<String> getDepartmentfieldList();
	
	List<String> getHospitalfieldList();
	
	List<String> getWebservicefieldList();
	
	Map<String,String> getDataListMapByUniKey(List<String> fieldList, String uniKey,String tableName);

	int addExtend(SubsExtendSubIdDto data);
	
	int addService(SubsServiceDto data);
	
	int addOrder(SubsOrderExecIdDto data);
	
	int addPerson(DictPersonDto data);
	
	int addSys(SubsSysDto data);
	
	int addDepartment(DictDepartmentDto data);
	
	int addHospital(DictMultihospitalInfoDto data);
	
	String getMaxId(String tableName);
	
	Boolean checkRuleUnit(String code);
	
	Boolean checkGroupUnit(String code);
	
	Boolean checkServiceSubscribes(String sysId);
	
	Boolean checkSubscribes(String sysId);
	
	Boolean checkExtend(String code);
	
	Boolean checkService(String serviceId);
	
	Boolean checkOrder(String orderCode);
	
	Boolean checkHospiatl(String hospiatlId);
	
	Boolean checkUniOne(String code,String tableName);

	List<DictMultihospitalInfoDto> selectHospitalInfoDatasByCode(String code);

	List<SubsSysDto> selectSysDatasByCode(String code);

	List<DictDepartmentDto> selectDepartmentDatasByCode(String code,String hospitalCode);
}
