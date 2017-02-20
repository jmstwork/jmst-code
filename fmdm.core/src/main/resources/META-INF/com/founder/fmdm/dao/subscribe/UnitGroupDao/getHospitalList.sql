select t.cd_div , 
       t.cd_value 
  from system_code t
 where t.category_cd='C011'
and  t.delete_flg = 0
 /*%if hospitalCode != null && hospitalCode != ""*/
and t.cd_div = /*hospitalCode*/''
 /*%end*/ 