select column_name 
from user_tab_columns 
where table_name='SUBS_EXTEND_SUB_ID'
and column_name in ('CODE','NAME')