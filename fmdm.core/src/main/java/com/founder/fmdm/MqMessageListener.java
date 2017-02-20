package com.founder.fmdm;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

import com.founder.fmdm.entity.MqMessageLib;
import com.founder.fmdm.service.MqRecieveService;
import com.founder.fmdm.service.audit.AuditMsgUpdateDataService;
import com.founder.util.StringUtil;
import com.founder.wmq.WMQMessageReader;
import com.ibm.mq.MQException;

public class MqMessageListener extends Thread {
	private static Logger logger = LoggerFactory
			.getLogger(MqMessageListener.class);

	@Autowired
	private DisruptorMessageEmitter emitter;
	
	private int count = 0;

	private boolean isBreak = false;

	private boolean isRunning = true;

	@Autowired
	private MqRecieveService mqRecieveService;

	private WMQMessageReader WMQMessageReader;
	
	@Autowired
    AuditMsgUpdateDataService auditMsgUpdateDataService;
	
	/**
	 * 应用启动加载未处理消息
	 */
	@PostConstruct
	public void init() {
		List<MqMessageLib> list = mqRecieveService
				.selectBySendFlg(Constants.FLAG_INITIAL);
		for (MqMessageLib mqMsg : list) {
			// 消息处理事件
			emitter.emitEvent(mqMsg);
		}
	}

	public WMQMessageReader getWMQMessageReader() {
		return WMQMessageReader;
	}

	public void setWMQMessageReader(WMQMessageReader wMQMessageReader) {
		WMQMessageReader = wMQMessageReader;
	}

	public void run() {
		while (!isBreak) {
			try {
				isRunning = true;
				this.count++;
				Message<?> message = WMQMessageReader.receive();
				if (message == null) {
					continue;
				}
				Object body = message.getPayload();
				String text = "";
				if (body instanceof byte[]) {
					try {
						text = new String((byte[]) body, "UTF-8");
					} catch (Exception e) {
						logger.error("MqMessage Recieve Error", e);
					}
				} else if (body instanceof String) {
					text = (String) body;
				}
				// 接收消息保存
				MqMessageLib mqMsg = new MqMessageLib();
				mqMsg.setMessageDetail(text);
				mqMsg.setArriveTime(new Date());
				mqMsg.setSendFlg(Constants.FLAG_INITIAL);
				extractHeader(message, mqMsg);
				if(mqMsg.getServiceId().indexOf("BS907") > -1){
					auditMsgUpdateDataService.addDataThroughMQMsg(mqMsg.getMessageDetail());
				}else{
				    mqMsg.setMqmsgId(StringUtil.getUUID());
				    mqRecieveService.insertMqMessageLib(mqMsg);
				    WMQMessageReader.readerCommite();
				    // 消息处理
				    emitter.emitEvent(mqMsg);
				}
				/*
				 * 审计事件BS907消息接入
				 * cao_rui
				 * 20160519
				 * */
//				if(mqMsg.getServiceId().indexOf("BS907") > -1){
//					auditMsgUpdateDataService.addDataThroughMQMsg(mqMsg.getMessageDetail());
//				}
				/*
				 * end
				 * */
			} catch (Exception e) {
				logger.error("接收处理消息异常：", e);
			} finally {
				this.isRunning = false;
			}
		}
	}

	private void extractHeader(Message<?> message, MqMessageLib mqMsg) {
		MessageHeaders headers = message.getHeaders();
		mqMsg.setDomainId(String.valueOf(headers
				.get(Constants.HEADER_DOMAIN_ID)));
		mqMsg.setServiceId(String.valueOf(headers
				.get(Constants.HEADER_SERVICE_ID)));
//		mqMsg.setOrderdispatchtypeCode(String.valueOf(headers
//				.get(Constants.HEADER_ORDER_DISPATCH_TYPE)));
		mqMsg.setExecunitId(String.valueOf(headers
				.get(Constants.HEADER_EXEC_UNIT)));
		logger.debug("Put Timestamp : {} {}",
				headers.get("JMS_IBM_PutDate", String.class),
				headers.get("JMS_IBM_PutTime", String.class));
	}

	@PreDestroy
	public void destroy() {
		logger.info("Stopping Monitor Event Listener...");
		setBreak(true);
		try {
			WMQMessageReader.shutdown();
		} catch (MQException e) {
			logger.error("Stopping WMQMessageReader falure!", e);
			Thread.currentThread().interrupt();
		}
		while (isRunning) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		emitter.shutdown();
		logger.info("Stop Monitor Event Listener finished.");
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
