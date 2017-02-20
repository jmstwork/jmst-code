package com.founder.fmdm.dao.audit;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.AudContentInfo;
import com.founder.fmdm.entity.AudInfo;
import com.founder.fmdm.service.audit.dto.AudContentInfo004Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfo005Dto;
import com.founder.fmdm.service.audit.dto.AudContentInfoDto;
import com.founder.fmdm.service.audit.dto.AuditInformation;
import com.founder.fmdm.service.audit.dto.AuditInformationDto;

@Dao
@AnnotateWith(annotations = {
		@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface AuditInformationDao {

	@Select
	List<AuditInformation> selectAuditQuery(String hospitalCode,String audId, String auditSys, String auditEvent, String optDt1,
			String optDt2, String userNo, String userName,String deptName,SelectOptions options);

	@Select
	List<AudContentInfoDto> selectContentInfoById(String audId);
	
	@Select
	List<AudContentInfo004Dto> selectContentInfoBy004(String audId);
	
	@Select
	List<AudContentInfo005Dto> selectContentInfoBy005(String audId);
	
	@Select
	List<AuditInformationDto> selectAuditSysName();

	@Select
	List<AuditInformationDto> selectAuditEventName();

	@Select
	List<AuditInformationDto> selectGroupName();

	@Select
	List<AuditInformationDto> selectDeptName();
	
	@Insert
	int executeAddInfo(AudInfo info);
	
	@Insert
	int executeAddContent(AudContentInfo cInfo);
}
