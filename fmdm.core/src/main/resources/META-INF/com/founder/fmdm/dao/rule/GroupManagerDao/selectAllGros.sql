select rulegroup_id, rulegroup_Name
  from rlmg_rulegroup
 where delete_flg = 0
   order by last_update_time desc nulls last