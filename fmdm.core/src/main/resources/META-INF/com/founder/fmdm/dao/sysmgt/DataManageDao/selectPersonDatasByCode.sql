select * from DICT_PERSON
where DELETE_FLG = 0
/*%if code != null && code != ""*/
and CODE = /*code*/''
/*%end*/