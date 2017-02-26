select r.role_id,
 r.role_name,
 r.memo
 from role r
 where r.delete_flg=0
  /*%if roleName != null && roleName != ""*/
  and r.role_name like '%'+/*roleName*/''+'%'
  /*%end*/
  /*%if userAccount != null && userAccount != ""*/
  and r.role_id not in (select ur.role_id from user_role  ur where ur.user_account = /*userAccount*/'' and ur.delete_flg=0)
  /*%end*/
  order by r.last_update_time desc