select * from SUBS_SYS
where DELETE_FLG = 0
/*%if code != null && code != ""*/
and SYS_ID = /*code*/''
/*%end*/