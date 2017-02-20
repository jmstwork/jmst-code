package com.founder.fmdm.dao.term;

import static org.seasar.doma.internal.util.AssertionUtil.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import org.seasar.doma.internal.jdbc.util.JdbcUtil;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.ScriptException;
import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.SqlKind;
import org.seasar.doma.jdbc.SqlParameter;
public class TermStructDaoDelegate {
	private Config config;

	protected ScriptException savedScriptException;
	
	public TermStructDaoDelegate(Config config, TermStructDao termStructDao) {
		this.config = config;
	}

	public Void executeTable(String sqlText) {
		Connection connection = JdbcUtil.getConnection(config.getDataSource());
		try {
			ScriptSql sql = new ScriptSql(sqlText,null);
			 Statement statement = JdbcUtil.createStatement(connection);
			 try {
                 log(sql,"executeTable");
                 setupOptions(statement);
                 statement.execute(sqlText);
             } catch (Exception e) {
            	 throw new ScriptException(e, sql,1);
             } finally {
                 JdbcUtil.close(statement, config.getJdbcLogger());
             }
		} finally {
            JdbcUtil.close(connection, config.getJdbcLogger());
        }
		return null;
	}
	
	
	public int executeDrop(String sqlText) {
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	protected static class ScriptSql implements Sql<SqlParameter> {

        protected final String rawSql;

        protected final String sqlFilePath;

        public ScriptSql(String rawSql, String sqlFilePath) {
            assertNotNull(rawSql);
            this.rawSql = rawSql;
            this.sqlFilePath = sqlFilePath;
        }

        @Override
        public SqlKind getKind() {
            return SqlKind.SCRIPT;
        }

        @Override
        public String getRawSql() {
            return rawSql;
        }

        @Override
        public String getFormattedSql() {
            return rawSql;
        }

        @Override
        public String getSqlFilePath() {
            return sqlFilePath;
        }

        @Override
        public List<SqlParameter> getParameters() {
            return Collections.emptyList();
        }

    }
	
    protected void log(ScriptSql sql,String executeMethod) {
        JdbcLogger logger = config.getJdbcLogger();
        logger.logSql(TermStructDaoDelegate.class.getName(), executeMethod, sql);
    }

    protected void setupOptions(Statement statement) throws SQLException {
        if (config.getQueryTimeout() > 0) {
            statement.setQueryTimeout(config.getQueryTimeout());
        }
    }
}
