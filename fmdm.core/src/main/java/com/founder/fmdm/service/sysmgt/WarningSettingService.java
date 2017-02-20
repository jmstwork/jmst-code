package com.founder.fmdm.service.sysmgt;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Service;

import com.founder.fmdm.entity.IamSysInfo;
import com.founder.fmdm.entity.NfSetting;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.TDictPerson;
import com.founder.fmdm.service.sysmgt.dto.DepartmentDto;
import com.founder.fmdm.service.sysmgt.dto.WarningSettingDto;

@Service
public interface WarningSettingService {

    /**
     * 获取警告设定数据列表
     * @param wsDto
     * @param orderBy
     * @param pagingContext
     * @return
     */
	List<WarningSettingDto> selectWSList(WarningSettingDto wsDto,String orderBy,SelectOptions options);

	/**
	 * 获取系统名称，用于界面展示
	 * @param sysId
	 * @return
	 */
	List<IamSysInfo> selectSysNameBySysId(String sysId);
	
	/**
	 * 从system_code表获取对象向类型，category=C002
	 * @return
	 */
	List<SystemCode> selectCodeList(String cdDiv);

	/**
	 * 警告设定增、删、改
	 * @param wsDto
	 * @param opt
	 * @return
	 */
	int wsOperate(WarningSettingDto wsDto,String opt);

	/**
	 * 从人员字典中获取人员信息，注意账户信息(从iam_account_info获取)外的都从人员(dict_person)获取
	 * @param dictPerson
	 * @param pageContext
	 * @return
	 */
	public List<TDictPerson> selectUserListDictPerson(TDictPerson dictPerson,SelectOptions options);
	
	/**
	 * 警告设定发送XML消息
	 * @param msg
	 */
	public void sendMQMessage(String msg);
	
	/**
	 * 警告设定组织XML数据
	 * @param ns
	 * @param opt
	 * @return
	 */
	public String getXmlMessage(NfSetting ns,String opt);
	
	/**
	 * 查询所有数据传参：all
	 * @param deptCode
	 * @return
	 */
	public List<DepartmentDto> selectDeptDataFromDictDepartment(String deptCode);
	
	//List<RlmgRulegroup> selectInitGros();
}
