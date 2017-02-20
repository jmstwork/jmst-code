package com.founder.fmdm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.MqMessageLib;
import com.founder.fmdm.handler.AbstractMessageDataHandler;
import com.founder.fmdm.handler.TermProgressHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

@Component
public class DisruptorMessageEmitter {
	private static Logger logger = LoggerFactory
			.getLogger(DisruptorMessageEmitter.class);
	private static final int RING_SIZE = 128;

	@Autowired
	private BeanFactory beanFactory;

	private ExecutorService executor;

	private Disruptor<MessageEvent> termDisruptor;

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void init() {
		executor = Executors.newFixedThreadPool(10);
		termDisruptor = new Disruptor<MessageEvent>(MessageEvent.EVENT_FACTORY, RING_SIZE, executor, ProducerType.SINGLE, new SleepingWaitStrategy());
		
		AbstractMessageDataHandler handler = beanFactory .getBean(TermProgressHandler.class);
		termDisruptor.handleEventsWith(handler);
		termDisruptor.start();
		
	}

	public void emitEvent(final MqMessageLib event) {
		termDisruptor.publishEvent(new EventTranslator<MessageEvent>() {
			@Override
			public void translateTo(MessageEvent data, long sequence) {
				data.reset();
				data.setEvent(event);
			}
		});
	}

	public void shutdown() {
		long size = 0;
		for (;;) {
			RingBuffer<MessageEvent> rb = termDisruptor.getRingBuffer();
			size = rb.getBufferSize() - rb.remainingCapacity();
			if (size == 0)
				break;

			logger.info("Waiting for shutdown...{}", size);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
