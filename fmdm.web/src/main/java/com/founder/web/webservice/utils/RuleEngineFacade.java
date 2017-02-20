package com.founder.web.webservice.utils;

import java.io.StringReader;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.type.FactType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

/**
 * 规则引擎
 * 
 * @author zdy_zdy
 * 
 */
public class RuleEngineFacade {
	static Logger logger = Logger.getLogger(RuleEngineFacade.class);
	/**
	 * 规则库(引擎)对象
	 */
	private static KnowledgeBase knowlegeBase;
	/**
	 * 规则库(引擎)状态
	 */
	private static boolean status = false;
	/**
	 * 同步锁key
	 */
	private static String lock = "lock";

	/**
	 * 重新加载规则
	 * 
	 * @param drl
	 *            drl规则
	 */
	public static boolean reload(String drl) {
		synchronized (lock) {
			status = false;
			try {
				knowlegeBase = createKnowledgeBase(drl);
				status = true;
			} catch (Exception e) {
				logger.error("RuleEngneer reload failed.", e);
				return false;
			}

		}
		return true;
	}

	/**
	 * 启动引擎
	 * 
	 * @param drl
	 *            drl规则
	 */
	public static boolean startUp(String drl) {
		synchronized (lock) {
			try {
				knowlegeBase = createKnowledgeBase(drl);
			} catch (Exception e) {
				logger.error("RuleEngneer startUp failed.", e);
				return false;
			}
			status = true;
		}
		return true;
	}
	/**
	 *  取得事实对象
	 *  @param pkg 事实类型所在包
	 *  @param factName 事实名称 
	 * @return
	 */
	public static FactType getFactType( String pkg ,String factName) {
		if (status) {
			return knowlegeBase.getFactType(pkg, factName);
		}
		return null;
	}

	/**
	 * 判断引擎状态
	 * 
	 * @return
	 */
	public static boolean isRun() {
		return status;

	}

	private RuleEngineFacade() {
	}

	public static StatefulKnowledgeSession getStatefulKnowledgeSession()
			throws Exception {
		if (status) {
			return knowlegeBase.newStatefulKnowledgeSession();
		}
		/*
		 * else { Thread.sleep(1000); getStatefulKnowledgeSession(); }
		 */
		return null;

	}

	public static StatelessKnowledgeSession getStatelessKnowledgeSession()
			throws Exception {
		if (status) {
			return knowlegeBase.newStatelessKnowledgeSession();
		}
		/*
		 * else { Thread.sleep(1000); getStatelessKnowledgeSession(); }
		 */
		return null;
	}

	private static KnowledgeBase createKnowledgeBase(String drl)
			throws Exception {
		logger.info("RuleEngneer load start.");
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		logger.debug("load drlRule: \n" + drl);
		kbuilder.add(ResourceFactory.newReaderResource(new StringReader(drl)),
				ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				logger.error("source code" + error);
			}
			//logger.info("RuleEngneer load fail.");
			//throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		logger.info("RuleEngneer load success.");
		return kbase;
	}

	// test
	public static void main(String... args) throws Exception {
		// startUp("");
		reload("package test\n no-loop true\ndeclare Album\n genre: String \n end\nrule1 \"rule\"\nwhen Album() \n then \nAlbum a = new Album(); \n end");
		getStatefulKnowledgeSession();
		getStatelessKnowledgeSession();
	}
}
