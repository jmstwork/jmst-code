/**
 * selectDictList.sql
 * 查询字典一览
 */
SELECT 
	dm.DICT_ID as dictId,
	dm.DICT_NAME as dictName,
	dm.STATUS as status,
	dm.TYPE_CD as typeCd,
	dm.TABLE_NAME as tableName,
	dm.ITEM_VERSION as itemVersion,
	dm.SERVICE_ID as serviceId,
	dm.SYS_ID as supplySys,
	sc5.CD_VALUE as statusValue,--术语状态
	dm.IS_EDIT as isEdit,
	isi.sys_name as supplySysName
	--isi.SYS_NAME --提供系统名称
FROM DICT_MAIN dm,SYSTEM_CODE sc5,subs_sys isi--,subs_sys isi
WHERE dm.DELETE_FLG='0'
	AND dm.IS_SAME = 'Y'
	AND dm.STATUS = sc5.CD_DIV AND sc5.CATEGORY_CD='C005'
	AND isi.sys_id = dm.sys_id
	--AND dm.SYS_ID = isi.SYS_ID
	/*%if serviceId != null && serviceId != "" */
	AND dm.SERVICE_ID like concat('%',concat(/* serviceId */'0001','%')) 
	/*%end*/
	/*%if dictId != null && dictId != "" */
	AND dm.DICT_ID = /* dictId */'0001'
	/*%end*/
	/*%if dictName != null && dictName != "" */
	AND dm.DICT_NAME like  concat('%',concat(/* dictName */'%aaaa%','%'))
	/*%end*/
	/*%if tableNames != null */
	AND dm.TABLE_NAME in /* tableNames */('0','1')
	/*%end*/
	/*%if status != null */
	AND dm.STATUS in /* status */('0','1')
	/*%end*/