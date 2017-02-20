select column_name 
from user_tab_columns 
where table_name='SUBS_ORDER_EXEC_ID'
and column_name in ('CODE','NAME')