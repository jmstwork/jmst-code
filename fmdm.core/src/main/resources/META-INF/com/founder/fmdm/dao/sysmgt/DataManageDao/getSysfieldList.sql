select column_name 
from user_tab_columns 
where table_name='SUBS_SYS'
and column_name in ('SYS_ID','SYS_NAME','SYS_APPLY','HOSPITAL_ID')