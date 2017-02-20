select * from SUBS_EXTEND_SUB_ID
where 1=1
/*%if uniKey != null && uniKey != ""*/
and UNI_KEY = /*uniKey*/''
/*%end*/