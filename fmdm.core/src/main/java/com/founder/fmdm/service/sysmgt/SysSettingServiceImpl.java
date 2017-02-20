package com.founder.fmdm.service.sysmgt;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.dao.sysmgt.SysSettingDao;
import com.founder.fmdm.entity.IamParm;
import com.founder.fmdm.service.sysmgt.dto.SysSettingDto;

@Service
public class SysSettingServiceImpl implements SystSettingService{

	@Autowired
	SysSettingDao sysSettingDao;
	
	//定义PCR和RM两个String类型的常量
	final static String PCR = "pwdCreateRule";
	final static String RM = "remindMode";
	final static String EPATH = "MQExceptionPath";
	
	final static String PCRDESC = "默认密码生成规则 ";
	final static String RMDESC = "创建用户或重置密码的提醒方式";
	final static String EPATHDESC = "MQ消息发送异常XML存储路径";
	
	//查询PCR值的方法
	public int selectPwdCreateRule() {
		int i = sysSettingDao.selectParms(PCR);
		return i;
	}

	//查询RM值的方法
	public int selectRemindMode() {
		int j = sysSettingDao.selectParms(RM);
		return j;
	}
	
	@Override
	public IamParm selectEPath() {
		return (IamParm)sysSettingDao.selectParameter(EPATH);
	}
	
	//更新数据库中的参数信息
	@Transactional
	public int saveParatemer(SysSettingDto sysSettingDto) {
		try{
			executeParmSave(PCR,String.valueOf(sysSettingDto.getPwdCreateRule()),PCRDESC);
			executeParmSave(RM,String.valueOf(sysSettingDto.getEmailMode()+sysSettingDto.getMessageMode()),RMDESC);
			File f = new File(sysSettingDto.getMqExceptionPath());
	        if(f.exists()){
	        	executeParmSave(EPATH,String.valueOf(sysSettingDto.getMqExceptionPath()),EPATHDESC);
	        }else{
	        	return 2;
	        }
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
    }

	@Override
	public int executeParmSave(String parName,String parValue,String parDesc) {
		 IamParm entity = (IamParm)sysSettingDao.selectParameter(parName);
		 SecurityContext ctx = SecurityContextHolder.getContext();
		 Authentication auth = ctx.getAuthentication();
		 String userNo = auth.getName();
		 if(entity != null){
			 entity.setParValue(parValue);
			 entity.setLastUpdateBy(userNo);
			 entity.setLastUpdateTime(new Date());
			 return sysSettingDao.executeParmUpdate(entity);
		 }else{
			 entity = new IamParm();
			 entity.setParName(parName);
			 entity.setParValue(String.valueOf(parValue));
			 entity.setParDesc(parDesc);
			 entity.setCreateBy(userNo);
			 entity.setCreateTime(new Date());
			 entity.setLastUpdateBy(userNo);
			 entity.setLastUpdateTime(new Date());
			 entity.setDeleteFlg(0);
			 return sysSettingDao.executeParmAdd(entity);
		 }
	}
}
