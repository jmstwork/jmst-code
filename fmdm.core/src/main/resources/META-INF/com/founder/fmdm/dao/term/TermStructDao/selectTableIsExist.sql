
/**
 * 根据tableName查询该表是否存在
 */
select count(1) 
from tabs 
where table_name =upper(/*tableName*/'DICT_FIELD1')
