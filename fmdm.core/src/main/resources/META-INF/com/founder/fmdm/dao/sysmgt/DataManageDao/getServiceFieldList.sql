select column_name 
from user_tab_columns 
where table_name='SUBS_SERVICE'
and column_name in ('SERVICE_ID','SERVICE_NAME','MSG_TYPE','SERVICE_TYPE','SERVICE_DESC')