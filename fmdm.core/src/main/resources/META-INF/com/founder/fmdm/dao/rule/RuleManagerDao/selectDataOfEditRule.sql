
select rrd.*
  from rlmg_rule_data rrd, rlmg_rule_detail rrde, rlmg_rule rr
 where rrd.rule_item_id = rrde.rule_item_id 
   and rrd.rule_id = rr.rule_id
   and rr.delete_flg=0
   and rrde.delete_flg = 0   
   and rrd.delete_flg = 0
   and rr.rule_id =/*ruleId*/''
   and rrd.data_row_no in(
  			 select distinct(data_row_no) from rlmg_rule_data
			 where delete_flg =0
 			  /*%if dataValue != null*/
			 and data_value like '%'+/*dataValue*/''+'%'
  			  /*%end*/ 
 		 	  /*%if ruleId != null*/
			 and rule_id = /*ruleId*/'a'
 			  /*%end*/ 
		)
 order by rrd.data_row_no ,rrde.rule_item_no;