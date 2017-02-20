package com.founder.fmdm.service.audit;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.AudEvent;
import com.founder.fmdm.service.audit.dto.AuditManage;
import com.founder.fmdm.service.audit.dto.AuditManageDto;

public interface AuditManageService {

	List<AuditManage> selectAuditManage(AuditManageDto auditManageDto, SelectOptions options);

	List<AuditManageDto> selectAuditEventName();

	AudEvent selectAuditMess(String eventCode);

	int saveAuditEvent(AudEvent audEvent);

	int saveEdit(AuditManageDto auditManageDto);
	
	public int updateAduit(AudEvent audEvent);
	
	public int deleteEvent(AudEvent audEvent);
	
	public int selectAduitEventCount(String etentCode);

}
