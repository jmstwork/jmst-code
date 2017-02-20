package com.founder.fmdm.service.term;

import java.util.List;
import java.util.Map;

import com.founder.fmdm.MessageEvent;


public interface TermProcessService {

	/**
	 * 接收第三方术语信息的处理
	 * 
	 * @param ServiceId
	 * @param insertData
	 * @param updateData
	 * @param deleteData
	 * @return
	 */
	public int termRecieveProcess(MessageEvent data,
			List<Map<String, Object>> insertData,
			List<Map<String, Object>> updateData,
			List<Map<String, Object>> deleteData) throws Exception;

}
