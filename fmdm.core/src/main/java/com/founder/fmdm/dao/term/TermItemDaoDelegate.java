package com.founder.fmdm.dao.term;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.builder.InsertBuilder;
import org.seasar.doma.jdbc.builder.SelectBuilder;
import org.seasar.doma.jdbc.builder.UpdateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.founder.fmdm.service.term.dto.DictItemDto;
import com.founder.msg.utils.DateUtils;

public class TermItemDaoDelegate {
	private static Logger logger = LoggerFactory.getLogger("readerApp_pf");
	
	private Config config;
	
	private static String STR_NULL = "";

	private TermItemDao termItemDao;

	public TermItemDaoDelegate(Config config, TermItemDao termItemDao) {
		this.config = config;
		this.termItemDao = termItemDao;
	}

	/**
	 * 新建数据
	 * 
	 * @param termItemList
	 * @param dataMap
	 * @return
	 * @throws ParseException
	 */
	public int executeAdd(List<DictItemDto> termItemList,
			Map<String, Object> dataMap) throws ParseException {
		long starttime = System.currentTimeMillis();
		InsertBuilder builder = InsertBuilder.newInstance(config);
		builder.sql("insert into ").sql(termItemList.get(0).getTable_name());
		builder.sql("(");
		boolean offset = true;
		for (int i = 0; i < termItemList.size(); i++) {
			DictItemDto dictItemDto = termItemList.get(i);
			Object value = dataMap.get(dictItemDto.getField_name());
			if (value != null && !STR_NULL.equals(value)) {
				if (!offset) {
					builder.sql(",");
				}
				offset = false;
				builder.sql(termItemList.get(i).getField_name());

			}

		}
		builder.sql(")");
		builder.sql("values (");
		offset = true;
		for (int i = 0; i < termItemList.size(); i++) {
			DictItemDto dictItemDto = termItemList.get(i);
			Object value = dataMap.get(dictItemDto.getField_name());
			if (value != null && !STR_NULL.equals(value)) {
				if (!offset) {
					builder.sql(",");
				}
				offset = false;
			}
			if ("NVARCHAR2".equals(dictItemDto.getField_type()) || "VARCHAR2".equals(dictItemDto.getField_type())) {
				if (value == null || STR_NULL.equals(value)) {
					continue;
				}
				builder.param(String.class, String.valueOf(value).trim());
			} else if ("NUMBER".equals(dictItemDto.getField_type())) {
				if (value == null || STR_NULL.equals(value)) {
					continue;
				}
				builder.param(BigDecimal.class,
						new BigDecimal(String.valueOf(value).trim()));
			} else if ("DATE".equals(dictItemDto.getField_type())) {
				if (value == null || STR_NULL.equals(value)) {
					continue;
					// builder.param(Date.class, null);
				} else if (value instanceof Date) {
					builder.param(Date.class, (Date) value);
				} else {
					builder.param(Date.class,
							DateUtils.stringToDate((String) value));
				}

			} else if ("NUMBER(1)".equals(dictItemDto.getField_type())) {
				if (value == null || STR_NULL.equals(value)) {
					continue;
				}
				builder.param(Integer.class,
						Integer.valueOf(String.valueOf(value)));
			}

		}
		builder.sql(")");
		long middletime = System.currentTimeMillis();
		int ret = builder.execute();
		long endtime = System.currentTimeMillis();
		logger.debug(
				"数据插入(TermItemDaoDelegate.executeAdd)操作，sql拼装时间{}ms，执行时间{}ms,总时间{}ms",
				(middletime - starttime), (endtime - middletime),(endtime - starttime));
		return ret;
	}

