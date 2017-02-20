select *
from SUBS_EXTEND_SUB_ID 
where DELETE_FLG = '0'
/*%if code != null && code != ""*/
and CODE like '%'+/*code*/''+'%'
/*%end*/
/*%if name != null && name != ""*/
and NAME like '%'+/*name*/''+'%'
/*%end*/
order by LAST_UPDATE_TIME desc