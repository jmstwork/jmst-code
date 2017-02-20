/**
 *  初始化字段数据
 */
select d.field_en_name, d.field_name, d.field_type, d.model_id
  from rlmg_model_detail d, rlmg_model_type t
 where d.model_id = t.model_id
   and d.model_id=/*modelId*/''
   and d.delete_flg = 0
   and t.delete_flg = 0