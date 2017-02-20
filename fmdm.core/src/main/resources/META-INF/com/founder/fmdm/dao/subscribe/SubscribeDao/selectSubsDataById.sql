/**
 *  根据subscribeId查询订阅信息记录
 */
select *
from SUBS_SUBSCRIBES s 
where s.delete_flg != 1
/*%if subscribeId !=null && subscribeId != "" */
and s.subscribe_id = /*subscribeId*/'9e62a44f15ef469fb4f375d4330502c5' 
/*%end*/  
