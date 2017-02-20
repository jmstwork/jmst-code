select rrd.*
  from rlmg_rule_data rrd
 where rrd.rule_id = /*ruleId*/''
   and rrd.delete_flg = 0
 order by rrd.data_row_no;
