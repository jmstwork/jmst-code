select * 
	from NF_SETTING_DETAIL t
    where t.setting_id=/*settingId*/'11'
    /*%if nfType!="" && nfType!=null*/
    and  t.nf_type=/*nfType*/'11'
    /*%end*/
     /*%if sValue !="" && sValue!=null*/
    and t.setting_value =/*sValue*/'11'
    /*%end*/
    and t.delete_flg=0
    order by t.setting_value