select count(1) 
from rlmg_rule rr,rlmg_rule_detail rrd,rlmg_model_detail rmd
where 
/*%if fieldId != null && fieldId!=""*/
rmd.field_id = /*fieldId*/'123' and
/*%end */
rr.delete_flg=0
and rrd.delete_flg=0
and rr.rule_id = rrd.rule_id
and rmd.model_id = rr.model_id
and rrd.item_expression like '%'||rmd.field_en_name||'%'