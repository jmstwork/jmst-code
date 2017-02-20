package com.founder.fmdm.sync;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.jetel.database.sql.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.founder.fmdm.entity.TermSync;
import com.founder.fmdm.service.term.TermStructService;
import com.founder.fmdm.service.term.TermSyncService;
import com.founder.fmdm.service.term.dto.DatasourceDto;
import com.founder.fmdm.util.SyncUtil;

public class TermSyncTask {
			
	Logger logger = LoggerFactory.getLogger(TermSyncTask.class);
	
	@Autowired
	TermStructService termStructService;
	
	@Autowired
	TermSyncService syncTermService;
	
	@Autowired
	BasicDataSource dataSourceLocal;
	
	public int isMainSource = 1;
	
	public void execute(){
		
		logger.info("target database params : userName:"+dataSourceLocal.getUsername() + "	URL:"+dataSourceLocal.getUrl());
		Properties targetPro = new Properties();
		targetPro.put(DBConnection.XML_DATABASE_ATTRIBUTE, "ORACLE");
		targetPro.put(DBConnection.XML_USER_ATTRIBUTE, dataSourceLocal.getUsername());
		targetPro.put(DBConnection.XML_PASSWORD_ATTRIBUTE, dataSourceLocal.getPassword());
		targetPro.put(DBConnection.XML_JDBC_SPECIFIC_ATTRIBUTE, "ORACLE");
		targetPro.put(DBConnection.XML_DBURL_ATTRIBUTE,dataSourceLocal.getUrl());
		
		//检索术语同步设定列表
		List<TermSync> list = syncTermService.selectSyncTermForBatList(null);
		
		for(TermSync term:list){
			DatasourceDto datasource = syncTermService.selectDatasourceList(null, term.getDatasourceId()).get(0);
			logger.info("source database params ：userName:"+datasource.getJdbcUserName() + "	URL:"+datasource.getJdbcUrl());
			// 数据源连接测试
			logger.info("========================================数据源连接测试：开始========================================");
			int status = syncTermService.testDataSource(datasource);
			if(0 == status){
				logger.info("========================================数据源连接测试：失败，处理结束。========================================");
			}else{
				logger.info("========================================数据源连接测试：成功，同步处理继续。========================================");
				String from = datasource.getSystemId();
				String to = termStructService.queryDictMainByServiceId(term.getDictId()).getSysId();
				
				if(!from.equals(to)){
					isMainSource = 0;
				}
				
//				源数据库配置
				Properties sourcePro = new Properties();
				sourcePro.put(DBConnection.XML_DATABASE_ATTRIBUTE, datasource.getJdbcSpecific());
				sourcePro.put(DBConnection.XML_USER_ATTRIBUTE, datasource.getJdbcUserName());
				sourcePro.put(DBConnection.XML_PASSWORD_ATTRIBUTE, datasource.getJdbcPassword());
//				sourcePro.put(DBConnection.XML_JDBC_SPECIFIC_ATTRIBUTE, "MSSQL");
				sourcePro.put(DBConnection.XML_DBURL_ATTRIBUTE,datasource.getJdbcUrl());
				
				logger.info(term.getDictName()+".Begin...");
				logger.debug("同步任务服务器IP："+SyncUtil.getLocalIP());
				logger.debug("同步结束时间："+ term.getEndtime());
				logger.debug("当前系统时间："+ new Date());
				if(term.getEndtime()!=null){
					//是否到时间进行下一次同步
					logger.debug("设定同步的间隔是：" + term.getSyncInterval());
					logger.debug("当前时间间隔是：" + Math.abs(Integer.valueOf(SyncUtil.getDateDiffMM(term.getEndtime(), new Date()))));
					boolean bool = Math.abs(Integer.valueOf(SyncUtil.getDateDiffMM(term.getEndtime(), new Date())))>=term.getSyncInterval();
					logger.debug("是否到了下一次同步时间："+ bool);
					if(bool){
						//调用同步线程
						logger.debug("========================================同步线程启动============================================");
						TermSyncThread syncThread = new TermSyncThread(syncTermService, term, sourcePro, targetPro,isMainSource);
						Thread thread = new Thread(syncThread);
						thread.setName("术语同步线程"+new Date().getTime());
						thread.start();
					}
				}else{
					//调用同步线程
					logger.debug("========================================同步线程启动============================================");
					TermSyncThread syncThread = new TermSyncThread(syncTermService, term, sourcePro, targetPro,isMainSource);
					Thread thread = new Thread(syncThread);
					thread.setName("术语同步线程"+new Date().getTime());
					thread.start();
				}
			}
			
			logger.info("End...");
		}
	}
}
