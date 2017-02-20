/**
 * selectDictStatus.sql
 * 查询字典状态
 */
SELECT sc.CD_DIV as statusCode,
sc.CD_VALUE as statusValue
FROM SYSTEM_CODE sc
WHERE sc.CATEGORY_CD='C005'
ORDER BY sc.DISP_ORDER