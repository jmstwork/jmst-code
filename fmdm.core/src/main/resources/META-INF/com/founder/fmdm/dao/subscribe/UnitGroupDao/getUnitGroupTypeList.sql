select t.cd_div , 
       t.cd_value 
  from system_code t
 where t.category_cd='C010'
and  t.delete_flg = 0