/**
 *  根据条件查询服务列表信息
 */
 select s1.subscribe_id as subscribeId,
        s2.service_id as serviceId,
        s2.service_name as serviceName,
        s1.hospital_id as hospitalId,
         (case
           when s1.hospital_id ='*' then
             '不区分'
           else  
             c.cd_value
           end) hospitalName,
        s1.domain_id as visitTypeId,
        cast(case
          when s1.domain_id = '*' then
           '不区分'
          else
           cast(d.name as VARCHAR )
        end as VARCHAR ) visitTypeName,
         cast(case
          when (s1.order_exec_id = '*' or s1.order_exec_id is null) then
           '不区分'
          else
           cast(ordid.name as VARCHAR )
        end as VARCHAR ) orderExecName,
        s1.apply_unit_group_id as applyUnitGroupId,
        (select g1.unit_group_name
           from SUBS_UNIT_GROUP g1
          where g1.unit_group_id = s1.apply_unit_group_id
            and (g1.unit_group_type = 0 or g1.unit_group_type = -1)
            and g1.delete_flg != 1) as applyUnitGroupName,
        s1.exec_unit_group_id as execUnitGroupId,
        (select g2.unit_group_name
           from SUBS_UNIT_GROUP g2
          where g2.unit_group_id = s1.exec_unit_group_id
            and (g2.unit_group_type = 1 or g2.unit_group_type = -1)
            and g2.delete_flg != 1) as execUnitGroupName,
        s1.extend_sub_id as extendSubId,
        s1.order_exec_Id as orderExecId,
        s1.subs_desc as subsDesc,
        s1.send_sys_id as sendSysId,
         (case
           when s1.send_sys_id ='*' then
             '不区分'
           else  
             cast(ss.sys_name as VARCHAR )
           end) as sendSysName
        /*%if sysId !=null && sysId != "" && method=="show"*/
        ,s3.subs_id as subsId,
        s3.output_queue_name as outputQueueName
        /*%end*/
   from SUBS_SUBSCRIBES         s1
    INNER join   SUBS_SERVICE  s2 on s1.service_id = s2.service_id,
    left join  (select v.code, v.name from dict_visit_type v
         where v.delete_flg =0 ) d on s1.domain_id=d.code
    left join   (select sd.code, cast(sd.name as VARCHAR ) name
            from subs_order_exec_id sd where sd.delete_flg !=1 ) ordid on s1.order_exec_id=ordid.code
    left join   (select s.cd_div,s.cd_value from system_code s
        where s.category_cd='C011' and s.delete_flg=0 ) c on s1.hospital_id = c.cd_div
    left join  SUBS_SYS ss on s1.send_sys_id = ss.sys_id
        /*%if sysId !=null && sysId != "" && method=="show"*/
    inner join SUBS_SERVICE_SUBSCRIBES s3 on s1.subscribe_id = s3.subscribe_id
        /*%end*/
  where s1.delete_flg != 1
    and s2.delete_flg != 1
    and s2.opt_status != 'd'
    and s2.release_status = 'c'

   /*%if serviceId !=null && serviceId != "" */
   and s2.service_id = /*serviceId*/'a'
   /*%end*/
   
   /*%if serviceName !=null && serviceName != "" */
   and s2.service_name like '%'+/*serviceName*/'MS021'+'%'
   /*%end*/
   
   /*%if hospitalCode !=null && hospitalCode != "" */
   and c.cd_div = /*hospitalCode*/'a'
   /*%end*/
   
   /*%if sysId !=null && sysId != "" && method=="choose"*/
   and s1.subscribe_id not in (select distinct(s4.subscribe_id) from SUBS_SERVICE_SUBSCRIBES s4 where s4.sys_id=/*sysId*/'S001' and s4.delete_flg != 1)
   /*%end*/
 
   /*%if sysId !=null && sysId != "" && method=="show"*/
   and s3.sys_id = /*sysId*/'S001'
   and s3.delete_flg != 1
   /*%end*/
   
   order by s2.service_id