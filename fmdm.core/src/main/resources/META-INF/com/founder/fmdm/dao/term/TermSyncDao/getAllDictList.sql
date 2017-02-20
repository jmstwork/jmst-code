select t.dict_id, t.dict_name, t.table_name, t.service_id
  from dict_main t
 where t.delete_flg = 0
 and t.sys_id != 'S028'
 order by t.service_id