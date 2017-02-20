select *
  from aud_event
  where event_code = (select max(to_number(event_code)) from aud_event)