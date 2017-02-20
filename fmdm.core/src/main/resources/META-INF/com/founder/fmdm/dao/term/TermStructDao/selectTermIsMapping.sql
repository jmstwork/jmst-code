/**
 * 根据tableName查询该表是否存在于映射表
 */
select COUNT(1) 
from DICT_CODE_MAP 
where SOURCE_TABLE = LOWER(/*tableName*/'dict_field') OR TARGET_TABLE = LOWER(/*tableName*/'dict_field')