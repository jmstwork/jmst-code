update Dict_Code_Map
set TAR_UNI_KEY = /*targetCode*/'TAR_UNI_KEY'
where source_Table = /*sourceTable*/'sourceTable'
and target_table = /*targetTable*/'targetTable'
and SRC_UNI_KEY = /*sourceCode_y*/'sourceCode_y' 
and TAR_UNI_KEY = /*targetCode_y*/'targetCode_y' 
and delete_flg = '0'