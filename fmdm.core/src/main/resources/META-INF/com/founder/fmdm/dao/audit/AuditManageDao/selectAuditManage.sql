select e.event_code evenCode, e.event_name evneName
  from aud_event e
 where e.delete_flg = 0
 /*%if eventCode != null && eventCode != ""*/
   	and e.event_code like '%'+/*eventCode*/'11'+'%'
 /*%end*/
 /*%if eventName != null && eventName != ""*/
   	and e.event_name like '%'+/*eventName*/'11'+'%'
 /*%end*/
 order by to_number(e.event_code)