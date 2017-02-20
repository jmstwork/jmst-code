
/**
 * 根据传入的字段名判断是否为oracle关键字
 */
select count(1) 
from dual
where upper(/*fieldName*/'DATE') in
 (select keyword from v$reserved_words where keyword not in /*keyWords*/('a','b'))