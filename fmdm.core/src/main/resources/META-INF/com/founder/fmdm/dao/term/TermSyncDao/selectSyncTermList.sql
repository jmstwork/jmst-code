select t1.sync_id,
	   t1.dict_id,
       t1.dict_name,
       t1.dict_table_name,
       t1.dict_status,
       t1.sync_status,
       t1.sync_interval,
       t1.sync_result,
       t1.datasource_id,
       t1.log_txt,
       t1.from_datasource_sql,
       t1.to_datasource_sql,
       t1.update_count
  from sync_dict t1
 where t1.delete_flg = 0  
    /*%if syncId != null && syncId !=""*/
   and    t1.sync_id = /*syncId*/''
    /*%end*/
   /*%if dictName != null && dictName != ""*/
   and t1.dict_name like /*@contain(dictName)*/''
     /*%end*/