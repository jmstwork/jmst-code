/** 
 * 检索,同步术语列表中已创建的术语,以外的的术语
 */

select t.dict_id, t.dict_name, t.table_name, t.service_id
  from dict_main t
 where t.delete_flg = 0
   and t.sys_id!= 'S028'
   and t.service_id not in
       (select d.dict_id from sync_dict d where d.delete_flg = 0)
 order by t.service_id