	/**
	 * 更新术语表
	 * 
	 * @param termItemList
	 * @param dataMapForUpdate
	 * @return
	 */
	public int executeUpdate(List<DictItemDto> termItemList,
			Map<String, Object> dataMapForUpdate) {
		long starttime = System.currentTimeMillis();
		UpdateBuilder builder = UpdateBuilder.newInstance(config);
		builder.sql("update ").sql(termItemList.get(0).getTable_name());
		builder.sql("set");
		builder.sql("release_status = ")
				.param(String.class,
						(String) dataMapForUpdate.get("release_status"))
				.sql(",");
		builder.sql("update_count = update_count+1")
		.sql(",");
		builder.sql("last_update_by = ")
				.param(String.class,
						(String) dataMapForUpdate.get("last_update_by"))
				.sql(",");
		builder.sql("last_update_time = ").param(Date.class,
				(Date) dataMapForUpdate.get("last_update_time"));
		builder.sql("where");
		builder.sql("uni_key = ").param(String.class,
				(String) dataMapForUpdate.get("uni_key"));
		long middletime = System.currentTimeMillis();
		int ret = builder.execute();
		long endtime = System.currentTimeMillis();
		logger.debug(
				"数据插入(TermItemDaoDelegate.executeUpdate)操作，sql拼装时间{}ms，执行时间{}ms,总时间{}ms",
				(middletime - starttime), (endtime - middletime),(endtime - starttime));
		return ret;
	}

