package com.founder.fmdm.service.sysmgt;

public final class ConstantsDef {

	/** 操作成功返回值 */
	public static final int OPERATION_SUCCESS = 1;
	
	/** 操作失败返回值 */
	public static final int OPERATION_FAILURE = 0;
	
	/** 操作无效返回值 */
	public static final int OPERATION_INVALID = 2;
	
	/** 编辑标记 */
	public static final String OPERATION_EDIT = "edit";
	
	/** 新增标记 */
	public static final String OPERATION_ADD = "add";
	
	/** 新增保存 */
	public static final String OPERATION_ADD_SAVE = "addSave";
	
	/** 修改保存 */
	public static final String OPERATION_EDIT_SAVE = "editSave";
	
	/** 用户权限设置 tree操作标记 */
	public static final String TREE_SAVE_USER = "userAuthorities";
	
	/** 账户权限设置 tree操作标记 */
	public static final String TREE_SAVE_ACCOUNT = "accountAuthorities";
	
	/** 字典订阅 tree操作标记 */
	public static final String TREE_SAVE_DICT = "dictOrder";
	
	/** 服务订阅 tree操作标记 */
	public static final String TREE_SAVE_SERVICE = "serviceOrder";
	
	/** tree根标记 */
	public static final String TREE_ROOT_ID = "root";
	
	/** tree根标记 */
	public static final String DEPT_TREE_ROOT = "北京大学人民医院";
	
	/**  账户权限设置tree根名称 */
	public static final String TREE_ROOT_SYS = "应用系统";
	
	/**  服务订阅设置tree根名称 */
	public static final String TREE_ROOT_SERVICE = "平台服务";
	
	/** 账户状态：未启用 */
	public static final int ACCOUNT_STATUS_0 = 0;
	
	/** 账户状态：已启用 */
	public static final int ACCOUNT_STATUS_1 = 1;
	
	/** 账户状态：已停用 */
	public static final int ACCOUNT_STATUS_2 = 2;
	
	/** 账户状态：已删除 */
	public static final int ACCOUNT_STATUS_3 = 3;
	
	/**  保存成功标记 */
	public static final String SAVE_SUCCESS = "saveSuccess";
	
	/**  保存失败标记 */
	public static final String SAVE_FAILURE = "saveFailure";
	
	/**  域-门急诊 */
	public static final String DOMAIN_01 = "门急诊";
	
	/**  门急诊-住院 */
	public static final String DOMAIN_02 = "住院";
	
	/**  js分隔符 */
	public static final String JS_SEPARATED = "sep";
	
	/**  tree叶子节点标记 */
	public static final String TREE_LEAF = "L";
	
	/******************************以下为短信接口参数***************************************/
	/**
	 * 缓存短信稍后发送
	 */
	public static final int SEND_MSG_LATER = 0;
	/**
	 * 短信发送状态
	 */
	public static final int SEND_MSG_STATUS_NO =  0 ;//"未发送";
	public static final int SEND_MSG_STATUS_SUC = 1;//"发送成功";
	public static final int SEND_MSG_STATUS_FAI = -1;//"发送失败";
	/**
	 * 是否发送短信
	 */
	public static final int IF_SEND_MSG =  1 ;//"发送";
	public static final int IF_SEND_MSG_NO =  0 ;//"不发送";
	/**
	 * 此手机号发送频率过高，限制发送
	 */
	public static final int SEND_MSG_LIMIT_MOBILE = -2;
	/**
	 * 此场景发送短信频率过高，限制发送
	 */
	public static final int SEND_MSG_LIMIT_SCENE = -3;
	/**
	 * 发送短信成功标识
	 */
	public static final int SEND_MSG_SUCCESS = 1;
	/**
	 * 短信内容太长
	 */
	public static final int SEND_MSG_TOOLLONG = -8;
	/**
	 * mas反馈参数错误
	 */
	public static final int SEND_MSG_PARAM_ERR = -6;
	/**
	 * 第一个参数为空
	 */
	public static final int SEND_MSG_PARAM1_ERR = -61;
	/**
	 * 第二个参数为空
	 */
	public static final int SEND_MSG_PARAM2_ERR = -62;
	/**
	 * 第三个参数为空
	 */
	public static final int SEND_MSG_PARAM3_ERR = -63;
	/**
	 * 第四个参数为空
	 */
	public static final int SEND_MSG_PARAM4_ERR = -64;
	/**
	 * 连接mas数据库失败
	 */
	public static final int SEND_MSG_CONN_ERR = -1;
	/**
	 * 数据插入mas数据库失败
	 */
	public static final int SEND_MSG_INSER_ERR = -33;
	/**
	 * 其它异常错误
	 */
	public static final int SEND_MSG_OTHER_ERR = -99;
	
	/**
	 * 消息ID
	 */
	public static final String XPATH_MSGID = "//msgId";
	/**
	 * 消息名称
	 */
	public static final String XPATH_MSGNAME = "//msgName";
	/**
	 * 消息源系统
	 */
	public static final String XPATH_SOURCESYSCODE = "//sourceSysCode";
	/**
	 * 消息目标系统
	 */
	public static final String XPATH_TARGETSYSCODE = "//targetSysCode";
	/**
	 * 消息创建时间
	 */
	public static final String  XPATH_CREATETIME = "//createTime";


	
	/*操作类型*/
	public static final String OPERATION = "opt";
	
	/*结果*/
	public static final String RESULTSET = "resultSet";
	
	public static final String SAVA_FLG = "saveFlg";
	
	public static final String MESSAGES = "messages";
	
	public static final String Total = "_total";
	
	public static final String SPRINGJSONVIEW = "springJsonView";
	
	public static final String OPERATION_DELETE = "delete";
	
	/*规则*/
	/**
	 * and
	 */
	public static final String STRING_OPER_AND = "&&";
	
	/**
	 * or
	 */
	public static final String STRING_OPER_OR = "||";
	
	
	public static final String RULE_EXPRESSION_OPERATE = ">=/<=/==/!=/>/</not contains/contains/not memberof/memberof/not matches/matches";
	
	public static final String RULE_OPERATE_SPLIT = "/";
}
