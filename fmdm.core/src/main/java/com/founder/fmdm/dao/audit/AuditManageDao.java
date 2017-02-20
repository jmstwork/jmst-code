package com.founder.fmdm.dao.audit;

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

import com.founder.fmdm.entity.AudEvent;
import com.founder.fmdm.service.audit.dto.AuditManage;
import com.founder.fmdm.service.audit.dto.AuditManageDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface AuditManageDao {
	@Select
	List<AuditManage> selectAuditManage(String eventCode, String eventName, SelectOptions options);
	
	@Select
	List<AuditManageDto> selectAuditEventName();

	@Select
	AudEvent selectAuditMess(String eventCode);

	@Select
	AudEvent selectMaxEventCode();
	
	@Select
	public int selectAduitEventCount(String etentCode);

	@Insert
	int executeEventAdd(AudEvent event);
	
	@Update
	int executeEventUpdate(AudEvent event);
	
	@Update
	int deleteEvent(AudEvent event);
	
}
