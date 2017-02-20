select auth_code,
	   auth_name
from SYS_AUTHORITY
where delete_flg = '0'
order by create_time