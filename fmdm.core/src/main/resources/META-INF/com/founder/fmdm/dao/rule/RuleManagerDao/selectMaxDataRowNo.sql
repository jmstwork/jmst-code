select max(data_row_no) 
from rlmg_rule_data
where rule_item_id=/*ruleItemId*/''
and rule_id=/*ruleId*/''
and delete_flg=0