/**
 *  根据检索条件查询对应的申请科室组和执行科室组信息
 */
select g.unit_group_id,
       g.unit_group_name
from SUBS_UNIT_GROUP g
where g.delete_flg !=1
/*%if type !=null && type != "" */
and g.unit_group_type = /*type*/'0'
/*%end*/
