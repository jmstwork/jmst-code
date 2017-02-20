select * from 
rlmg_rule 
where delete_flg = 0
   /*%if ruleId != null */
   and RULE_ID = /*ruleId*/'11'
   /*%end */