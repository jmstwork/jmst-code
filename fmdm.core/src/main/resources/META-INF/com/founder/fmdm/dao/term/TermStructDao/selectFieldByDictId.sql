
/**
 * 根据dictId查询术语结构对应的字段信息，排除共通字段
 */
 select tt.*,  s.field_id || '&' || s.dict_sql || '&' || s.last_update_time as dict_sql 
 from (select t.disp_order    disp_order, 
		 	  t.field_id      field_id,
              t.field_name    field_name,
              t.field_desc    field_desc,
              t.Field_Type    field_type,
              t.length        length,
              t.precision     precision,
              replace(t.default_value,'''','')  default_value,
              t.is_must       is_must,
              t.is_primary    is_primary,
              t.is_show       is_show,
              t.is_filter     is_filter,
              c.cd_value      as field_type_name
         from dict_field t, system_code c
        where t.dict_id = /*dictId*/'c4b8c9a67e62436abfd049fd29d81099'
      and t.field_type = c.cd_div
      and t.field_name not in ('create_by',
                               'create_time',
                               'last_update_by',
                               'last_update_time',
                               'delete_by',
                               'delete_time',
                               'delete_flg',
                               'update_count',
                               'item_version',
                               'opt_status',
                               'release_status',
                               'uni_key')
          and t.delete_flg = 0
        order by cast(t.disp_order as INT )) tt
  left join dict_sql s
  on tt.field_id = s.field_id
  and s.delete_flg = 0
  and s.opt_status = 1