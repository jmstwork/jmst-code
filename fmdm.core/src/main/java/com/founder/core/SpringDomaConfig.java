package com.founder.core;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.DomaAbstractConfig;
import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.dialect.Mssql2008Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.seasar.doma.jdbc.dialect.OracleDialect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringDomaConfig extends DomaAbstractConfig implements InitializingBean
{
    @Autowired
    private DataSource dataSource;
    
    private Dialect dialect;
    @Autowired
    private JdbcLogger jdbcLogger;
    
    @Value("${jdbc.dbtype}")
    private String dbType;
    
    public SpringDomaConfig()
    {
//        dialect = new org.seasar.doma.jdbc.dialect.OracleDialect();
//        dialect = new org.seasar.doma.jdbc.dialect.MysqlDialect();
//        dialect = new H2Dialect();
    }
    
    @Override
    public DataSource getDataSource()
    {
        return dataSource;
    }

    @Override
    public Dialect getDialect()
    {
        return dialect;
    }

	@Override
	public JdbcLogger getJdbcLogger() 
	{
		return this.jdbcLogger;
	}

    @Override
    public void afterPropertiesSet() throws Exception
    {
        if("oracle".equalsIgnoreCase(dbType))
            dialect = new OracleDialect();
        else if("mysql".equalsIgnoreCase(dbType))
            dialect = new MysqlDialect();
        else if("h2".equalsIgnoreCase(dbType))
            dialect = new H2Dialect();
        else if("sqlserver".equalsIgnoreCase(dbType))
            dialect = new Mssql2008Dialect();
        else
            throw new Exception("Unsupported database type.");
    }
}
