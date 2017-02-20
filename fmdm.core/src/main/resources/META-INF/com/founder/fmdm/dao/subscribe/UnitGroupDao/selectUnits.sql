select t.code  as unitId,
       t.name as unitName,
       t.py_code   as pyCode
  from  /*#tableName*/ t
 where t.delete_flg = 0
 /*%if unitIds != null */
 and  t.code in /*unitIds*/('a','b')
    /*%end */
 order by t.code