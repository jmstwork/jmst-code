package com.founder.fmdm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.founder.core.AppSettings;
import com.founder.fmdm.MessageEvent;
import com.founder.fmdm.entity.MqMessageLib;
import com.founder.fmdm.service.term.TermProcessService;
import com.founder.msg.utils.CamelUtils;
import com.founder.xml.XPathQuery;
import com.founder.xml.XmlHelper;
import com.lmax.disruptor.dsl.Disruptor;

@Component
public class TermProgressHandler extends AbstractMessageDataHandler {
	
	private static Logger logger = LoggerFactory.getLogger("readerApp_pf");
	/**
	 * XPATH设置
	 */
	private final static String INSERT_PATH = "//row[@action='insert']";
	private final static String UPDATE_PATH = "//row[@action='update']";
	private final static String DELETE_PATH = "//row[@action='delete']";

	@Autowired
	private TermProcessService termProcessService;

	public TermProgressHandler() {
		this(null);
	}

	public TermProgressHandler(Disruptor<MessageEvent> disruptor) {

		this.setDisruptor(disruptor);
	}

	public void processEvent(final MessageEvent data, long sequence,
			boolean endOfBatch) throws Exception {
		long starttime = System.currentTimeMillis();
		MqMessageLib event = data.getEvent();
		if (event == null)
			return;
		
		// document初始化
		String text = event.getMessageDetail();
		Document doc = XmlHelper.loadXMLResource(text, XmlHelper.ENCODING_UTF8);
		XPathQuery xpath = new XPathQuery();
		xpath.init();
		
		// 服务id处理
		String msgId = xpath.getStringValue("//msgId", doc);
		String serviceId = null;
		if(msgId != null){
			try {
			    serviceId = AppSettings.getConfig(msgId);
			} catch (Exception e) {
				logger.error("未配置对应的key！");
			}
		}
		if(serviceId == null ){
			serviceId = msgId;
		}
		data.setMessageType(serviceId);
		data.setOldValue(msgId);
		// xpath查询
		NodeList insertList = xpath.getListValue(INSERT_PATH, doc);
		NodeList deleteList = xpath.getListValue(DELETE_PATH, doc);
		NodeList updateList = xpath.getListValue(UPDATE_PATH, doc);
		
		// 数据封装处理
		List<Map<String, Object>> insertData = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateData = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> deleteData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < insertList.getLength(); i++) {
			Node node = insertList.item(i);
			node = node.getFirstChild();
			Map<String, Object> map = new HashMap<String, Object>();
			for (; node != null;) {
				if (!node.getNodeName().contains("#")) {
					map.put(CamelUtils.getUnderscoreString(node.getNodeName()), node.getTextContent());
				}
				node = node.getNextSibling();
			}
			insertData.add(map);
		}
		for (int i = 0; i < deleteList.getLength(); i++) {
			Node node = deleteList.item(i);
			node = node.getFirstChild();
			Map<String, Object> map = new HashMap<String, Object>();
			for (; node != null;) {
				if (!node.getNodeName().contains("#")) {
					map.put(CamelUtils.getUnderscoreString(node.getNodeName()), node.getTextContent());
				}
				node = node.getNextSibling();
			}
			deleteData.add(map);
		}
		for (int i = 0; i < updateList.getLength(); i++) {
			Node node = updateList.item(i);
			node = node.getFirstChild();
			Map<String, Object> map = new HashMap<String, Object>();
			for (; node != null;) {
				if (!node.getNodeName().contains("#")) {
					map.put(CamelUtils.getUnderscoreString(node.getNodeName()), node.getTextContent());
				}
				node = node.getNextSibling();
			}
			updateData.add(map);
		}
		long middletime = System.currentTimeMillis();
		termProcessService.termRecieveProcess(data, insertData, updateData,
				deleteData);
		long endtime = System.currentTimeMillis();
		int totalSize = insertData.size()+updateData.size()+deleteData.size();
		
		logger.debug("{}消息({}条)处理，解析时间{}ms，处理时间{}ms，总时间{}ms", event.getServiceId(),
				totalSize, (middletime - starttime), (endtime - middletime),(endtime - starttime));

	}
}
