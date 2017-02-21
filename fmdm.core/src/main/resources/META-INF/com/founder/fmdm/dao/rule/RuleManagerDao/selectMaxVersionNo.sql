/*
*	exRuleManagerDao_selectMaxVersionNo.sql
*/
select max(convert(DECIMAL(18,2),version_no)) from rlmg_rule_version