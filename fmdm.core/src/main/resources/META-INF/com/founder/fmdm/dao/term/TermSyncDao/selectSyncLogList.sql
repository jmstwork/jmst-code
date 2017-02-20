
select t.log_id,
       t.dict_id,
       t.dict_name,
       t.start_time,
       t.end_time,
       t.sync_result,
       t.log_txt
  from sync_log t
 where t.dict_id = /*dictId*/''
 order by t.last_update_time desc