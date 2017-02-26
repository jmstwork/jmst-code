select i.user_no,
       i.user_name,
       i.mobile,
       i.email
  from iam_account_info i
 where i.delete_flg != 1
   and i.user_no not in(select u.user_account from tse_users u where u.delete_flg!=1)
  /*%if userAccount != null && userAccount != ""*/
  and user_no like '%'+/*userAccount*/''+'%'
  /*%end*/
  /*%if userName != null && userName != ""*/
  and user_name like '%'+/*userName*/''+'%'
  /*%end*/
  order by user_no