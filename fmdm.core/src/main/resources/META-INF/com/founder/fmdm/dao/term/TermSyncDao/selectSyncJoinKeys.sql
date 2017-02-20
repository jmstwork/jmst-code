select upper(f.field_name) as key
  from dict_field f
 where f.dict_id =
       (select t.dict_id from dict_main t where t.service_id = /*serviceId*/'MS001' and t.delete_flg!=1)
   and f.is_primary = 'Y'
   and f.delete_flg = 0