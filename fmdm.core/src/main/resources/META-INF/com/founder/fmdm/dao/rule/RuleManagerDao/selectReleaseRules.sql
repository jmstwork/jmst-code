/**
 *  已发布规则列表
 */
select rr.rule_id  ruleId,
       rr.rule_Name ruleName,
       rr.status,
       rr.memo,
       rrg.rulegroup_name rulegroupName,
       dp.name departName,
       rmt.model_name modelName,
       sc.cd_value statusText
  from rlmg_rule       rr,
       rlmg_rulegroup  rrg,
       dict_department     dp,
       rlmg_model_type rmt,
       system_code sc
 where rr.rule_group_id = rrg.rulegroup_id
   and rr.unit_id = dp.code
   and rr.model_id = rmt.model_id
   and dp.item_version = (select max(dp2.item_version) from dict_department dp2 where dp2.code = rr.unit_id and dp2.delete_flg = 0)
   and sc.category_cd ='C001'
   and rr.status=sc.cd_div
   and rr.delete_flg = 0
   and rrg.delete_flg = 0
   and dp.delete_flg = 0
   and rmt.delete_flg = 0
   and sc.delete_flg=0
   /*%if ruleName != null && ruleName != "" */
   and rr.rule_Name like '%'+/*ruleName*/''+'%'
   /*%end*/
      /*%if rulegroupName != null && rulegroupName != "" */
   and rrg.rulegroup_Name like '%'+/*rulegroupName*/''+'%'
   /*%end*/
      /*%if modelName != null && modelName != "" */
   and rmt.model_Name like '%'+/*modelName*/''+'%'
   /*%end*/
      /*%if status != null && status != "" */
   and rr.status like '%'+/*status*/''+'%'
   /*%end*/
   order by rr.last_update_time desc