/**
 *  查询所有的医嘱执行分类
 */
select s.code, 
       to_char(s.name) name
from subs_order_exec_id s 
where s.delete_flg != 1
	and s.release_status = 'c'
   and s.opt_status != 'd'
