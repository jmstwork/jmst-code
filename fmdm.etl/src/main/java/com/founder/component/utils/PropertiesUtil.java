package com.founder.component.utils;

import java.util.Properties;

public class PropertiesUtil {

	
	public static void main(String[] args) {
		Properties p = ResourceLoader.getResourceAsProperties("setting.properties");
		System.out.println(p.get("mq.hostName"));
		System.out.println(p.get("mq.channel"));
		System.out.println(p.get("mq.ccsid"));
		System.out.println(p.get("mq.port"));
	}
}
