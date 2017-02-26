select 
 t.user_id, 
  t.user_account, 
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo
  from 
   tse_users t
  where t.delete_flg=0
  /*%if userName != null && userName != ""*/
  and LOWER(t.user_name) like '%'+/*userName*/''+'%'
  /*%end*/
  /*%if userAccount !=null && userAccount!=""*/
  and LOWER(t.user_account) like '%'+/*userAccount*/''+'%'
  /*%end*/
  order by t.last_update_time desc