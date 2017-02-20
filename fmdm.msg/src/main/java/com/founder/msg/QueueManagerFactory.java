package com.founder.msg;

import java.util.Hashtable;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class QueueManagerFactory {
	private final static Logger logger = LoggerFactory .getLogger(QueueManagerFactory.class);

	private MQQueueManager queueManager;

	@SuppressWarnings("rawtypes")
	private Hashtable<String, Comparable> env = new Hashtable<String, Comparable>();

	private String host;

	private String channel;

	private String ccsid;

	private String port;

	private String transport;

	private String managerName;

	private boolean reconnect = false;
	
	public boolean mqOpen =true;
	
	public MQQueueManager getQueueManager() throws MQException {

		if (this.reconnect || this.queueManager == null) {
			// 关闭之前创建的队列管理器
			attempCloseQueueManager(this.queueManager);
			// 重新连接队列管理器
			createQueueManager();
		}
		return queueManager;
	}

	public static QueueManagerFactory getInstance(Hashtable<String, ?> env)
			throws MQException {
		QueueManagerFactory mf = new QueueManagerFactory();
		mf.setHost(String.valueOf(env.get(MQConstants.HOST_NAME_PROPERTY)));
		mf.setChannel(String.valueOf(env.get(MQConstants.CHANNEL_PROPERTY)));
		mf.setCcsid(String.valueOf(env.get(MQConstants.CCSID_PROPERTY)));
		mf.setPort(String.valueOf(env.get(MQConstants.PORT_PROPERTY)));
		mf.setTransport("1");
		mf.createQueueManager();
		return mf;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCcsid() {
		return ccsid;
	}

	public void setCcsid(String ccsid) {
		this.ccsid = ccsid;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setReconnect() {
		this.reconnect = true;
	}

	protected void createQueueManager() throws MQException {
		logger.info("平台开关状态为：{}",mqOpen?"打开":"关闭");
		if(mqOpen){
			env.put(MQConstants.HOST_NAME_PROPERTY, host);
			env.put(MQConstants.CHANNEL_PROPERTY, channel);
			// 服务器MQ服务使用的编码(Coded Character Set Identifier:CCSID)
			// 1381代表GBK、1208代表UTF
			env.put(MQConstants.CCSID_PROPERTY, Integer.valueOf(ccsid).intValue());
			env.put(MQConstants.PORT_PROPERTY, Integer.valueOf(port).intValue());
			if ("1".equalsIgnoreCase(transport))
				env.put(MQConstants.TRANSPORT_PROPERTY, MQConstants.TRANSPORT_MQSERIES);
			logger.info(
					"创建新的队列管理器对象：\r\n服务器:{}\r\n通道:{}\r\n编码:{}\r\n端口:{}\r\n绑定类型:{}\r\n服务管理器:{}",
					new Object[] { host, channel, ccsid, port, transport,
							managerName });
			
			queueManager = new MQQueueManager(managerName, env);
			this.reconnect = false;
		}
	}
	public static void attempCloseQueueManager(MQQueueManager qm) {
		if (qm == null || !qm.isConnected())
			return;
		try {
			qm.disconnect();
		} catch (Throwable t) {
			logger.warn("关闭队列管理器时发生异常：{}", ExceptionUtils.getStackTrace(t));
		}
	}
	public MQQueue getMQQueue(String queueName, int openOptions)
			throws MQException {
		MQQueueManager queueManager = getQueueManager();
		return queueManager.accessQueue(queueName, openOptions);
	}

	public int getCharacterSet() throws MQException {
		return this.queueManager.getCharacterSet();
	}
	
	public void shutdown() throws MQException{
		if(queueManager!=null){
			queueManager.disconnect();
			queueManager = null;
		}
	}
	public boolean isMqOpen() {
		return mqOpen;
	}

	public void setMqOpen(boolean mqOpen) {
		this.mqOpen = mqOpen;
	}
}
