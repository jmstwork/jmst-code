SELECT sc.cd_div , sc.cd_value 
  FROM system_code sc
 where sc.category_cd = 'C013'
   and sc.delete_flg = '0'
 order by sc.cd_div