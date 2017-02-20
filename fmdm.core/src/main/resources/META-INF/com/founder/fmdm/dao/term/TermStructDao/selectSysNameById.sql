
/**
 * 根据数据源id，查询对应的名称
 */
select SYS_NAME 
from subs_sys
where SYS_ID=upper(/*sysId*/'')
and DELETE_FLG = 0;