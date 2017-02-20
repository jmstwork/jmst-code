select distinct t.unit_group_id,
       t.unit_group_name,
       t.hospital_code,
       c2.cd_value       as hospitalName,
       c1.cd_value       as unit_group_type,
       t.unit_group_desc,
       t.last_update_time
  from subs_unit_group t,
       (select s.cd_value,
       		  s.cd_div
          from subs_unit_group t1, system_code s
         where t1.unit_group_type = s.cd_div
           and s.category_cd = 'C010'
           and t1.delete_flg=0) c1,
       (select s.cd_value,
       		  s.cd_div
          from subs_unit_group t2, system_code s
         where t2.hospital_code = s.cd_div
           and s.category_cd = 'C011'
           and t2.delete_flg=0) c2
 where t.delete_flg = 0
 	and c2.cd_div = t.hospital_code
	and c1.cd_div = t.unit_group_type
    /*%if unitGroupId != null && unitGroupId !=""*/
    and t.unit_group_id=/*unitGroupId*/''
    /*%end*/
   /*%if unitGroupName != null && unitGroupName != ""*/
    and t.unit_group_name like /*@contain(unitGroupName)*/''
     /*%end*/
     /*%if hospitalCode != null && hospitalCode != ""*/
    and t.hospital_code =/*hospitalCode*/''
     /*%end*/
 order by t.last_update_time desc