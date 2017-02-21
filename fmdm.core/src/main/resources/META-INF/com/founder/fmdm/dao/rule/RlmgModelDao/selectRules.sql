select distinct rr.rule_id,
       rr.rule_Name,
       rr.apply_by,
       convert(rr.apply_time, getdate(), 120) as apply_Time,
       rr.permit_by,
       convert(rr.permit_Time, getdate(), 120) as permit_Time,
       rr.status,
       rr.memo,
       rr.opinions
  from rlmg_rule  rr
 where rr.delete_flg = 0
   /*%if ruleName != null && ruleName != "" */
   and rr.rule_Name like concat('%',concat(/*ruleName*/'','%'))
   /*%end*/
   /*%if status != null && status != "" */
   and rr.status like concat('%',concat(/*status*/'','%'))
   /*%end*/

   and rr.status not in ('0')

   order by rr.last_update_time desc