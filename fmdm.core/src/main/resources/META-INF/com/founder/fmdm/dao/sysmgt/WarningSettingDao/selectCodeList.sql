select c.cd_div,c.cd_value
  from system_code c
  where c.category_cd='C002'
  and c.delete_flg=0  
  /*%if cdDiv!="all"*/
  and c.cd_div=/*cdDiv*/'123'
  /*%end*/