package com.founder.fmdm.service.audit;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.service.audit.dto.AudContentInfo004Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfo005Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfoDto;
import com.founder.fmdm.service.audit.dto.AuditInformation;
import com.founder.fmdm.service.audit.dto.AuditInformationDto;

public interface AuditInformationService {

	List<AuditInformation> selectAuditQuery(AuditInformationDto auditInformationDto,
			SelectOptions options);

	/**
	 * 审计事件001和002内容
	 * @param audId
	 * @return
	 */
	List<AudContentInfoDto> selectContentInfoById(String audId);
	
	/**
	 * 审计事件004内容
	 * @param audId
	 * @return
	 */
	List<AudContentInfo004Dto> selectContentInfoBy004(String audId);
	
	/**
	 * 审计内容005内容
	 * @param audId
	 * @return
	 */
	List<AudContentInfo005Dto> selectContentInfoBy005(String audId);
	
	
	List<AuditInformationDto> selectAuditSysName();

	List<AuditInformationDto> selectAuditEventName();

	List<AuditInformationDto> selectGroupName();

	List<AuditInformationDto> selectDeptName();

}
