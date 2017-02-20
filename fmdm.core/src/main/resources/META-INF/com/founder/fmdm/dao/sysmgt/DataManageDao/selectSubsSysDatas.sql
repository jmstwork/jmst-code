select * from SUBS_SYS 
where DELETE_FLG = 0
/*%if sysId != null && sysId != ""*/
and sys_id like '%'+/*sysId*/''+'%'
/*%end*/
/*%if sysName != null && sysName != ""*/
and sys_name like '%'+/*sysName*/''+'%'
/*%end*/
/*%if sysApply != null && sysApply != ""*/
and sys_apply like '%'+/*sysApply*/''+'%'
/*%end*/
/*%if hospitalId != null && hospitalId != ""*/
and hospital_id like '%'+/*hospitalId*/''+'%'
/*%end*/
order by LAST_UPDATE_TIME desc