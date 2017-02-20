      select srcUniKey,sourceCode,sourceCodeContent,sourceCodeVersion,tarUniKey,targetCode,targetCodeContent,targetCodeVersion,sourceTable,targetTable from(
       select dcm.SRC_UNI_KEY as srcUniKey,
              get_codename(dcm.SOURCE_TABLE,dcm.SRC_UNI_KEY,'code') as sourceCode,
              get_codename(dcm.SOURCE_TABLE,dcm.SRC_UNI_KEY,'name') as sourceCodeContent,
              get_codename(dcm.SOURCE_TABLE,dcm.SRC_UNI_KEY,'value_domain_version') as sourceCodeVersion,
              dcm.TAR_UNI_KEY as tarUniKey,
              get_codename(dcm.TARGET_TABLE,dcm.TAR_UNI_KEY,'code') as targetCode,
              get_codename(dcm.TARGET_TABLE,dcm.TAR_UNI_KEY,'name') as targetCodeContent,
              get_codename(dcm.SOURCE_TABLE,dcm.SRC_UNI_KEY,'value_domain_version') as targetCodeVersion,
              Get_dictname(lower(dcm.SOURCE_TABLE)) AS sourceName,
              Get_dictname(lower(dcm.TARGET_TABLE)) AS targetName,
              lower(dcm.SOURCE_TABLE) as sourceTable,
              lower(dcm.TARGET_TABLE) as targetTable
       from DICT_CODE_MAP dcm 
       where dcm.DELETE_FLG='0')a
       where 1=1
       /*%if @isNotEmpty(sourceTable) */
         AND a.sourceTable  = /*sourceTable*/'sourceTable'
       /*%end*/
       /*%if @isNotEmpty(sourceName) */
         AND a.sourceName = /*sourceName*/'sourceName'
       /*%end*/ 
       /*%if @isNotEmpty(targetTable) */
         AND a.targetTable  = /*targetTable*/'targetTable'
       /*%end*/
       /*%if @isNotEmpty(targetName) */
         AND a.targetName = /*targetName*/'targetName'
       /*%end*/ 
       order by a.sourceCode
