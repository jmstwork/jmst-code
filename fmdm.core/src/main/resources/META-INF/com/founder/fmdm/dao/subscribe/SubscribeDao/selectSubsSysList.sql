/**
 *  查询所有的系统ID
 */
select s.sys_id, 
       s.sys_name
from subs_sys s 
where s.delete_flg != 1
/*%if @isNotEmpty(hospitalId) */
  and s.hospital_id = /* hospitalId */'hospitalId' 
/*%end*/
