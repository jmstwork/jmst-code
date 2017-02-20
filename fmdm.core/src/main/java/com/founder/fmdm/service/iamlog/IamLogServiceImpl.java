package com.founder.fmdm.service.iamlog;

import java.util.Date;
import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.iamlog.IamLogDao;
import com.founder.fmdm.entity.IamLog;
import com.founder.fmdm.service.iamlog.dto.LogDetailDto;
import com.founder.fmdm.service.iamlog.dto.LogManagerDetailDto;

@Service
public class IamLogServiceImpl implements IamLogService {

	@Autowired
	private IamLogDao iamLogDao;

	@Override
	public int saveIamLog(IamLog iamLog) {
		return iamLogDao.saveIamLog(iamLog);
	}

	public List<LogDetailDto> selectLogManager(LogManagerDetailDto logManagerDetailDto, SelectOptions options) {
		String operorId = logManagerDetailDto.getOperorId() == null ? logManagerDetailDto.getOperorId()
				: logManagerDetailDto.getOperorId().trim();
		String operModu = logManagerDetailDto.getOperModu() == null ? logManagerDetailDto.getOperModu()
				: logManagerDetailDto.getOperModu().trim();
		String operObj = logManagerDetailDto.getOperObj() == null ? logManagerDetailDto.getOperObj()
				: logManagerDetailDto.getOperObj().trim();
		Date operDt1 = logManagerDetailDto.getOperDt1();
		Date operDt2 = logManagerDetailDto.getOperDt2();
		return iamLogDao.selectLogManager(operorId, operDt1, operDt2, operModu, operObj, options);
	}
	
	
	public List<LogManagerDetailDto> selectModu(){
		return iamLogDao.selectModu();
	}
	
	
	public List<LogManagerDetailDto> selectObj(String resrCode){
		return iamLogDao.selectObj(resrCode);
	}

}
