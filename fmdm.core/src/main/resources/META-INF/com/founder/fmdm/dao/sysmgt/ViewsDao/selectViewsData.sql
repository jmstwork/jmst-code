/**
 * selectDictViewData.sql
 * 视图列表集合
 * 
 */  
select v.view_id, v.dict_id, d.dict_name,upper(d.table_name) table_name, v.view_name, v.view_type,s.cd_value as view_type_CN,v.add_flg, v.delete_flg, v.approve_flg,v.release_flg
  from dict_view v, dict_main d, system_code s
 where v.dict_id = d.dict_id
   and cast(v.view_type as VARCHAR ) = s.cd_div
   and s.category_cd='C013'
   and v.delete_flag = 0
   and d.delete_flg = 0
   /*%if viewName != null && viewName != ""*/
   and v.view_name like /* @contain(viewName) */'aaa'
   /*%end*/
   /*%if viewType != null && viewType != ""*/
   and v.view_type = /* viewType */'0'  
   /*%end*/
   /*%if dictName != null && dictName != ""*/
   and d.dict_name like /* @contain(dictName) */'aaa'  
   /*%end*/
   /*%if roleId != null && roleId != ""*/
   and v.view_id not in (select t.view_id from  ROLE_VIEW t where t.role_id=/*roleId*/'' and t.delete_flg=0)
   /*%end*/
   order by v.last_update_time desc nulls last;
 