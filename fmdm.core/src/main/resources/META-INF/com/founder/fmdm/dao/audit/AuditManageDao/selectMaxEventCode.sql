select *
  from aud_event
  where event_code = (select max(cast(event_code as INT )) from aud_event)