package com.founder.core.log;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.founder.fmdm.entity.IamLog;
import com.founder.fmdm.service.iamlog.IamLogService;

@Component
public class LogUtils {

	@Autowired
	IamLogService iamLogService;

	public void insertLog(String modelName, String operateName, String operateDescribe, Object... objects) {		
		if (StringUtils.isEmpty(modelName) || StringUtils.isEmpty(operateName)
				|| StringUtils.isEmpty(operateDescribe)) {
			return;
		}
		int i = 0;
		while (operateDescribe.indexOf("{}") > -1 && null != objects) {
			if (objects.length == i) {
				break;
			}
			operateDescribe = operateDescribe.substring(0, operateDescribe.indexOf("{}")) + objects[i].toString()
					+ operateDescribe.substring(operateDescribe.indexOf("{}") + 2, operateDescribe.length());
			i++;
		}

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		IamLog iLog = new IamLog();
		iLog.setLogId(String.valueOf(UUID.randomUUID()));
		iLog.setCreateBy(userName);
		iLog.setCreateTime(new Date());
		iLog.setOperorId(userName);
		iLog.setOperDt(new Date());
		iLog.setUpdateCount(0);
		iLog.setOperCont(operateDescribe);
		iLog.setOperModu(modelName);
		iLog.setOperObj(operateName);

		iamLogService.saveIamLog(iLog);
	}
	
	/**
	 * 
	 * @param logs 多条日志的集合
	 * @return 融合后的一条长日志，以@分割每条日志
	 */
	public String mergeLog(List<String> logs){
		StringBuffer sfb = new StringBuffer();
		for(String log: logs){
			sfb.append(log).append("@");
		}
		return sfb.toString();
	}
}
