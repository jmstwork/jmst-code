/**
 * selectDictFieldList.sql
 * 查询字典字段一览
 */
SELECT 
df.FIELD_ID AS field_id,
df.DICT_ID AS dict_id,
lower(df.FIELD_NAME) AS field_name,
df.FIELD_DESC AS field_desc,
df.FIElD_TYPE AS field_type,
df.LENGTH AS length,
df.IS_MUST AS is_must,
df.IS_SHOW AS is_show,
replace(df.default_value,char(39),'') AS default_value,
df.IS_PRIMARY AS is_primary,
df.IS_DEFAULT AS is_default,
df.IS_FILTER AS is_filter,
df.PRECISION AS precision,
df.FIELD_IS_EDIT AS field_is_edit,
dm.TABLE_NAME AS table_name,
dm.DICT_NAME AS dict_name,
dm.IS_EDIT AS is_edit,
dm.SERVICE_ID AS service_id
FROM DICT_MAIN dm, DICT_FIELD df
WHERE dm.DICT_ID = df.DICT_ID
AND df.delete_flg=0
AND dm.delete_flg=0
/*%if status != null && status == "1" */
AND df.IS_SHOW='Y'
/*%end */
/*%if status != null && status == "2" */
AND df.IS_DEFAULT= 'N'
/*%end */
/*%if status != null && status == "3" */
AND df.IS_PRIMARY= 'Y'
AND df.IS_DEFAULT= 'N'
AND df.field_is_edit = 'Y'
/*%end */
/*%if dictId != null */
AND df.DICT_ID=/* dictId */'2CAF3a3EA1RYW6936234F05421DC004'
/*%end */
/*%if serviceId != null */
AND dm.SERVICE_ID=/* serviceId */'MS000'
/*%end */
order by df.disp_order
