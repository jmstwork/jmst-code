package com.founder.fmdm.service.term;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.dao.term.TermMainDao;
import com.founder.fmdm.service.term.dto.DictMainDto;

@Service
public class TermListServiceImpl implements TermListService {

	    
    @Autowired
    private TermMainDao dictListDao;
    
	@Override
	@Transactional
	public List<DictMainDto> getDictMainList1(String dictId,String serviceId,
			String dictName, List<String> status,SelectOptions options,List<String> tableNames) {
		return dictListDao.selectDictList1(dictId,serviceId, dictName, status, options,tableNames);
	}
	
	@Override
	@Transactional
	public List<DictMainDto> getAdminDictMainList(String dictId,String serviceId,
			String dictName, List<String> status,SelectOptions options) {
		return dictListDao.selectAdminDictList(dictId,serviceId, dictName, status, options);
	}
	
	@Override
	@Transactional
	public List<DictMainDto> getDictMainList(String dictId,String serviceId,
			String dictName, List<String> status) {
		return dictListDao.selectDictList(dictId,serviceId, dictName, status);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getDictStatus() {
		return dictListDao.selectDictStatus();
	}

	@Override
	public List<DictMainDto> getDictMainListByName(String dictId, String serviceId, String dictName,
			List<String> statusForshow, SelectOptions options, List<String> tableNames) {
		return dictListDao.selectDictMainListByName(dictId,serviceId, dictName, statusForshow, options, tableNames);
	}
	
	@Override
	public List<DictMainDto> getAdminDictMainListByName(String dictId, String serviceId, String dictName,
			List<String> statusForshow, SelectOptions options) {
		return dictListDao.selectAdminDictMainListByName(dictId,serviceId, dictName, statusForshow, options);
	}

}
