package com.founder.fmdm.dao.subscribe;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface SubscribeDao {
	
	/**
	 * 检索服务列表信息
	 * @param method
	 * @param sysId
	 * @param serviceId
	 * @param serviceName
	 * @param hospitalCode
	 * @param options
	 * @return
	 */
	@Select
	public List<ServiceDto> selectServiceList(String method,String sysId,String serviceId, String serviceName,String hospitalCode,SelectOptions options);
	
	@Select
	public List<SubsUnitGroup> selectGroupList(String type);
	
	@Select
	public List<SystemCode> selectHospitalList(String category);
	
	@Insert
	int saveSubsServiceData(SubsServiceSubscribes s);
	
	@Insert
	int saveSubsData(SubsSubscribes s);
	
	@Select
	public List<SubsService> selectServiceName(String serviceId);
	
	@Select
	SubsServiceSubscribes selectSubsServiceDataById(String subsId);
	
	@Delete
	int deleteSubsServiceData(SubsServiceSubscribes s);
	
	@Select
	SubsSubscribes selectSubsDataById(String subscribeId);
	
	@Update
	int updateSubsData(SubsSubscribes s);
	
	@Select
	int selectServiceCount(String subscribeId);
	
	@Delete
	int deleteSubsData(SubsSubscribes s);
	
	@Select
	List<SubsUnitGroup> selectGroupByHospitalCode(String hospitalCode,String type);

	@Select
	public List<DictVisitType> selectVisitTypeList();
	
	@Select
	public List<SubsOrderExecId> selectSubsOrderExecIdList();
	
	@Select
	public List<SubsExtendSubId> selectSubsExtendSubIdList();
	
	@Select
	public List<SubsSys> selectSubsSysList(String hospitalId);
}

