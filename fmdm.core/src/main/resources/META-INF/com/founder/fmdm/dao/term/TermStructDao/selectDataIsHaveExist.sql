
/**
 * 根据tableName查询该表是否存在
 */
select count(1) from DICT_CODE_MAP where source_table=/*sourceTable*/'sourceTable' 
and target_table=/*targetTable*/'targetTable' and SRC_UNI_KEY=/*sourceCode*/'sourceCode'
 
