select distinct rr.rule_id,
       rr.rule_Name,
       rr.status,
       rr.memo,
       rr.apply_by,
       rr.apply_time,
       rr.permit_by,
       rr.permit_time,
       rr.opinions,
       rr.create_by,
       rr.create_time,
       rr.last_update_by,
       rr.last_update_time,
       rr.delete_by,
       rr.delete_time,
       rr.delete_flg,
       rr.update_count,
       rrg.rulegroup_id,
       rrg.rulegroup_name,
       rrg.rulegroup_en_name,
       dp.name depart_name,
       dp.code depart_cd,
       rmt.model_id,
       rmt.model_name,
       rmt.model_en_name,
       sc.cd_value as statusText
  from rlmg_rule       rr,
       rlmg_rulegroup  rrg,
       dict_department     dp,
       rlmg_model_type rmt,
       system_code sc
 where rr.rulegroup_id = rrg.rulegroup_id
   and rr.unit_id = dp.code
   and rr.model_id = rmt.model_id
   and sc.category_cd ='C001'
   and rr.status=sc.cd_div
   and rr.delete_flg = 0
   and rrg.delete_flg = 0
   and dp.delete_flg = 0
   and rmt.delete_flg = 0
   and sc.delete_flg=0
   /*%if ruleName != null && ruleName != "" */
   and rr.rule_Name like concat('%',concat(/*ruleName*/'','%'))
   /*%end*/
      /*%if rulegroupName != null && rulegroupName != "" */
   and rrg.rulegroup_Name like concat('%',concat(/*rulegroupName*/'','%'))
   /*%end*/
      /*%if modelName != null && modelName != "" */
   and rmt.model_Name like concat('%',concat(/*modelName*/'','%'))
   /*%end*/
      /*%if status != null && status != "" */
   and rr.status like concat('%',concat(/*status*/'','%'))
   /*%end*/
   order by 
 /*%if orderBy != null */
    /*orderBy*/''
  /*%end*/
   /*%if orderBy == null */
    1
  /*%end*/