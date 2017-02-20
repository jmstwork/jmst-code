
/**提供系统
 * 
 */

SELECT   DISTINCT sys.sys_id,sys.sys_name
FROM     SUBS_SYS sys
WHERE    sys.delete_flg=0
ORDER BY sys.sys_id