	/**
	 * 查询差异数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectTermDataCompareList(
			String tableName, List<String> termItemList,
			List<String> termItemFiter) {
		List<Map<String, Object>> termDataCompare = new ArrayList<Map<String, Object>>();
		SelectBuilder builder = SelectBuilder.newInstance(config);
		builder.sql("select");
		for (int i = 0; i < termItemList.size(); i++) {
			builder.sql(termItemList.get(i));
			if (i != termItemList.size() - 1) {
				builder.sql(",");
			}
		}
		builder.sql("from " + tableName);
		builder.sql("where");
		for (int i = 0; i < termItemFiter.size(); i++) {
			builder.sql(termItemFiter.get(i));
			if (i != termItemFiter.size() - 1) {
				builder.sql("and");
			}
		}
		builder.sql("order by last_update_time desc");
		termDataCompare = builder.getResultList(MapKeyNamingType.LOWER_CASE);
		return termDataCompare;
	}

	/**
	 * 根据业务 主键查询最新版本业务数据
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> selectMaxVersionTermData(String tableName,
			Map<String, Object> dataMap, List<DictItemDto> primaryKeys)
			throws ParseException {
		long starttime = System.currentTimeMillis();
		SelectBuilder builder = SelectBuilder.newInstance(config);
		builder.sql("select * ");
		builder.sql("from " + tableName);
		builder.sql("where ");
		for (int i = 0; i < primaryKeys.size(); i++) {
			DictItemDto dictItemDto = primaryKeys.get(i);
			if ("VARCHAR2".equals(dictItemDto.getField_type())) {
				Object nvarchar = dataMap.get(dictItemDto.getField_name());
				if (nvarchar == null) {
					nvarchar = "";
				}
				builder.sql(dictItemDto.getField_name() + " = ").param(
						String.class, String.valueOf(nvarchar));
			} else if ("NUMBER".equals(dictItemDto.getField_type())) {
				Object nvarchar = dataMap.get(dictItemDto.getField_name());
				BigDecimal param = new BigDecimal(0);
				if (nvarchar != null && !STR_NULL.equals(nvarchar)) {
					param = new BigDecimal(String.valueOf(nvarchar));
				}
				builder.sql(dictItemDto.getField_name() + " = ").param(
						BigDecimal.class, param);
			} else if ("DATE".equals(dictItemDto.getField_type())) {
				if (dataMap.get(dictItemDto.getField_name()) == null
						|| STR_NULL.equals(dataMap.get(dictItemDto.getField_name()))) {
					builder.sql(dictItemDto.getField_name() + " = ").param(
							Date.class, null);
				} else if (dataMap.get(
						dictItemDto.getField_name()) instanceof Date) {
					builder.sql(dictItemDto.getField_name() + " = ").param(
							Date.class,
							(Date) dataMap.get(dictItemDto.getField_name()));
				} else {
					builder.sql(dictItemDto.getField_name() + " = ").param(
							Date.class,
							DateUtils.stringToDate((String) dataMap
									.get(dictItemDto.getField_name())));
				}

			} else if ("NUMBER(1)".equals(dictItemDto.getField_type())) {
				Object numberString = dataMap.get(dictItemDto.getField_name());
				Integer numberInteger = 0;
				if (numberString != null && !STR_NULL.equals(numberString)) {
					numberInteger = new Integer(String.valueOf(numberString));
				}
				builder.sql(dictItemDto.getField_name() + " = ").param(
						Integer.class, numberInteger);
			}
			builder.sql("and ");

		}
		builder.sql("delete_flg = 0 and release_status = 'c'");
//		builder.sql("delete_flg = 0 and ITEM_VERSION = (select max(t2.ITEM_VERSION) from "
//				+ tableName + " t2 ");
//		builder.sql("where ");
//		for (int i = 0; i < primaryKeys.size(); i++) {
//			DictItemDto dictItemDto = primaryKeys.get(i);
//			if ("NVARCHAR2".equals(dictItemDto.getField_type())) {
//				Object nvarchar = dataMap.get(dictItemDto.getField_name());
//				if (nvarchar == null) {
//					nvarchar = "";
//				}
//				builder.sql("t2." + dictItemDto.getField_name() + " = ").param(
//						String.class, String.valueOf(nvarchar));
//			} else if ("NUMBER".equals(dictItemDto.getField_type())) {
//				Object nvarchar = dataMap.get(dictItemDto.getField_name());
//				BigDecimal param = new BigDecimal(0);
//				if (nvarchar != null  && !STR_NULL.equals(nvarchar)) {
//					param = new BigDecimal(String.valueOf(nvarchar));
//				}
//				builder.sql("t2." + dictItemDto.getField_name() + " = ").param(
//						BigDecimal.class, param);
//			} else if ("DATE".equals(dictItemDto.getField_type())) {
//				if (dataMap.get(dictItemDto.getField_name()) == null
//						|| STR_NULL.equals(dataMap.get(dictItemDto.getField_name()))) {
//					builder.sql("t2." + dictItemDto.getField_name() + " = ")
//							.param(Date.class, null);
//				} else if (dataMap.get(
//						dictItemDto.getField_name()) instanceof Date) {
//					builder.sql("t2." + dictItemDto.getField_name() + " = ")
//							.param(Date.class,
//									(Date) dataMap.get(dictItemDto
//											.getField_name()));
//				} else {
//					builder.sql("t2." + dictItemDto.getField_name() + " = ")
//							.param(Date.class,
//									DateUtils.stringToDate((String) dataMap
//											.get(dictItemDto.getField_name())));
//				}
//			} else if ("NUMBER(1)".equals(dictItemDto.getField_type())) {
//				Object numberString = dataMap.get(dictItemDto.getField_name());
//				Integer numberInteger = 0;
//				if (numberString != null  && !STR_NULL.equals(numberString)) {
//					numberInteger = new Integer(String.valueOf(numberString));
//				}
//				builder.sql("t2." + dictItemDto.getField_name() + " = ").param(
//						Integer.class, numberInteger);
//			}
//			builder.sql("and ");
//		}
//		builder.sql("t2.delete_flg = 0 )");

		long middletime = System.currentTimeMillis();
		Map<String, Object> ret = builder.getSingleResult(MapKeyNamingType.LOWER_CASE);
		long endtime = System.currentTimeMillis();
		logger.debug(
				"数据插入(TermItemDaoDelegate.selectMaxVersionTermData)操作，sql拼装时间{}ms，执行时间{}ms，总时间{}ms",
				(middletime - starttime), (endtime - middletime),(endtime - starttime));
		return ret;
	}

	/**
	 * 审批，发布术语表
	 * 
	 * @param uniKeys
	 * @param tableName
	 * @param status
	 * @param userNo
	 * @return
	 */
	public int executeStatusUpdate(List<String> uniKeys, String tableName,
			String status, String userNo) {
		int state = 0;
		UpdateBuilder builder = UpdateBuilder.newInstance(config);
		builder.sql("update ").sql(tableName);
		builder.sql("set");
		builder.sql("release_status = ").param(String.class, status)
				.sql(",");
		builder.sql("update_count = update_count+1")
		.sql(",");
		builder.sql("last_update_by = ").param(String.class, userNo)
				.sql(",");
		builder.sql("last_update_time = ").param(Date.class, new Date());
		builder.sql("where");
		builder.sql("uni_key in (");
		for (int i = 0; i < uniKeys.size(); i++) {
			builder.sql("\'" + uniKeys.get(i) + "\'");
			if (i != uniKeys.size() - 1) {
				builder.sql(",");
			} else {
				builder.sql(")");
			}
		}
		state = builder.execute();
		return state;
	}
	
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
	public int addExamineComments(List<String> uniKeys, String tableName,
			String status, String userNo,String comment)
	{
		//插入审批意见
		for(int i =0;i<uniKeys.size();i++){
			InsertBuilder insertbuilder = InsertBuilder.newInstance(config);
			insertbuilder.sql("insert into TERM_EXAMINE_COMMENTS(TERM_UNI_KEY,TERM_UPDATE_COUNT,EXAMINE_COMMENT,CREATE_TIME,CREATE_BY,DELETE_FLG)");
			insertbuilder.sql("values(");
			insertbuilder.param(String.class,uniKeys.get(i)).sql(",");
			insertbuilder.sql("(select update_count from "+tableName+" where uni_key= '"+uniKeys.get(i)+"')").sql(",");
			insertbuilder.param(String.class,comment).sql(",");
			insertbuilder.param(Date.class, new Date()).sql(",");
			insertbuilder.param(String.class,userNo).sql(",");
			insertbuilder.param(Integer.class,0);
			insertbuilder.sql(")");
			insertbuilder.execute();
		}
		return 1;
	}
	/**
	 * 更新未发布的数据
	 * 
	 * @param termItemList
	 * @param dataMap
	 * @return
	 * @throws ParseException
	 */
	public int executeUnpublishDataUpdate(List<DictItemDto> termItemList,
			Map<String, Object> dataMap) throws ParseException {
		UpdateBuilder builder = UpdateBuilder.newInstance(config);
		builder.sql("update ").sql(termItemList.get(0).getTable_name());
		builder.sql("set ");
		DictItemDto dictItemDto;
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		for (int i = 0; i < termItemList.size(); i++) {
			dictItemDto = termItemList.get(i);
			if ("admin".equals(userName)?"N".equals(dictItemDto.getIs_default()):"N".equals(dictItemDto.getIs_default())&&"Y".equals(dictItemDto.getField_is_edit())) {
				if ("NVARCHAR2".equals(dictItemDto.getField_type()) || "VARCHAR2".equals(dictItemDto.getField_type())) {
					Object nvarchar = dataMap.get(dictItemDto.getField_name());
					if (nvarchar == null) {
						nvarchar = "";
					}
					builder.sql(dictItemDto.getField_name() + " = ").param(
							String.class, String.valueOf(nvarchar));
				} else if ("NUMBER".equals(dictItemDto.getField_type())) {
					Object nvarchar = dataMap.get(dictItemDto.getField_name());
					BigDecimal param = new BigDecimal(0);
					if (nvarchar != null && !STR_NULL.equals(nvarchar)) {
						param = new BigDecimal(String.valueOf(nvarchar));
					}
					builder.sql(dictItemDto.getField_name() + " = ").param(
							BigDecimal.class, param);
				} else if ("DATE".equals(dictItemDto.getField_type())) {
					if (dataMap.get(dictItemDto.getField_name()) == null
							|| STR_NULL.equals(dataMap.get(dictItemDto
									.getField_name()))) {
						builder.sql(dictItemDto.getField_name() + " = ").param(
								Date.class, null);
					} else if (dataMap.get(
							dictItemDto.getField_name()) instanceof Date) {
						builder.sql(dictItemDto.getField_name() + " = ")
								.param(Date.class,
										(Date) dataMap.get(dictItemDto
												.getField_name()));
					} else {
						builder.sql(dictItemDto.getField_name() + " = ").param(
								Date.class,
								DateUtils.stringToDate((String) dataMap
										.get(dictItemDto.getField_name())));
					}

				} else if ("NUMBER(1)".equals(dictItemDto.getField_type())) {
					Object numberString = dataMap.get(dictItemDto
							.getField_name());
					Integer numberInteger = 0;
					if (numberString != null && !STR_NULL.equals(numberString)) {
						numberInteger = new Integer(
								String.valueOf(numberString));
					}
					builder.sql(dictItemDto.getField_name() + " = ").param(
							Integer.class, numberInteger);
				}
				builder.sql(",");
			}

		}
		builder.sql("opt_status = ")
				.param(String.class, (String) dataMap.get("opt_status"))
				.sql(",");
		builder.sql("release_status = ")
				.param(String.class, (String) dataMap.get("release_status"))
				.sql(",");
		builder.sql("last_update_by = ")
				.param(String.class, (String) dataMap.get("last_update_by"))
				.sql(",");
		builder.sql("last_update_time = ").param(Date.class, new Date());
		builder.sql("where");
		builder.sql("uni_key = ").param(String.class,
				(String) dataMap.get("uni_key"));
		return builder.execute();
	}

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
	public int deleteTermAddData(String tableName, String uniKey,
			String userNo, String status) {
		Date date = new Date();
		UpdateBuilder builder = UpdateBuilder.newInstance(config);
		builder.sql("update ").sql(tableName);
		builder.sql("set ");
		builder.sql("release_status = ").param(String.class, status).sql(",");
		builder.sql("last_update_by = ").param(String.class, userNo).sql(",");
		builder.sql("last_update_time = ").param(Date.class, date).sql(",");
		builder.sql("delete_by = ").param(String.class, userNo).sql(",");
		builder.sql("delete_time = ").param(Date.class, date).sql(",");
		builder.sql("delete_flg = ").param(String.class, "1");
		builder.sql("where");
		builder.sql("uni_key = ").param(String.class, uniKey);
		return builder.execute();
	}

