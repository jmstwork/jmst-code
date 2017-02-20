select t.dict_table_name
	from dict_multihospital_info t 
	where t.delete_flg=0
	 /*%if hospitalCode != null && hospitalCode != ""*/
 	and t.hospital_code = /*hospitalCode*/''
     /*%end*/ 
      /*%if classifyCode != null && classifyCode != ""*/
 	and t.classify_code = /*classifyCode*/''
     /*%end*/ 