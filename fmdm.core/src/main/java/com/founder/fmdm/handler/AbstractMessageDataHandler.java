package com.founder.fmdm.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.founder.fmdm.MessageEvent;
import com.founder.fmdm.entity.MqMessageLib;
import com.founder.fmdm.service.MqRecieveService;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

public abstract class AbstractMessageDataHandler implements
		EventHandler<MessageEvent> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected MqRecieveService mqRecieveService;
	
	protected Disruptor<MessageEvent> disruptor;

	public void setDisruptor(Disruptor<MessageEvent> disruptor) {
		this.disruptor = disruptor;
	}

	public void onEvent(final MessageEvent data, long sequence,
			boolean endOfBatch) throws Exception {
		// 1.Parse message data
		logger.debug("Start parsing monitor event.");
		try {
			processEvent(data, sequence, endOfBatch);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			// 异常处理
			MqMessageLib event = data.getEvent();
			// 设置 处理状态 3（解析处理失败）
			MqMessageLib tempMsgLib = new MqMessageLib();
			tempMsgLib.setMqmsgId(event.getMqmsgId());
			tempMsgLib.setSendFlg(3);
			tempMsgLib.setErrorInfo(exception(e));
			mqRecieveService.updateMqMessageLib(tempMsgLib);
		}

		// 2.send the message data to next ring buffer
		emitEvent(data);
	}

	public abstract void processEvent(final MessageEvent data, long sequence,
			boolean endOfBatch) throws Exception;

	public void emitEvent(final MessageEvent data) {
		if (this.disruptor == null)
			return;

		disruptor.publishEvent(new EventTranslator<MessageEvent>() {

			public void translateTo(MessageEvent outData, long sequence) {
				logger.debug("publish event to next disruptor.");
				outData.reset();
				outData.copyFrom(data);
			}
		});
	}

	public void emitEvent(final MqMessageLib event) {
		if (this.disruptor == null)
			return;

		disruptor.publishEvent(new EventTranslator<MessageEvent>() {

			public void translateTo(MessageEvent outData, long sequence) {
				logger.debug("publish event to next disruptor.");
				outData.reset();
				outData.setEvent(event);
			}
		});
	}
	
	protected static String exception(Throwable t) throws IOException{
        if(t == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            t.printStackTrace(new PrintStream(baos));
        }finally{
            baos.close();
        }
        return baos.toString();
    }
}
