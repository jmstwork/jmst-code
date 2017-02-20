package com.founder.fmdm.service.sysmgt;

import com.founder.fmdm.entity.IamParm;
import com.founder.fmdm.service.sysmgt.dto.SysSettingDto;

public interface SystSettingService
{

    // int类型的查询PwdCreateRule属性的接口
    int selectPwdCreateRule();

    // int类型的查询RemindMode属性的接口
    int selectRemindMode();
    
    IamParm selectEPath();
    
    // 查询系统参数的接口
    int executeParmSave(String parName,String parValue,String parDesc);

    //更新数据库中的参数信息
  	int saveParatemer(SysSettingDto systemSettingsDto);
}
