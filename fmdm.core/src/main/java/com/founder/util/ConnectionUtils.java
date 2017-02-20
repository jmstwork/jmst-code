package com.founder.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.founder.fmdm.service.term.dto.DatasourceDto;

public class ConnectionUtils 
{

	public static Connection dataBaseConnection(DatasourceDto datasourceDto,String driverClass) throws Exception{
		Connection conn = null;
		Class.forName(driverClass);
		conn = DriverManager.getConnection(datasourceDto.getJdbcUrl(), datasourceDto.getJdbcUserName(), datasourceDto.getJdbcPassword());
		return conn;
	}
	
}
