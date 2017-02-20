 select d.sync_id,
	d.dict_id,
        d.dict_name,
        d.dict_table_name,
        d.dict_status,
        d.sync_status,
        d.sync_interval,
        d.datasource_id,
        d.from_datasource_sql,
        d.to_datasource_sql,
        d.start_time,
        d.end_time,
        d.sync_result,
        d.log_txt,
	d.update_count
   from sync_dict d
  where d.dict_status = 1
    and d.sync_status = 0
    and d.delete_flg=0
    /*%if dictId != null && dictId != ""*/
    and d.dict_id = /*dictId*/''
    /*%end*/
