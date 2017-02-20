
select iam.sys_name 
	from subs_sys iam 
	where iam.delete_flg = 0 
	and iam.sys_id  = /*sysId*/'sysId' 