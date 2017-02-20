/**
 * selectFieldsByViewIdList.sql
 * 列名列表集合
 * 
 */  

select  vf.view_field_id,vf.view_id,vf.field_id,df.field_desc,df.field_name,vf.edit_flg,vf.retrieval_flg,vf.item_flg,vf.item_order,vf.order_flg,vf.field_order
  from  view_field vf,dict_field df
 where vf.field_id = df.field_id 
 	and vf.delete_flg = 0
  	and df.delete_flg = 0
  	and df.field_name not in('create_by','create_time',
  	'last_update_by','last_update_time','delete_by','delete_time',
  	'delete_flg','update_count','item_version','opt_status','release_status')
  /*%if viewId != null && viewId != ""*/
  	and vf.view_Id = /*viewId*/'33abfdbd859584d129d1df1a7d10cdda6'
   /*%end*/  	
 