package com.founder.fmdm;

import com.founder.core.AppSettings;

public class Constants {
	/**
	 * 消息ID
	 */
	public final static String HEADER_SERVICE_ID = "service_id";

	/**
	 * 域ID
	 */
	public final static String HEADER_DOMAIN_ID = "domain_id";
	
	/**
	 * 申请科室ID
	 */
	public final static String HEADER_APPLY_UNIT_ID = "apply_unit_id";

	/**
	 * 发送系统ID
	 */
	public final static String HEADER_SEND_SYS_ID = "send_sys_id";

	/**
	 * 医疗机构代码
	 */
	public final static String HEADER_HOSPITAL_ID = "hospital_id";
	
	/**
	 * 执行科室
	 */
	public final static String HEADER_EXEC_UNIT = "exec_unit_id";
	 /**
     * $Author :LSG
     * $Date : 2014/10/16 14:27$
     * [BUG]0049708 MODIFY BEGIN 
     * */
	/**
	 * 医嘱执行分类编码
	 */
	public final static String ORDER_EXEC_ID = "order_exec_id";
	/* [BUG]0049708 MODIFY End */
	
	/**
	 * 扩展码
	 */
	public final static String HEADER_EXTEND_SUB_ID = "extend_sub_id";
	
	/**
	 * 医嘱小分类
	 */
	public final static String ORDER_DISPATCH_TYPE_ID = "order_dispatch_type_code";

	/**
	 * replyToQueueName
	 */
	public final static String replyToQueueName = "replyToQueueName";

	/**
	 * replyToQueueManagerName
	 */
	public final static String replyToQueueManagerName = "replyToQueueManagerName";

	
	/**
	 * add
	 */
	public final static String OPT_STATUS_INSERT="insert";
	
	/**
	 * update
	 */
	public final static String OPT_STATUS_UPDATE="update";
	
	
	/**
	 * delete
	 */
	public final static String OPT_STATUS_DELETE="delete";


	/**
	 * 初始化标识
	 */
	public static final Integer FLAG_INITIAL = 0;

	/**
	 * 已处理发送标识
	 */
	public static final Integer FLAG_SENDED = 1;
	
	/**
	 * 字符0
	 */
	public static final String CHAR_0 = "0";
	
	/**
	 * 人员字典分类
	 */
	public static final String PERSON_CODE = "D001";
	
	/**
	 * 科室字典分类
	 */
	public static final String DEPARTMENT_CODE = "D002";
	/**
	 * 病区字典分类
	 */
	public static final String INPATIENT_CODE = "D003";
	
	/**
	 * 关键字key
	 */
	public static final String STR_ORACLE_KEY_WORD = "oracle.key_word";
	 /**
     * $Author :LSG
     * $Date : 2014/10/16 14:27$
     * [BUG]0049708 MODIFY BEGIN 
     * */
	public static final String MSG_HEADER_TYPE_BDRM = "07";
	public static final String MSG_HEADER_TYPE_JSRM = "08";
	public static final String MSG_HEADER_TYPE_PKURM = "04";
	public static final String MSG_HEADER_TYPE = AppSettings.getConfig("MSG_HEADER_TYPE");
	public static final String HEADER_DOMAIN_ID_VALUE = AppSettings.getConfig("HEADER_DOMAIN_ID");
	public static final String HEADER_APPLY_UNIT_ID_VALUE = AppSettings.getConfig("HEADER_APPLY_UNIT_ID");
	public static final String HEADER_EXEC_UNIT_VALUE = AppSettings.getConfig("HEADER_EXEC_UNIT");
	public static final String HEADER_HOSPITAL_ID_VALUE = AppSettings.getConfig("HEADER_HOSPITAL_ID");
	public static final String HEADER_SEND_SYS_ID_VALUE = AppSettings.getConfig("HEADER_SEND_SYS_ID");
	public static final String HEADER_EXTEND_SUB_ID_VALUE = AppSettings.getConfig("HEADER_EXTEND_SUB_ID");
	public static final String ORDER_EXEC_ID_VALUE = AppSettings.getConfig("ORDER_EXEC_ID");
	public static final String ORDER_DISPATCH_TYPE_CODE = AppSettings.getConfig("ORDER_DISPATCH_TYPE_CODE");
	/* [BUG]0049708 MODIFY End */
	 /**
     * $Author :yang_mingjie
     * $Date : 2014/08/13 10:09$
     * [BUG]0046046 MODIFY BEGIN 
     * */

//	public static final String COMPANY_LOGIN_BG = "images/login/logo.png";
    public static final String COMPANY_LOGIN_BGHOSPITAL = "images/login/bg-hospital.png";   
    public static final String COMPANY_MAIN_PIC = "images/top/top-left.png";  
    public static final String COMPANY_MAIN_PKUIH_PIC = "images/top/top-left-international.png";  
    
    public static final String HOSPITAL_PIC_FOLDER = "images";
    public static final String HOSPITAL_PIC_PATH = "/properties/";
    
    public static final String HOSPITAL_LOGIN_BG = AppSettings.getConfig("HOSPITAL_LOGIN_BG");
    public static final String HOSPITAL_LOGIN_BGHOSPITAL = AppSettings.getConfig("HOSPITAL_LOGIN_BGHOSPITAL");
    public static final String HOSPITAL_MAIN_PIC = AppSettings.getConfig("HOSPITAL_MAIN_PIC");

    public static final String OPERATION = "opt";
    /** 操作成功返回值 */
	public static final int OPERATION_SUCCESS = 1;
	/** 操作失败返回值 */
	public static final int OPERATION_FAILURE = 0;
	/** 编辑标记 */
	public static final String OPERATION_EDIT = "edit";
	/** 新增标记 */
	public static final String OPERATION_ADD = "add";
	
	public static final String SAVA_FLG = "saveFlg";
	
	public static final String MESSAGES = "messages";
	/**  保存成功标记 */
	public static final String SAVE_SUCCESS = "saveSuccess";
	
	/**  保存失败标记 */
	public static final String SAVE_FAILURE = "saveFailure";
	
	public static final String Total = "_total";
	
	/*结果*/
	public static final String RESULTSET = "resultSet";
	
	/** 账户状态：未启用 */
	public static final int ACCOUNT_STATUS_0 = 0;
	
	/** 账户状态：已启用 */
	public static final int ACCOUNT_STATUS_1 = 1;
	
	/** 账户状态：已停用 */
	public static final int ACCOUNT_STATUS_2 = 2;
	
	/** 账户状态：已删除 */
	public static final int ACCOUNT_STATUS_3 = 3;
	
	public static final String SPRINGJSONVIEW = "springJsonView";

	/* [BUG]0046046 MODIFY End */
	
    /** 
     * $Author: wu_guangyou
 	 * $Date: 2014/10/11 11:00
 	 * [BUG]0049411 ADD BEGIN
 	 */
    public static final String ADMIN_USER_PASSWORD = AppSettings.getConfig("ADMIN_USER_PASSWORD");
    /** [BUG]0049411 MODIFY End */
    
    public static final String SYSTEM_CODE = AppSettings.getConfig("SYSTEM_CODE");
    
    
    public static final Integer DELETE_FLAG_DEFAULT = 0;
    
    public static final Integer DELETE_FLAG_NONDEFAULT = 1;
    
    public static final String MQ_OPEN = AppSettings.getConfig("MQ_OPEN");
    
    /**
     * 登录页面个性化开关
     */
    public static final String HOSPITAL_LOGIN_STYLE = AppSettings.getConfig("HOSPITAL_LOGIN_STYLE");
}
