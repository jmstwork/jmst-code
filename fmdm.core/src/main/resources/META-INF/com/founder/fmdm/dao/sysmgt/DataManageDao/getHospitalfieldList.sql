select column_name 
from user_tab_columns 
where table_name='DICT_HOSPITAL'
and column_name in ('HOSPITAL_CODE','HOSPITAL_NAME')