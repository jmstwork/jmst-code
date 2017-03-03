select distinct rr.rule_id,
       rr.rule_Name,
       rr.apply_by,
       convert(varchar(20), rr.apply_time, 120) as apply_Time,
       rr.permit_by,
       convert(varchar(20), rr.apply_time, 120) as permit_Time,
       rr.status,
       rr.memo,
       rr.opinions
  from rlmg_rule  rr
 where rr.delete_flg = 0
   /*%if ruleName != null && ruleName != "" */
   and rr.rule_Name like '%'+/*ruleName*/''+'%'
   /*%end*/
   /*%if status != null && status != "" */
   and rr.status like '%'+/*status*/''+'%'
   /*%end*/

   and rr.status not in ('0')

   order by rr.last_update_time desc