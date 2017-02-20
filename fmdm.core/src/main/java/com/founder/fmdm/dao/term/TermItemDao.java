package com.founder.fmdm.dao.term;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Delegate;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.service.term.dto.DictItemDto;

@Dao
@AnnotateWith(annotations = {
		@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface TermItemDao {
	/**
	 * 查询字典字段信息
	 * 
	 * @param dictId
	 * @param status
	 *            "1" isShow,"2" isDefault,"3" isPrimary
	 * @return
	 */
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<DictItemDto> selectDictFieldList(String dictId, String status,
			String serviceId);
	
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	DictMain selectDictMainByDictId(String dictId);
	
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectFilterOrderByViewIdAndFieldId(String viewId,String fieldId);

	/**
	 * 查询字典数据
	 * 
	 * @param tableName
	 * @param termNameList
	 * @param release_status
	 * @param uni_key
	 * @return
	 */
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectDictDataList(String tableName,
			List<String> termItemList, List<String> releaseStates, String uniKey);

	/**
	 * 查询最新版本术语信息
	 * 
	 * @param tableName
	 * @param filterSql
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	Map<String, Object> selectMaxVersionTermData(String tableName,
			Map<String, Object> dataMap, List<DictItemDto> primaryKeys) throws ParseException;

	/**
	 * 新建数据
	 * 
	 * @param termItemList
	 * @param dataMap
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int executeAdd(List<DictItemDto> termItemList, Map<String, Object> dataMap) throws ParseException;

	/**
	 * 更新术语表
	 * 
	 * @param termItemList
	 * @param dataMapForUpdate
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int executeUpdate(List<DictItemDto> termItemList,
			Map<String, Object> dataMap);

	/**
	 * 查询差异数据
	 * 
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	List<Map<String, Object>> selectTermDataCompareList(String tableName,
			List<String> termItemList, List<String> termItemFiter);

	/**
	 * 审批，发布术语表
	 * 
	 * @param uniKeys
	 * @param tableName
	 * @param status
	 * @param userNo
	 * @param comment 审批意见
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int executeStatusUpdate(List<String> uniKeys, String tableName,
			String status, String userNo);

	/**
	 * 查询术语表各状态的数据条数
	 * 
	 * @param tableName
	 *            术语表名
	 * @param releaseState
	 *            数据状态
	 * @return
	 */
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	int selectReleaseStatusCount(String tableName, String releaseState,
			String uniKey);

	/**
	 * 更新未发布的数据
	 * 
	 * @param termItemList
	 * @param dataMap
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int executeUnpublishDataUpdate(List<DictItemDto> termItemList,
			Map<String, Object> dataMap) throws ParseException;

	/**
	 * 删除新增术语数据
	 * 
	 * @param tableName
	 * @param uniKey
	 * @param userNo
	 * @param status
	 *            h
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int deleteTermAddData(String tableName, String uniKey, String userNo,
			String status);

	/**
	 * 删除新增术语数据
	 * 
	 * @param tableName
	 * @param uniKey
	 * @param userNo
	 * @param status
	 *            发布状态
	 * @param optStatus
	 *            操作状态
	 * @param updateCount
	 * @param itemVersion
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int deleteTermData(String tableName, String uniKey, String userNo,
			String status, String optStatus, BigDecimal updateCount,
			BigDecimal itemVersion);

	/**
	 * 为发布查询字典数据
	 * 
	 * @param uniKeys
	 * @param termItemList
	 * @param tableName
	 * @return
	 */
	@Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
	List<Map<String, Object>> selectDictDataListForPublish(
			List<String> uniKeys, List<String> termItemList, String tableName);

	/**
	 * 查询术语数据一览
	 * @param tableName
	 * @param dictItemList
	 * @param releaseStatus
	 * @param mapSearch 查询条件
	 * @param options
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	List<Map<String, Object>> selectDictDataListForSearch(String tableName,
			List<String> dictItemList, List<String> releaseStatus,
			Map<String,Object> mapSearchValue,Map<String,Object> mapSearchType, SelectOptions options, String export);
	
	/**
	 * 唯一性校验
	 * @param dictId
	 * @param primaryKeys
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int checkUniPrimaryKey(String dictId, String primaryKeys);
	
	/**
	 * 插入审批意见
	 * 
	 * @param uniKeys
	 * @param tableName
	 * @param status
	 * @param userNo
	 * @param comment 审批意见
	 * @return
	 */
	@Delegate(to = TermItemDaoDelegate.class)
	int addExamineComments(List<String> uniKeys, String tableName,
			String status, String userNo,String comment);

}
