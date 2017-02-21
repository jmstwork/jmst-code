SELECT sourceTable,
       sourceName,
       targetTable,
       targetName
FROM  (SELECT lower(SOURCE_TABLE)        AS sourceTable,
              Get_dictname(lower(SOURCE_TABLE)) AS sourceName,
              lower(TARGET_TABLE)        AS targetTable,
              Get_dictname(lower(TARGET_TABLE)) AS targetName
       FROM   dict_code_map
       where delete_flg = '0'
       )a 
                 where 1=1
       /*%if @isNotEmpty(sourceTable) */
         AND a.sourceTable like '%'+/*sourceTable*/''+'%'
       /*%end*/
       /*%if @isNotEmpty(sourceName) */
         AND a.sourceName like '%'+/*sourceName*/''+'%'
       /*%end*/ 
       /*%if @isNotEmpty(targetTable) */
         AND a.targetTable like '%'+/*targetTable*/''+'%'
       /*%end*/
       /*%if @isNotEmpty(targetName) */
         AND a.targetName like '%'+/*targetName*/''+'%'
       /*%end*/ 
       GROUP  BY a.sourceTable,
                 a.targetTable,
                 a.sourceName,
                 a.targetName
       order by a.sourceTable
       
      
                 