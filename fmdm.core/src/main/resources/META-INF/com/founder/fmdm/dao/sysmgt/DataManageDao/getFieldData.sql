select /*#fieldName*/
from /*#tableName*/
where 1=1
/*%if uniKey != null && uniKey != ""*/
 and UNI_KEY = /*uniKey*/''
/*%end*/