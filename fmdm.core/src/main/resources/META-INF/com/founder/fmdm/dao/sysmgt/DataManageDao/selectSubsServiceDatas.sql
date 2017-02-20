select *
from SUBS_SERVICE 
where DELETE_FLG = '0'
/*%if serviceId != null && serviceId != ""*/
and SERVICE_ID like '%'+/*serviceId*/''+'%'
/*%end*/
/*%if serviceName != null && serviceName != ""*/
and SERVICE_NAME like '%'+/*serviceName*/''+'%'
/*%end*/
order by LAST_UPDATE_TIME desc