select *
 from user_role u
 where  u.delete_flg=0
 /*%if roleId != null && roleId != ""*/
  and u.role_id =/*roleId*/''
  /*%end*/