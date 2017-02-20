select data.datasource_id,
       data.datasource_name,
       data.jdbc_specific,
       data.jdbc_url,
       data.jdbc_user_name,
       data.jdbc_password,
       s.cd_value as jdbcSpecificName,
        data.update_count,
       data.system_id,
       iam.sys_name
  from datasource_set data, subs_sys iam,
  		system_code s
 where data.system_id = iam.sys_id
  and	data.jdbc_specific=s.cd_div
  and  s.category_cd='C006'
  and data.delete_flg=0
  and iam.delete_flg=0
   /*%if systemId != null && systemId != ""*/
   and data.system_id = /*systemId*/''
   /*%end*/
   
   /*%if datasourceId != null && datasourceId != ""*/
   and data.datasource_id = /*datasourceId*/''
   /*%end*/

 order by data.datasource_name