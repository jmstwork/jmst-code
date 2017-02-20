package com.founder.core;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.SqlExecutionSkipCause;
import org.seasar.doma.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Slf4jJdbcLogger implements JdbcLogger 
{
	private static Logger logger = LoggerFactory.getLogger(Slf4jJdbcLogger.class);
	
	private String enableFilters = "*";
	private String disableFilters = "*";
	private Map<String, Boolean> filters;
	
	public Slf4jJdbcLogger()
	{
		filters = new HashMap<String, Boolean>();
	}
	
	protected boolean isLogEnabled(String callerClassName, String callerMethodName)
	{
		if("*".equals(enableFilters))
			return true;
		else
		{
			Boolean flag = filters.get(callerMethodName + "." + callerMethodName);
			if(flag != null)
				return flag.booleanValue();
			else if("*".equals(disableFilters))
				return false;
			else
				return true;
		}
	}
	
	@Override
	public void logDaoMethodEntering(String callerClassName, String callerMethodName, Object... args) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.trace("{}.{} ENTRY", callerClassName, callerMethodName);
	}

	@Override
	public void logDaoMethodExiting(String callerClassName, String callerMethodName, Object result) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.trace("RETURN {}", result);
	}

	@Override
	public void logDaoMethodThrowing(String callerClassName, String callerMethodName, RuntimeException e) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.error("THROW {}", e);
	}

	@Override
	public void logSqlExecutionSkipping(String callerClassName,	String callerMethodName, SqlExecutionSkipCause cause) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.info(cause.name());
	}

	@Override
	public void logSql(String callerClassName, String callerMethodName, Sql<?> sql) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.info(Message.DOMA2076.getMessage(sql.getSqlFilePath(), sql.getFormattedSql()));
	}

	@Override
	public void logLocalTransactionBegun(String callerClassName, String callerMethodName, String transactionId) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2063.getMessage(transactionId));
	}

	@Override
	public void logLocalTransactionEnded(String callerClassName, String callerMethodName, String transactionId) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2064.getMessage(transactionId));
	}

	@Override
	public void logLocalTransactionCommitted(String callerClassName, String callerMethodName, String transactionId) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2067.getMessage(transactionId));
	}

	@Override
	public void logLocalTransactionSavepointCreated(String callerClassName, String callerMethodName, String transactionId, String savepointName) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2065.getMessage(transactionId, savepointName));
	}

	@Override
	public void logLocalTransactionSavepointReleased(String callerClassName, String callerMethodName, String transactionId, String savepointName) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2066.getMessage(transactionId, savepointName));
	}

	@Override
	public void logLocalTransactionRolledback(String callerClassName, String callerMethodName, String transactionId) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2068.getMessage(transactionId));
	}

	@Override
	public void logLocalTransactionSavepointRolledback(String callerClassName, String callerMethodName, String transactionId, String savepointName) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2069.getMessage(transactionId, savepointName));
	}

	@Override
	public void logLocalTransactionRollbackFailure(String callerClassName, String callerMethodName, String transactionId, SQLException e) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug(Message.DOMA2070.getMessage(transactionId));
	}

	@Override
	public void logAutoCommitEnablingFailure(String callerClassName, String callerMethodName, SQLException e) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug("{}, {}", Message.DOMA2071.getMessage(), e);
	}

	@Override
	public void logTransactionIsolationSettingFailuer(String callerClassName, String callerMethodName, int transactionIsolationLevel, SQLException e) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug("{}, {}", Message.DOMA2072.getMessage(transactionIsolationLevel), e);
	}

	@Override
	public void logConnectionClosingFailure(String callerClassName, String callerMethodName, SQLException e) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug("{}, {}", Message.DOMA2073.getMessage(), e);
	}

	@Override
	public void logStatementClosingFailure(String callerClassName, String callerMethodName, SQLException e) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug("{}, {}", Message.DOMA2074.getMessage(), e);
	}

	@Override
	public void logResultSetClosingFailure(String callerClassName, String callerMethodName, SQLException e) 
	{
		if(isLogEnabled(callerClassName, callerMethodName))
			logger.debug("{}, {}", Message.DOMA2075.getMessage(), e);
	}
}
