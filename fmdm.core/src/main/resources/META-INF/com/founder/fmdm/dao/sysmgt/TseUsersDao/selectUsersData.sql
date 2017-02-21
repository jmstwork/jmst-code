select * from (select
  t.user_id, 
  t.user_account, 
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo,
  t.last_update_time,
 WMSYS.WM_CONCAT(r.role_name) as roleName
from
  tse_users t ,user_role u ,role r
where
      t.user_account = u.user_account(+)
  and u.role_id =r.role_id(+)
  and t.delete_flg=0
  and u.delete_flg(+)=0
  /*%if roleId != null && roleId != ""*/
  and t.user_account not in (select ur.user_account from USER_ROLE ur where ur.role_id = /*roleId*/'' and ur.delete_flg=0)
  /*%end*/
  /*%if enableFlg != null*/
  and t.enable_flag = /*enableFlg*/'1'
  /*%end*/
  group by 
  t.last_update_time,
  t.user_account, 
  t.user_id,
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo
  order by t.last_update_time desc nulls last
  ) a 
  where 1=1
  /*%if userAccount != null && userAccount != ""*/
  and a.user_account like '%'+/*userAccount*/''+'%'
  /*%end*/
  /*%if userName != null && userName != ""*/
  and a.user_name like '%'+/*userName*/''+'%'
  /*%end*/