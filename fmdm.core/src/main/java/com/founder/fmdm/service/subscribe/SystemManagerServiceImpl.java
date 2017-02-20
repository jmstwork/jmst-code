package com.founder.fmdm.service.subscribe;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.subscribe.SystemListDao;
import com.founder.fmdm.entity.SubsSys;

@Service
public class SystemManagerServiceImpl implements SystemManagerService {

	@Autowired
	SystemListDao systemListDao;
	
	@Override
	public List<SubsSys> selecSystemData(String sysId, String sysName,
			SelectOptions options) {
		
		return systemListDao.selecSystemData(sysId, sysName, options);
	}

}
