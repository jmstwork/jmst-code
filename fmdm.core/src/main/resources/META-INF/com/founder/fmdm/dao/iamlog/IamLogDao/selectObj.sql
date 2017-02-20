select opt_Code optCode, opt_Name optName
  from iam_opt io
where 1=1
/*%if resrCode != null && resrCode!=""*/
   and resr_Code = /*resrCode*/''
   	/*%end */
   order by io.opt_code