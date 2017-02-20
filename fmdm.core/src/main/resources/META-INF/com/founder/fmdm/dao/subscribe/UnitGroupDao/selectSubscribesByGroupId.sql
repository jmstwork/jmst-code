select count(*)
  from subs_subscribes t
 where t.apply_unit_group_id = /*id*/''
    or t.exec_unit_group_id = /*id*/''
    and t.delete_flg=0