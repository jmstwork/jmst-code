/*
*	exRuleManagerDao_selectRuleDataDetailCol.sql
*/
select d.rule_item_id, 
       d.rule_item_no, 
       d.rule_item_name
  from rlmg_rule_detail d
 where d.delete_flg != 1
   /*IF ruleId!=null*/
   and d.rule_id = /*ruleId*/'3276b28717f9443499ed855e47e7910c'
   /*END*/
 order by d.rule_item_no