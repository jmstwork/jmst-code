/**
 *  查询所有的就诊类型
 */
select v.code, 
       to_char(v.name) name
from dict_visit_type v 
where v.delete_flg = 0
