select t.model_name
   from rlmg_model_type t
   where 1=1 
   /*%if modelId!=null && modelId!=""*/
   and t.model_id=/*modelId*/'123'
   /*%end*/