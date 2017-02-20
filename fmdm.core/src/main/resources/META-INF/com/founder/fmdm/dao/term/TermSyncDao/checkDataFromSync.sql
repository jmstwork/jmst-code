select count(*)
 from sync_dict t 
 where t.delete_flg=0
  /*%if datasourceId != null && datasourceId != ""*/
   and t.datasource_id = /*datasourceId*/''
   /*%end*/