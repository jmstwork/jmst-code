/**
 *  查询所有的扩展码
 */
select s.code, 
       cast(s.name as VARCHAR ) name
from subs_extend_sub_id s 
where s.delete_flg != 1
	and s.release_status = 'c'
   and s.opt_status != 'd'
