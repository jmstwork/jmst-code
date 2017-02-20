package com.founder.fmdm.dao.rule;

import java.util.List;
import java.util.Map;

public interface LogProcessorDao
{
    //查询除8个共通字段之外的字段
    List<String> selectNeedColumns(String resName);
    
    //查询除8个共通字段之外的其它字段值
    public Map<String,Object> selectInfoWithout8CommFields(String resName,List<String> columns,String name,String value);
    
    //专门针对术语信息查询
    public Map<String,Object> selectDictInfoWithout8CommFields(String resName,List<String> columns,String codeValue,String itemVersion);
    
    //专门针对术语信息查询
    public Map<String,Object> selectDictTitleInfoWithout8CommFields(String resName,List<String> columns,String personCd,String titleCd,String itemVersion);
    
   

}
