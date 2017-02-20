package com.founder.fmdm.service.sysmgt;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.service.sysmgt.dto.SystemRegisterDto;
import com.founder.fmdm.service.term.dto.UserInfoDto;

public interface UserManagerService {
	
	/**
	 * 提供系统
	 * @return
	 */
	List<UserInfoDto> selectUserInfoTable(String userNo,String userName, int status,SelectOptions options);
	
	public List<Map<String,Object>> selectSystemReg(SystemRegisterDto systemRegisterDto,
			SelectOptions options);
}
