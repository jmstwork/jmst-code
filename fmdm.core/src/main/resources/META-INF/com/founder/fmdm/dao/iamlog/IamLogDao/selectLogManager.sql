select l.log_Id logId,
       l.operor_Id operorId,
       convert(a.opt_dt, getdate(), 120) as operDt,
       l.oper_Modu operModu,
       r.resr_Name resrName,
       l.oper_Obj operObj,
       o.opt_Name optName,
       l.oper_Cont operCont,
       a.user_name userName
  from iam_log l, iam_resr r, iam_opt o, iam_account_info a
 where l.oper_Modu = r.resr_Code(+)
   and l.oper_Obj = o.opt_Code(+)
   and l.operor_Id = a.user_No(+)
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