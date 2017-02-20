select *
from DICT_HOSPITAL 
where DELETE_FLG = '0'
/*%if hospitalCode != null && hospitalCode != ""*/
and HOSPITAL_CODE like '%'+/*hospitalCode*/''+'%'
/*%end*/
/*%if hospitalName != null && hospitalName != ""*/
and HOSPITAL_NAME like '%'+/*hospitalCode*/''+'%'
/*%end*/
order by LAST_UPDATE_TIME desc