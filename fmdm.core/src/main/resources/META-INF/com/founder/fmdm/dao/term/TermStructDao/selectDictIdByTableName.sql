
/**
 * 根据tableName查询该表是否存在
 */
select t.dict_id 
from dict_main t
where t.table_name =/*tableName*/'DICT_FIELD'
and t.delete_flg=0
