select
  t.user_id, 
  t.user_account, 
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo,
 roleName=stuff((select ','+r.role_name from
  tse_users ts
  left join user_role u on t.user_account = u.user_account and u.delete_flg=0
  left join role r on u.role_id =r.role_id where ts.user_id =t.user_id
for xml path('')), 1, 1, '')
from
  tse_users t
where
 t.delete_flg=0
  /*%if roleId != null && roleId != ""*/
  and r.role_id = /* roleId */'a'
  /*%end*/	
  /*%if userAccount != null && userAccount != ""*/
  and t.user_account  like  '%'+/*userAccount*/''+'%'
  /*%end*/
  /*%if userName != null && userName != ""*/
  and t.user_name like '%'+/*userName*/''+'%'
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
		 
		  