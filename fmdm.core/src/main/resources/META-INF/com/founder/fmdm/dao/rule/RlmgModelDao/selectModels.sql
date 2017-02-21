select model_id, model_Name, model_En_Name
  from rlmg_model_type
 where delete_flg = 0
   /*%if modelName != null */
   and model_Name like '%'+/*modelName*/''+'%'
   /*%end */
   order by create_time desc