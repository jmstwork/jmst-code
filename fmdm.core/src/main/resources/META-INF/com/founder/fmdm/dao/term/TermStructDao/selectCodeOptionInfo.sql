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
         AND LOWER(code)  like  '%'+/*code*/''+'%' escape '$'
         or 
         LOWER(name) like '%'+/*code*/''+'%' escape '$'
/*%end*/