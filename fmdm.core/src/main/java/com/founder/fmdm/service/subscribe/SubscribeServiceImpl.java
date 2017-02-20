package com.founder.fmdm.service.subscribe;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.subscribe.SubscribeDao;
import com.founder.fmdm.entity.DictVisitType;
import com.founder.fmdm.entity.SubsExtendSubId;
import com.founder.fmdm.entity.SubsOrderExecId;
import com.founder.fmdm.entity.SubsService;
import com.founder.fmdm.entity.SubsServiceSubscribes;
import com.founder.fmdm.entity.SubsSubscribes;
import com.founder.fmdm.entity.SubsSys;
import com.founder.fmdm.entity.SubsUnitGroup;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.service.subscribe.dto.ServiceDto;

@Service
public class SubscribeServiceImpl implements SubscribeService {

	@Autowired
	SubscribeDao subscribeDao;
	
	@Override
	public List<ServiceDto> selectServiceList(String method,String sysId,String serviceId, String serviceName,String hospitalCode,SelectOptions options) {
		return subscribeDao.selectServiceList(method,sysId,serviceId, serviceName, hospitalCode,options);
	}

	@Override
	public List<SubsUnitGroup> selectGroupList(String type) {
		return subscribeDao.selectGroupList(type);
	}

	@Override
	public List<SystemCode> selectHospitalList(String category) {
		return subscribeDao.selectHospitalList(category);
	}

	@Override
	public int saveSubsServiceData(SubsServiceSubscribes s) {
		return subscribeDao.saveSubsServiceData(s);
	}

	@Override
	public int saveSubsData(SubsSubscribes s) {
		return subscribeDao.saveSubsData(s);
	}

	@Override
	public List<SubsService> selectServiceName(String serviceId) {
		return subscribeDao.selectServiceName(serviceId);
	}

	@Override
	public SubsServiceSubscribes selectSubsServiceDataById(String subsId) {
		return subscribeDao.selectSubsServiceDataById(subsId);
	}

	@Override
	public int deleteSubsServiceData(SubsServiceSubscribes s) {
		return subscribeDao.deleteSubsServiceData(s);
	}

	@Override
	public SubsSubscribes selectSubsDataById(String subscribeId) {
		return subscribeDao.selectSubsDataById(subscribeId);
	}

	@Override
	public int updateSubsData(SubsSubscribes s) {
		return subscribeDao.updateSubsData(s);
	}

	@Override
	public int selectServiceCount(String subscribeId) {
		return subscribeDao.selectServiceCount(subscribeId);
	}

	@Override
	public int deleteSubsData(SubsSubscribes s) {
		return subscribeDao.deleteSubsData(s);
	}

	@Override
	public List<SubsUnitGroup> selectGroupByHospitalCode(String hospitalCode,
			String type) {
		return subscribeDao.selectGroupByHospitalCode(hospitalCode,type);
	}

	@Override
	public List<DictVisitType> selectVisitTypeList() {		
		 return subscribeDao.selectVisitTypeList();
	}
	@Override
	public List<SubsOrderExecId> selectSubsOrderExecIdList() {		
		 return subscribeDao.selectSubsOrderExecIdList();
	}	
	@Override
	public List<SubsExtendSubId> selectSubsExtendSubIdList() {		
		 return subscribeDao.selectSubsExtendSubIdList();
	}

	@Override
	public List<SubsSys> selectSubsSysList(String hospitalId) {
		return subscribeDao.selectSubsSysList(hospitalId);
	}	
}
