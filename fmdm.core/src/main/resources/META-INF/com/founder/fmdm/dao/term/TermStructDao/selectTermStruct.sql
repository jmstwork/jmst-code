
/**
 * 根据dictId查询术语结构字段信息
 */
select nvl(t.field_name, '-1') as field_name,
       case
         when t.field_type = 'NUMBER(1)' then
          'NUMBER,1,0'
         else
          nvl(t.field_type, '-1') || ',' || nvl(t.length, '-1') || ',' ||
          nvl(t.precision, '-1')
       end || ',' || nvl(t.is_must, '-1') || ',' ||
       
       case
         when t.default_value is null then
          '-1'
         else
         t.default_value
       end as field_type

  from dict_field t
  where t.dict_id = /*dictId*/'872dc3491317418299ae4d6b1aa44bd6'
  and t.delete_flg=0
