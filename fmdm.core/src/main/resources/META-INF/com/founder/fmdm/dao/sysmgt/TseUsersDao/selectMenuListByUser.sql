select * from sys_menu_button 

where delete_flg = '0'
/*%if userAccount != null && userAccount != "" && userId != null && userId != ""*/
and smb_id in (
select distinct (smb_id)
  from role_authority
 where role_id in (select role_id from role r
                    where r.delete_flg = '0'
                      and EXISTS (SELECT 1 FROM USER_ROLE t where t.user_account = /*userAccount*/'jiangjunjun' AND t.role_id = r.role_id and t.delete_flg = '0'))
union
select distinct (smb_id)
  from user_authority
 where user_id = /*userId*/'0001' and delete_flg = '0')
/*%end*/
 order by create_time