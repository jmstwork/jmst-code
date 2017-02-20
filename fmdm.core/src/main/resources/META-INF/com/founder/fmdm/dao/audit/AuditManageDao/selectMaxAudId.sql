select *
  from aud_content_info
  where aud_id = (select max(cast(rtrim(ltrim(aud_id)) as float)) from aud_content_info)