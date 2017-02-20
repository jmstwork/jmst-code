select max(data_row_no) from rlmg_rule_data 
where delete_flg = 0
/*%if ruleId != null*/
and  rule_id = /*ruleId*/''
/*%end*/