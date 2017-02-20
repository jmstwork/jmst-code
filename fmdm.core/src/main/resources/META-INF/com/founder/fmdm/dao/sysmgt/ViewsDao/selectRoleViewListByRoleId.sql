/**
 * selectRoleViewListByRoleId.sql
 * 查询角色相关视图
 * 
 */  
select rv.role_view_id,
rv.role_id,
rv.view_id
 from role_view rv
 where  rv.delete_flg=0
 /*%if roleId != null && roleId != ""*/
  and rv.role_id =/*roleId*/''
  /*%end*/
