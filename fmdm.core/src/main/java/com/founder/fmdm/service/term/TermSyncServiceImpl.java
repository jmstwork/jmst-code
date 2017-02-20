package com.founder.fmdm.service.term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.dao.term.TermSyncDao;
import com.founder.fmdm.entity.DatasourceSet;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.entity.SyncLog;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.TermSync;
import com.founder.fmdm.service.term.dto.DatasourceDto;
import com.founder.fmdm.service.term.dto.SyncLogDto;
import com.founder.fmdm.service.term.dto.SyncTermDto;
import com.founder.util.ConnectionUtils;
import com.founder.util.StringUtil;

@Service
public class TermSyncServiceImpl implements TermSyncService {

	@Autowired
	TermSyncDao syncTermDao;

	@Autowired
	BasicDataSource dataSourceO;
	
	@Autowired
	LogUtils logUtils;
	@Override
	public List<Map<String, Object>> getSysList() {
		// TODO Auto-generated method stub
		return syncTermDao.getSysList();
	}

	@Override
	public List<SystemCode> getDataTypeList() {

		return syncTermDao.getDataTypeList();
	}

	@Override
	public List<DatasourceDto> selectDatasourceList(String systemId,
			String datasourceId) {

		return syncTermDao.selectDatasourceList(systemId, datasourceId);
	}

	@Override
	public List<DatasourceDto> selectDatasourceTable(String systemId,
			String datasourceId, SelectOptions options) {

		return syncTermDao.selectDatasourceTable(systemId, datasourceId,
				options);
	}

	@Override
	public List<SyncTermDto> selectSyncTermTable(String dictId,
			String dictName, SelectOptions options) {
		String dictIdNew = dictId;
		String dictNameNew = dictName;
		if (dictId != null && !dictId.equals("")){
			dictIdNew =dictId.trim();
		}
		if (dictNameNew != null && !dictNameNew.equals("")){
			dictNameNew =dictName.trim();
		}
		return syncTermDao.selectSyncTermTable(dictIdNew, dictNameNew, options);
	}

	@Override
	public List<SyncTermDto> selectSyncTermList(String syncId, String dictName) {

		return syncTermDao.selectSyncTermList(syncId, dictName);
	}

