package com.founder.fmdm.service.subscribe;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;

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

public interface SubscribeService {

	

	/**
	 * 服务信息一览
	 * @param serviceId
	 * @param serviceName
	 * @param hospitalCode
	 * @param options
	 * @return
	 */
	public List<ServiceDto> selectServiceList(String method,String sysId,String serviceId,String serviceName,String hospitalCode,SelectOptions options);
	
	/**
	 * 根据类型查询所有申请科室组和执行科室组
	 * @param type
	 * @return
	 */
	public List<SubsUnitGroup> selectGroupList(String type);
	
	/**
	 * 查询所有医疗机构
	 * @param category
	 * @return
	 */
	public List<SystemCode> selectHospitalList(String category);
	
	/**
	 * 保存系统订阅信息
	 * @param s
	 * @return
	 */
	int saveSubsServiceData(SubsServiceSubscribes s);
	
	/**
	 * 保存订阅信息表
	 * @param s
	 * @return
	 */
	int saveSubsData(SubsSubscribes s);
	
	/**
	 * 根据serviceId获取serviceName
	 * @param serviceId
	 * @return
	 */
	List<SubsService> selectServiceName(String serviceId);
	
	/**
	 * 根据subsId查询订阅映射记录
	 * @param subsId
	 * @return
	 */
	SubsServiceSubscribes selectSubsServiceDataById(String subsId);
	
	/**
	 * 删除订阅信息表记录
	 * @param s
	 * @return
	 */
	int deleteSubsData(SubsSubscribes s);
	
	/**
	 * 删除和更新订阅映射记录信息
	 * @return
	 */
	int deleteSubsServiceData(SubsServiceSubscribes s);
	
	/**
	 * 根据ID查询订阅信息的记录
	 * @param subscribeId
	 * @return
	 */
	SubsSubscribes selectSubsDataById(String subscribeId);
	
	/**
	 * 更新订阅信息记录
	 * @param s
	 * @return
	 */
	int updateSubsData(SubsSubscribes s);
	
	/**
	 * 根据subscribeId，判断是否有系统还在订阅某个服务
	 * @param subscribeId
	 * @return
	 */
	int selectServiceCount(String subscribeId);
	
	/**
	 * 根据hospitalCode获取对应的组信息
	 * @param hospitalCode
	 * @param type
	 * @return
	 */
	List<SubsUnitGroup> selectGroupByHospitalCode(String hospitalCode,String type);
	/**
	 * 查询所有就诊类型
	 * @param 
	 * @return
	 */
	public List<DictVisitType> selectVisitTypeList();
	/**
	 * 查询所有医嘱执行分类
	 * @param 
	 * @return
	 */
	public List<SubsOrderExecId> selectSubsOrderExecIdList();
	/**
	 * 查询所有扩展码
	 * @param 
	 * @return
	 */
	public List<SubsExtendSubId> selectSubsExtendSubIdList();
	/**
	 * 查询所有系统ID
	 * @param 
	 * @return
	 */
	public List<SubsSys> selectSubsSysList(String hospitalId);
}
