 select	d.sync_id,
 		d.dict_id,
        d.dict_name,
        d.dict_table_name,
        d.dict_status,
        d.sync_status,
        d.sync_interval,
        d.datasource_id,
        d.from_datasource_sql,
        d.to_datasource_sql,
        d.sync_result,
        d.log_txt,
        d.update_count,
        d.create_time,
        d.create_by
   from sync_dict d 
   where  d.delete_flg = 0 
    /*%if syncId != null && syncId !=""*/
   and    d.sync_id = /*syncId*/''
    /*%end*/
