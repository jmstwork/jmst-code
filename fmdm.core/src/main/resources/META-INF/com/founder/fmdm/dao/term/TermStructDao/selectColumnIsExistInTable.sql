
/**
 * 根据列名和表名，查询该列是否存在该表中
 */
 select count(1)
  from ALL_TAB_COLUMNS
 where table_name = upper(/*tableName*/'system_code')
   and column_name = upper(/*fieldName*/'create_by')
     