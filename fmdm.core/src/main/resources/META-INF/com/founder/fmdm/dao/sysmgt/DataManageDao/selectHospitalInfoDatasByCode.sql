select * from DICT_HOSPITAL
where DELETE_FLG = 0
/*%if code != null && code != ""*/
and HOSPITAL_CODE = /*code*/''
/*%end*/