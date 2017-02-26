select role_id,
 role_name,
 memo
 from role
 where delete_flg=0
 and role_name like '%'+/*roleName*/''+'%'