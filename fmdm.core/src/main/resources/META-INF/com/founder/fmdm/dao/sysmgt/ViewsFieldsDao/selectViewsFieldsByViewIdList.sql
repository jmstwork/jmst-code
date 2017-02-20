/**
 * selectFieldsByDictIdList.sql
 * 列名列表集合
 * 
 */  
select  vf.view_field_id,vf.view_id,vf.field_id,vf.edit_flg,vf.retrieval_flg,vf.item_flg,vf.item_order,vf.order_flg,vf.field_order,vf.create_time,vf.last_update_time,vf.update_count,vf.delete_flg,vf.delete_time,vf.create_by,vf.last_update_by,vf.delete_by
  from  view_field vf
 where vf.delete_flg = 0
  /*%if viewId != null && viewId != ""*/
  	and vf.view_Id = /*viewId*/'ac5d0234dd204e0c889eb02cee2b12fd'
   /*%end*/
 