select count(*)
  from rlmg_rule t
  where delete_flg=0
 /*%if modelId != null && modelId!="" */
  and model_id=/*modelId*/'a'
 /*%end */