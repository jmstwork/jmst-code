package com.founder.fmdm.service.audit;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.audit.AuditInformationDao;
import com.founder.fmdm.service.audit.dto.AudContentInfo004Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfo005Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfoDto;
import com.founder.fmdm.service.audit.dto.AuditInformation;
import com.founder.fmdm.service.audit.dto.AuditInformationDto;

@Service
public class AuditInformationServiceImpl implements AuditInformationService {

	@Autowired
	AuditInformationDao auditInformationDao;

	/***
	 * 全部事件信息查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AuditInformation> selectAuditQuery(AuditInformationDto auditInformationDto,SelectOptions options) {
	   
		String hospital = auditInformationDto.getHospitalCode() == null ? ""
				: auditInformationDto.getHospitalCode();
		String audId = auditInformationDto.getAudId() == null ? ""
				: auditInformationDto.getAudId();
		String auditSys = auditInformationDto.getSysId() == null ? ""
				: auditInformationDto.getSysId();
		String auditEvent = auditInformationDto.getEventCode() == null ? ""
				: auditInformationDto.getEventCode();
		String optDt1 = auditInformationDto.getOptDt1() == null ? ""
				: auditInformationDto.getOptDt1();
		String optDt2 = auditInformationDto.getOptDt2() == null ? ""
				: auditInformationDto.getOptDt2();
		String userNo = auditInformationDto.getUserNo() == null ? ""
				: auditInformationDto.getUserNo().trim();
		String userName = auditInformationDto.getUserName() == null ? ""
				: auditInformationDto.getUserName().trim();
		String deptName = auditInformationDto.getDeptName() == null ? ""
				: auditInformationDto.getDeptName();
		List<AuditInformation> auditQueryList = auditInformationDao.selectAuditQuery(hospital,audId,auditSys, auditEvent,
				optDt1, optDt2, userNo, userName,deptName,options);
		for(int i = 0; i < auditQueryList.size(); i++){
			auditQueryList.get(i).setRownum((i + 1) + "");
		}
		/*return auditInformationDao.selectAuditQuery(hospital,audId,auditSys, auditEvent,
				optDt1, optDt2, userNo, userName,deptName,options);*/
		return auditQueryList;
	}
	
	/**
	 *  审计事件001和002内容查询
	 */
	public List<AudContentInfoDto> selectContentInfoById(String audId){
		return auditInformationDao.selectContentInfoById(audId);
	}
	
	/**
	 *  审计事件004内容查询
	 */
	public List<AudContentInfo004Dto> selectContentInfoBy004(String audId){
		return auditInformationDao.selectContentInfoBy004(audId);
	}
	
	/**
	 *  审计事件005内容查询
	 */
	public List<AudContentInfo005Dto> selectContentInfoBy005(String audId){
		return auditInformationDao.selectContentInfoBy005(audId);
	}
	
	/***
	 * 审计系统查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AuditInformationDto> selectAuditSysName() {
		return auditInformationDao.selectAuditSysName();
	}

	/***
	 * 审计事件查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AuditInformationDto> selectAuditEventName() {
		List<AuditInformationDto> auditEventNameList = auditInformationDao.selectAuditEventName();
		String eventName = null;
		for(int i = 0; i < auditEventNameList.size();i++){
			if(null != auditEventNameList.get(i).getEventName() && auditEventNameList.get(i).getEventName().length() > 15){
				eventName = auditEventNameList.get(i).getEventName().substring(0, 15);
				auditEventNameList.get(i).setEventName(eventName + "...");
			}
		}
		//return auditInformationDao.selectAuditEventName();
		return auditEventNameList;
	}

	/***
	 * 职务查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AuditInformationDto> selectGroupName() {
		return auditInformationDao.selectGroupName();
	}

	/***
	 * 科室查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AuditInformationDto> selectDeptName() {
		return auditInformationDao.selectDeptName();
	}

}
