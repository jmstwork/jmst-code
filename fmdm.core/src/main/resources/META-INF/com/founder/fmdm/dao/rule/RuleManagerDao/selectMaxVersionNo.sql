/*
*	exRuleManagerDao_selectMaxVersionNo.sql
*/
select max(to_number(version_no)) from rlmg_rule_version 