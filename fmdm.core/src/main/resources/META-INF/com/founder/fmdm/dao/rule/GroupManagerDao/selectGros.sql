select rulegroup_id, rulegroup_Name, rulegroup_En_Name
  from rlmg_rulegroup
 where delete_flg = 0
 /*%if rulegroupName != null && rulegroupName !=""*/
   and rulegroup_Name like concat('%',concat(/*rulegroupName*/'11','%'))
   /*%end*/
   /*%if rulegroupEnName != null && rulegroupEnName !=""*/
   and rulegroup_En_Name like concat('%',concat(/*rulegroupEnName*/'11','%'))
   /*%end*/
   order by    
   /*%if operFlg=="edit" */
   last_update_time desc nulls last,
    /*%end */
   create_time desc