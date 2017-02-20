package com.founder.wmq;

import org.springframework.beans.factory.InitializingBean;

import com.founder.fmdm.Constants;
import com.founder.msg.QueueManagerFactory;

public class WMQQueueFactory extends QueueManagerFactory implements
		InitializingBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		this.setMqOpen(Boolean.parseBoolean(Constants.MQ_OPEN));
		createQueueManager();
	}
}
