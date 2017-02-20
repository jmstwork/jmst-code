package com.founder.wmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.util.StringUtils;

import com.founder.fmdm.Constants;
import com.founder.util.ExceptionUtils;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPropertyDescriptor;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;

public class WMQMessageSender {
	protected static Logger logger = LoggerFactory
			.getLogger(WMQMessageSender.class);

	private int openOptions;

	private MQPutMessageOptions pmo;

	private WMQQueueFactory queueFactory;

	public WMQMessageSender() {
		initQueueOption();
	}

	private void initQueueOption() {
		// Set the put message options.（设置放置消息选项）
		pmo = new MQPutMessageOptions();
		// Put messages under sync point control
		// 在同步点控制下发送消息
		pmo.options = pmo.options + MQConstants.MQGMO_SYNCPOINT;

		// Fail if QueueManager Quiescing
		// 如果队列管理器停顿则失败
		pmo.options = pmo.options + MQConstants.MQGMO_FAIL_IF_QUIESCING;

		openOptions = MQConstants.MQOO_BIND_AS_Q_DEF;
		openOptions = openOptions + MQConstants.MQOO_OUTPUT;
		openOptions = openOptions + MQConstants.MQPMO_PASS_ALL_CONTEXT;
	}

	public void sendMessage(Message<String> message, String queueName)
			throws Exception {
		MQQueue queue = null;
		MQMessage msg = new MQMessage();
		msg.format = MQConstants.MQFMT_STRING;
		MQPropertyDescriptor descriptor = new MQPropertyDescriptor();
		descriptor.context = CMQC.MQPD_USER_CONTEXT;
		descriptor.support = CMQC.MQPD_SUPPORT_REQUIRED;
		if (Constants.MSG_HEADER_TYPE_BDRM.equals(Constants.MSG_HEADER_TYPE)) {//7个消息头
			// 设置消息头信息
			msg.setStringProperty(Constants.HEADER_DOMAIN_ID, (String) message.getHeaders()
					.get(Constants.HEADER_DOMAIN_ID));
			msg.setStringProperty(Constants.HEADER_SERVICE_ID, (String) message.getHeaders()
					.get(Constants.HEADER_SERVICE_ID));
			msg.setStringProperty(Constants.HEADER_APPLY_UNIT_ID, (String) message.getHeaders()
					.get(Constants.HEADER_APPLY_UNIT_ID));
			msg.setStringProperty(Constants.HEADER_EXEC_UNIT, (String) message.getHeaders()
					.get(Constants.HEADER_EXEC_UNIT));
			msg.setStringProperty(Constants.HEADER_HOSPITAL_ID, (String) message.getHeaders()
					.get(Constants.HEADER_HOSPITAL_ID));
			msg.setStringProperty(Constants.HEADER_SEND_SYS_ID, (String) message.getHeaders()
					.get(Constants.HEADER_SEND_SYS_ID));
			msg.setStringProperty(Constants.HEADER_EXTEND_SUB_ID, (String) message.getHeaders()
					.get(Constants.HEADER_EXTEND_SUB_ID));
			logger.debug("apply_unit_id: "+msg.getStringProperty(Constants.HEADER_APPLY_UNIT_ID));
			logger.debug("hospital_id: "+msg.getStringProperty(Constants.HEADER_HOSPITAL_ID));
			logger.debug("send_sys_id: "+msg.getStringProperty(Constants.HEADER_SEND_SYS_ID));
			logger.debug("extend_sub_id: "+msg.getStringProperty(Constants.HEADER_EXTEND_SUB_ID));
		}else if (Constants.MSG_HEADER_TYPE_JSRM.equals(Constants.MSG_HEADER_TYPE)) {//8个消息头
			// 设置消息头信息
			msg.setStringProperty(Constants.HEADER_DOMAIN_ID, (String) message.getHeaders()
					.get(Constants.HEADER_DOMAIN_ID));
			msg.setStringProperty(Constants.HEADER_SERVICE_ID, (String) message.getHeaders()
					.get(Constants.HEADER_SERVICE_ID));
			msg.setStringProperty(Constants.HEADER_APPLY_UNIT_ID, (String) message.getHeaders()
					.get(Constants.HEADER_APPLY_UNIT_ID));
			msg.setStringProperty(Constants.HEADER_EXEC_UNIT, (String) message.getHeaders()
					.get(Constants.HEADER_EXEC_UNIT));
			msg.setStringProperty(Constants.HEADER_HOSPITAL_ID, (String) message.getHeaders()
					.get(Constants.HEADER_HOSPITAL_ID));
			msg.setStringProperty(Constants.HEADER_SEND_SYS_ID, (String) message.getHeaders()
					.get(Constants.HEADER_SEND_SYS_ID));
		    //$Author :LSG
		    //$Date : 2014/10/16 14:27$
		    //[BUG]0049708 MODIFY BEGIN 
			//消息头添加医嘱执行分类编码
			msg.setStringProperty(Constants.ORDER_EXEC_ID, (String) message.getHeaders()
					.get(Constants.ORDER_EXEC_ID));
			//[BUG]0049708 MODIFY End
			msg.setStringProperty(Constants.HEADER_EXTEND_SUB_ID, (String) message.getHeaders()
					.get(Constants.HEADER_EXTEND_SUB_ID));
			logger.debug("apply_unit_id: "+msg.getStringProperty(Constants.HEADER_APPLY_UNIT_ID));
			logger.debug("hospital_id: "+msg.getStringProperty(Constants.HEADER_HOSPITAL_ID));
			logger.debug("send_sys_id: "+msg.getStringProperty(Constants.HEADER_SEND_SYS_ID));
			logger.debug("order_exec_id: "+msg.getStringProperty(Constants.ORDER_EXEC_ID));
			logger.debug("extend_sub_id: "+msg.getStringProperty(Constants.HEADER_EXTEND_SUB_ID));
		}else if(Constants.MSG_HEADER_TYPE_PKURM.equals(Constants.MSG_HEADER_TYPE)){//4个消息头
			msg.setStringProperty(Constants.HEADER_DOMAIN_ID, (String) message.getHeaders()
					.get(Constants.HEADER_DOMAIN_ID));
			msg.setStringProperty(Constants.HEADER_SERVICE_ID, (String) message.getHeaders()
					.get(Constants.HEADER_SERVICE_ID));
			msg.setStringProperty(Constants.HEADER_EXEC_UNIT, (String) message.getHeaders()
					.get(Constants.HEADER_EXEC_UNIT));
			msg.setStringProperty(Constants.ORDER_DISPATCH_TYPE_ID, (String) message.getHeaders()
					.get(Constants.ORDER_DISPATCH_TYPE_ID));
			logger.debug("order_dispatch_type_code: "+msg.getStringProperty(Constants.ORDER_DISPATCH_TYPE_ID));
		}
		logger.debug("domain_id: "+msg.getStringProperty(Constants.HEADER_DOMAIN_ID));
		logger.debug("service_id: "+msg.getStringProperty(Constants.HEADER_SERVICE_ID));
		logger.debug("exec_unit_id: "+msg.getStringProperty(Constants.HEADER_EXEC_UNIT));
		if(!StringUtils.isEmpty(message.getHeaders().get(Constants.replyToQueueManagerName))){
			msg.replyToQueueManagerName = (String) message.getHeaders().get(Constants.replyToQueueManagerName);
		}
		
		if(!StringUtils.isEmpty(message.getHeaders().get(Constants.replyToQueueName))){
			msg.replyToQueueName = (String) message.getHeaders().get(Constants.replyToQueueName);
		}
		logger.debug(message.getPayload().getBytes("UTF-8").toString());
		msg.write(message.getPayload().getBytes("UTF-8"));
		try {
			queue = queueFactory.getMQQueue(queueName, openOptions);
			queue.put(msg, pmo);
			MQQueueManager manager = queue.getConnectionReference();
			manager.commit();
			logger.debug("消息发送成功！");
		} catch (MQException e) {
			if (e.reasonCode == MQConstants.MQRC_CHANNEL_NOT_AVAILABLE
					|| e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN
					|| e.reasonCode == MQConstants.MQRC_CONNECTION_ERROR
					|| e.reasonCode == MQConstants.MQRC_CONNECTION_STOPPED
					|| e.reasonCode == MQConstants.MQRC_Q_MGR_QUIESCING) {
				queueFactory.setReconnect();
				logger.error("队列管理器不可用，需要重新创建队列管理器!");
			}
			throw e;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("发送消息{}异常：\r\n{}", message.getHeaders().getId()
					.toString(), ExceptionUtils.getStackTrace(e));
		} finally {
			if (queue != null) {
				queue.close();
			}
		}
	}

	public WMQQueueFactory getQueueFactory() {
		return queueFactory;
	}

	public void setQueueFactory(WMQQueueFactory queueFactory) {
		this.queueFactory = queueFactory;
	}
}
