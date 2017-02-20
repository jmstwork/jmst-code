package com.founder.fmdm.service.term;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.service.term.dto.DictItemDto;

public interface TermItemListService {
	
	/**
	 * 查询字典字段信息
     * @param serviceId
     * @param status "1" : isShow,"2" : isDefault, "3" : isPrimary
     * @return
	 */
	List<DictItemDto> getDictFieldList(String serviceId,String status);
	
	/**
	 * 根据术语Id查询术语信息
	 * @param dictId
	 * @return
	 */
	DictMain selectDictMainByDictId(String dictId);
	
	/**
	 * 查询对应字段的数据
	 * @param tableName 表名
	 * @param dictItemList 字段
	 * @param release_status 数据状态
	 * @param uni_key 数据ID 
	 * @return
	 */
	List<Map<String,Object>> getDictDataList(String tableName,List<String> dictItemList,List<String> release_status,String uni_key);
	
	/**
	 * 通过uni_key获取字典数据
	 * @param termItemList 字典字段
     * @param release_status 字典字段发布状态
     * @param uni_key 数据ID 
     * @return
	 */
	Map<String,Object> getTermListMapByUniKey(List<DictItemDto> termItemList,List<String> release_status,String uni_key);
	
	/**
	 * 修改字典数据
	 * @param statusMap 状态Map
     * @param dataMap 数据Map
     * @param release_status 字典字段发布状态
     * @return
	 */
	int editTermData(Map<String,Object> statusMap, Map<String,Object> dataMap,List<String> release_state);
	
	
	/**
	 * 获取数据对比信息（两条）
	 * @param uni_key
	 * @param dictId
	 * @param title
	 * @return
	 */
	List<Map<String,Object>> getTermDataCompare(String uni_key,String dictId,String title,List<String> release_state_forlist);
	
	/**
	 * 审批。发布字典
	 * @param uni_keys 字典字段uni_key
	 * @param tableName 字典表名
	 * @param dictId 字典ID
	 * @param status 状态(审批，发布)
	 * @param userNo 用户
	 * @param comment 审批意见
	 * @return
	 */
	int releaseStatusUpdate(List<String> uniKeys, String tableName,String dictId,String status,String userNo,String comment);
	
	/**
	 * 修改术语主表状态
	 * @param tableName
	 * @param dictId
	 * @param user_no
	 * @return
	 */
	int getAndUpdateReleaseStatusCount(String tableName,String dictId,String userNo);
	
	/**
	 * 删除术语数据
	 * @param uniKey
	 * @param userNo
	 * @param releaseStatus list
	 * @param dictId
	 * @return
	 */
	int deleteTermData(String uniKey,String userNo,List<String> releaseStatus,String dictId,String newUniKey,String title) throws ParseException;
	
	/**
	 * 为一览画面查询对应字段的数据
	 * @param tableName 表名
	 * @param dictItemList 字段
	 * @param release_status 数据状态
	 * @param mapSearch
	 * @param options
	 * @return
	 */
	List<Map<String,Object>> getDictDataForSearchList(String tableName,List<String> dictItemList,List<String> releaseStatus,Map<String,Object> mapSearchValue,Map<String,Object> mapSearchType,SelectOptions options,String export);
	
	/**
	 * 唯一性校验
	 * @param dictId
	 * @param primaryKeys
	 * @return
	 */
	boolean checkUniPrimaryKey(String dictId,String primaryKeys);
	
	/**
	 * 根据视图Id和字段Id查询FilterOrder(查询字段的顺序)
	 */
	int selectFilterOrderByViewIdAndFieldId(String viewId,String fieldId);
}
