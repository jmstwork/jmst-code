select * from 
rlmg_rule_data 
where DELETE_FLG = '0'
 /*%if ruleItemId != null */
   and RULE_ITEM_ID = /*ruleItemId*/'11'
   /*%end */