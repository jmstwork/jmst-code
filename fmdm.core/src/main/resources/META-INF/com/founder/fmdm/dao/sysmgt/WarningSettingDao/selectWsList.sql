select s.setting_id, s.user_no, s.unit_id,s.user_type,s.rulegroup_id group_code,pp.depart_name as unit_name,r.rulegroup_name,a.name user_name
  from NF_SETTING s,
  (select distinct p.name depart_name, p.code depart_cd
          from dict_department p) pp,rlmg_rulegroup r,(select p1.code,p1.name
  from dict_person p1
 where p1.delete_flg = 0
   and p1.item_version = (select max(p2.item_version)
                           from dict_person p2
                          where p1.code = p2.code) 
   order by p1.code) a
     /*%if (tel!="" && tel!=null) || (email!=""&&email!=null)*/    
          ,NF_SETTING_DETAIL d
     /*%end*/
 where s.unit_id = pp.depart_cd(+)
       and s.rulegroup_id=r.rulegroup_id(+)
       and s.user_no=a.code(+)
       and s.delete_flg=0
       /*%if settingId!="" && settingId!=null*/
       and s.setting_id=/*settingId*/'11'
       /*%end*/
       /*%if unitId !="" && unitId!=null*/
       and s.unit_id like '%'+/*unitId*/''+'%'
       /*%end*/
       /*%if unitName !="" && unitName != null*/
       and pp.depart_name like '%'+/*unitName*/''+'%'
       /*%end*/
       /*%if receiveId !="" && receiveId!=null*/
       and s.user_no like '%'+/*receiveId*/''+'%'
       /*%end*/
       /*%if rulegroupId !="" && rulegroupId!=null*/
       and s.rulegroup_id like '%'+/*rulegroupId*/''+'%'
       /*%end*/
       /*%if userType !="" && userType!=null*/
       and s.user_type like '%'+/*userType*/''+'%'
       /*%end*/
       /*%if tel !="" && tel!=null*/
       and s.setting_id=d.setting_id(+) and d.setting_value like '%'+/*tel*/''+'%' and  d.nf_type='1' and d.delete_flg=0
       /*%end*/
       /*%if email !="" && email!=null*/
       and s.setting_id=d.setting_id(+) and d.setting_value like '%'+/*email*/''+'%' and d.nf_type='2' and d.delete_flg=0
       /*%end*/
	   order by /*# orderBy */ 
       