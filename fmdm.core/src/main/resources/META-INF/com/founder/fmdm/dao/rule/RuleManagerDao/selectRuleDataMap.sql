/*
*	exRuleManagerDao_selectRuleDataMap.sql
*/
select rrd.data_row_no, rrd.data_id, rrd.rule_id, rrd.rule_item_id, rrd.data_value,rrde.rule_item_no
  from rlmg_rule_data rrd ,rlmg_rule_detail rrde
 where rrd.delete_flg != 1   
   and rrde.delete_flg != 1
   and rrd.rule_item_id=rrde.rule_item_id
   and rrd.rule_id = /*ruleId*/
  /*IF dataRowNo != null && dataRowNo!=""*/
   and rrd.data_row_no in (${dataRowNo})
   /*END*/
order by rrd.data_row_no