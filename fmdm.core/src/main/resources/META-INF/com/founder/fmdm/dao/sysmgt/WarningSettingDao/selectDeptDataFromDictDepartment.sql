select d.code, d.name
  from dict_department d
 where d.delete_flg = 0
   and d.item_version = (select max(t1.item_version)
                           from dict_department t1
                          where t1.code = d.code) 
						  /*%if deptCode != "all" */
                          and d.code = /*deptCode*/'11'
                          /*%end*/
                          order by d.code 
                       