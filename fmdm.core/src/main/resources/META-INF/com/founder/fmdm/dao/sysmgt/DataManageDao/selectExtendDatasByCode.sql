select * from SUBS_EXTEND_SUB_ID
where DELETE_FLG = 0
/*%if code != null && code != ""*/
and CODE = /*code*/''
/*%end*/