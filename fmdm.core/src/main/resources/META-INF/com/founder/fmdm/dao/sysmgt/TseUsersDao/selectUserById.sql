select
  *
from
  tse_users
where
  delete_flg!=1
  /*%if userAccount != null && userAccount != ""*/
  and user_account = /* userAccount */'a'
  /*%end*/