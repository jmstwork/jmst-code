/**
 * 共通查询
 */
	SELECT 
		code,
		name,
		get_codename(/* table */'aa',uni_key,'value_domain_version') as version,
		uni_key as unikey
	FROM /*# table */ 
	WHERE delete_flg = 0
/*%if @isNotEmpty(code) */
         AND LOWER(code)  like  /* @contain(code) */'%X%' escape '$' 
         or 
         LOWER(name) like /* @contain(code) */'%X%' escape '$' 
/*%end*/