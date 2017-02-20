select column_name 
from user_tab_columns 
where table_name='DICT_PERSON'
and column_name in ('CODE','NAME','BIRTHDAY','GENDERID','NATIONAL_IDENTIFIER','ORGID','EMAIL','ORGNAME','EMPTELENUM')