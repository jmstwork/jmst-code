SELECT *
  FROM RLMG_MODEL_TYPE t
 where t.delete_flg = '0'
   and (t.model_name = /*modelName*/'人玩儿玩儿' or t.model_en_name = /*modelEnName*/'srtatewt')

