/**
 * selectFieldsByDictIdList.sql
 * 列名列表集合
 * 
 */  
SELECT 
    /*%if viewId != null && viewId != ""*/
  		VF.VIEW_FIELD_ID,
   /*%end*/
  	/*%if viewId == null || viewId == ""*/
  		''AS VIEW_FIELD_ID,
   /*%end*/
       '' AS VIEW_ID,
       DF.FIELD_ID,
       DF.FIELD_DESC,
       DF.FIELD_NAME,
       '' AS EDIT_FLG,
       '' AS RETRIEVAL_FLG,
       '' AS ITEM_FLG,
       '' AS ITEM_ORDER,
       '' AS ORDER_FLG,
       '' AS FIELD_ORDER
  FROM (SELECT * FROM DICT_FIELD WHERE 1=1
    /*%if dictId != null && dictId != ""*/
  	AND DICT_ID = /*dictId*/'ac5d0234dd204e0c889eb02cee2b12fd'
   /*%end*/
  	AND DELETE_FLG = 0
  	AND FIELD_NAME NOT IN('delete_flg',
                             'last_update_time',
                             'delete_time',
                             'create_by',
                             'delete_by',
                             'create_time',
                             'last_update_by',
                             'update_count',
                             'uni_key',
                             'item_version',
                             'release_status',
                             'opt_status'))DF 
  --LEFT JOIN VIEW_FIELD VF
   -- ON DF.FIELD_ID = VF.FIELD_ID
  -- AND VF.DELETE_FLG = 0
  -- AND DF.DELETE_FLG = 0  	