/**
 * selectFieldsByViewFieldId.sql
 * 查询视图字段表信息
 * 
 */  
select vf.*
 from view_field vf
 where  vf.delete_flg=0
  /*%if viewFieldId != null && viewFieldId != ""*/
  and vf.view_field_id =/*viewFieldId*/''
  /*%end*/
  
