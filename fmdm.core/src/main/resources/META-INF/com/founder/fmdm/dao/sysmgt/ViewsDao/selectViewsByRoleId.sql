 select
  v.view_id
from 
    dict_view v,dict_main d,role r,role_view rv
where
     v.dict_id = d.dict_id
 and v.view_id = rv.view_id
 and rv.role_id =r.role_id
 and v.delete_flag=0
 and d.delete_flg=0
 and r.delete_flg=0
 and rv.delete_flg=0
 /*%if roleId != null && roleId != ""*/
  and rv.role_Id = /*roleId*/'1469690555762'
 /*%end*/
order by v.create_time desc 
  
  	