	/**
	 * 删除术语数据
	 * 
	 * @param tableName
	 * @param uniKey
	 * @param userNo
	 * @param status
	 *            d
	 * @return
	 */
	public int deleteTermData(String tableName, String uniKey, String userNo,
			String status, String optStatus, BigDecimal updateCount,
			BigDecimal itemVersion) {
		Date date = new Date();
		UpdateBuilder builder = UpdateBuilder.newInstance(config);
		builder.sql("update ").sql(tableName);
		builder.sql("set ");
		builder.sql("release_status = ").param(String.class, status).sql(",");
		builder.sql("opt_status = ").param(String.class, optStatus).sql(",");
		builder.sql("update_count = ").param(BigDecimal.class, updateCount)
				.sql(",");
		builder.sql("item_version = ").param(BigDecimal.class, itemVersion)
				.sql(",");
		builder.sql("last_update_by = ").param(String.class, userNo).sql(",");
		builder.sql("last_update_time = ").param(Date.class, date);
		builder.sql("where");
		builder.sql("uni_key = ").param(String.class, uniKey);
		return builder.execute();
	}
	
	/**
	 * 查询术语数据一览
	 * @param tableName
	 * @param dictItemList
	 * @param releaseStatus
	 * @param mapSearch 查询条件
	 * @param options
	 * @return
	 */
	public List<Map<String, Object>> selectDictDataListForSearch(String tableName,
 			List<String> dictItemList, List<String> releaseStatus,
 			Map<String,Object> mapSearchValue,Map<String,Object> mapSearchType, SelectOptions options, String export){
		List<Map<String, Object>> termDataList = new ArrayList<Map<String, Object>>();
		SelectBuilder builder = SelectBuilder.newInstance(config);
		builder.sql("select ");
		for (int i = 0; i < dictItemList.size(); i++) {
			builder.sql("tt."+dictItemList.get(i));
			builder.sql(",");
		}
//		builder.sql("tt.uni_key,tt.opt_status,tt.release_status,tt.item_version,tt.update_count,tec.examine_comment ");
		builder.sql("tt.uni_key,tt.opt_status,tt.release_status,tt.item_version,tt.update_count,tec.examine_comment ");
		builder.sql("from " + tableName + " tt ");
		builder.sql("left join TERM_EXAMINE_COMMENTS tec on tt.update_count = tec.TERM_UPDATE_COUNT and tt.uni_key = tec.TERM_UNI_KEY");
		builder.sql(" where ");
		builder.sql(" tt.delete_flg = 0 ");
		builder.sql(" and ");
		if (releaseStatus.size() != 0) {
			builder.sql(" tt.release_status in  (");
			for (int i = 0; i < releaseStatus.size(); i++) {
				builder.sql("'" + releaseStatus.get(i) + "'");
				if (i != releaseStatus.size() - 1) {
					builder.sql(",");
				}
			}

			builder.sql(")");
			builder.sql(" and ");
		}
		if(mapSearchValue.size()!=0){
			for(Entry<String, Object> entry : mapSearchValue.entrySet()){
				if ("NVARCHAR2".equals(mapSearchType.get(entry.getKey())) || "VARCHAR2".equals(mapSearchType.get(entry.getKey()))) {
					builder.sql("tt."+entry.getKey()+" like '%"+entry.getValue()+"%'");
				} else if ("NUMBER".equals(mapSearchType.get(entry.getKey()))) {
					builder.sql("tt."+entry.getKey()+" = "+entry.getValue()+"");
				} else if ("DATE".equals(mapSearchType.get(entry.getKey()))) {
					builder.sql("to_char(tt."+entry.getKey()+",'yyyy-MM-dd') = '"+entry.getValue()+"'");
				} else if ("NUMBER(1)".equals(mapSearchType.get(entry.getKey()))) {
					builder.sql("tt."+entry.getKey()+" = "+entry.getValue()+"");
				}
				builder.sql(" and ");
			}
		}
		if(!"export".equals(export)){
		    builder.sql(" tt.uni_key not in (select t2.uni_key from "+tableName+" t2 where  t2.release_status = 'c' and t2.opt_status = 'd')");
		}else{
			builder.sql(" tt.opt_status != 'd' ");
		}
		builder.sql("order by tt.create_time desc");
		if(!"export".equals(export)){
			builder.options(options);
		}
		termDataList = builder.getResultList(MapKeyNamingType.LOWER_CASE);

		return termDataList;
	}

	/**
	 * 唯一性校验
	 * 
	 * @param dictId
	 * @param primaryKeys
	 * @return
	 */
	public int checkUniPrimaryKey(String dictId, String primaryKeys) {
		int count = 0;
		String[] items = primaryKeys.split(",");
		SelectBuilder builder0 = SelectBuilder.newInstance(config);
		builder0.sql("SELECT table_name FROM dict_main WHERE DICT_ID='"
				+ dictId + "'");
		String tableName = builder0.getSingleResult(String.class);
		SelectBuilder builder = SelectBuilder.newInstance(config);
		builder.sql("select count(1)");
		builder.sql("FROM " + tableName);
		builder.sql("where delete_flg=0");
		for (int i = 0; i < items.length; i++) {
			String[] itemDetails = items[i].split(":");
			builder.sql(" and " + itemDetails[0] + "='" + itemDetails[1].trim() + "'");
		}
		builder.sql(" and uni_key not in (select t2.uni_key from " + tableName
				+ " t2 where  t2.release_status = 'c' and t2.opt_status = 'd')");
		builder.sql(" and release_status != 'h'");
		count = builder.getSingleResult(Integer.class);
		return count;
	}
}
