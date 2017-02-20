package com.founder.fmdm.service.term;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.entity.DatasourceSet;
import com.founder.fmdm.entity.SyncLog;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.TermSync;
import com.founder.fmdm.service.term.dto.DatasourceDto;
import com.founder.fmdm.service.term.dto.SyncLogDto;
import com.founder.fmdm.service.term.dto.SyncTermDto;

public interface TermSyncService {
	
	/**
	 * 提供系统
	 * @return
	 */
	List<Map<String, Object>> getSysList();
	/**
	 * 获取数据库类型
	 * @return
	 */
	List<SystemCode> getDataTypeList();
	/**
	 * 数据源列表 
	 * @return
	 */
	List<DatasourceDto> selectDatasourceList(String systemId, String datasourceId);
	/**
	 * 分页数据源列表 
	 * @return
	 */
	List<DatasourceDto> selectDatasourceTable(String systemId, String datasourceId,SelectOptions options);
	/**
	 * 分页术语同步列表
	 * @return
	 */
	List<SyncTermDto> selectSyncTermTable(String dictId,String dictName,SelectOptions options);
	/**
	 * 术语同步列表
	 * @param dictId
	 * @param dictName
	 */
	List<SyncTermDto> selectSyncTermList(String syncId,String dictName);
	
	/**
	 * 根据Id搜索术语同步
	 * @param dictId
	 * @param dictName
	 */
	TermSync selectSyncTermById(String syncId);

	/**
	 * 更新同步字典状态
	 * @param entity
	 * @return
	 */
	int updateSyncTermStatus(TermSync entity);
	
	/**
	 * 更新同步字典
	 * @param entity
	 * @return
	 */
	int updateSyncTerm(TermSync entity);
	
	/**
	 * 测试数据是否连接成功
	 * @return
	 */
	int  testDataSource(DatasourceDto datasourceDto);
	/**
	 * 数据保存
	 * @param datasourceDto
	 * @return
	 */
	int  addDataSource(DatasourceSet datasourceSet);
	/**
	 * 数据删除
	 * @param datasourceDto
	 * @return
	 */
	@Transactional
	int  deleteDataSource(DatasourceSet datasourceSet);
	/**
	 * 根据Id检索数据源数据表
	 * @param datasourceDto
	 * @return
	 */
	DatasourceSet selectDatasourceSetById(String datasourceId);
	/**
	 *  数据源名称重复校验
	 */
	int checkDataName(String datasourceName,String datasourceId);
	/**
	 *  数据id是否在同步字典中使用
	 */
	int checkDataFromSync(String datasourceId);
	/**
	 * 术语同步日志列表
	 * @return
	 */
	List<SyncLogDto> selectSyncLogList(String dictId);
	
	/**
	 * 创建同步日志
	 * @param log
	 * @return
	 */
	int createSyncLog(SyncLog entity);

	/**
	 * 启用的术语同步bat检索
	 * @return
	 */
	List<TermSync>  selectSyncTermForBatList(String dictId);
	
	/**
	 * 检索术语主键字段
	 * @param ServiceId
	 * @return
	 */
	List<String> selectSyncJoinKeys(String serviceId);
	
	/**
	 * 更新术语状态
	 */
	@Transactional
	int updateDictStatus(String syncIds, int status);
	
	/**
	 * 术语服务启用停用同步校验SQL
	 */
	String testDictSql(String syncIds, int status);
	/**
	 * 字典信息：除去同步表中的字典
	 * @return
	 */
	List<Map<String, Object>> getDictList();
	/**
	 * 全部字典信息
	 * @return
	 */
	List<Map<String, Object>> getAllDictList();
	/**
	 * 添加同步术语表
	 */
	int insertSyncTerm(TermSync syncTerm);
	
	/**
	 * 更新数据源
	 */
	@Transactional
	int updateDataSource(DatasourceSet datasourceSet);
	/**
	 * 校验sql是否可执行返回结果集字段
	 */
	String returnFromSql(String fromSql, DatasourceDto datasourceDto);
	
	String returnToSql( String toSql);
	
	/**
	 * 删除术语同步表
	 */
	int deleteTermSync(TermSync termSync);
	
}
