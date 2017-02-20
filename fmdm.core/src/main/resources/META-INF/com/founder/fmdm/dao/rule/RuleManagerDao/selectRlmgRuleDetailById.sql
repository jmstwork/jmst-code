select * from 
rlmg_rule_detail 
where delete_flg = 0
   /*%if ruleItemId != null */
   and RULE_ITEM_ID = /*ruleItemId*/'11'
   /*%end */