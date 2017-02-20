
/**
 * 根据dictId或者field_id查询dict_sql表信息
 */
select t.*
from  dict_sql t 
where 
t.delete_flg=0
/*%if dictId != null && dictId != ""*/
and t.dict_id = /*dictId*/'c4b8c9a67e62436abfd049fd29d81099'
/*%end*/
/*%if fieldId != null && fieldId != ""*/
and t.field_id = /*fieldId*/'c4b8c9a67e62436abfd049fd29d81099'
/*%end*/
and t.opt_status=/*#status*/
order by t.last_update_time asc