select t.code  as unitId,
       t.name as unitName,
       t.py_code   as pyCode
  from  /*#tableName*/ t
 where t.delete_flg = 0
 and t.opt_status !='d'
 and t.release_status ='c'
 /*%if unitId != null && unitId !=""*/
 and  t.code=/*unitId*/''
    /*%end*/
 /*%if unitName != null && unitName != ""*/
 and t.name like /*@contain(unitName)*/''
     /*%end*/
 order by t.code