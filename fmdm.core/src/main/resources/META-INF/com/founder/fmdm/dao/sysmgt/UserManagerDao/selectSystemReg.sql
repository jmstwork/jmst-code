select ss.sys_id, ss.sys_name,ss.sys_apply 
 from subs_sys ss
 where ss.delete_flg = 0
 	and ss.release_status = 'c'
 	/*%if searchSysID != null && searchSysID != ""*/
   	and ss.sys_id like '%'+/*searchSysID*/'111'+'%'
	/*%end*/
	/*%if searchSysName != null && searchSysName != "" */
   and ss.sys_name like '%'+/*searchSysName*/'111'+'%'
	/*%end*/
    /*%if searchSupName != null && searchSupName != "" */
   and ss.sys_apply like '%'+/*searchSupName*/'111'+'%'
    /*%end*/
   order by ss.sys_id