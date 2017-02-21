select iam.user_name as create_by,
'V'|| rlmg.version_no as version_no,
convert(rlmg.create_time, getdate(), 120) as create_time,
rlmg.version_memo
  from rlmg_rule_version rlmg,
       iam_account_info iam
   where rlmg.create_by=iam.user_no
  /*%if versionNo != "" */
and  version_no like '%'+/*versionNo*/''+'%'
   /*%end*/
    order by convert(DECIMAL(18,2),version_no) desc