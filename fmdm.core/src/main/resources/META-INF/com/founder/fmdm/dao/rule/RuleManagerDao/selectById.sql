select * from 
rlmg_rule_data 
where delete_flg = 0
   /*%if dataId != null */
   and data_id = /*dataId*/'11'
   /*%end */
