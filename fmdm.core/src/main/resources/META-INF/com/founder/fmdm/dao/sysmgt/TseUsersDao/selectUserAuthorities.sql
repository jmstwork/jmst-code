
select *
  from rmt_user_access_auth
  where 1=1
    /*%if userId != null && userId != ""*/
  and user_id = /* userId */'a'
  /*%end*/
  order by resource_id;
   