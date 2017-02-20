
/**
 * 查询结构类型列表
 */
select t.cd_div as typeCd,
	   t.cd_value as typeName
  from system_code t
 where t.category_cd = 'C004'
 and t.delete_flg=0