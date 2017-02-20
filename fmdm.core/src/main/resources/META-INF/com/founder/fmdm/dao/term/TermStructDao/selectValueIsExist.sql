
/**
 * 根据dictName,tableName和serviceId判断是否存在该数据
 */
select count(1) 
from dict_main t
where
/*%if dictName != null && dictName != ""*/
upper(t.dict_name) =upper(/*dictName*/'人员表')
/*%end*/
/*%if tableName != null && tableName != ""*/
upper(t.table_name) = upper(/*tableName*/'dict_person')
/*%end*/
/*%if serviceId != null && serviceId != ""*/
upper(t.service_id) = upper(/*serviceId*/'MS042')
/*%end*/
and t.delete_flg=0
