select i.sys_id sysId, i.sys_name sysName
  from subs_sys i
where i.delete_flg = 0
order by i.sys_id