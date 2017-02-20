select u.user_role_id,
u.role_id,
u.user_id
 from user_role u
 where  u.delete_flg=0
 /*%if userId != null && userId != ""*/
  and u.user_account =/*userId*/''
  /*%end*/