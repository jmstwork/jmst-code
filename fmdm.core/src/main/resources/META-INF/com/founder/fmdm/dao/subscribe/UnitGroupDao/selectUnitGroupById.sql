select * 
from subs_unit_group s
where s.delete_flg = 0
/*%if unitGroupId != null && unitGroupId !=""*/
 and  s.unit_group_id=/*unitGroupId*/''
    /*%end*/
    
   order by s.unit_group_id