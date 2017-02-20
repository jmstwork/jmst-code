select m.model_id as modelId,
     m.model_en_name as modelEnName,
     t.rule_name ruleName,
     t.rule_group_id rulegroupId,
     t.unit_id unitId,
     g.rulegroup_en_name rulegroupEnName,
     m.model_en_name modelEnName
  from rlmg_rule t,rlmg_model_type m,rlmg_rulegroup g
  where t.model_id=m.model_id
  and   t.rule_group_id=g.rulegroup_id
  /*%if ruleId!=null*/
  and t.rule_id = /*ruleId*/''
  /*%end*/