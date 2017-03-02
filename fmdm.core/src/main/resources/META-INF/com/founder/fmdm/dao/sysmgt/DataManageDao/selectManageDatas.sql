select sdm.data_id, sdm.data_name , sdm.table_name , sdm.data_momo
from sys_data_manage sdm
where 1=1 
/*%if dataName != null && dataName != "" */
and sdm.data_name like '%'+/*dataName*/''+'%'
/*%end*/
/*%if tableName != null && tableName != "" */
and sdm.table_name like '%'+/*dataName*/''+'%'
/*%end*/
