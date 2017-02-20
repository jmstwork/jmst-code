/**
 * 根据dictId查询术语结构对应的index_name字段信息
 */
select t.index_name
  from user_ind_columns t
 where t.table_name = upper(/*tableName*/'AAA')
 and (t.index_name = 'INDEX_' || upper(/*tableName*/'AAA')) or
     (t.index_name = 'IDX_' || upper(/*tableName*/'AAA'));