/**
 * selectViewByViewId.sql
 * 列名列表集合
 * 
 */  
select  v.view_Id,v.dict_id,v.view_name,v.view_type,v.add_flg,v.delete_flg,v.approve_flg,v.release_flg,v.create_time,v.last_update_time,v.update_count,v.delete_by,v.delete_flag,v.delete_time,v.create_by,v.last_update_by
  from  dict_view v
 where v.delete_flag = 0
  /*%if viewId != null && viewId != ""*/
  	and v.view_Id = /*viewId*/'33abfdbd859584d129d1df1a7d10cdda6'
   /*%end*/
 