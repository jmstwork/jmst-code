
select distinct(data_row_no) from rlmg_rule_data
where delete_flg =0
  /*%if dataValue != null */
and data_value like '%'+/*dataValue*/''+'%'
  /*%end*/ 