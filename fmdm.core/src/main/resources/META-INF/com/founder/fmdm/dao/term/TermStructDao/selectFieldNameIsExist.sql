
/**
 * 根据fieldName和dictId查询在具体的术语结构下是否存在该名称的字段
 */
select count(1)
from  dict_field t
where t.field_name = /*fieldName*/'人员字典'
and t.dict_id = /*dictId*/'dsfsdfsf'
and t.delete_flg=0