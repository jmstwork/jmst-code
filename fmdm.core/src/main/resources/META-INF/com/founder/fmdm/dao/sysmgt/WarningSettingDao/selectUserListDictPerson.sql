/**
 * WarningSettingDao_selectUserListDictPerson.sql
 */
 select p1.code,p1.name
  from dict_person p1
 where p1.delete_flg = 0
  /*%if userNo!=null && userNo != "" */
   and p1.code like '%'+/*userNo*/''+'%'
   /*%end*/
   /*%if userName!=null && userName != "" */
   and p1.name like '%'+/*userName*/''+'%'
   /*%end*/
   and p1.item_version = (select max(p2.item_version)
                           from dict_person p2
                          where p1.code = p2.code) 
   order by p1.code