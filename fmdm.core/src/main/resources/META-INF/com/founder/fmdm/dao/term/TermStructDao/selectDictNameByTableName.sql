
/**
 * 根据tableName,查询对应的字典名称
 */
select dict_name 
from dict_main 
where upper(table_name)=upper(/*tableName*/'tableName')

 