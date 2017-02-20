select iam.user_name as create_by,
'V'|| rlmg.version_no as version_no,
to_char(rlmg.create_time,'yyyy-MM-dd HH24:mm:ss') as create_time,
rlmg.version_memo
  from rlmg_rule_version rlmg,
       iam_account_info iam
   where rlmg.create_by=iam.user_no
  /*%if versionNo != "" */
and  version_no like concat('%',concat(/*versionNo*/'33','%'))
   /*%end*/
    order by to_number(substr(version_no,2)) desc