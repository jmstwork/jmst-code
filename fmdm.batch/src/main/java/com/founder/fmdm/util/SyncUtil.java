package com.founder.fmdm.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SyncUtil.class);
	
	
	public static String  getDateDiffMM(Date d1, Date d2){
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		//long ns = 1000;//一秒钟的毫秒数
		
		long diff = d2.getTime() - d1.getTime();
		long min = diff%nd%nh/nm;//计算差多少分钟
		return String.valueOf(min);
	}
	
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.debug("==========================获取服务器IP报错啦=============================");
			logger.debug("UnknownHostException:"+e.getMessage());
			logger.debug("==========================获取服务器IP报错啦=============================");
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}
}