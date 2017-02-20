/**
 * 根据serviceId查询主表信息
 */
select t.* 
from dict_main t
where t.service_id =/*serviceId*/'MS028'
and t.delete_flg=0
