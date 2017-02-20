package com.founder.fmdm.service.iamlog;

import java.util.List;
import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.IamLog;
import com.founder.fmdm.service.iamlog.dto.LogDetailDto;
import com.founder.fmdm.service.iamlog.dto.LogManagerDetailDto;

public interface IamLogService {

	public int saveIamLog(IamLog iamLog);
    
    public List<LogDetailDto> selectLogManager(LogManagerDetailDto logManagerDetailDto, SelectOptions options);
    
    
    public List<LogManagerDetailDto> selectModu();
    
    
    public List<LogManagerDetailDto> selectObj(String resrCode);

}
