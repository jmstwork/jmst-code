/**
 * selectDictDataList.sql
 * 为修改框，查询字典数据
 */

SELECT 
	/*%for name : termItemList */
		/*# name*/
		/*# "," */
	/*%end*/
	uni_key
	,opt_status
	,release_status
	,item_version
	,update_count
FROM /*# tableName */ 
WHERE delete_flg = 0
/*%if releaseStates != null */
	and release_status in /* releaseStates */("a","b")
/*%end */
/*%if uniKey != null && uniKey != "" */
	and	uni_key = /* uniKey */'000013'
/*%end */
	and uni_key not in (select t2.uni_key from /*# tableName */ t2 where  t2.release_status = 'c' and t2.opt_status = 'd')