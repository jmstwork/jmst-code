
/**
 * 根据tableName查询该表是否存在
 */
select count(1) from(
   SELECT upper(table_name) as table_name FROM dict_main WHERE delete_flg='0'
)a where where table_name =upper(/*tableName*/'tableName')
 
