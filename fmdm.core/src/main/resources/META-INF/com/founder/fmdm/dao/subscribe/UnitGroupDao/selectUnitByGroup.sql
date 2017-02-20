select t.code   as unitId,
       t.name as unitName,
       t.py_code   as pyCode
  from SUBS_UNIT_GROUP_INFO s,
  		/*#tableName*/ t
 where s.unit_id = t.code
	/*%if unitGroupId != null && unitGroupId !=""*/
   and s.unit_group_id=/*unitGroupId*/''
    /*%end*/
   and s.delete_flg = 0
   and t.delete_flg = 0
 order by t.code