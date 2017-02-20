/**
 * 
 * 检索审计内容
 **/
select 
  t.aud_Id audId,
  t.row_No rowNo,
  max(t.row_action) rowAction,
  max(case t.item_name 
    when '住院号' then t.old_value
      else '' end) hospitalNo,
  max(case t.item_name 
    when '临床路径名称' then t.old_value
      else '' end) clinicPathName,
  max(case t.item_name 
    when '退出路径原因' then t.old_value
      else '' end) exitTxt
  from aud_content_info t
 where t.aud_id = /*audId*/'1384226577416'
 group by t.aud_id,t.row_no;