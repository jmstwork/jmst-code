/**
 * li_zhong
 * 根据dictId查询术语结构对应的字段信息，排除共通字段
 */
select t.*
from  dict_field t
where t.field_id = /*fieldId*/'c4b8c9a67e62436abfd049fd29d81099'
and t.field_name not in('create_by','create_time','last_update_by','last_update_time','delete_by','delete_time','delete_flg','update_count','item_version','opt_status','release_status')
and t.delete_flg=0