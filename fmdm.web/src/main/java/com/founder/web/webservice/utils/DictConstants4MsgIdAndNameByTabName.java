package com.founder.web.webservice.utils;

import java.util.HashMap;
import java.util.Map;

public class DictConstants4MsgIdAndNameByTabName {

	public static final Map<String, String> CONSTANTS_4MsgIdAndName_MAP = new HashMap<String, String>();

	static {
		init();
	}

	private static void init() {
		CONSTANTS_4MsgIdAndName_MAP.put("IAM_ACCOUNT_INFO", "BS901@账户信息服务");
		// 业务需求暂无具体对照表
		/*
		 * CONSTANTS_4MsgIdAndName_MAP.put("3", "BS902@短信接口服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("4", "BS903@短信发送状态查询服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("5", "BS904@短信回复信息查询服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("6", "BS905@邮件发送接口服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("7", "BS906@规则判定服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("8", "BS907@审计信息更新服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("9", "BS908@访问控制信息服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("10", "BS909@警告通知接口服务");
		 * CONSTANTS_4MsgIdAndName_MAP.put("11", "MS001@术语全数据同步服务");
		 */

		CONSTANTS_4MsgIdAndName_MAP.put("DICT_ICD", "MS002@国际疾病分类（ICD）-门诊");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_ICD_EMR", "MS003@国际疾病分类（ICD）-电子病历");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_OPERATION_OPERATE", "MS004@手术与操作字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_SEX", "MS005@性别码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_NATION", "MS006@民族码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_OCCUPATION", "MS007@职业代码(病人)");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_MARRY", "MS008@婚姻状况类别代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CULTURE_LEVEL", "MS009@文化程度代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_COUNTRY_NAME", "MS010@国家名称");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_DISTRICT", "MS011@区县码字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PATIENT_TYPE", "MS012@病人类型代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_ABO_BLOOD_TYPE", "MS013@ABO血型代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CERTIFICATES_TYPE", "MS014@证件类型");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_OPERATION_INCISION", "MS015@手术切口愈合等级代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_DISEASE_DIAGNOSIS", "MS016@疾病诊断类别代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_ADMINISTRATION_ROUTE", "MS017@用药途径代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PAY", "MS018@支付方式代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_MEDICINE_DOSAGE", "MS019@药物剂型代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_SPECIMEN_TYPE", "MS020@标本类别代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_OUTPATIENT_COST", "MS021@门诊费用类别代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_HOSPITAL_COST", "MS022@住院费用分类代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_TRANSFUSION_BLOOD", "MS023@输血品种代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PERSON", "MS024@人员字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_DEPARTMENT", "MS025@科室字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_INPATIENT_AREA", "MS026@病区字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_MEDICINE_TYPE", "MS027@药物类型代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_DRUGS", "MS028@药品字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_EMPLOYMENTSTATUS", "MS029@在岗状态");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_EMPLOYEETYPE", "MS030@人员类型字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CHARGE_ITEM", "MS031@收费项目字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_RESPONCE_TYPE", "MS032@病人身份字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_INSPECTION_ITEM", "MS033@检验项目字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_INSPECTION_TYPE", "MS034@检验类型字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CLINIC_DIAGNOSIS", "MS035@门诊诊断字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_ORDER_ITEM", "MS036@医嘱字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CHARGE_TYPE", "MS037@病人付费类别");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_HIGH_PRICE", "MS038@高值耗材字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CHECK_ITEM", "MS039@检查项目字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CHECK_ITEM_GROUP", "MS040@检查项目分组");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PATIENT_DOMAIN", "MS041@患者域分类");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_LOW_PRICE", "MS042@低值耗材字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CHARGE_TEAM", "MS043@收费项目分组字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_HOLIDAY", "MS044@节假日字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PRESCRIPTION", "MS045@处方类型字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_OPERATE_AUTH", "MS047@手术分级权限字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PERSON_TITLE_RELATION", "MS048@人员职称关系字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PACING_MEASUREMENT", "MS049@药品包装单位计量单位字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PHARMACEUTICAL_FACTORY", "MS050@药品的制药厂信息");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_BLOOD_REQUIREMENT", "MS051@血液特殊要求");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_MEDICAL_STORAGEROOM", "MS052@药品库房字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_MEDICAL_NAME", "MS053@药品名称字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_MEDICAL_WHOLESALER", "MS054@药品批发商信息");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_DRUG_MARK", "MS057@毒麻标志");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_MEDICINE_CATEGORY", "MS058@药品类别");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_FREQUENCY", "MS059@常用频率");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CHECK_TYPE", "MS060@检查类型");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_BLOOD_URGENT", "MS061@用血紧急程度字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_FINANCIAL_DEPARTMENT", "MS062@财务科室字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_APPLY_INSPECTION", "MS063@输血申请检验项目");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_INSPECTION_SUB_ITEM", "MS064@检验子项目字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_TITLE", "MS067@职称字典");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_RH_BLOOD_TYPE", "MS068@RH血型");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PATIENT_RELATION", "MS069@与患者关系");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_BLOOD_TRANSFUSION", "MS070@输血目的");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CHECK_POSITION", "MS071@检查部位");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_ORDER_TYPE", "MS072@医嘱类型");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_ORDER_EXEC_STATE", "MS073@医嘱执行状态");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CLINIC_STATE", "MS074@就诊状态");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_PHYSICAL_EXAM", "MS075@体格检查项目");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_BOOLEAN", "MS076@布尔");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_COST_STATE", "MS077@费用状态");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_BILL_TYPE", "MS078@账单类别代码");
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CUT_HEAL", "MS066@手术切口"); // 手术切口字典表
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CUT_TYPE", "MS065@手术愈合等级"); // 手术愈合等级字典表
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_DISPATCH_CD", "MS046@消息分类"); // 消息分类字典表
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_LIS_ITEM_MAPPING", "MS080@检验项目对应关系字典"); // 医嘱与LIS检验项目关系字典
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CONTAINER_MAPPING", "MS081@检验项目对应容器字典"); // LIS检验项目与容器关系字典
		CONSTANTS_4MsgIdAndName_MAP.put("DICT_CONTAINER_INFO", "MS082@容器字典"); // 容器字典
	}
}
