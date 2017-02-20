select *
  from rlmg_model_type
 where delete_flg = 0
   /*%if modelId != null */
   and model_id = /*modelId*/'11'
   /*%end */
   order by create_time