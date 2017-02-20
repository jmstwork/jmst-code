
select t.rule_item_name
from rlmg_rule_detail t
where delete_flg=0
/*%if ruleItemId!=null && ruleItemId!=""*/
 and   t.rule_item_id=/*ruleItemId*/''
/*%end*/
