package com.founder.fmdm.service.audit;

import java.util.Date;
import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.dao.audit.AuditManageDao;
import com.founder.fmdm.entity.AudEvent;
import com.founder.fmdm.service.audit.dto.AuditManage;
import com.founder.fmdm.service.audit.dto.AuditManageDto;

@Service
public class AuditManageServiceImpl implements AuditManageService
{
    @Autowired
    AuditManageDao auditManageDao;

    /***
     * 鏌ヨ鍏ㄩ儴瀹¤浜嬩欢
     * 
     * @param
     * @return
     */
    @Override
    public List<AuditManage> selectAuditManage(AuditManageDto auditManageDto,
    		SelectOptions options){
    	String eventCode = auditManageDto.getEventCode();
    	String eventName = auditManageDto.getEventName();
    	//List<AuditManage> auditManage = auditManageDao.selectAuditManage(eventCode,eventName,options);
    	return auditManageDao.selectAuditManage(eventCode,eventName,options);
    }

    /***
     * 鏌ヨiam_sys_info琛�涓殑鎵�湁绯荤粺 delete_flg=0
     * 
     * @param
     * @return
     */
    public List<AuditManageDto> selectAuditEventName()
    {
        return auditManageDao.selectAuditEventName();
    }

    /***
     * 淇濆瓨瀹¤浜嬩欢
     * 
     * @param
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveAuditEvent(AudEvent audEvent)
    {
        if (audEvent.getEventCode()==null || audEvent.getEventCode() == "")
        {
            audEvent.setEventCode(this.selectMaxEventCode());
            audEvent.setCreateDt(new Date());
            audEvent.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
            /*SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            if(auth!=null){
                audEvent.setCreateBy(auth.getName());
            }*/
            auditManageDao.executeEventAdd(audEvent);
            return 0;
        }
        else
        {
            return 1;
        }
    }

    /***
     * 淇濆瓨瀹¤浜嬩欢
     * 
     * @param
     * @return
     */
    private String selectMaxEventCode()
    {
        AudEvent audEvent = auditManageDao.selectMaxEventCode();
        if (audEvent == null)
        {
            return "001";
        }
        String code = audEvent.getEventCode() == null ? "001"
                : audEvent.getEventCode();
        //Integer seqNo = Integer.parseInt(code.substring(1, code.length()));
        Integer seqNo = Integer.parseInt(code);
        java.text.DecimalFormat df = new java.text.DecimalFormat("000");
        code = df.format(seqNo + 1);
        return code;
    }

    /***
     * 缂栬緫鍒濆鍖栦俊鎭煡璇�
     * 
     * @param
     * @return
     */
    @Override
    public AudEvent selectAuditMess(String eventCode)
    {
        return auditManageDao.selectAuditMess(eventCode);
    }

    @Override
    public int saveEdit(AuditManageDto auditManageDto)
    {
        if (auditManageDto != null && auditManageDto.getEventCode() != null)
        {
            AudEvent audEvent = (AudEvent) auditManageDao.selectAuditMess(auditManageDto.getEventCode());
            audEvent.setEventCode(auditManageDto.getEventCode());
            audEvent.setEventName(auditManageDto.getEventName());
//            audEvent.setSysId(auditManageDto.getSysId());
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            if(auth!=null){
                audEvent.setUpdateBy(auth.getName());
                audEvent.setUpdateDt(new Date());
            }

            return auditManageDao.executeEventUpdate(audEvent);
        }
        return 0;
    }
    
    
    
    public int updateAduit(AudEvent audEvent){
    	audEvent.setUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
		audEvent.setUpdateDt(new Date());
    	return auditManageDao.executeEventUpdate(audEvent);
    }
    
    
    public int deleteEvent(AudEvent audEvent){
    	audEvent.setDeleteFlg(1);
		audEvent.setDeleteDt(new Date());
		audEvent.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
    	return auditManageDao.deleteEvent(audEvent);
    }
    
    public int selectAduitEventCount(String etentCode){
    	return auditManageDao.selectAduitEventCount(etentCode);
    }

}
