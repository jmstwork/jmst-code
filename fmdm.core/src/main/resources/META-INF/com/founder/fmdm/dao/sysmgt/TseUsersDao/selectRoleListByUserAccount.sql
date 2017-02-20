select *
  from role r
 where r.delete_flg = '0'
   and EXISTS (SELECT 1
          FROM USER_ROLE t
         where t.user_account = /*userAccount*/'jiangjunjun'
           AND t.role_id = r.role_id
           and t.delete_flg = '0')
