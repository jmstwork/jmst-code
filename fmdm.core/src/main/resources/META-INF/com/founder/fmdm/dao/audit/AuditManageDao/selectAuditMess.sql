select * 
  from aud_event e
 where cast(e.event_code as INT ) = cast(/*eventCode*/'123' as INT )
