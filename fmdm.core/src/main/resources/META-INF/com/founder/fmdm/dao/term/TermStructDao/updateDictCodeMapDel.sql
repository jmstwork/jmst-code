delete from Dict_Code_Map
where lower(source_Table) = /*sourceTable*/'sourceTable' and
      lower(target_table) = /*targetTable*/'targetTable'
/*%if @isNotEmpty(sourceCode) */     
 and SRC_UNI_KEY = /*sourceCode*/'sourceCode' 
 and TAR_UNI_KEY = /*targetCode*/'targetCode' 
/*%end*/
