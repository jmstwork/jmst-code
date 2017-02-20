package com.founder.fmdm.dao.rule;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * 系统日志记录
 * 
 * @author yang_jianbo
 * @time 2012-8-2
 */
@Controller
public class LogProcessor {
//	@Autowired
//	HttpSession session;
//
//	@Autowired
//	LogDao logDao;
//
//	@Autowired
//	LogProcessorDao logProcessorDao;
//
//	@Autowired
//	DictItemsDao dictItemsDao;
//
//	private Map<String, String> findRightNameMap;
//
//	Properties props = new Properties();
//
//	public LogProcessor() {
//		findRightNameMap = FindRightName4PropertiesFile.CONSTANTS_4RIGHTNAME_MAP;
//
//		InputStream ips = null;
//		try {
//			String filePath = this.getClass().getResource("/").getPath();
//			ips = new BufferedInputStream(new FileInputStream(filePath
//					+ "ApplicationResources_zh_CN.properties"));
//			props.load(ips);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 往iam_log表插入系统日志
//	 * 
//	 * @param arg0
//	 * @param userName
//	 */
//	public void LogHandler(String tableName, Map<String, Object> arg0,
//			String userName, String operation) {
//		try {
//			if (tableName == null || arg0 == null || userName == null
//					|| operation == null) {
//				return;
//			}
//			tableName = tableName.toLowerCase();
//			if ("rlmg_model_detail".equalsIgnoreCase(tableName)
//					|| "nf_setting_detail".equalsIgnoreCase(tableName)
//					|| "nf_setting".equalsIgnoreCase(tableName)
//					|| "rlmg_rule_data".equalsIgnoreCase(tableName)) {
//				return;
//			}
//			IamLog iLog = new IamLog();
//			iLog.setLogId(String.valueOf(UUID.randomUUID()));
//			iLog.setCreateBy(userName);
//			iLog.setCreateTime(new Date());
//			iLog.setOperorId(userName);
//			iLog.setOperDt(new Date());
//			iLog.setUpdateCount(0);
//			IamAccountInfo iai = null;
//			String compareResult = null;
//			Map<String, String> condition = new HashMap<String, String>();
//			// 主要记录新增和删除的操作
//			// 用户管理——配置注册系统权限
//			boolean insert = "insert".equalsIgnoreCase(operation);
//			boolean update = "update".equalsIgnoreCase(operation);
//			boolean delete = "delete".equalsIgnoreCase(operation);
//			if ("aud_content_info".equalsIgnoreCase(tableName)) {
//				this.saveAuditOptLog(insert, iLog, arg0);
//			}// 模型相关操作
//			else if ("rlmg_model_type".equalsIgnoreCase(tableName)) {
//				this.saveModelOptLog(insert, update, delete, iLog, arg0);
//			}
//			// 规则组相关操作
//			else if ("rlmg_rulegroup".equalsIgnoreCase(tableName)) {
//				this.saveRulegroupOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName);
//			}
//			// 规则的相关操作
//			else if ("rlmg_rule".equalsIgnoreCase(tableName)) {
//				this.saveRuleOptLog(insert, update, delete, iLog, arg0);
//			}
//			// 记录“规则管理”——“规则版本”操作日志
//			else if ("rlmg_rule_version".equalsIgnoreCase(tableName)) {
//				this.saveRuleVersionOptLog(insert, iLog, arg0);
//			}
//			// 记录“审计管理”——“审计事件”操作日志
//			else if ("aud_event".equalsIgnoreCase(tableName)) {
//				this.saveAudEventOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName);
//			}
//			// 记录“统一身份管理和权限管理”——“账户管理”操作日志
//			else if ("iam_account_info".equalsIgnoreCase(tableName)) {
//				this.saveAccountInfoOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName, iai);
//			}
//			// 记录“统一身份管理和权限管理”——“账户管理”——“配置权限”操作日志
//			else if ("IAM_ACCOUNT_SYS".equalsIgnoreCase(tableName)) {
//				this.saveAccountSysOptLog(insert, delete, iLog, arg0, iai);
//			}
//			// 记录“统一身份管理和权限管理”——“系统供应商注册管理”操作日志
//			else if ("IAM_SUPPLIER".equalsIgnoreCase(tableName)) {
//				this.saveSupplierOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName);
//			}
//			// 记录“统一身份管理和权限管理”——“系统注册管理”操作日志
//			else if ("IAM_SYS_INFO".equalsIgnoreCase(tableName)) {
//				this.saveSysInfoOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName);
//			}
//			// 记录“访问控制管理”——“角色访问权限设定”操作日志
//			else if ("ACTL_ROLE".equalsIgnoreCase(tableName)) {
//				this.saveActlRoleOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName);
//			}
//			// 记录“访问控制管理”——“角色访问权限设定”——“角色编辑”操作日志
//			else if ("ACTL_ROLE_USER".equalsIgnoreCase(tableName)) {
//				this.saveActlRoleUserOptLog(insert, update, delete, iLog, arg0,
//						iai);
//			}
//			// 记录“访问控制管理”——“角色访问权限设定”——“权限设定”操作日志
//			else if ("ACTL_ROLE_AUTH".equalsIgnoreCase(tableName)) {
//				this.saveActlRoleAuthOptLog(insert, update, delete, iLog, arg0);
//			}
//			// 记录“访问控制管理”——“角色访问权限设定”——“权限设定”操作日志
//			else if ("iam_user_info".equalsIgnoreCase(tableName)) {
//				this.saveUserInfoOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName, iai);
//			}
//			// 记录“访问控制管理”——“个人访问权限设定”——“权限设定”操作日志
//			else if ("ACTL_USER_AUTH".equalsIgnoreCase(tableName)) {
//				this.saveActlUserAuthOptLog(insert, update, delete, iLog, arg0,
//						iai);
//			}
//			// 记录“系统管理”——“服务订阅”操作日志
//			else if ("IEM_SERVICE_SUBSCRIBE".equalsIgnoreCase(tableName)) {
//				this.saveServiceSubscribeOptLog(insert, update, delete, iLog,
//						arg0);
//			}
//			// 记录“系统管理”——“用户管理”——“配置权限”操作日志
//			else if ("iam_user_resr".equalsIgnoreCase(tableName)) {
//				this.saveUserResrOptLog(insert, update, delete, iLog, arg0, iai);
//			}
//			// 记录“系统管理”——“系统设值”操作日志
//			else if ("IAM_PARM".equalsIgnoreCase(tableName)) {
//				this.saveSysMgrOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName);
//			}
//			// 处理ggfw——“术语编码服务”相关操作
//			else if (tableName.indexOf("dict") != -1
//					&& !"system".equalsIgnoreCase(userName)) {
//				this.saveDictInfoOptLog(insert, update, delete, iLog, arg0,
//						condition, compareResult, tableName);
//			}
//			// 同步其它系统术语消息,msgId铁定不为空
//			else if (tableName.indexOf("dict") != -1
//					&& "SYSTEM".equalsIgnoreCase(userName)
//					&& arg0.containsKey("msgId")) {
//				this.saveReceiveDictInfoOptLog(insert, update, delete, iLog,
//						arg0, condition, compareResult, tableName);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 比照新旧数据的不同，记录不同之处。
//	 * 
//	 * @param newData
//	 * @return
//	 */
//	public String compareContent(String tableName, Map<String, Object> newData,
//			Map<String, String> condition) {
//		String differentValue = "内容无差异,无需插入日志。";
//		StringBuffer bf = new StringBuffer();
//		// 排除掉共通字段
//		if (tableName != null) {
//			List<String> columns = logProcessorDao.selectNeedColumns(tableName);
//			if (columns != null && columns.size() > 0) {
//				Map<String, Object> oldData = null;
//				if (tableName.indexOf("dict") != -1) {
//					//人员职称字典联合主键特殊(person_cd、title_cd、item_version)
//					if("dict_person_title_relation".equalsIgnoreCase(tableName)){
//						oldData = logProcessorDao.selectDictTitleInfoWithout8CommFields(
//								tableName, columns, condition.get("personCd"),condition.get("titleCd"),
//								condition.get("itemVersion"));
//					}else{
//						oldData = logProcessorDao.selectDictInfoWithout8CommFields(
//								tableName, columns, condition.get("code"),
//								condition.get("itemVersion"));
//					}
//				} else {
//					oldData = logProcessorDao.selectInfoWithout8CommFields(
//							tableName, columns, condition.get("name"),
//							condition.get("value"));
//				}
//				if (oldData != null && oldData.size() > 0) {
//					Iterator<String> it = newData.keySet().iterator();
//					while (it.hasNext()) {
//						Object key = it.next();
//						if (!"itemVersion".equalsIgnoreCase(key.toString())
//								&& !"editStatus".equalsIgnoreCase(key
//										.toString())
//								&& !"deleteFlg"
//										.equalsIgnoreCase(key.toString())
//								&& !"updateDt".equalsIgnoreCase(key.toString())
//								&& !"createDt".equalsIgnoreCase(key.toString())
//								&& !"createBy".equalsIgnoreCase(key.toString())
//								&& !"updateBy".equalsIgnoreCase(key.toString())) {
//							// 由于过滤掉8个共同字段，就不用再进入比较了。
//							if (oldData.containsKey(key)) {
//								boolean same = true;
//								if (newData.get(key) == null
//										|| oldData.get(key) == null) {
//									String newNull = "";
//									String oldNull = "";
//									if (oldData.get(key) == null
//											|| oldData.get(key) == ""
//											|| oldData.get(key).toString()
//													.trim().isEmpty()) {
//										oldNull = "空";
//									}
//									if (newData.get(key) == null
//											|| newData.get(key) == ""
//											|| newData.get(key).toString()
//													.trim().isEmpty()) {
//										newNull = "空";
//									}
//									same = oldNull.equalsIgnoreCase(newNull);
//								} else {
//									if ("iam_account_info"
//											.equalsIgnoreCase(tableName)
//											|| "dict_person"
//													.equalsIgnoreCase(tableName)) {
//										if ("serviceStartDate"
//												.equalsIgnoreCase(key
//														.toString())) {
//											SimpleDateFormat df = new SimpleDateFormat(
//													"yyyy-MM-dd HH:mm:ss");
//											same = df.format(oldData.get(key))
//													.equalsIgnoreCase(
//															df.format(newData
//																	.get(key)));
//										} else {
//											same = newData
//													.get(key)
//													.toString()
//													.equalsIgnoreCase(
//															oldData.get(key)
//																	.toString());
//										}
//									} else {
//										if(key.toString().contains("date")){
//											SimpleDateFormat df2 = new SimpleDateFormat(
//													"yyyy-MM-dd HH:mm:ss");
//											
//											same = df2.format(newData.get(key))
//													.toString()
//													.equalsIgnoreCase(
//															df2.format(oldData.get(key))
//																	.toString());
//										}else{
//											same = newData
//													.get(key)
//													.toString()
//													.equalsIgnoreCase(
//															oldData.get(key)
//																	.toString());
//										}
//									}
//								}
//								if (!same) {
//									// 系统设值，针对同一个字段
//									if ("IAM_PARM".equalsIgnoreCase(tableName)) {
//										Object cacheKey = null;
//										if ("pwdCreateRule"
//												.equalsIgnoreCase(newData.get(
//														"parName").toString())) {
//											cacheKey = key;
//											key = "passwdRules";
//										} else if ("remindMode"
//												.equalsIgnoreCase(newData.get(
//														"parName").toString())) {
//											cacheKey = key;
//											key = "remindMethods";
//										}
//										bf.append(props.get(findRightNameMap
//												.get(key)));
//										key = cacheKey;
//									}
//									// 处理术语
//									else if (tableName.indexOf("dict") != -1) {
//										bf.append(props.get(key + "Label"));
//									} else {
//										bf.append(props.get(findRightNameMap
//												.get(key)));
//									}
//									bf.append(":\"");
//									String oldInfo = null;
//									String newInfo = null;
//									if (oldData.get(key) == null
//											|| oldData.get(key) == "") {
//										oldInfo = "空";
//									} else {
//										oldInfo = oldData.get(key).toString();
//										if ("ACTL_ROLE"
//												.equalsIgnoreCase(tableName)
//												&& "occupationalType"
//														.equalsIgnoreCase(key
//																.toString())) {
//											if ("0".equalsIgnoreCase(oldInfo)) {
//												oldInfo = "医生";
//											} else if ("1"
//													.equalsIgnoreCase(oldInfo)) {
//												oldInfo = "护士";
//											}
//										} else if ("iam_sys_info"
//												.equalsIgnoreCase(tableName)) {
//											if ("cateCode".equalsIgnoreCase(key
//													.toString())) {
//												// 查询对应的系统分类名
//												List<IamSysCate> iscList = logProcessorDao
//														.selectCateNameByCateCode(oldInfo);
//												IamSysCate isc = null;
//												if (iscList.size() > 0) {
//													isc = (IamSysCate) iscList
//															.get(0);
//													oldInfo = isc.getCateName();
//												}
//											} else if ("supId"
//													.equalsIgnoreCase(key
//															.toString())) {
//												// 查询对应的供应商名
//												List<IamSupplier> iscList = logProcessorDao
//														.selectSupNameBySupId(oldInfo);
//												IamSupplier is = null;
//												if (iscList.size() > 0) {
//													is = (IamSupplier) iscList
//															.get(0);
//													oldInfo = is.getSupName();
//												}
//											}
//										} else if ("IAM_PARM"
//												.equalsIgnoreCase(tableName)
//												&& "parValue"
//														.equalsIgnoreCase(key
//																.toString())) {
//											if ("pwdCreateRule"
//													.equalsIgnoreCase(newData
//															.get("parName")
//															.toString())) {
//												if ("0".equalsIgnoreCase(oldInfo)) {
//													oldInfo = "和用户ID一样";
//												} else if ("1"
//														.equalsIgnoreCase(oldInfo)) {
//													oldInfo = "系统随机生成";
//												}
//											} else if ("remindMode"
//													.equalsIgnoreCase(newData
//															.get("parName")
//															.toString())) {
//												if ("0".equalsIgnoreCase(oldInfo)) {
//													oldInfo = "不提醒";
//												} else if ("1"
//														.equalsIgnoreCase(oldInfo)) {
//													oldInfo = "短信提醒";
//												} else if ("2"
//														.equalsIgnoreCase(oldInfo)) {
//													oldInfo = "邮件提醒";
//												} else if ("3"
//														.equalsIgnoreCase(oldInfo)) {
//													oldInfo = "短信和邮件提醒";
//												}
//											}
//										}
//									}
//									if (newData.get(key) == null
//											|| newData.get(key) == "") {
//										newInfo = "空";
//									} else {
//										newInfo = newData.get(key).toString();
//										if ("ACTL_ROLE"
//												.equalsIgnoreCase(tableName)
//												&& "occupationalType"
//														.equalsIgnoreCase(key
//																.toString())) {
//											if ("0".equalsIgnoreCase(newInfo)) {
//												newInfo = "医生";
//											} else if ("1"
//													.equalsIgnoreCase(newInfo)) {
//												newInfo = "护士";
//											}
//										} else if ("iam_sys_info"
//												.equalsIgnoreCase(tableName)) {
//											if ("cateCode".equalsIgnoreCase(key
//													.toString())) {
//												// 查询对应的系统分类名
//												List<IamSysCate> iscList = logProcessorDao
//														.selectCateNameByCateCode(newInfo);
//												IamSysCate isc = null;
//												if (iscList.size() > 0) {
//													isc = (IamSysCate) iscList
//															.get(0);
//													newInfo = isc.getCateName();
//												}
//											} else if ("supId"
//													.equalsIgnoreCase(key
//															.toString())) {
//												// 查询对应的供应商名
//												List<IamSupplier> iscList = logProcessorDao
//														.selectSupNameBySupId(newInfo);
//												IamSupplier is = null;
//												if (iscList.size() > 0) {
//													is = (IamSupplier) iscList
//															.get(0);
//													newInfo = is.getSupName();
//												}
//											}
//										} else if ("IAM_PARM"
//												.equalsIgnoreCase(tableName)
//												&& "parValue"
//														.equalsIgnoreCase(key
//																.toString())) {
//											if ("pwdCreateRule"
//													.equalsIgnoreCase(newData
//															.get("parName")
//															.toString())) {
//												if ("0".equalsIgnoreCase(newInfo)) {
//													newInfo = "和用户ID一样";
//												} else if ("1"
//														.equalsIgnoreCase(newInfo)) {
//													newInfo = "系统随机生成";
//												}
//											} else if ("remindMode"
//													.equalsIgnoreCase(newData
//															.get("parName")
//															.toString())) {
//												if ("0".equalsIgnoreCase(newInfo)) {
//													newInfo = "不提醒";
//												} else if ("1"
//														.equalsIgnoreCase(newInfo)) {
//													newInfo = "短信提醒";
//												} else if ("2"
//														.equalsIgnoreCase(newInfo)) {
//													newInfo = "邮件提醒";
//												} else if ("3"
//														.equalsIgnoreCase(newInfo)) {
//													newInfo = "短信和邮件提醒";
//												}
//											}
//										}
//									}
//									if ("IAM_PARM".equalsIgnoreCase(tableName)) {
//										bf.append(oldInfo + "\"变更为\"" + newInfo);
//									} else {
//										bf.append(oldInfo + "\"为\"" + newInfo);
//									}
//									bf.append("\"、");
//								}
//							}
//						}
//
//					}
//					if (!"".equalsIgnoreCase(bf.toString())) {
//						differentValue = bf.toString().substring(0,
//								bf.toString().length() - 1);
//						differentValue += ".]";
//					}
//				}
//			}
//		}
//		return differentValue;
//	}
//
//	//记录“审计服务”——“查询审计信息”操作日志
//	private void saveAuditOptLog(boolean insert, IamLog iLog, Map<String, Object> arg0) {
//		if (insert) {
//			iLog.setOperModu("0X2");
//			iLog.setOperObj("0X201");
//			iLog.setOperCont("同步新增审计信息[审计ID:" + arg0.get("eventCode") + ",审计系统ID:" + arg0.get("sysId")+ "]");
//		}
//		logDao.executeAdd(iLog);
//	}
//	
//	// 记录“规则管理”——“模型”操作日志
//	private void saveModelOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0) {
//		if (insert) {
//			iLog.setOperModu("00402");
//			iLog.setOperObj("00402001");
//			iLog.setOperCont("新建模型[" + arg0.get("modelName") + "]");
//		}
//		// 更新已经在更新处单独记录,如果不慎进入，则直接返回。
//		else if (update) {
//			return;
//		} else if (delete) {
//			iLog.setOperModu("00402");
//			iLog.setOperObj("00402003");
//			iLog.setOperCont("删除模型[" + arg0.get("modelName") + "]");
//		}
//		logDao.executeAdd(iLog);
//	}
//
//	// 记录“规则管理”——“规则组”操作日志
//	private void saveRulegroupOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		iLog.setOperModu("00401");
//		if (insert) {
//			iLog.setOperObj("00401001");
//			iLog.setOperCont("新建规则组[" + arg0.get("rulegroupName") + "]");
//		} else if (update) {
//			iLog.setOperObj("00401002");
//			condition.put("name", "rulegroup_id");
//			condition.put("value", arg0.get("rulegroupId").toString());
//			compareResult = compareContent(tableName, arg0, condition);
//			iLog.setOperCont("编辑规则组[" + arg0.get("rulegroupName") + "]的["
//					+ compareResult);
//		} else if (delete) {
//			iLog.setOperObj("00401003");
//			iLog.setOperCont("删除规则组[" + arg0.get("rulegroupName") + "]");
//		}
//		if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“规则管理”——“规则”操作日志
//	private void saveRuleOptLog(boolean insert, boolean update, boolean delete,
//			IamLog iLog, Map<String, Object> arg0) {
//
//		if (insert) {
//			iLog.setOperModu("00403");
//			iLog.setOperObj("00403001");
//			iLog.setOperCont("新建规则[" + arg0.get("ruleName") + "]");
//		} else if (update) {
//			// 编辑规则___已经单独记录
//			if ("0".equalsIgnoreCase(arg0.get("status").toString())) {
//				return;
//			}// 提交审批
//			else if ("1".equalsIgnoreCase(arg0.get("status").toString())) {
//				iLog.setOperModu("00404");
//				iLog.setOperObj("00404001");
//				iLog.setOperCont("提交审批规则[" + arg0.get("ruleName") + "]");
//			}// 同意
//			else if ("2".equalsIgnoreCase(arg0.get("status").toString())) {
//				iLog.setOperModu("00404");
//				iLog.setOperObj("00404002");
//				iLog.setOperCont("审批同意规则[" + arg0.get("ruleName") + "]");
//			}// 驳回
//			else if ("3".equalsIgnoreCase(arg0.get("status").toString())) {
//				iLog.setOperModu("00404");
//				iLog.setOperObj("00404003");
//				iLog.setOperCont("审批驳回规则[" + arg0.get("ruleName") + "]");
//			}// 发布
//			else if ("4".equalsIgnoreCase(arg0.get("status").toString())) {
//				iLog.setOperModu("00404");
//				iLog.setOperObj("00404004");
//				iLog.setOperCont("发布规则[" + arg0.get("ruleName") + "]");
//			}
//		} else if (delete) {
//			iLog.setOperModu("00403");
//			iLog.setOperObj("00403004");
//			iLog.setOperCont("删除规则[" + arg0.get("ruleName") + "]");
//		}
//		logDao.executeAdd(iLog);
//	}
//
//	// 记录“规则管理”——“规则版本”操作日志
//	private void saveRuleVersionOptLog(boolean insert, IamLog iLog,
//			Map<String, Object> arg0) {
//		if (insert) {
//			iLog.setOperModu("00404");
//			iLog.setOperObj("00404005");
//			iLog.setOperCont("新增规则版本[V" + arg0.get("versionNo") + "]");
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“审计管理”——“审计事件”操作日志
//	private void saveAudEventOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		iLog.setOperModu("00501");
//		if (insert) {
//			iLog.setOperObj("00501001");
//
//			iLog.setOperCont("新增审计事件[" + arg0.get("eventName") + "]");
//		} else if (update) {
//			iLog.setOperObj("00501002");
//			condition.put("name", "event_code");
//			condition.put("value", arg0.get("eventCode").toString());
//			compareResult = compareContent(tableName, arg0, condition);
//			iLog.setOperCont("修改审计事件[" + arg0.get("eventName") + "]的["
//					+ compareResult);
//		}
//		if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“统一身份管理和权限管理”——“账户管理”操作日志
//	private void saveAccountInfoOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName, IamAccountInfo iai) {
//		// 如果是人员字典引起同步账户表信息的情况，暂不考虑记录日志
//		if (!arg0.containsKey("passwd")) {
//			return;
//		}
//		if (arg0.containsKey("status")) {
//			if ("1".equalsIgnoreCase(arg0.get("status").toString())) {
//				iLog.setOperModu("00101");
//				List<IamAccountInfo> iaiList = logProcessorDao
//						.selectUserNameByUserNo(arg0.get("userNo").toString());
//				if (iaiList.size() > 0) {
//					iai = (IamAccountInfo) iaiList.get(0);
//					if (arg0.get("passwd").toString()
//							.equalsIgnoreCase(iai.getPasswd().toString())
//							&& !arg0.get("status")
//									.toString()
//									.equalsIgnoreCase(
//											String.valueOf(iai.getStatus()))) {
//						iLog.setOperObj("00101001");
//						iLog.setOperCont("启用账户[" + arg0.get("userNo") + ","
//								+ arg0.get("userName") + "]");
//					} else if (!arg0.get("passwd").toString()
//							.equalsIgnoreCase(iai.getPasswd().toString())) {
//						iLog.setOperObj("00101003");
//						iLog.setOperCont("重置账户[" + arg0.get("userNo") + ","
//								+ arg0.get("userName") + "]的密码");
//					}
//				}
//			} else if ("2".equalsIgnoreCase(arg0.get("status").toString())) {
//				iLog.setOperModu("00101");
//				iLog.setOperObj("00101002");
//				iLog.setOperCont("停用账户[" + arg0.get("userNo") + ","
//						+ arg0.get("userName") + "]");
//			} else if ("0".equalsIgnoreCase(arg0.get("status").toString())) {
//				// 同步人员字典时，还需要操作iam_account_info
//				iLog.setOperModu("0X1");
//				iLog.setOperObj("0X101");
//				if (insert) {
//					iLog.setOperCont("同步添加账户[" + arg0.get("userNo") + ","
//							+ arg0.get("userName") + "]");
//				} else if (update
//						&& "0".equalsIgnoreCase(arg0.get("deleteFlg")
//								.toString())) {
//					condition.put("name", "user_no");
//					condition.put("value", arg0.get("userNo").toString());
//					compareResult = compareContent(tableName, arg0, condition);
//					if (compareResult == null) {
//						return;
//					}
//					iLog.setOperCont("同步修改账户[" + arg0.get("userNo") + ","
//							+ arg0.get("userName") + "]的[" + compareResult);
//				} else if (delete) {
//					iLog.setOperCont("同步删除账户[" + arg0.get("userNo") + ","
//							+ arg0.get("userName") + "]");
//				}
//			}
//
//			if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//				logDao.executeAdd(iLog);
//			}
//		}
//	}
//
//	// 记录“统一身份管理和权限管理”——“账户管理”——“配置权限”操作日志
//	private void saveAccountSysOptLog(boolean insert, boolean delete,
//			IamLog iLog, Map<String, Object> arg0, IamAccountInfo iai) {
//		iLog.setOperModu("00101");
//		if (arg0.containsKey("userNo") && arg0.containsKey("sysId")) {
//			List<IamAccountInfo> list1 = logProcessorDao
//					.selectUserNameByUserNo(arg0.get("userNo").toString());
//			List<IamSysInfo> list2 = logProcessorDao.selectSysNameBySysId(arg0
//					.get("sysId").toString());
//			IamSysInfo isi = null;
//			if (list1.size() > 0 && list2.size() > 0) {
//				iai = (IamAccountInfo) list1.get(0);
//				isi = (IamSysInfo) list2.get(0);
//				if (insert) {
//					iLog.setOperObj("00101004");
//					iLog.setOperCont("添加用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]对注册系统[" + isi.getSysId()
//							+ "," + isi.getSysName() + "]的访问权限");
//				} else if (delete) {
//					iLog.setOperObj("00101004");
//					iLog.setOperCont("删除用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]对注册系统[" + isi.getSysId()
//							+ "," + isi.getSysName() + "]的访问权限");
//				}
//				logDao.executeAdd(iLog);
//			}
//		}
//	}
//
//	// 记录“统一身份管理和权限管理”——“系统供应商注册管理”操作日志
//	private void saveSupplierOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		iLog.setOperModu("00102");
//		if (insert) {
//			iLog.setOperObj("00102001");
//			if (arg0.containsKey("supId") && arg0.containsKey("supName")) {
//				iLog.setOperCont("添加供应商[" + arg0.get("supId") + ","
//						+ arg0.get("supName") + "]");
//			}
//		} else if (delete) {
//			iLog.setOperObj("00102003");
//			if (arg0.containsKey("supId") && arg0.containsKey("supName")) {
//				iLog.setOperCont("删除供应商[" + arg0.get("supId") + ","
//						+ arg0.get("supName") + "]");
//			}
//		} else if (update) {
//			condition.put("name", "sup_id");
//			condition.put("value", arg0.get("supId").toString());
//			compareResult = compareContent(tableName, arg0, condition);
//			iLog.setOperObj("00102002");
//			iLog.setOperCont("修改供应商[" + arg0.get("supId") + ","
//					+ arg0.get("supName") + "]的[" + compareResult);
//
//		}
//		if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“统一身份管理和权限管理”——“系统注册管理”操作日志
//	private void saveSysInfoOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		iLog.setOperModu("00103");
//		if (insert) {
//			iLog.setOperObj("00103001");
//			if (arg0.containsKey("sysId") && arg0.containsKey("sysName")) {
//				iLog.setOperCont("添加注册系统[" + arg0.get("sysId") + ","
//						+ arg0.get("sysName") + "]");
//			}
//		} else if (delete) {
//			iLog.setOperObj("00103005");
//			if (arg0.containsKey("sysId") && arg0.containsKey("sysName")) {
//				iLog.setOperCont("删除注册系统[" + arg0.get("sysId") + ","
//						+ arg0.get("sysName") + "]");
//			}
//		} else if (update) {
//			condition.put("name", "sys_id");
//			condition.put("value", arg0.get("sysId").toString());
//			compareResult = compareContent(tableName, arg0, condition);
//			iLog.setOperObj("00103002");
//			iLog.setOperCont("修改注册系统[" + arg0.get("sysId") + ","
//					+ arg0.get("sysName") + "]的[" + compareResult);
//
//		}
//		if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“访问控制管理”——“角色访问权限设定”——“增删改”操作日志
//	private void saveActlRoleOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		iLog.setOperModu("00302");
//		if (insert) {
//			iLog.setOperObj("00302001");
//			if (arg0.containsKey("roleId") && arg0.containsKey("roleName")) {
//				iLog.setOperCont("添加角色[" + arg0.get("roleName") + "]");
//			}
//		} else if (delete) {
//			iLog.setOperObj("00302004");
//			if (arg0.containsKey("roleId") && arg0.containsKey("roleName")) {
//				iLog.setOperCont("删除角色[" + arg0.get("roleName") + "]");
//			}
//		} else if (update) {
//			condition.put("name", "role_id");
//			condition.put("value", arg0.get("roleId").toString());
//			compareResult = compareContent(tableName, arg0, condition);
//			iLog.setOperObj("00302002");
//			iLog.setOperCont("修改角色[" + arg0.get("roleName") + "]的["
//					+ compareResult);
//		}
//		if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“访问控制管理”——“角色访问权限设定”——“角色编辑”操作日志
//	private void saveActlRoleUserOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			IamAccountInfo iai) {
//		iLog.setOperModu("00302");
//		if (arg0.containsKey("userNo") && arg0.containsKey("roleId")) {
//			List<IamAccountInfo> list1 = logProcessorDao
//					.selectUserNameByUserNo(arg0.get("userNo").toString());
//			List<ActlRole> list2 = logProcessorDao.selectRoleNameByRoleId(arg0
//					.get("roleId").toString());
//			ActlRole ar = null;
//			if (list1.size() > 0 && list2.size() > 0) {
//				iai = (IamAccountInfo) list1.get(0);
//				ar = (ActlRole) list2.get(0);
//				if (insert) {
//					iLog.setOperObj("00302002");
//					iLog.setOperCont("角色[" + ar.getRoleName() + "]添加人员["
//							+ iai.getUserNo() + "," + iai.getUserName() + "]");
//				} else if (delete) {
//					iLog.setOperObj("00302002");
//					iLog.setOperCont("角色[" + ar.getRoleName() + "]删除人员["
//							+ iai.getUserNo() + "," + iai.getUserName() + "]");
//				}
//				logDao.executeAdd(iLog);
//			}
//		}
//	}
//
//	// 记录“访问控制管理”——“角色访问权限设定”——“权限设定”操作日志
//	private void saveActlRoleAuthOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0) {
//		iLog.setOperModu("00302");
//		iLog.setOperObj("00302003");
//		// 1.取角色名称
//		List<ActlRole> arList = logProcessorDao.selectRoleNameByRoleId(arg0
//				.get("roleId").toString());
//		// 2.取角色权限
//		List<ActlAuth> aaList = logProcessorDao.selectMemoByAuthId(arg0.get(
//				"authId").toString());
//		ActlRole ar = null;
//		ActlAuth aa = null;
//		if (arList.size() > 0 && aaList.size() > 0) {
//			ar = (ActlRole) arList.get(0);
//			aa = (ActlAuth) aaList.get(0);
//			if (insert) {
//				if (aa.getAuthType() == 0) {
//					iLog.setOperCont("增加角色[" + ar.getRoleName() + "]对患者范围["
//							+ aa.getMemo() + "]的访问权限");
//				} else if (aa.getAuthType() == 1) {
//					iLog.setOperCont("增加角色[" + ar.getRoleName() + "]显示患者信息["
//							+ aa.getMemo() + "]");
//				} else if (aa.getAuthType() == 2) {
//					iLog.setOperCont("增加角色[" + ar.getRoleName() + "]对临床信息["
//							+ aa.getMemo() + "]的访问权限");
//				}
//			} else if (delete) {
//				if (aa.getAuthType() == 0) {
//					iLog.setOperCont("删除角色[" + ar.getRoleName() + "]对患者范围["
//							+ aa.getMemo() + "]的访问权限");
//				} else if (aa.getAuthType() == 1) {
//					iLog.setOperCont("删除角色[" + ar.getRoleName() + "]显示患者信息["
//							+ aa.getMemo() + "]");
//				} else if (aa.getAuthType() == 2) {
//					iLog.setOperCont("删除角色[" + ar.getRoleName() + "]对临床信息["
//							+ aa.getMemo() + "]的访问权限");
//				}
//			}
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“访问控制管理”——“角色访问权限设定”——“权限设定”操作日志
//	private void saveUserInfoOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName, IamAccountInfo iai) {
//		iLog.setOperModu("00601");
//		if (arg0.containsKey("userNo")) {
//			List<IamAccountInfo> list = logProcessorDao
//					.selectUserNameByUserNo(arg0.get("userNo").toString());
//			if (list.size() > 0) {
//				iai = (IamAccountInfo) list.get(0);
//				if (insert) {
//					iLog.setOperObj("00601001");
//					iLog.setOperCont("添加用户[" + arg0.get("userNo") + ","
//							+ iai.getUserName() + "]");
//
//				} else if (delete) {
//					iLog.setOperObj("00601002");
//					iLog.setOperCont("删除用户[" + arg0.get("userNo") + ","
//							+ iai.getUserName() + "]");
//				} else if (update) {
//					condition.put("name", "user_no");
//					condition.put("value", arg0.get("userNo").toString());
//					compareResult = compareContent(tableName, arg0, condition);
//					iLog.setOperObj("00601003");
//					iLog.setOperCont("修改系统用户[" + arg0.get("userNo") + ","
//							+ iai.getUserName() + "]的[" + compareResult);
//				}
//				if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//					logDao.executeAdd(iLog);
//				}
//			}
//		}
//	}
//
//	// 记录“访问控制管理”——“个人访问权限设定”——“权限设定”操作日志
//	private void saveActlUserAuthOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			IamAccountInfo iai) {
//		iLog.setOperModu("00303");
//		iLog.setOperObj("00303001");
//		List<IamAccountInfo> accountList = logProcessorDao
//				.selectUserNameByUserNo(arg0.get("userNo").toString());
//		List<ActlAuth> aaList = logProcessorDao.selectMemoByAuthId(arg0.get(
//				"authId").toString());
//		ActlAuth aa = null;
//		if (accountList.size() > 0 && aaList.size() > 0) {
//			iai = (IamAccountInfo) accountList.get(0);
//			aa = (ActlAuth) aaList.get(0);
//			if (insert) {
//				if (aa.getAuthType() == 0) {
//					iLog.setOperCont("增加用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]对患者范围[" + aa.getMemo()
//							+ "]的访问权限");
//				} else if (aa.getAuthType() == 1) {
//					iLog.setOperCont("增加用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]显示患者信息[" + aa.getMemo()
//							+ "]");
//				} else if (aa.getAuthType() == 2) {
//					iLog.setOperCont("增加用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]对临床信息[" + aa.getMemo()
//							+ "]的访问权限");
//				}
//			} else if (delete) {
//				if (aa.getAuthType() == 0) {
//					iLog.setOperCont("删除用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]对患者范围[" + aa.getMemo()
//							+ "]的访问权限");
//				} else if (aa.getAuthType() == 1) {
//					iLog.setOperCont("删除用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]显示患者信息[" + aa.getMemo()
//							+ "]");
//				} else if (aa.getAuthType() == 2) {
//					iLog.setOperCont("删除用户[" + iai.getUserNo() + ","
//							+ iai.getUserName() + "]对临床信息[" + aa.getMemo()
//							+ "]的访问权限");
//				}
//			}
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“系统管理”——“服务订阅”操作日志
//	private void saveServiceSubscribeOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0) {
//		iLog.setOperModu("00604");
//		if (arg0.containsKey("sysId") && arg0.containsKey("serviceId")) {
//
//			List<IamSysInfo> sysList = logProcessorDao
//					.selectSysNameBySysId(arg0.get("sysId").toString());
//			List<IemService> serviceList = logProcessorDao
//					.selectServiceNameByServiceId(arg0.get("serviceId")
//							.toString());
//
//			IamSysInfo isi = null;
//			IemService is = null;
//			IemDomain id = null;
//			IemOrderSubType iost = null;
//			if (sysList.size() > 0 && serviceList.size() > 0) {
//				isi = (IamSysInfo) sysList.get(0);
//				is = (IemService) serviceList.get(0);
//				boolean flag = true;
//				StringBuffer sb = new StringBuffer();
//				sb.append("注册系统[");
//				sb.append(isi.getSysId());
//				sb.append(",");
//				sb.append(isi.getSysName());
//				if (insert) {
//					sb.append("]订阅服务[");
//				} else if (delete) {
//					sb.append("]取消订阅服务[");
//				}
//
//				sb.append(is.getServiceId());
//				sb.append(",");
//				sb.append(is.getServiceName());
//				sb.append(",");
//				// domainId=0时，不显示域名称。
//				if (!"0".equalsIgnoreCase(arg0.get("domainId").toString())) {
//
//					List<IemDomain> domainList = logProcessorDao
//							.selectDomainNameByDomainId(arg0.get("domainId")
//									.toString());
//					if (domainList.size() > 0) {
//						id = (IemDomain) domainList.get(0);
//						sb.append(id.getDomainName());
//						sb.append(",");
//					}
//				} else {
//					if ("0".equalsIgnoreCase(arg0.get("orderSubTypeCd")
//							.toString())) {
//						flag = false;
//						sb.delete(sb.length() - 1, sb.length());
//					}
//				}
//				// 医嘱小分类cd=0时，不显示医嘱小分类名称。
//				if (!"0".equalsIgnoreCase(arg0.get("orderSubTypeCd").toString())) {
//
//					List<IemOrderSubType> orderSubTypeList = logProcessorDao
//							.selectOrderSubTypeNameByOrderSubTypeCd(arg0.get(
//									"orderSubTypeCd").toString());
//					if (orderSubTypeList.size() > 0) {
//						iost = (IemOrderSubType) orderSubTypeList.get(0);
//						sb.append(iost.getOrderSubTypeName());
//					}
//				} else {
//					if (flag) {
//						sb.delete(sb.length() - 1, sb.length());
//					}
//				}
//				sb.append("]");
//
//				if (insert) {
//					iLog.setOperObj("00604001");
//					iLog.setOperCont(sb.toString());
//				} else if (delete) {
//					iLog.setOperObj("00604001");
//					iLog.setOperCont(sb.toString());
//				}
//				logDao.executeAdd(iLog);
//			}
//		}
//	}
//
//	// 记录“系统管理”——“用户管理”——“配置权限”操作日志
//	private void saveUserResrOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			IamAccountInfo iai) {
//		iLog.setOperModu("00601");
//		iLog.setOperObj("00601004");
//		List<IamAccountInfo> accountList = logProcessorDao
//				.selectUserNameByUserNo(arg0.get("userNo").toString());
//		String objectName = null;
//		String objectId = null;
//		IamButtonAuth iba = null;
//		List<IamButtonAuth> buttonList = logProcessorDao
//				.selectOptNameByResrCode(arg0.get("resrCode").toString());
//		if (buttonList.size() > 0) {
//			iba = (IamButtonAuth) buttonList.get(0);
//			objectName = iba.getOptName();
//			objectId = iba.getOptCode();
//		}
//		if (accountList.size() > 0 && objectName != null && objectId != null) {
//			iai = (IamAccountInfo) accountList.get(0);
//			if (insert) {
//				iLog.setOperCont("添加用户[" + iai.getUserNo() + ","
//						+ iai.getUserName() + "]对系统对象[" + objectId + ","
//						+ objectName + "]的操作权限");
//			} else if (delete) {
//				iLog.setOperCont("删除用户[" + iai.getUserNo() + ","
//						+ iai.getUserName() + "]对系统对象[" + objectId + ","
//						+ objectName + "]的操作权限");
//			}
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 记录“系统管理”——“系统设值”操作日志
//	private void saveSysMgrOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		iLog.setOperModu("00602");
//		iLog.setOperObj("00602001");
//		if ("pwdCreateRule".equalsIgnoreCase(arg0.get("parName").toString())) {
//			condition.put("name", "par_name");
//			condition.put("value", "pwdCreateRule");
//			compareResult = compareContent(tableName, arg0, condition);
//			iLog.setOperCont(compareResult);
//		} else if ("remindMode"
//				.equalsIgnoreCase(arg0.get("parName").toString())) {
//			condition.put("name", "par_name");
//			condition.put("value", "remindMode");
//			compareResult = compareContent(tableName, arg0, condition);
//			iLog.setOperCont(compareResult);
//		}
//		if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//			logDao.executeAdd(iLog);
//		}
//	}
//
//	// 处理ggfw——“术语编码服务”相关操作
//	private void saveDictInfoOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		// 送审和发布操作
//		if ("dict_info".equalsIgnoreCase(tableName)) {
//			List<TDictInfo> diList = logProcessorDao
//					.selectDictInfoStatusByDictId(arg0.get("dictId").toString());
//			TDictInfo tdi = null;
//			if (diList.size() > 0) {
//				tdi = (TDictInfo) diList.get(0);
//				String dictName = props.get(
//						"tableName." + tdi.getDictTableName()).toString();
//				int oldStatus = tdi.getStatus();
//				int newStatus = Integer.parseInt(arg0.get("status").toString());
//				int newVersion = Integer.parseInt(arg0.get("versionNo")
//						.toString());
//				int oldVersion = tdi.getVersionNo();
//				if (((oldStatus == 0 && newStatus == 1) || (oldStatus == 2 && newStatus == 1))
//						&& newVersion == oldVersion) {
//					iLog.setOperModu("00202");
//					iLog.setOperObj("00202002");
//					iLog.setOperCont("审批同意[" + dictName + "]");
//					logDao.executeAdd(iLog);
//				} else if (((oldStatus == 0 && newStatus == 2) || (oldStatus == 1 && newStatus == 2))
//						&& newVersion == oldVersion) {
//					iLog.setOperModu("00202");
//					iLog.setOperObj("00202001");
//					iLog.setOperCont("审批驳回[" + dictName + "]");
//					logDao.executeAdd(iLog);
//				} else if (oldStatus == 1 && newStatus == 2
//						&& newVersion != oldVersion) {
//					iLog.setOperModu("00203");
//					iLog.setOperObj("00203001");
//					iLog.setOperCont("发布[" + dictName + "]成功");
//					logDao.executeAdd(iLog);
//				}
//			}
//		} else {
//			// 各个字典的增删改操作
//			if (arg0.containsKey("code") && arg0.containsKey("itemVersion")) {
//				iLog.setOperModu("00201");
//				String dictName = props.get("tableName." + tableName)
//						.toString();
//				if (insert) {
//					if ("1".equalsIgnoreCase(arg0.get("editStatus").toString())) {
//						iLog.setOperObj("00201001");
//						iLog.setOperCont("[" + dictName + "]添加项目["
//								+ arg0.get("code") + "," + arg0.get("name")
//								+ "]");
//					} else if ("3".equalsIgnoreCase(arg0.get("editStatus")
//							.toString())) {
//						iLog.setOperObj("00201003");
//						iLog.setOperCont("[" + dictName + "]删除项目["
//								+ arg0.get("code") + "," + arg0.get("name")
//								+ "]");
//					} else if ("2".equalsIgnoreCase(arg0.get("editStatus")
//							.toString())) {
//						iLog.setOperObj("00201002");
//						condition.put("code", arg0.get("code").toString());
//						// 取当前最大版本号记录
//						// dictItemsDao.selectMaxItemVersionByCode();
//						condition.put("itemVersion", String
//								.valueOf(dictItemsDao
//										.selectMaxItemVersionByCode(tableName,
//												arg0.get("code").toString())));
//						compareResult = compareContent(tableName, arg0,
//								condition);
//						iLog.setOperCont("[" + dictName + "]修改项目["
//								+ arg0.get("code") + "," + arg0.get("name")
//								+ "],修改[" + compareResult);
//					}
//				} else if (update) {
//					if ("3".equalsIgnoreCase(arg0.get("editStatus").toString())) {
//						iLog.setOperObj("00201003");
//						iLog.setOperCont("[" + dictName + "]删除项目["
//								+ arg0.get("code") + "," + arg0.get("name")
//								+ "]");
//					} else {
//						iLog.setOperObj("00201002");
//						condition.put("code", arg0.get("code").toString());
//						condition.put("itemVersion", arg0.get("itemVersion")
//								.toString());
//						compareResult = compareContent(tableName, arg0,
//								condition);
//						iLog.setOperCont("[" + dictName + "]修改项目["
//								+ arg0.get("code") + "," + arg0.get("name")
//								+ "],修改[" + compareResult);
//					}
//				} else if (delete) {
//					iLog.setOperObj("00201003");
//					iLog.setOperCont("[" + dictName + "]删除项目["
//							+ arg0.get("code") + "," + arg0.get("name") + "]");
//				}
//				if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//					logDao.executeAdd(iLog);
//				}
//			}
//		}
//	}
//
//	// 同步其它系统术语消息,msgId铁定不为空
//	private void saveReceiveDictInfoOptLog(boolean insert, boolean update,
//			boolean delete, IamLog iLog, Map<String, Object> arg0,
//			Map<String, String> condition, String compareResult,
//			String tableName) {
//		iLog.setOperModu("0X1");
//		iLog.setOperObj("0X101");
//		// 同步完其它系统术语消息后会更新dict_info表version，但无需记录日志。
//		if ("dict_info".equalsIgnoreCase(tableName)) {
//			return;
//		}
//		String dictName = props.get("tableName." + tableName).toString();
//		if (insert) {
//			iLog.setOperCont("同步添加[" + dictName + "]项目[" + arg0.get("code")
//					+ "," + arg0.get("name") + "]");
//		} else if (update) {
//			//人员职称字典联合主键特殊(person_cd、title_cd、item_version)
//			if("dict_person_title_relation".equalsIgnoreCase(tableName)){
//				condition.put("personCd", arg0.get("personCd").toString());
//				condition.put("titleCd", arg0.get("titleCd").toString());
//				condition.put("itemVersion", String.valueOf((Integer.parseInt(arg0
//						.get("itemVersion").toString()) - 1)));
//			}else{
//				condition.put("code", arg0.get("code").toString());
//				condition.put("itemVersion", String.valueOf((Integer.parseInt(arg0
//						.get("itemVersion").toString()) - 1)));
//			}
//			compareResult = compareContent(tableName, arg0, condition);
//			//人员职称字典联合主键特殊(person_cd、title_cd、item_version)
//			if("dict_person_title_relation".equalsIgnoreCase(tableName)){
//				iLog.setOperCont("同步修改[" + dictName + "]项目[" + arg0.get("personCd")
//						+ "," + arg0.get("titleCd") + "],修改[" + compareResult);
//			}else{
//				iLog.setOperCont("同步修改[" + dictName + "]项目[" + arg0.get("code")
//						+ "," + arg0.get("name") + "],修改[" + compareResult);
//			}
//			
//		} else if (delete) {
//			//人员职称字典联合主键特殊(person_cd、title_cd、item_version)
//			if("dict_person_title_relation".equalsIgnoreCase(tableName)){
//				iLog.setOperCont("同步删除[" + dictName + "]项目[" + arg0.get("personCd")
//						+ "," + arg0.get("titleCd") + "]");
//			}else{
//				iLog.setOperCont("同步删除[" + dictName + "]项目[" + arg0.get("code")
//						+ "," + arg0.get("name") + "]");
//			}
//			
//		}
//		if (!"内容无差异,无需插入日志。".equalsIgnoreCase(compareResult)) {
//			logDao.executeAdd(iLog);
//		}
//	}
}
