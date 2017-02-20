/**
 * 获取数据库类型
 */
select cd_div, cd_value, cd_en_value
  from system_code
 where category_cd = 'C006'
 order by cd_div