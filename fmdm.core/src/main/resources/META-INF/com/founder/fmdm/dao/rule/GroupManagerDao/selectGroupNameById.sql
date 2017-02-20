select t.rulegroup_name
 from rlmg_rulegroup t
  where 1=1
 /*%if rulegroupId!=null && rulegroupId!=""*/
  and t.rulegroup_id = /*rulegroupId*/'123'
/*%end */