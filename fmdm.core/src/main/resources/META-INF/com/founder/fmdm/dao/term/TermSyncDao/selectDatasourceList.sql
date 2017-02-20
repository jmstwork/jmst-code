select data.datasource_id,
       data.datasource_name,
       data.jdbc_specific,
       data.jdbc_url,
       data.jdbc_user_name,
       data.jdbc_password,
        data.update_count,
       data.system_id,
       iam.sys_name
  from datasource_set data, subs_sys iam
 where data.system_id = iam.sys_id
	and data.delete_flg=0
	and iam.delete_flg=0
   /*%if systemId != null && systemId != ""*/
   and data.system_id = /*systemId*/''
   /*%end*/
   
   /*%if datasourceId != null && datasourceId != ""*/
   and data.datasource_id = /*datasourceId*/''
   /*%end*/

 order by data.datasource_name