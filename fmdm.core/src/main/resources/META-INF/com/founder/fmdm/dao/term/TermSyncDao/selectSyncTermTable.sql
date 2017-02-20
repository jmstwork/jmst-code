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
       t1.update_count,
       d.datasource_name
  from sync_dict t1,datasource_set d
 where t1.delete_flg = 0  
 and d.delete_flg=0
 and t1.datasource_id = d.datasource_id
    /*%if dictId != null && dictId !=""*/
   and    t1.dict_id = /*dictId*/''
    /*%end*/
   /*%if dictName != null && dictName != ""*/
   and t1.dict_name like /*@contain(dictName)*/''
     /*%end*/
   order by t1.dict_id