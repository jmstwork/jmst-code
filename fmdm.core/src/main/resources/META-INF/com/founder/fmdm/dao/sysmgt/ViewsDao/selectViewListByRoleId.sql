select
	v.view_id,
	v.dict_id,
	v.view_name, 
	v.view_type,
	s.cd_value as view_type_CN,
	d.dict_name,
	d.table_name
from 
    dict_view v,dict_main d, role r,role_view rv, system_code s
where
     v.dict_id = d.dict_id
 and v.view_id = rv.view_id
 and rv.role_id =r.role_id
 and cast(v.view_type as VARCHAR ) = s.cd_div
 and s.category_cd='C013'
 and v.delete_flag=0
 and d.delete_flg=0
 and r.delete_flg=0
 and rv.delete_flg=0
   /*%if roleId != null && roleId != ""*/
   and r.role_id = /* roleId */'a'
   /*%end*/
   /*%if viewName != null && viewName != ""*/
   and v.view_name like '%'+/*viewName*/''+'%'
   /*%end*/
   /*%if viewType != null && viewType != ""*/
   and v.view_type = /* viewType */'0'
   /*%end*/
   /*%if dictName != null && dictName != ""*/
   and d.dict_name like '%'+/*dictName*/''+'%'
   /*%end*/
order by v.create_time desc;
		 
		  