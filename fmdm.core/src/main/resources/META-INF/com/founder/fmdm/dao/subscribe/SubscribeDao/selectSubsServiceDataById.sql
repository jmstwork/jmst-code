/**
 *  根据subsId查询对应的订阅映射记录
 */
select *
from SUBS_SERVICE_SUBSCRIBES s 
where s.delete_flg != 1
/*%if subsId !=null && subsId != "" */
and s.subs_id = /*subsId*/'9e62a44f15ef469fb4f375d4330502c5' 
/*%end*/  
