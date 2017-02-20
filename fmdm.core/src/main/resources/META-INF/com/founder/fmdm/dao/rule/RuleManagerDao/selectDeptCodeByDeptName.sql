
select t1.code
   from dict_department t1
  where t1.delete_flg = 0
	and t1.item_version = (select max(t2.item_version) from dict_department t2 where t1.code = t2.code )
    and t1.name = /*deptName*/''