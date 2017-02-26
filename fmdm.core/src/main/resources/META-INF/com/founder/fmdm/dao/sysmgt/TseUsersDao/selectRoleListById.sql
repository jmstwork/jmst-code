select r.role_id,
 r.role_name,
r.memo
 from role r ,user_role u
 where r.delete_flg=0
 and u.delete_flg=0
 and r.role_id=u.role_id
 /*%if userId != null && userId != ""*/
  and u.user_account =/*userId*/''
  /*%end*/
   /*%if roleName != null && roleName != ""*/
  and r.role_name like '%'+/*roleName*/''+'%'
  /*%end*/