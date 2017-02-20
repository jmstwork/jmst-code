/**
 * 获取全部权限(除去操作权限)
 */
select * 
from sys_menu_button 
where delete_flg='0'
and type < 3
order by create_time;