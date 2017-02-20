select * 
       from datasource_set data
       where  data.delete_flg=0
   /*%if datasourceId != null && datasourceId != ""*/
   and data.datasource_id = /*datasourceId*/''
   /*%end*/
order by data.last_update_time