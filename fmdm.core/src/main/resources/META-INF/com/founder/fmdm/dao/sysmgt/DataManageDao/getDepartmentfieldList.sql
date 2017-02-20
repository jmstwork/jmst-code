select column_name 
from user_tab_columns 
where table_name='DICT_DEPARTMENT'
and column_name in ('CODE','NAME','PY_CODE')