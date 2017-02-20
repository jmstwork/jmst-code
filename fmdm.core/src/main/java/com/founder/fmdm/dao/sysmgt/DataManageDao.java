package com.founder.fmdm.dao.sysmgt;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface DataManageDao {

	@Select
	List<DataManageDto> selectManageDatas(String dataName, String tableName,SelectOptions options);
 
	@Select
	List<SubsSysDto> selectSubsSysDatas(String sysId,String sysName, String sysApply, String hospitalId, SelectOptions options);
	
	@Select
	List<SubsExtendSubIdDto> selectSubsExtendSubIdDatas(String code, String name, SelectOptions options);
	
	@Select
	List<DictDepartmentDto> selectDictDepartmentDatas(String code, String name, SelectOptions options);
	
	@Select
	List<SubsOrderExecIdDto> selectSubsOrderExecIdDatas(String code, String name, SelectOptions options);
	
	@Select
	List<SubsServiceDto> selectSubsServiceDatas(String serviceId, String serviceName, SelectOptions options);
	
	@Select
	List<DictPersonDto> selectDictPersonDatas(String code, String name,  SelectOptions options);
	
	@Select
	List<DictMultihospitalInfoDto> selectHospitalInfoDatas(String hospitalCode, String hospitalName,SelectOptions options);
    
	@Select
	SubsExtendSubIdDto selectExtendDatasById(String uniKey);
	
	@Select
	SubsOrderExecIdDto selectOrderDatasById(String uniKey);
	
	@Select
	SubsServiceDto selectServiceDatasById(String uniKey);
	
	@Select
	DictPersonDto selectPersonDatasById(String uniKey);
	
	@Select
	SubsSysDto selectSysDatasById(String uniKey);
	
	@Select
	DictDepartmentDto selectDepartmentDatasById(String uniKey);
	
	@Select
	List<DictDepartmentDto> selectDepartmentDatasByCode(String code);
	
	@Select
	DictMultihospitalInfoDto selectHospitalInfoDatasById(String uniKey);
	
	@Update
	int updateExtend(SubsExtendSubIdDto subsExtendSubIdDto);
	
	@Update
	int updateOrder(SubsOrderExecIdDto subsOrderExecIdDto);
	
	@Update
	int updateService(SubsServiceDto subsServiceDto);
	
	@Update
	int updatePerson(DictPersonDto dictPersonDto);
	
	@Update
	int updateSys(SubsSysDto subsSysDto);
	
	@Update
	int updateDepartment(DictDepartmentDto dictDepartmentDto);
	
	@Update
	int updateHospital(DictMultihospitalInfoDto dictMultihospitalInfoDto);
	
	@Insert
	int addExtend(SubsExtendSubIdDto data);
	
	@Insert
	int addService(SubsServiceDto data);
	
	@Insert
	int addOrder(SubsOrderExecIdDto data);
	
	@Insert
	int addPerson(DictPersonDto data);
	
	@Insert
	int addSys(SubsSysDto data);
	
	@Insert
	int addDepartment(DictDepartmentDto data);
	
	@Insert
	int addHospital(DictMultihospitalInfoDto data);
	
	@Select
	List<String> getExtendFieldList();
	
	@Select
	List<String> getOrderFieldList();
	
	@Select
	List<String> getServiceFieldList();
	
	@Select
	List<String> getPersonFieldList();
	
	@Select
	List<String> getSysfieldList();
	
	@Select
	List<String> getDepartmentfieldList();
	
	@Select
	List<String> getHospitalfieldList();
	
	@Select
	String getFieldData(String fieldName, String uniKey,String tableName);
	
	@Select
	String getMaxId(String tableName);
	
	@Select
	List<RlmgRuleDto> checkRuleUnit(String code);
	
	@Select
	List<SubsUnitGroupInfo> checkGroupUnit(String code);
	
	@Select
	List<SubsServiceSubscribes> checkServiceSubscribes(String sysId);
	
	@Select
	List<SubsSubscribes> checkSubscribes(String sysId);
	
	@Select
	List<SubsSubscribes> checkExtend(String code);
	
	@Select
	List<SubsSubscribes> checkService(String serviceId);
	
	@Select
	List<SubsSubscribes> checkOrder(String orderCode);
	
	@Select
	List<SubsSubscribes> checkHospiatl(String hospiatlId);
	
	@Select
	List<SubsSysDto> selectSysDatasByCode(String code);
	
	@Select
	List<SubsExtendSubIdDto> selectExtendDatasByCode(String code);
	
	@Select
	List<SubsServiceDto> selectServiceDatasByCode(String code);
	
	@Select
	List<SubsOrderExecIdDto> selectOrderDatasByCode(String code);
	
	@Select
	List<DictPersonDto> selectPersonDatasByCode(String code);
	
	@Select
	List<DictMultihospitalInfoDto> selectHospitalInfoDatasByCode(String code);
}
 