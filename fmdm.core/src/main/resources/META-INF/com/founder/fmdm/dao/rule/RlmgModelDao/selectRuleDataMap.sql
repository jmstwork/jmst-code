SELECT data_row_no, data_id, rule_id, rule_item_id, data_value from(
select rownum nos,vv.* from
(SELECT rrd.data_row_no, rrd.data_id, rrd.rule_id, rrd.rule_item_id, rrd.data_value
FROM rlmg_rule_data rrd ,rlmg_rule_detail rrde
WHERE  rrd.delete_flg != 1   
   and rrde.delete_flg != 1
   and rrd.rule_item_id=rrde.rule_item_id
   and rrd.rule_id = /*ruleId*/''
  /*%if dataRowNos != null*/
   and rrd.data_row_no in /*dataRowNos*/('a','b')
   /*%end*/
   ORDER BY rrd.data_row_no) vv)
   where rule_id = /*ruleId*/''
   /*%if currentPage != null*/
   and nos > (/*currentPage*/'2'-1)*10*/*colSize*/'2'
   /*%end*/
   /*%if currentPage != null*/
   and nos <= /*currentPage*/'2'*10*/*colSize*/'2'
   /*%end*/
