/**
 *  根据serviceId查询对应的serviceName
 */
select s.SERVICE_NAME
from SUBS_SERVICE s 
where s.delete_flg !=1
and s.opt_status != 'd'
    and s.release_status = 'c'
/*%if serviceId !=null && serviceId != "" */
and s.SERVICE_ID = /*serviceId*/'BS001' 
/*%end*/  
