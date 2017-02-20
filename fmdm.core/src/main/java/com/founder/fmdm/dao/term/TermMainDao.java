package com.founder.fmdm.dao.term;

import java.util.List;
import java.util.Map;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.service.term.dto.DictMainDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface TermMainDao 
{
    /**
     * 
     * @param dictId
     * @param serviceId
     * @param dictName
     * @param status
     * @param options
     * @return
     */
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
    List<DictMainDto> selectDictList1(String dictId,String serviceId,String dictName,List<String> status,SelectOptions options,List<String> tableNames);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<DictMainDto> selectAdminDictList(String dictId,String serviceId,String dictName,List<String> status,SelectOptions options);
    
	/**
	 * 
	 * @param dictId
	 * @param serviceId
	 * @param dictName
	 * @param status
	 * @return
	 */
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
    List<DictMainDto> selectDictList(String dictId,String serviceId,String dictName,List<String> status);
    
    /**
     * 查询字典状态信息
     * @return
     */
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
    List<Map<String,Object>> selectDictStatus();

	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<DictMainDto> selectDictMainListByName(String dictId,String serviceId, String dictName, List<String> status,
			SelectOptions options, List<String> tableNames);
	
	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	List<DictMainDto> selectAdminDictMainListByName(String dictId,String serviceId, String dictName, List<String> status,
			SelectOptions options);
}
