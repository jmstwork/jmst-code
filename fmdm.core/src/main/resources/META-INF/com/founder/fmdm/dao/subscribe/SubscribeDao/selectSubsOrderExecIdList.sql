/**
 *  查询所有的医嘱执行分类
 */
select s.code, 
       cast(s.name as VARCHAR ) name
from subs_order_exec_id s 
where s.delete_flg != 1
	and s.release_status = 'c'
   and s.opt_status != 'd'
