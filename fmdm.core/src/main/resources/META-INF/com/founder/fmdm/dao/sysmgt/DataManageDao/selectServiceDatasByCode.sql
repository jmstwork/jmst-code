select * from SUBS_SERVICE
where DELETE_FLG = 0
/*%if code != null && code != ""*/
and SERVICE_ID = /*code*/''
/*%end*/