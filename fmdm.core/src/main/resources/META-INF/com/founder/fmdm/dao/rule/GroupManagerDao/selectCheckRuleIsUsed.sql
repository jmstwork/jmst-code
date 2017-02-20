select count(1)
  from (select rr.rule_id
          from rlmg_rule rr
         where rr.rule_group_id = /*rulegroupId*/'111'
           and rr.delete_flg = 0)