	@Override
	public int testDataSource(DatasourceDto datasourceDto) {

		try {
			Connection conn=null;
			String driverClass = this.selectDriverClass(datasourceDto
					.getJdbcSpecific());
			conn=ConnectionUtils.dataBaseConnection(datasourceDto, driverClass);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 获取驱动
	public String selectDriverClass(String cdDiv) {
		return syncTermDao.selectDriverClass(cdDiv);
	}

	@Override
	@Transactional
	public int updateSyncTermStatus(TermSync entity) {
		int status =0;
		status = syncTermDao.updateSyncDict(entity);
		return status;
	}
	
	@Override
	@Transactional
	public int updateSyncTerm(TermSync entity) {
		int status =0;
		try {
			status = this.updateDictMain(entity);
			if(status ==0){
				return status;
			}else{
				TermSync oldTermSync = null;
				oldTermSync = syncTermDao.selectSyncTerm(entity.getSyncId());
				entity.setLastUpdateTime(new Date());
				status = syncTermDao.updateSyncDict(entity);
				List<String> modifyDataPair = this.getModifyDataPair(oldTermSync, entity);
				this.insertSyncModifyLog(modifyDataPair, entity.getDictName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return status;

	}
	
	/*
	 * 根据传入的修改数据对，生成并插入文件
	 * */
	private void insertSyncModifyLog(List<String> dataPair,String dictName){
		StringBuffer mergeLog = new StringBuffer("编辑术语同步[").append(dictName).append("]").append("@");
		String mergeLogContent = logUtils.mergeLog(dataPair);
		if(!"".equals(mergeLogContent)){
			mergeLog = mergeLog.append(mergeLogContent);
			logUtils.insertLog("00702", "00702001", "{}",mergeLog.toString());
		}
	}
	
	/**
	 * 获取术语同步列表的变更数据对
	 * @param oldTermSync 旧术语同步对象
	 * @param newTermSync 新术语同步对象
	 * @return 产生的变更数据对集合
	 */
	private List<String> getTermSyncModifyDataPair(TermSync oldTermSync, TermSync newTermSync){
		List<String> dataPair = new ArrayList<String>();
		if(!oldTermSync.getDatasourceId().equals(newTermSync.getDatasourceId())){
			//把数据原名称查出来，在加入map
			String oldDataSourceName = syncTermDao.selectDatasourceSetById(oldTermSync.getDatasourceId())
										.getDatasourceName();
			String newDataSourceName = syncTermDao.selectDatasourceSetById(newTermSync.getDatasourceId())
										.getDatasourceName();
			dataPair.add("数据源："+oldDataSourceName+"=>"+newDataSourceName);
		}
		//判断间隔时间
		if(!oldTermSync.getSyncInterval().equals(newTermSync.getSyncInterval())){
			dataPair.add("间隔时间："+oldTermSync.getSyncInterval()+"=>"+newTermSync.getSyncInterval());
		}
		//判断是否重启
		if(oldTermSync.getDictStatus()!=newTermSync.getDictStatus()){
			if(oldTermSync.getSyncStatus()==1){//原状态为启动
				dataPair.add("启用状态：启用=>停用");
			}else{
				dataPair.add("启用状态：停用=>启用");
			}
		}
		return dataPair;
	}
	
	
	/*
	 * 获取数据源列表，变更的数据对
	 *
	 * */
	private List<String> getDatasourceSetModifyDataPair(DatasourceSet oldDatasource, DatasourceSet newDatasource){
		List<String> dataPair = new ArrayList<String>();
		if(!oldDatasource.getDatasourceName().equals(newDatasource.getDatasourceName())){
			dataPair.add("数据源："+oldDatasource.getDatasourceName()+"=>"+ newDatasource.getDatasourceName());
		}
		if(!oldDatasource.getJdbcSpecific().equals(newDatasource.getJdbcSpecific())){//数据库类型{
			dataPair.add("数据库类型："+oldDatasource.getJdbcSpecific()+"=>"+newDatasource.getJdbcSpecific());
		}
		if(!oldDatasource.getJdbcUrl().equals(newDatasource.getJdbcUrl())){
			dataPair.add("数据库连接："+oldDatasource.getJdbcUrl()+"=>"+newDatasource.getJdbcUrl());
		}
		if(!oldDatasource.getJdbcUserName().equals(newDatasource.getJdbcUserName())){
			dataPair.add("用户名："+oldDatasource.getJdbcUserName()+"=>"+newDatasource.getJdbcUserName());
		}
		if(!oldDatasource.getJdbcPassword().equals(newDatasource.getJdbcPassword())){
			dataPair.add("密码变更");
		}
		if(!oldDatasource.getSystemId().equals(newDatasource.getSystemId())){//提供系统的id
			String oldSysName = syncTermDao.selectSysNameById(oldDatasource.getSystemId());
			String newSysName = syncTermDao.selectSysNameById(newDatasource.getSystemId());
			dataPair.add("提供系统："+oldDatasource.getSystemId()+" "+oldSysName+"=>"+
					newDatasource.getSystemId()+" "+newSysName);
		}
		return dataPair;
	} 	
	
	/*获取变更的修改
	 *获取将要修改的数据对，map的key为修改的选项，valule为变更，其格式为：“新数据=》新数据”
	 * 
	 * */
	private List<String> getModifyDataPair(Object oldObj, Object newObj){
		if(oldObj instanceof TermSync && newObj instanceof TermSync){
			return this.getTermSyncModifyDataPair((TermSync)oldObj, (TermSync)newObj);
		}else if(oldObj instanceof DatasourceSet && newObj instanceof DatasourceSet){
			return this.getDatasourceSetModifyDataPair((DatasourceSet)oldObj, (DatasourceSet)newObj);
		}
		return new ArrayList<String>();
	}
	
	@Override
	public List<SyncLogDto> selectSyncLogList(String dictId) {
		return syncTermDao.selectSyncLogList(dictId);
	}

	@Override
	@Transactional
	public int createSyncLog(SyncLog entity) {
		return syncTermDao.insertSyncLog(entity);
	}

	@Override
	@Transactional
	public int addDataSource(DatasourceSet entity) {
		try{
			entity.setDatasourceId(StringUtil.getUUID());
			entity.setUpdateCount(0);
			entity.setDeleteFlg(0);
			entity.setCreateTime(new Date());
			syncTermDao.insertDatasourceSet(entity);
			logUtils.insertLog("00701", "00701001", "新增数据源[{}]", entity.getDatasourceName());
		}catch (Exception e) {
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int deleteDataSource(DatasourceSet datasourceSet) {
		// TODO Auto-generated method stub
		logUtils.insertLog("00701", "00701003", "删除数据源[{}]", datasourceSet.getDatasourceName());
		return syncTermDao.updateDatasourceSet(datasourceSet);
	}

	@Override
	public DatasourceSet selectDatasourceSetById(String datasourceId) {
		return syncTermDao.selectDatasourceSetById(datasourceId);
	}

	@Override
	public List<TermSync> selectSyncTermForBatList(String dictId) {
		return syncTermDao.selectSyncTermForBatList(dictId);
	}

	@Override
	public List<String> selectSyncJoinKeys(String serviceId) {
		return syncTermDao.selectSyncJoinKeys(serviceId);
	}

	@Override
	public int updateDictStatus(String syncIds, int status) {
		int backStatus =0;
		String opt_code="";//Logutil.insertlog的第一个参数
		String description="";//Logutil.insertlog的第三个参数
		if(status==0){//停用
			opt_code="00702004";
			description="停用术语同步[{}]";
		}else if (status==1){//启用
			opt_code="00702003";
			description="启用术语同步[{}]";
		}
		try {
			String[] syncId = syncIds.split("@");
			for (int i = 0; i < syncId.length; i++) {
				TermSync syncTerm = syncTermDao.selectSyncTerm(syncId[i]);
				if(status==0){
					syncTerm.setSyncResult(3);
				}
				syncTerm.setSyncId(syncId[i]);
				syncTerm.setDictStatus(status);
				backStatus=this.updateDictMain(syncTerm);
				if(backStatus==0){
					return backStatus;
				}else{
					syncTermDao.updateSyncDict(syncTerm);
					logUtils.insertLog("00702", opt_code, description, syncTerm.getDictName());
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	@Override
	public List<Map<String, Object>> getDictList() {
		// TODO Auto-generated method stub
		return syncTermDao.getDictList();
	}

	@Override
	public List<Map<String, Object>> getAllDictList() {
		// TODO Auto-generated method stub
		return syncTermDao.getAllDictList();
	}

	@Override
	public int insertSyncTerm(TermSync entity) {
		int status = 0;
		try {
			//更新术语dict_main表中lock_stauts
			entity.setSyncId(StringUtil.getUUID());
			entity.setSyncStatus(0);
			entity.setUpdateCount(0);
			entity.setSyncResult(3);
			entity.setDeleteFlg(0);
			entity.setCreateTime(new Date());
			status =this.updateDictMain(entity);
			if(status ==0){
				return status;
			}else{
				status= syncTermDao.insertTermSync(entity);
				if(1==entity.getDictStatus()){//如果勾选了启用checkbox
					logUtils.insertLog("00702", "00702000", "新增并启用术语同步[{}]", entity.getDictName());
				}else{
					logUtils.insertLog("00702", "00702000", "新增术语同步[{}]", entity.getDictName());
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return status;
	}
	//更新dict_main中锁状态
	public int updateDictMain(TermSync entity) {
		try {
			DictMain dictMain = syncTermDao.selectDictMainByServiceId(entity.getDictId());
			if(entity.getDictStatus()==0){
				dictMain.setLockStatus(1);
			}else if(entity.getDictStatus() == 1&& entity.getSyncStatus() == 0){
				dictMain.setLockStatus(2);
			}else if(entity.getDictStatus() ==1 && entity.getSyncStatus() == 1){
				dictMain.setLockStatus(3);
			}
			syncTermDao.updateDictMain(dictMain);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@Override
	public int updateDataSource(DatasourceSet datasourceSet) {
		DatasourceSet oldDataSourceSet = syncTermDao.selectDatasourceSetById(datasourceSet.getDatasourceId());
		//获取变更数据对，插入日志
		List<String> dataPair = this.getModifyDataPair(oldDataSourceSet, datasourceSet);
		StringBuffer mergeLog = new StringBuffer("编辑数据源[").append(datasourceSet.getDatasourceName())
								.append("]").append("@");
		//融合后日志
		String mergeLogContent = logUtils.mergeLog(dataPair);
		if(!"".equals(mergeLogContent)){//数据对中有数据
			mergeLog = mergeLog.append(mergeLogContent);
			logUtils.insertLog("00701", "00701002", "{}",mergeLog.toString()); 
		}
		
		datasourceSet.setLastUpdateTime(new Date());
		return syncTermDao.updateDataSource(datasourceSet);
	}
	
	@Override
	public String returnFromSql(String fromSql, DatasourceDto datasourceDto) {
		String excep = "exception";
		Connection conn = null;
		try {
			String fromSqlStr = "";
			/*
			 * Class.forName("oracle.jdbc.driver.OracleDriver"); Connection conn
			 * = DriverManager.getConnection(
			 * "jdbc:oracle:thin:@10.8.4.53:1521:ggfw", "testUser", "123");
			 */
			if(fromSql.contains("where")){
				StringBuilder fromBuilder=new StringBuilder(fromSql);
				int n=fromSql.indexOf("where")+6;
				fromSql=fromBuilder.insert(n,"1=2 and ").toString();
			}else{
				fromSql= fromSql+" where 1=2";
			}
			
			String driverClass = this.selectDriverClass(datasourceDto
					.getJdbcSpecific());
			conn = ConnectionUtils.dataBaseConnection(datasourceDto,
					driverClass);
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(fromSql);
			pstmt.execute();
			ResultSetMetaData rsmd = (ResultSetMetaData) pstmt.getMetaData();
			for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
				fromSqlStr += rsmd.getColumnName(i) + ",";
			}
			pstmt.close();
			conn.close();
			return fromSqlStr;
		} catch (Exception e) {
			e.printStackTrace();
			return excep;
		}
	}

	@Override
	public String returnToSql(String toSql) {
		String excep = "exception";
		try{
			String toSqlStr="";
			if(toSql.contains("where")){
				StringBuilder fromBuilder=new StringBuilder(toSql);
				int n=toSql.indexOf("where")+6;
				toSql=fromBuilder.insert(n,"1=2 and ").toString();
			}else{
				toSql= toSql+" where 1=2";
			}
			Connection conn = null;
			conn =dataSourceO.getConnection();
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(toSql);
			pstmt.execute();
			ResultSetMetaData rsmd = (ResultSetMetaData) pstmt.getMetaData();
			for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
				toSqlStr += rsmd.getColumnName(i) + ",";
			}
			pstmt.close();
			conn.close();
			return toSqlStr;
		}catch(Exception e){
			e.printStackTrace();
			return excep;
		}
		
	}
	
	//删除术语同步
	@Override
	public int deleteTermSync(TermSync termSync) {
		int status=0;
		try{
			DictMain dictMain = syncTermDao.selectDictMainByServiceId(termSync.getDictId());
			dictMain.setLockStatus(0);
			status = syncTermDao.updateDictMain(dictMain);
			if(status==0){
				return status;
			}else{
				status = syncTermDao.updateTermSync(termSync);
			}
		}catch(Exception e){
			return 0;
		}
		logUtils.insertLog("00702", "00702002", "删除术语同步[{}]", termSync.getDictName());
		return status;
	}

	@Override
	public int checkDataName(String datasourceName,String datasourceId) {
		return syncTermDao.checkDataName(datasourceName,datasourceId);
	}

	@Override
	public int checkDataFromSync(String datasourceId) {
		return syncTermDao.checkDataFromSync(datasourceId);
	}

	@Override
	public String testDictSql(String syncIds, int status) {
		String testResult = "";
		//启用术语sql校验必须通过
		if (status == 1) {
			String[] syncId = syncIds.split("@");
			for (int i = 0; i < syncId.length; i++) {
				TermSync syncTerm = syncTermDao.selectSyncTerm(syncId[i]);
				List<DatasourceDto> datasourceDtoList = syncTermDao
						.selectDatasourceList(null, syncTerm.getDatasourceId());
				String resultFrom = this.returnFromSql(
						syncTerm.getFromDatasourceSql(), datasourceDtoList.get(0));
				String resultTo = this.returnToSql(syncTerm
						.getToDatasourceSql());
				if (resultFrom.equals("exception") || resultTo.equals("exception")) {
					return testResult = syncTerm.getDictId();
				} else {
					testResult = "pass";
				}
			}
			//停用术语同步状态不可为执行中
		} else {
			String[] dictId = syncIds.split("@");
			for (int i = 0; i < dictId.length; i++) {
				TermSync syncTerm = syncTermDao.selectSyncTerm(dictId[i]);
				if (syncTerm.getSyncStatus() == 1) {
					return  testResult = syncTerm.getDictId();
				}else{
					testResult = "pass";
				}
			}
		}
		return testResult;

	}

	@Override
	public TermSync selectSyncTermById(String syncId) {
		
		return syncTermDao.selectSyncTerm(syncId);
	}

}
