 select * 
 	from rlmg_rule_version t1 
 	where t1.version_no = 
 	(select Max(to_number(t2.version_no)) from rlmg_rule_version t2)