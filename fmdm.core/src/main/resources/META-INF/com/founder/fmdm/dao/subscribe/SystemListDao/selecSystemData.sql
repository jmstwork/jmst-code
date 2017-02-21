/**
 *  检索应用系统一览
 */

select s.sys_id, s.sys_name, s.sys_apply
  from subs_sys s
 where s.delete_flg != 1
   /*%if sysId!=null && sysId!="" */
   and s.sys_id = /*sysId*/'a'
   /*%end*/
   /*%if sysName!=null && sysName!="" */
   and s.sys_name like '%'+/*sysName*/'MS021'+'%'
   /*%end*/
   and s.release_status = 'c'
   and s.opt_status != 'd'
   order by s.sys_id