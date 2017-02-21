
select r.rule_id, r.rule_name, ia.user_name apply_by,convert(r.apply_time, getdate(), 120) as apply_time,iai.user_name permit_by,convert(r.permit_time, getdate(), 120) as permit_time,c.cd_value,opinions
  from rlmg_rule r,system_code c,iam_account_info ia,iam_account_info iai
 where r.delete_flg = 0
 	and  r.status!=0   
    and  r.status=c.cd_div(+)
    and r.apply_by=ia.user_no(+)
    and r.permit_by=iai.user_no(+)
    and  c.category_cd='C001' 
   /*%if ruleName != null */
   and r.rule_name like '%'+/*ruleName*/''+'%'
   /*%end*/
   /*%if status != null */
   and r.status like '%'+/*status*/''+'%'
   /*%end*/
   order by
     /*%if orderBy != null */
    	/*orderBy*/''
  /*%end*/
   /*%if orderBy == null */
    	1
  /*%end*/