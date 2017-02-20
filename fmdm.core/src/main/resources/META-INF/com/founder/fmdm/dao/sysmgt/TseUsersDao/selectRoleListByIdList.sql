select role_id,
 role_name,
 memo
 from role
 where delete_flg=0
 /*%if roleIdList != null */
  and role_id in /*roleIdList*/('a','b')
  /*%end*/