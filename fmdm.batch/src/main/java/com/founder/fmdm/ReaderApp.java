package com.founder.fmdm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.founder.fmdm.MqMessageListener;

public class ReaderApp {
	private static Logger logger = LoggerFactory.getLogger(ReaderApp.class);
	private static final String PROFILES_ACTIVE = "spring.profiles.active";

	private GenericApplicationContext ctx;

	public GenericApplicationContext initAppContext(String activeProfile) {
		// 如果要求ApplicationContext对象必须实现BeanDefinitionRegistry接口
		// 则不能直接使用ClassPathXmlApplicationContext或者FileSystemXmlApplicationContext
		ctx = new GenericApplicationContext();
		if (activeProfile != null) {
			System.setProperty(PROFILES_ACTIVE, activeProfile);
			ctx.getEnvironment().setActiveProfiles(activeProfile);
		}

		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		xmlReader.loadBeanDefinitions(new ClassPathResource(
				"META-INF/spring/core-context.xml"));
		xmlReader.loadBeanDefinitions(new ClassPathResource(
				"META-INF/spring/cache-license.xml"));
		xmlReader.loadBeanDefinitions(new ClassPathResource(
				"META-INF/spring/termReader-context.xml"));
		ctx.refresh();
		return ctx;
	}

	public GenericApplicationContext getApplicationContext() {
		return ctx;
	}

	public static void main(String[] args) throws InterruptedException {
		ReaderApp app = new ReaderApp();
		// -Dspring.profiles.active="pro"
		// 设置Spring的当前环境
		String activeProfile = null;
		if (args != null && args.length > 0)
			activeProfile = args[0];
		final GenericApplicationContext ctx = app.initAppContext(activeProfile);
		final MqMessageListener listener = (MqMessageListener) ctx
				.getBean(MqMessageListener.class);
		listener.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info("开始停止......");
				listener.destroy();
				ctx.stop();
				logger.info("正常停止。");
			}
		});
	}
}
