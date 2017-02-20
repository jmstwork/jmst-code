/**
 * AuditInformationDao_selectAuditQuery
 * */
select a.aud_id audId,
	   a.hospital_code hospitalCode,
       a.hospital_name hospitalName,
       to_char(a.opt_dt,'yyyy-MM-dd HH24:mi:ss') as optDate,
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
  from aud_info a,
       aud_event e,
       subs_sys s,
       iam_account_info u,
       (select d1.code, d1.name
          from dict_department d1
         where d1.item_version =
               (select max(d2.item_version)
                  from dict_department d2
                 where d2.code = d1.code)) d
 where a.event_code = e.event_code(+)
   and a.sys_id = s.sys_id(+)
   and a.user_id = u.user_no(+)
   and a.dept_code = d.code(+)
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
   	and to_char(a.opt_dt,'yyyy-MM-dd hh24:mi:ss') >= /*optDt1*/'123'
   	/*%end*/
   	/*%if optDt2 != null && optDt2 != ""*/
   	and to_char(a.opt_dt,'yyyy-MM-dd hh24:mi:ss') <= /*optDt2*/'123'
   	/*%end*/
   	/*%if userNo != null && userNo != ""*/
   and a.user_id like concat('%',concat(/*userNo*/'11','%'))
   	/*%end*/
    /*%if userName != null && userName != ""*/
   and u.user_name like concat('%',concat(/*userName*/'123','%'))
   	/*%end*/
   
   /*%if deptName != null && deptName != ""*/
   and d.name like concat('%',concat(/*deptName*/'33','%'))
   	/*%end*/
 order by opt_dt desc