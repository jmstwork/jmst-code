/**
 *  查询所有的扩展码
 */
select s.code, 
       to_char(s.name) name
from subs_extend_sub_id s 
where s.delete_flg != 1
	and s.release_status = 'c'
   and s.opt_status != 'd'
