select e.event_code eventCode, e.event_name eventName
 from aud_event e
 where delete_flg = 0
 order by e.event_code