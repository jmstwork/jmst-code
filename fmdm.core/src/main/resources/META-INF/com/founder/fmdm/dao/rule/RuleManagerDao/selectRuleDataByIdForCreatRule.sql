select rrd.data_row_no,
       rrd.data_id,
       rrd.rule_id,
       rrd.rule_item_id,
       rrd.data_value
  from rlmg_rule_data rrd, rlmg_rule_detail rrde, rlmg_rule rr
 where rrd.rule_item_id = rrde.rule_item_id 
   and rrd.rule_id = rr.rule_id
   and rr.delete_flg=0
   and rrde.delete_flg = 0   
   and rrd.delete_flg = 0
   and rr.rule_id =/*ruleId*/''
 order by rrd.data_row_no ,rrde.rule_item_no;