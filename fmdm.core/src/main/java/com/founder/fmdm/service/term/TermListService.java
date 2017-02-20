package com.founder.fmdm.service.term;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.service.term.dto.DictMainDto;

public interface TermListService {
	/**
	 * 
	 * @param dictId
	 * @param serviceId
	 * @param dictName
	 * @param status
	 * @param options
	 * @return
	 */
    public List<DictMainDto> getDictMainList1(String dictId,String serviceId,String dictName,List<String> status,SelectOptions options,List<String> tableNames);
    /**
     * 查询超级管理员admin的术语列表
     * @param dictId
     * @param serviceId
     * @param dictName
     * @param status
     * @param options
     * @param tableNames
     * @return
     */
    public List<DictMainDto> getAdminDictMainList(String dictId,String serviceId,String dictName,List<String> status,SelectOptions options);
	
    /**
     * 
     * @param dictId
     * @param serviceId
     * @param dictName
     * @param status
     * @return
     */
    public List<DictMainDto> getDictMainList(String dictId,String serviceId,String dictName,List<String> status);
    
    public List<Map<String,Object>> getDictStatus();

	public List<DictMainDto> getDictMainListByName(String dictId, String serviceId, String dictName,
			List<String> statusForshow, SelectOptions options, List<String> tableNames);
	
	public List<DictMainDto> getAdminDictMainListByName(String dictId, String serviceId, String dictName,
			List<String> statusForshow, SelectOptions options);

}
