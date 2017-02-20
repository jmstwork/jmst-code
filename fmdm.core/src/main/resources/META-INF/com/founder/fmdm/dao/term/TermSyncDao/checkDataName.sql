select count(*) 
from datasource_set t 
where t.delete_flg=0
 /*%if datasourceName != null && datasourceName != ""*/
and t.datasource_name=/*datasourceName*/''
/*%end*/
 /*%if datasourceId != null && datasourceId != ""*/
and t.datasource_Id !=/*datasourceId*/''
/*%end*/