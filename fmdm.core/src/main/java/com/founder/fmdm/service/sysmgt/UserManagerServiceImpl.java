package com.founder.fmdm.service.sysmgt;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.sysmgt.UserManagerDao;
import com.founder.fmdm.service.sysmgt.dto.SystemRegisterDto;
import com.founder.fmdm.service.term.dto.UserInfoDto;


@Service
public class UserManagerServiceImpl implements UserManagerService {

	@Autowired
	UserManagerDao userManagerDao;

	@Autowired
	BasicDataSource dataSourceO;

	@Override
	public List<UserInfoDto> selectUserInfoTable(String userNo,
			String userName, int status, SelectOptions options) {
		return userManagerDao.selectUserInfoTable(userNo,userName,status,options);
	}

	 @Override
	 public List<Map<String,Object>> selectSystemReg(SystemRegisterDto systemRegisterDto,
			 SelectOptions options)
	 {
		 	
			String searchSysID = systemRegisterDto.getSearchSysID() == null ? ""
	                : systemRegisterDto.getSearchSysID();
		    String searchSysNameValue = systemRegisterDto.getSearchSysName() == null ? ""
		            : systemRegisterDto.getSearchSysName();
		    String searchSysName = searchSysNameValue.trim();
		    String searchSupName = systemRegisterDto.getSearchSupName() == null ? ""
		            : systemRegisterDto.getSearchSupId();
		    return userManagerDao.selectSystemReg(searchSysID,searchSysName,
		             searchSupName, options);
	    }
}
