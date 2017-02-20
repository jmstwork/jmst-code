
/**
 * 根据tableName查询物理表对应的表结构信息
 */
select nvl(t.TABLE_NAME,'-1') as table_name,
	   nvl(t.COLUMN_NAME,'-1') as field_name,
	   nvl(t.DATA_TYPE,'-1') || ',' ||
     case
       when t.DATA_TYPE='NUMBER' then nvl(t.DATA_PRECISION,'-1') || ',' || nvl(t.DATA_SCALE, '-1') || ','
       else  nvl(t.CHAR_COL_DECL_LENGTH,'-1') || ',' || nvl(t.DATA_PRECISION, '-1') || ','
       end
      ||  
      case 
      		when t.NULLABLE='Y' then 'N'
      		when t.NULLABLE='N' then 'Y'
      else nvl(t.NULLABLE,'-1')
      end  as field_type,
       t.DATA_DEFAULT as default_value,
       t.NULLABLE
  from cols t 
  WHERE   TABLE_name=upper(/*tableName*/'dict_field');
