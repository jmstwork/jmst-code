select l.log_Id logId,
       l.operor_Id operorId,
       convert(varchar(20), l.oper_Dt, 120) as operDt,
       l.oper_Modu operModu,
       r.resr_Name resrName,
       l.oper_Obj operObj,
       o.opt_Name optName,
       l.oper_Cont operCont,
       a.user_name userName
  from iam_log l
  left join iam_resr r on l.oper_Modu = r.resr_Code
  left join iam_opt o on l.oper_Obj = o.opt_Code
  left join iam_account_info a on l.operor_Id = a.user_No
 where 1=1
  /*%if operorId != null && operorId!=""*/
   and l.operor_Id like '%'+/*operorId*/''+'%'
   	/*%end */
   /*%if operModu != null && operModu!=""*/
   and l.oper_Modu = /*operModu*/''
   	/*%end */
   /*%if operObj != null && operObj!=""*/
   and l.oper_Obj = /*operObj*/''
   	/*%end */
   /*%if operDt1 != null*/
   	and l.oper_Dt >= /*operDt1*/'2013-10-28 14:47:05.198'
   	/*%end */
   	/*%if operDt2 != null*/
   	and l.oper_Dt <= /*operDt2*/'2013-10-28 14:47:05.198'
   	/*%end */
   order by l.oper_Dt desc