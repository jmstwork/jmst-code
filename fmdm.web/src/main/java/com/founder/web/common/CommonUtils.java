package com.founder.web.common;

import com.founder.core.AppSettings;


/**
 * 
 * @author wp
 *
 */
public class CommonUtils {

	public static String loadProperty(String propertyName) {
		return AppSettings.getConfig(propertyName);
	}

}
