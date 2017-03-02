select * from (select
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
  group by t.user_id,t.user_account, 
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo
  union all
  select
  t.user_id, 
  t.user_account, 
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo,
  null as roleName
from
  tse_users t ,user_role u 
where
   t.user_id not in (select s.user_id from tse_users s,user_role u  where s.user_id=u.user_account  and u.delete_flg=0)
  and u.delete_flg=0
  and t.delete_flg=0
  group by t.user_id,t.user_account, 
  t.user_name, 
  t.user_mobile,
  t.user_mail,
  t.enable_flag, 
  t.super_user,
  t.memo
  ) a 
  where 1=1		
  and a.user_account = /* userAccount */'a'
  order by a.user_account
