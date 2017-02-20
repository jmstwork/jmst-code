
/**
 * 根据typeid，查询对应的结构类型名称
 */
select cd_value 
from system_code
where category_cd = 'C004'
and delete_flg = 0
and cd_div = /*typeCd*/''
