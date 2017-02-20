package com.founder.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	private static final Object EMPTY_STRING = "";

	public static String getUUID(){
		
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 去掉空格
	 * @param value
	 * @return
	 */
	public static String trim(String value){
		if(!StringUtils.isEmpty(value)){
			return value.trim();
		}
		return value;
	}
	
	
	/**
	 * 判断一个字符串是否是空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if (value == null || EMPTY_STRING.equals(value))
			return true;
		else
			return false;
	}
}
