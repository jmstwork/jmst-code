/**
 * selectReleaseStatusCount.sql
 * 查询术语表中各状态的数据有多少条
 */

SELECT 
	count(1)
FROM /*# tableName */ 
WHERE delete_flg = 0
	/*%if releaseState != null && releaseState != "" */
		and release_status = /* releaseState */'aa'
	/*%end */
	/*%if uniKey != null && uniKey != "" */
	and	uni_key = /* uniKey */'000013'
	/*%end */