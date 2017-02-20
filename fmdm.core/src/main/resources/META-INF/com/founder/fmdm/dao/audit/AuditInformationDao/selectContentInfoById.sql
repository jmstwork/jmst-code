/**
 * 
 * 检索审计内容
 **/
select 
  t.aud_id audId,
  t.row_no rowNo,
  max(t.row_action) rowAction,
  max(case t.item_name 
    when '药品名称' then t.old_value
      else '' end) drugName,
  max(case t.item_name 
    when '包装序号' then t.old_value
      else '' end) packageNo,
  max(case t.item_name 
    when '零售价' then t.old_value
      else '' end) drugPriceOld,
  max(case t.item_name 
    when '零售价' then t.new_value
      else '' end) drugPriceNew,
  max(case t.item_name 
    when '生效日期' then t.new_value
      else '' end) effectDate
  from aud_content_info t
 where t.aud_id = /*audId*/'1384220975739'
 group by t.aud_id,t.row_no