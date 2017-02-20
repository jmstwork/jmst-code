
select * 
from rlmg_rule_detail
 where delete_flg = 0
 	/*%if ruleId != null && ruleId != "" */
   and rule_Id = /*ruleId*/''
   /*%end*/
   order by rule_item_no