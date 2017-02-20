select count(*)
  from rlmg_rule t, rlmg_rulegroup r
 where t.rulegroup_id = r.rulegroup_id
  /*%if visitDept != null && visitDept != "" */
   and t.unit_id = /*visitDept*/'1000008'
   /*%end*/
   /*%if ruleType != null && ruleType != "" */
   and r.rulegroup_en_name =/*ruleType*/'ExceptionGroup'
     /*%end*/
   and t.delete_flg=0
   and r.delete_flg =0