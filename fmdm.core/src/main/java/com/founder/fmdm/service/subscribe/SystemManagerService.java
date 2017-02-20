package com.founder.fmdm.service.subscribe;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.SubsSys;

public interface SystemManagerService {

	/**
	 * 订阅管理应用系统一览
	 * @param sysId
	 * @param sysName
	 * @param options
	 * @return
	 */
	public List<SubsSys> selecSystemData(String sysId,String sysName,SelectOptions options);
}
