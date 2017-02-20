select * 
  from aud_event e
 where to_number(e.event_code) = to_number(/*eventCode*/'123')
