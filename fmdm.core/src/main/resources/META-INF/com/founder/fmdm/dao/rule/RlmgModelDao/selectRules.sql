select distinct rr.rule_id,
       rr.rule_Name,
       rr.apply_by,
       to_char(rr.apply_time,'yyyy-MM-dd HH24:mm:ss') as apply_Time,
       rr.permit_by,
       to_char(rr.permit_time,'yyyy-MM-dd HH24:mm:ss') as permit_Time,
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