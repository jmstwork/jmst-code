
select t.rule_name
  from rlmg_rule t
 where t.rule_name = /*ruleName*/''
   and t.delete_flg = 0
   /*%if ruleId != null */
   and t.rule_id != /*ruleId*/''
  /*%end*/
