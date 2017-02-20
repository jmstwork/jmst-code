/**
 *  查询所有的医疗机构
 */
select c.cd_div, 
       c.cd_value 
from system_code c 
where c.delete_flg !=1
/*%if category !=null && category != "" */
and c.category_cd = /*category*/'C011' 
/*%end*/  
