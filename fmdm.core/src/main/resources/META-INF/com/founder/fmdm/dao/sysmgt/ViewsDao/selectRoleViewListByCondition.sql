/**
 * selectRoleViewListByRoleId.sql
 * 查询角色相关视图
 * 
 */  
select rv.*
 from role_view rv
 where  rv.delete_flg=0
  /*%if roleId != null && roleId != ""*/
  and rv.role_id =/*roleId*/''
  /*%end*/
  /*%if viewId != null && viewId != ""*/
  and rv.view_id =/*viewId*/''
  /*%end*/
