/**
 *  查询所有的就诊类型
 */
select v.code, 
       cast(s.name as VARCHAR ) name
from dict_visit_type v 
where v.delete_flg = 0
