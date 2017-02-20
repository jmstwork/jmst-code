select *
from subs_unit_group_info t
where t.delete_flg=0
	/*%if unitGroupId != null && unitGroupId !=""*/
   and t.unit_group_id=/*unitGroupId*/''
    /*%end*/
