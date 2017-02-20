select resr_Code resrCode, resr_Name resrName
  from iam_resr
where is_module>0
order by resr_Code ASC