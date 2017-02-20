select s.sys_id sysId, s.sys_name sysName
 from subs_sys s
 where delete_flg = 0
 order by s.sys_id
