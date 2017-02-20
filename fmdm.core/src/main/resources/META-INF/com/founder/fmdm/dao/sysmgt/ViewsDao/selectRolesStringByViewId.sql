/**
 * 根据viewid查询出绑定的
 */
select roleName=stuff((select '|'+(r.role_name)
  from role_view v right join role r on v.role_id = r.role_id
  where v.view_id=/*viewId*/'6a73f1c8ffb8419eb09582de04749a72'
  and v.delete_flg=0 and r.delete_flg = 0
  for xml path('')),1,1,'')
  from role_view v right join role r on v.role_id = r.role_id
  where v.view_id=/*viewId*/'6a73f1c8ffb8419eb09582de04749a72'
  and v.delete_flg=0 and r.delete_flg = 0