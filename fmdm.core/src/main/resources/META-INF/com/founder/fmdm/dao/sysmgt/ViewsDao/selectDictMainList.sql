/**
 * selectDictMainList.sql
 * 视图列表集合
 * 
 */  
select  d.dict_id, d.service_id+'-',d.service_id+'-'+d.dict_name+'('+d.table_name+')' as dict_name
  from  dict_main d
 where d.delete_flg = 0
   and d.is_same = 'Y'
   order by d.service_id
 
 