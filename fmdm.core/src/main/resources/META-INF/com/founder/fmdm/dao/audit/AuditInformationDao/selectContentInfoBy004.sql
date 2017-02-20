/**
 * 
 * 检索审计内容
 **/
select 
  t.aud_id audId,
  t.row_no rowNo,
  max(t.row_action) rowAction,
  max(case t.item_name 
    when '门诊卡号' then t.old_value
      else '' end) patientId,
  max(case t.item_name 
    when '住院号' then t.old_value
      else '' end) domainId,
  max(case t.item_name 
    when '临床数据分类' then t.old_value
      else '' end) clinicDataCategory,
  max(case t.item_name 
    when '临床数据ID' then t.old_value
      else '' end) clinicDataId,
  max(case t.item_name 
    when '检验项目名称' then t.old_value
      else '' end) examineName
        
  from aud_content_info t
 where t.aud_id = /*audId*/'1384226577416'
 group by t.aud_id,t.row_no;