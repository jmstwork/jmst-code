select
  v.view_id, 
  v.dict_id,
  v.view_name, 
  v.view_type,
  d.dict_name,
  d.table_name
from 
    dict_view v, dict_main d
where
     v.dict_id = d.dict_id
 and v.delete_flag=0
 and d.delete_flg=0
order by v.create_time desc