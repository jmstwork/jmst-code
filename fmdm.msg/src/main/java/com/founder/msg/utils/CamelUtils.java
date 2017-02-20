package com.founder.msg.utils;

public class CamelUtils {
	/**
	 * 将字符串转换成驼峰命名格式
	 * 如：person_type ==> personType
	 * @param key
	 * @return
	 */
	public static String convertToString(String key) {
		key = key.toLowerCase();
		while(key.contains("_")){
			int index = key.indexOf("_");
			if(index < 0)
				return key;
			char c = key.charAt(index + 1);
			String _c = String.valueOf(c);
			key = key.replace("_" + _c.toLowerCase(), _c.toUpperCase());
		}
		return key;
	}

	/**
	 * 将驼峰命名字符串转换成下划线分隔类型
	 * 如：personType ==> person_type
	 * @param strCamel
	 * @return
	 */
	public static String getUnderscoreString(String strCamel) {
		StringBuilder sb = new StringBuilder();
		if ((strCamel != null) && (strCamel.length() > 0)) {
			sb.append(strCamel.substring(0, 1).toLowerCase());
			for (int i = 1; i < strCamel.length(); ++i) {
				char c = strCamel.charAt(i);
				if ((Character.isLetter(c)) && (Character.isUpperCase(c))) {
					sb.append("_");
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}
}
