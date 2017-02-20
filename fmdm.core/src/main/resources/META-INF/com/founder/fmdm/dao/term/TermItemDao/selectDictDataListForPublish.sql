/**
 * selectDictDataList.sql
 * 为修改框，查询字典数据
 */

SELECT 
	/*%for name : termItemList */
		/*# name*/
		/*# "," */
	/*%end*/
	opt_status,
	item_version
FROM /*# tableName */ 
WHERE delete_flg = 0
/*%if uniKeys != null*/
	and	
	(
		/*%for uniKey : uniKeys */
			uni_key = /* uniKey */'000013'  
			/*%if uniKey_has_next */
				/*# "or" */  
			/*%end */
		/*%end*/
	)
	--uni_key in /* uniKeys */('000013','001013')
/*%end */
