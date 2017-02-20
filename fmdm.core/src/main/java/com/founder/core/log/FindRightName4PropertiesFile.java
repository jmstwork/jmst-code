package com.founder.core.log;

import java.util.HashMap;
import java.util.Map;

public class FindRightName4PropertiesFile
{

    public static final Map<String, String> CONSTANTS_4RIGHTNAME_MAP = new HashMap<String, String>();

    static
    {
        init();
    }

    private static void init()
    {
        //编辑系统供应商
        CONSTANTS_4RIGHTNAME_MAP.put("supId", "supplierManager.supplierNum");
        CONSTANTS_4RIGHTNAME_MAP.put("supName", "supplierManager.supplierName");
        CONSTANTS_4RIGHTNAME_MAP.put("supNameEn", "supplierManager.supplierNameEn");
        CONSTANTS_4RIGHTNAME_MAP.put("supNameEnAbbr", "supplierManager.supplierNameEnAbbr");
        CONSTANTS_4RIGHTNAME_MAP.put("supDesc", "supplierManage.desc");
        
        //编辑注册系统
        CONSTANTS_4RIGHTNAME_MAP.put("sysId", "sysinfoManager.sysId");
        CONSTANTS_4RIGHTNAME_MAP.put("sysName", "sysinfoManager.sysName");
        CONSTANTS_4RIGHTNAME_MAP.put("cateCode", "sysinfoManager.kind");
        CONSTANTS_4RIGHTNAME_MAP.put("supId", "sysinfoManager.supName");
        CONSTANTS_4RIGHTNAME_MAP.put("sysUrl", "sysinfoManager.sysUrl");
        CONSTANTS_4RIGHTNAME_MAP.put("sysDesc", "sysinfoManager.sysDesc");
        
        //角色编辑
        CONSTANTS_4RIGHTNAME_MAP.put("roleName", "access.role.nameLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("occupationalType", "access.role.posiLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("memo", "access.role.roleDesLabel");
        
        //用户管理-编辑用户
        CONSTANTS_4RIGHTNAME_MAP.put("memo", "sysUser.memo");  //只需要"备注"2字
        CONSTANTS_4RIGHTNAME_MAP.put("sex", "userManager.sex");
        CONSTANTS_4RIGHTNAME_MAP.put("userNo", "userManager.userNo");
        CONSTANTS_4RIGHTNAME_MAP.put("userName", "userManager.userName");
        CONSTANTS_4RIGHTNAME_MAP.put("departCode", "departCdLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("email", "userManager.email");
        CONSTANTS_4RIGHTNAME_MAP.put("mobile", "userManager.mobile");
        CONSTANTS_4RIGHTNAME_MAP.put("employmentStatusCd", "employmentStatusCdLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("employeeTypeCd", "employeeTypeCdLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("jobCategory", "jobCategoryLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("serviceStartDate", "serviceStartDateLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("groupCd", "groupCdLabel");
        CONSTANTS_4RIGHTNAME_MAP.put("passwd", "login.password");
        
        //提供设值
        CONSTANTS_4RIGHTNAME_MAP.put("passwdRules", "systemSet.passwdRules");
        CONSTANTS_4RIGHTNAME_MAP.put("remindMethods", "systemSet.remindMethods");
 
        //审计管理
        CONSTANTS_4RIGHTNAME_MAP.put("eventCode", "audit.eventCode");
        CONSTANTS_4RIGHTNAME_MAP.put("eventName", "audit.eventName");
        
        //规则管理
        CONSTANTS_4RIGHTNAME_MAP.put("modelName", "rlmg.model.name");
        CONSTANTS_4RIGHTNAME_MAP.put("modelEnName", "rlmg.model.enName");
        CONSTANTS_4RIGHTNAME_MAP.put("rulegroupEnName", "groupManager.groupNameEn");
        CONSTANTS_4RIGHTNAME_MAP.put("rulegroupName", "groupManager.groupName");
        CONSTANTS_4RIGHTNAME_MAP.put("unitName", "userManager.deptName");
        CONSTANTS_4RIGHTNAME_MAP.put("rulegourpName", "groupManager.groupName");
        CONSTANTS_4RIGHTNAME_MAP.put("ruleName", "rlmg.rule.name");
        
    }
}
