/**
 *  根据hospitalCode查询对应的组信息
 */
select g.unit_group_id, 
       g.unit_group_name
  from subs_unit_group g
 where g.delete_flg != 1
	   /*%if hospitalCode !=null && hospitalCode != "" */
	   and g.hospital_code = /*hospitalCode*/'H0001'
	   /*%end*/
	   /*%if type !=null && type != "" */
	   and g.unit_group_type = /*type*/'H0001' 
	   /*%end*/