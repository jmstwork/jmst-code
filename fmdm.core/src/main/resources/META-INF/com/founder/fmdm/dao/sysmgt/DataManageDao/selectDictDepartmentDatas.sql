select *
from DICT_DEPARTMENT 
where DELETE_FLG = '0'
/*%if code != null && code != ""*/
and CODE like '%'+/*code*/''+'%'
/*%end*/
/*%if name != null && name != ""*/
and NAME like '%'+/*code*/''+'%'
/*%end*/
order by LAST_UPDATE_TIME desc