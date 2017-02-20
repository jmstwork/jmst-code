/**
 * selectViewListByIdList.sql
 * 选择视图列表集合
 * 
 */  
select v.view_id, v.dict_id, d.dict_name,d.table_name, v.view_name, v.view_type,s.cd_value as view_type_CN,v.add_flg, v.delete_flg, v.approve_flg,v.release_flg
  from dict_view v, dict_main d, system_code s
 where v.dict_id = d.dict_id
   and cast(v.view_type as VARCHAR ) = s.cd_div
   and s.category_cd='C013'
   and v.delete_flag = 0
   and d.delete_flg = 0
   /*%if viewIdList != null*/
   and v.view_id in /* viewIdList*/('a','b')
   /*%end*/
   order by v.create_time desc;