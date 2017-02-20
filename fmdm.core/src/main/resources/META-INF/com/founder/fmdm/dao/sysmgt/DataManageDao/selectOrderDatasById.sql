select * from SUBS_ORDER_EXEC_ID
where 1=1
/*%if uniKey != null && uniKey != ""*/
and UNI_KEY = /*uniKey*/'123'
/*%end*/