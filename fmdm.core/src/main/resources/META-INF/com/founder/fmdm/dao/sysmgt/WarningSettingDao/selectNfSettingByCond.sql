select t.*
	from nf_setting t
where t.userNo = /*userNo*/'111'
and t.rulegroup_id = /*groupCode*/'111'
and t.user_type = /*userType*/'11'
and t.unit_id = /*unitId*/'11'
and t.delete_flg = 0
       