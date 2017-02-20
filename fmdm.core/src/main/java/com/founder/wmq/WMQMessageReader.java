package com.founder.wmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.founder.core.AppSettings;
import com.founder.util.ExceptionUtils;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.MQHeaderIterator;
import com.ibm.mq.headers.MQRFH2;
import com.ibm.mq.headers.MQRFH2.Element;

public class WMQMessageReader {
	protected static Logger logger = LoggerFactory
			.getLogger(WMQMessageReader.class);

	private static final Integer WAIT_INTERVAL = Integer.valueOf(AppSettings
			.getConfig("WMQ.Reader.WaitInterval"));

	private MQGetMessageOptions gmo;

	private WMQQueueFactory queueFactory;

	private String queueName;

	protected final Object lifecycleMonitor = new Object();
	private boolean cacheQueue = true;
	private boolean isBreak = false;
	private boolean isRunning = false;

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isBreak() {
		synchronized (this.lifecycleMonitor) {
			return isBreak;
		}
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	private MQQueue queue = null;

	public WMQMessageReader() {
		initGetOption();
	}

	/**
	 * 从MQ接收一条消息，队列为空的情况下无限期等待。
	 * 
	 * @return
	 * @throws Exception
	 */
	public MQMessage receiveRawMessage() throws MQException {
		MQMessage message = null;
		while (!isBreak()) {
			try {
				synchronized (this.lifecycleMonitor) {
					isRunning = true;
					int openOptions = MQConstants.MQOO_OUTPUT
							| MQConstants.MQOO_FAIL_IF_QUIESCING;
					openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
							| MQConstants.MQOO_INQUIRE;
					message = new MQMessage();
					if (!isCacheQueue() || this.queue == null)
						queue = queueFactory.getMQQueue(queueName, openOptions);
					message.characterSet = queueFactory.getCharacterSet();
					logger.debug("开始获取下一条消息。。。。。。");
					// 从队列中取出消息
					gmo.waitInterval = WAIT_INTERVAL;
					queue.get(message, gmo);
					logger.debug("成功获取消息");
				}

			} catch (MQException e) {
				if (e.completionCode == MQConstants.MQCC_FAILED
						&& e.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
					logger.debug("检测接收状态（是否停止）：{}", isBreak);
					if (isBreak()) {
						return null;
					}
					continue;
				}
				// wubiao BUG14649 test
				else if (e.reasonCode == MQConstants.MQRC_CHANNEL_NOT_AVAILABLE
						|| e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN
						|| e.reasonCode == MQConstants.MQRC_CONNECTION_ERROR
						|| e.reasonCode == MQConstants.MQRC_CONNECTION_STOPPED
						|| e.reasonCode == MQConstants.MQRC_Q_MGR_QUIESCING
						|| e.reasonCode == MQConstants.MQRC_GET_INHIBITED) {
					queueFactory.setReconnect();
					this.queue = null;
					logger.error("队列管理器不可用，需要重新创建队列管理器! 原因：{}", e.reasonCode);
				} else
					throw e;
			} finally {
				isRunning = false;
				if (!isCacheQueue()) {
					queue.close();
					this.queue = null;
				}
			}
			return message;
		}
		return message;
	}

	public Message<String> receive() throws Exception {
		MQMessage message = receiveRawMessage();
		return constructMessage(message);
	}

	/**
	 * 根据WMQ的消息构建SpringIntegration所使用的Message对象 参考
	 * http://publib.boulder.ibm.com/
	 * infocenter/wmqv7/v7r0/index.jsp?topic=%2Fcom
	 * .ibm.mq.csqzak.doc%2Ffr14000_.htm
	 * http://publib.boulder.ibm.com/infocenter
	 * /wmqv7/v7r0/index.jsp?topic=%2Fcom.ibm.mq.csqzak.doc%2Ffr14000_.htm
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private Message<String> constructMessage(MQMessage message)
			throws Exception {
		if (message == null) {
			return null;
		}
		Map<String, String> header = extractHeader(message);

		message.seek(0);
		MQHeaderIterator hit = new MQHeaderIterator(message);
		String msgText = hit.getBodyAsText();
		MessageBuilder<String> result = MessageBuilder.withPayload(msgText);
		result.copyHeaders(header);

		return result.build();
	}

	private Map<String, String> extractHeader(MQMessage message) {
		Map<String, String> header = new HashMap<String, String>();

		MQRFH2 rfh2 = null;
		try {
			rfh2 = new MQRFH2(message);
			Element e = rfh2.getFolder("usr", false);
			if (e != null) {
				Element[] elements = e.getChildren();
				for (Element ei : elements) {
					String name = ei.getName();
					if (acceptHeader(name)) {
						String value = ei.getStringValue();
						header.put(name, value);
					} else
						continue;
				}
			}
		} catch (MQDataException e1) {
			if (e1.completionCode == MQConstants.MQCC_FAILED
					&& e1.reasonCode == MQConstants.MQRC_INSUFFICIENT_DATA) // MQConstants.MQRC_PROPERTY_NOT_AVAILABLE)
				logger.warn("No header data availiable.");
			else
				// e1.printStackTrace();
				logger.error("MQ异常：\r\n{}", e1);
		} catch (IOException e1) {
			// e1.printStackTrace();
			logger.error("接收消息时IO异常：\r\n{}", e1);
		}

		return header;
	}

	private boolean acceptHeader(String name) {
		return true;
	}

	/**
	 * 设置获取消息的选项
	 */
	private void initGetOption() {
		// 设置取出消息的属性（默认属性）
		// Set the put message options.（设置放置消息选项）
		gmo = new MQGetMessageOptions();
		// Get messages under sync point control
		// 在同步点控制下获取消息

		// Wait if no messages on the Queue
		// 如果在队列上没有消息则等待
		gmo.options = gmo.options | MQConstants.MQGMO_WAIT;
		gmo.options = gmo.options | MQConstants.MQGMO_PROPERTIES_COMPATIBILITY;

		// Fail if QueueManager Quiescing
		// 如果队列管理器停顿则失败
		gmo.options = gmo.options | MQConstants.MQGMO_FAIL_IF_QUIESCING;

		// Sets the time limit for the wait.
		// 设置等待的毫秒时间限制
		gmo.waitInterval = MQConstants.MQWI_UNLIMITED;
		// gmo.matchOptions = MQConstants.MQMO_MATCH_MSG_ID;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public boolean isCacheQueue() {
		return cacheQueue;
	}

	public void setCacheQueue(boolean cacheQueue) {
		this.cacheQueue = cacheQueue;
	}

	public void setQueueFactory(WMQQueueFactory queueFactory) {
		this.queueFactory = queueFactory;
	}

	public void shutdown() throws MQException {
		synchronized (this.lifecycleMonitor) {
			setBreak(true);
			while (isRunning) {
				if (queue != null) {
					queue.close();
					queue = null;
					return;
				} else {
					return;
				}
			}
			// queueFactory.shutdown();
		}
	}

	public void readerCommite() throws MQException {
		if (queue != null && isCacheQueue()) {
			MQQueueManager manager = queue.getConnectionReference();
			manager.commit();
		} else {
			queueFactory.getQueueManager().commit();
		}
	}
}
