select d.data_row_no
  from rlmg_rule_data d
 where d.delete_flg=0
 and d.rule_id = /*ruleId*/''
 group by d.data_row_no
 order by d.data_row_no;