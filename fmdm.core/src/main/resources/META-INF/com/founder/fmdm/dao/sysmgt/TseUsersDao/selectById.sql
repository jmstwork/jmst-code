select
  user_id, 
  user_account, 
  user_name, 
  user_passwd, 
  enabled, 
  super_user
from
  tse_users
where
  user_id = /* userId */'a'
