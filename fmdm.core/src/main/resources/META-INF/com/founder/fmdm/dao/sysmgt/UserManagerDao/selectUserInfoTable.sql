select iai.user_no as userNo,
       iai.user_name as userName,
       iai.sex,
       iai.mobile,
       vs.name sexName,
       iai.dept_code as deptCode,
       vd.name deptName,
       iai.status,
       s.name statusName
  from iam_account_info iai, iam_userstatus s, dict_department vd, dict_sex vs
 where iai.dept_code = vd.code(+)
   and iai.sex = vs.code(+)
   and iai.status = s.code(+)
   and iai.delete_flg = 0
   and iai.user_no != 'admin'
    /*%if userNo != null && userNo !=""*/
    and iai.user_no =/*userNo*/''
    /*%end*/
   /*%if userName != null && userName != ""*/
    and iai.user_name like /*@contain(userName)*/''
     /*%end*/
    /*%if status != -1*/
    and iai.status = /*#status*/
    /*%end*/
   order by iai.user_no