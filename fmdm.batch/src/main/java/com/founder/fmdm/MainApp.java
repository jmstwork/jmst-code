package com.founder.fmdm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class MainApp 
{
	private static Logger logger = LoggerFactory.getLogger(MainApp.class);
	private static final String PROFILES_ACTIVE = "spring.profiles.active";
	
	private GenericApplicationContext ctx;
	
    public GenericApplicationContext initAppContext(String activeProfile)
    {
        // 如果要求ApplicationContext对象必须实现BeanDefinitionRegistry接口
        // 则不能直接使用ClassPathXmlApplicationContext或者FileSystemXmlApplicationContext
        ctx = new GenericApplicationContext();
        if(activeProfile != null)
        {
        	System.setProperty(PROFILES_ACTIVE, activeProfile);
	        ctx.getEnvironment().setActiveProfiles(activeProfile);
        }
        
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("META-INF/spring/core-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("META-INF/spring/cache-license.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("META-INF/spring/termSync-context.xml"));
        ctx.refresh();
        return ctx;
    }
    
    public GenericApplicationContext getApplicationContext() 
    {
		return ctx;
	}

	public static void main(String[] args)
    {
    	MainApp app = new MainApp();
    	//-Dspring.profiles.active="pro"
    	// 设置Spring的当前环境
    	String activeProfile = null;
    	if(args != null && args.length > 0)
    		activeProfile = args[0];
        final GenericApplicationContext ctx = app.initAppContext(activeProfile);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            	logger.info("开始停止......");
            	ctx.stop();
            	logger.info("正常停止。");
            }
        });
    }
}
