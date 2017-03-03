/**
 * AuditInformationDao_selectAuditQuery
 * */
select a.aud_id audId,
	   a.hospital_code hospitalCode,
       a.hospital_name hospitalName,
       convert(a.opt_dt, getdate(), 120) as optDate,
       a.sys_id sysId,
       s.sys_name sysName,
       a.event_code eventCode,
       e.event_name eventName,
       d.name as deptName,
       a.dept_code deptCode,
       u.user_name userName,
       a.user_id userId,
       a.ip_address ipAddress,
       a.machine_name machineName
  from aud_info a
   left join  aud_event e on a.event_code = e.event_code
   left join  subs_sys s on a.sys_id = s.sys_id
   left join  iam_account_info u on a.user_id = u.user_no
   left join   (select d1.code, d1.name
          from dict_department d1
         where d1.item_version =
               (select max(d2.item_version)
                  from dict_department d2
                 where d2.code = d1.code)) d on a.dept_code = d.code
 where 1=1
   /*%if hospitalCode != null && hospitalCode !=""*/
   and a.hospital_code = /*hospitalCode*/'123'
   	/*%end*/
   /*%if audId != null && audId != ""*/
   and a.aud_id = /*audId*/'11'
   	/*%end*/
    /*%if auditSys != null && auditSys != ""*/
   and a.sys_id = /*auditSys*/'11'
   	/*%end*/
    /*%if auditEvent != null && auditEvent != ""*/
   and a.event_code = /*auditEvent*/'11'
   	/*%end*/
   	/*%if optDt1 != null && optDt1 != ""*/
   	and convert(varchar(20), a.opt_dt, 120) >= /*optDt1*/'123'
   	/*%end*/
   	/*%if optDt2 != null && optDt2 != ""*/
   	and convert(varchar(20), a.opt_dt, 120) <= /*optDt2*/'123'
   	/*%end*/
   	/*%if userNo != null && userNo != ""*/
   and a.user_id like '%'+/*userNo*/'11'+'%'
   	/*%end*/
    /*%if userName != null && userName != ""*/
   and u.user_name like '%'+/*userName*/'123'+'%'
   	/*%end*/
   
   /*%if deptName != null && deptName != ""*/
   and d.name like '%'+/*deptName*/'33'+'%'
   	/*%end*/
 order by opt_dt desc