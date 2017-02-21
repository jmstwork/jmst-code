
/**
 * 查询模型列表
 */
select t1.dict_id,
       t1.service_id,
       t1.dict_name,
       t1.table_name,
       t2.cd_value as type_name,
       t1.is_same
  from dict_main t1, system_code t2
 where t1.type_cd = t2.cd_div
   and t2.category_cd = 'C004'
   /*%if serviceId != null && serviceId != ""*/
   and t1.service_id like like '%'+/*serviceId*/''+'%'
   /*%end*/
   /*%if dictName != null && dictName != ""*/
   and t1.dict_name like like '%'+/*dictName*/''+'%'
   /*%end*/
   /*%if typeCd != null && typeCd != ""*/
   and t1.type_cd = /*typeCd*/'0'
   /*%end*/
   /*%if isSame != null && isSame != ""*/
   and t1.is_same = /*isSame*/'Y'
   /*%end*/
   and t1.delete_flg=0
   order by t1.service_id