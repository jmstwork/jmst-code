select
	*
from
  tse_users
where
  delete_flg=0
  --and enable_flag=1
  and user_account = /* userName */'a'