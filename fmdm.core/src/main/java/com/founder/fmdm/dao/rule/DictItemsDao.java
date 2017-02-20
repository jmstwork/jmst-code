package com.founder.fmdm.dao.rule;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;


public interface DictItemsDao
{
    List<Map> selectRes(String resName);

    Map[] selectRes(String resName, Collection<String> columns, Filter filter);

    /**
     *  药品名称字典项目一览检索  特殊处理，主键为联合主键
     * @param resName
     * @param columns
     * @param filter
     * @param pageContext
     * @return
     */
    Map[] selectResMedicalName(String resName, Collection<String> columns, Filter filter);
    
    /**
     *  药品字典项目一览检索  特殊处理，主键为联合主键
     * @param resName
     * @param columns
     * @param filter
     * @param pageContext
     * @return
     */
    Map[] selectResDrugs(String resName, Collection<String> columns, Filter filter);
    
    /**
     * 
     * @param <T>
     * @param resName
     * @param code
     * @return
     */
    Map[] selectDictItem(String resName , String code,Collection<String> columns);
    
    /**
     *  病区字典项目一览检索  特殊处理，主键为联合主键
     * @param resName
     * @param columns
     * @param filter
     * @param pageContext
     * @return
     */
    Map[] selectResInpatientArea(String resName, Collection<String> columns, Filter filter);
    
    Map[] selectResPersonTitleRelation(String resName, Collection<String> columns, Filter filter);
    
    /**
     *  检索变更详细
     * @param resName
     * @param columns
     * @param filter
     * @return
     */
    List<Map> selectResHistory(String resName, List<String> columns,
            Filter filter);

    /***
     *  动态查询字典的业务字段
     * @param resName
     * @return
     */
    List<String> selectColumns(String resName);

    /***
     *  全数据取消不需要的字段
     * @param resName
     * @return
     */
    List<String> selectPassColumns(String resName);
    
    /***
     *  查询某一字典表对应字典一览众的记录
     * @param resName
     * @return
     */
/*    public TDictInfo selectDictInfoItem(String resName);*/

    /***
     *  字典审批 驳回  将修改过的数据（即新增记录）物理删除掉
     * @param resName
     */
    public void deleteDictItemsByVersion(String resName, int itemVersion);

    /***
     *  字典发布，检索修改过的字典数据（需将编辑状态置为0）
     * @param resName
     */
    public List<Map> selectUpdateDictItemsStatus(String resName, int itemVersion);

    /***
     *  字典发布，检索删除的字典数据（标记为已删除）
     * @param resName
     */
    public List<Map> selectUpdateDictItemsDeleteFlg(String resName,
            int itemVersion);

    /***
     *  查询字典中被修改的记录
     * @param resName
     * @return
     */
    public List<Map> selectReleaseDictItems(String resName, List<String> columns);

    /***
     *  查询指定记录的updateCount
     * @param resName,codeValue,itemVersion
     * @return
     */
    public Map selectDictInfo(String resName, String codeValue, int itemVersion);
    
    /***
     *  药品名称字典查询
     * @param codeValue,nameValue,itemVersion
     * @return
     */
    public Map selectMedicalInfo(String codeValue, String nameValue,int itemVersion);
    
    /***
     *  药品字典查询
     * @param codeValue,serialValue,itemVersion
     * @return
     */
    public Map selectDrugsInfo(String codeValue, String serialValue,int itemVersion);
    

    /***
     *  病区字典查询
     * @param codeValue,deptSnValue,itemVersion
     * @return
     */
    public Map selectInpatientInfo(String codeValue, String deptSnValue,int itemVersion);
    
    
    /***
     *  查询指定记录的最大item_version
     * @param resName,codeValue
     * @return
     */
    public int selectMaxItemVersionByCode(String resName, String codeValue);
    
    /***
     *  药品名称字典指定记录的最大item_version
     * @param codeValue,nameValue
     * @return
     */
    public int selectMaxItemVersionByCodeAndName(String codeValue,String nameValue);
    
   
    /***
     *  药品字典查询指定记录的最大item_version
     * @param codeValue,serialValue
     * @return
     */
    public int selectMaxItemVersionByCodeAndSerial(String codeValue,String serialValue);
    
    /***
     *  病区字典查询指定记录的最大item_version
     * @param codeValue,deptSnValue
     * @return
     */
    public int selectMaxItemVersionByCodeAndDeptSn(String codeValue,String deptSnValue);
    
   /* *//**
     *  检索药品字典记录
     * @param code
     * @param value
     * @param name
     * @return
     *//*
    public DictDrugs selectDictDrugs(String code,String serial,String name);
    
    *//**
     * 获取调价信息记录
     * @return
     *//*
    public List<ChangePriceInfoDictDrugs> selectAdjustPriceList();*/
    
    /**
     * 获取所有的手术等级
     */
    public Map[] selectOperateGrade();
}
