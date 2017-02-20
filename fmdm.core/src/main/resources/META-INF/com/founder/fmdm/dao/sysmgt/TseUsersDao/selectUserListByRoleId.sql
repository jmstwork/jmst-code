select
  t.user_id, 
  t.user_account, 
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo,
 WMSYS.WM_CONCAT(r.role_name) as roleName
from
  tse_users t ,user_role u ,role r
where
  t.user_account = u.user_account
 and u.role_id =r.role_id
  and u.delete_flg=0
  and t.delete_flg=0
  and r.delete_flg=0
  /*%if roleId != null && roleId != ""*/
  and r.role_id = /* roleId */'a'
  /*%end*/	
  /*%if userAccount != null && userAccount != ""*/
  and t.user_account  like  /* @contain(userAccount) */'b'
  /*%end*/
  /*%if userName != null && userName != ""*/
  and t.user_name like /* @contain(userName) */'a' 
  /*%end*/
  group by t.user_id,
		  t.user_account, 
		  t.user_name, 
		  t.user_mobile,
		  t.user_mail,
		  t.enable_flag, 
		  t.super_user,
		  t.memo
 order by t.user_account
		 
		  