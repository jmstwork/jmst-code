 select * 
 	from rlmg_rule_version t1 
 	where t1.version_no = 
 	(select Max(convert(DECIMAL(18,2),t2.version_no)) from rlmg_rule_version t2)