/**
 * li_zhong
 * 根据视图ID和字段ID查询字段信息
 */
select vf.*
 from view_field vf
 where  vf.delete_flg=0
  /*%if viewId != null && viewId != ""*/
  and vf.view_id =/*viewId*/''
  /*%end*/
  /*%if fieldId != null && fieldId != ""*/
  and vf.field_id =/*fieldId*/''
  /*%end*/