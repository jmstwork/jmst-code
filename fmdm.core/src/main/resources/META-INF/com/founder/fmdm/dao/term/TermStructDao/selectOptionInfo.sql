      select dictName,tableName from(
         select lower(dm.dict_name) as dictName,
             lower(dm.table_name) as tableName
       from dict_main dm
       where dm.delete_flg='0'
      )a where 1=1
       /*%if @isNotEmpty(sourceTable) */
         AND a.dictName like  /* @contain(sourceTable) */'%X%' escape '$' 
         or 
         a.tableName like /* @contain(sourceTable) */'%X%' escape '$' 
       /*%end*/
