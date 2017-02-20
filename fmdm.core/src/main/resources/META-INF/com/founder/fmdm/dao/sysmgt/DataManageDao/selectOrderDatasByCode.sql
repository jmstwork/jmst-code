select * from SUBS_ORDER_EXEC_ID
where DELETE_FLG = 0
/*%if code != null && code != ""*/
and CODE = /*code*/''
/*%end*/