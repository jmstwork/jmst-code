package com.founder.component.sender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jetel.data.DataField;
import org.jetel.data.DataRecord;
import org.jetel.data.DataRecordFactory;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.exception.ConfigurationStatus;
import org.jetel.exception.XMLConfigurationException;
import org.jetel.graph.InputPort;
import org.jetel.graph.Node;
import org.jetel.graph.Result;
import org.jetel.graph.TransformationGraph;
import org.jetel.util.property.ComponentXMLAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.founder.component.utils.ResourceLoader;
import com.founder.msg.QueueManagerFactory;
import com.founder.msg.utils.CamelUtils;
import com.founder.msg.utils.DateUtils;
import com.founder.msg.utils.Message;
import com.founder.msg.utils.MessageHead;
import com.founder.msg.utils.MessageUtils;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class MQSender extends Node {

	static Logger logger = LoggerFactory.getLogger(MQSender.class);

	public static final String ENV ="ENV";
	public static final String PREFIX ="{";
	public static final String ENDFIX ="}";
	
	public static final String SETTING_CONFIG = "setting-{ENV}.properties";
	
	/**
	 * wmbQueueManage工厂实例
	 */
	private static QueueManagerFactory qmf = null;
	/**
	 * 服务器IP地址
	 */
	private String hostName;
	/**
	 * 连接通道名称
	 */
	private String channel;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 字符编码
	 */
	private int ccsid;
	/**
	 * 队列管理器名称
	 */
	private String queueManagerName;
	/**
	 * 队列名称
	 */
	private static String queueName;

	/**
	 * 消息属性
	 */
	private Map<String, Object> properties = null;

	private MessageHead msgHead = null;
	private String action = null;
	private MQQueue queue = null;

	public final static String COMPONENT_TYPE = "MQ_SENDER";
	private final static int READ_FROM_PORT = 0;

	public MQSender(String id) {
		super(id);
//		this.hostName = "10.110.0.176"; // 服务器IP地址
//		this.channel = "SYSTEM.BKR.CONFIG";// 连接通道名称
//		this.port = 1414; // 端口
//		this.ccsid = 1208;// 字符编码
//		this.queueManagerName = "WBRKQM"; // 队列管理器名称
//		this.queueName = "Q.IN"; // 队列名称
		
		this.hostName = "172.25.16.109"; // 服务器IP地址
		this.channel = "IE.SVRCONN";// 连接通道名称
		this.port = 5000; // 端口
		this.ccsid = 1208;// 字符编码
		this.queueManagerName = "GWI.QM"; // 队列管理器名称
		this.queueName="IN.MS000.LQ";
	}

	public MQSender(String id, Map<String, Object> properties,
			MessageHead msgHead, String action) {
		super(id);
		this.properties = properties;
		this.msgHead = msgHead;
		this.action = action;
	}

	@Override
	public String getType() {
		return COMPONENT_TYPE;
	}

	@Override
	protected Result execute() throws Exception {
		InputPort inPort = getInputPort(READ_FROM_PORT);
		DataRecord record = DataRecordFactory.newRecord(inPort.getMetadata());
		record.init();

		int i = 0;
		List<Object> datas = new ArrayList<Object>();
		while (record != null && runIt) {
			record = inPort.readRecord(record);
			if (record != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				Iterator<DataField> it = record.iterator();
 				while (it.hasNext()) {
					DataField data = it.next();
					String key = CamelUtils.convertToString(data.getMetadata()
							.getName());
					Object value = null;
					if("date".equalsIgnoreCase(data.getMetadata().getDataType().getName())){
						value = DateUtils.dateToString((Date) data.getValue(),DateUtils.PATTERN_COMPACT_FULL);
					}else{
						value = data.getValue();
					}
					
					map.put(key, String.valueOf((value == null ? "" : value)).trim());
				}
				datas.add(map);
				i++;
			}
			// SynchronizeUtils.cloverYield();
		}
		if (datas.size() > 0) {
			this.sendMessage(datas);
			logger.info("This message contains " + i + "records!");
		}
		return runIt ? Result.FINISHED_OK : Result.ABORTED;
	}
	
	
	@Override
	public void init() throws ComponentNotReadyException {
		String env = System.getenv(ENV);
		if (isInitialized())
			return;
		super.init();
		if(qmf ==null){
//			String setting = SETTING_CONFIG.replace(PREFIX+ENV+ENDFIX, env);
			Properties p = ResourceLoader.getResourceAsProperties("setting-sit.properties");
			hostName = (String) p.getProperty("wmqsender.hostname");
			channel = p.getProperty("wmqsender.channel");
			ccsid = Integer.valueOf(p.getProperty("wmqsender.ccsid"));
			port = Integer.valueOf(p.getProperty("wmqsender.port"));
			queueManagerName = p.getProperty("wmqsender.queueManagerName");
			queueName = p.getProperty("wmqsender.queueName");
			// 定义初始化参数
			Hashtable<String, Object> table = new Hashtable<String, Object>();
			table.put(MQConstants.HOST_NAME_PROPERTY, hostName); // 服务器IP地址
			table.put(MQConstants.CHANNEL_PROPERTY, channel); // 连接通道名称
			table.put(MQConstants.CCSID_PROPERTY, ccsid); // 字符编码，与服务器保持一致
			table.put(MQConstants.PORT_PROPERTY, port); // 监听端口名称
			try {
				qmf = QueueManagerFactory.getInstance(table);
			} catch (MQException e) {
				logger.error("QueueManageFactory init faile!",e);
				throw new ComponentNotReadyException(e);
			}
		}
	}

	private void sendMessage(List<Object> datas) throws Exception{
		Properties p = ResourceLoader.getResourceAsProperties("setting.properties");
		String lengthStr = p.getProperty("mq.msg.length");
		List<Object> subList = new ArrayList<Object>();
		
		int a = datas.size();
		int b = Integer.valueOf(lengthStr);
		int l = 1;
		
		if(a%b!=0){
			l = (a/b) + 1;
		}else{
			l = a/b;
		}
		
		for(int i =0;i<l;i++){
			subList = datas.subList(i*b, (i*b+b)>a?a:(i*b+b));
			int openOptions = MQConstants.MQOO_BIND_AS_Q_DEF
					| MQConstants.MQOO_OUTPUT;
			// 创建消息，MQMessage类包含实际消息数据的数据缓冲区和描述消息的所有MQMD参数
			MQMessage message = new MQMessage();
			message.format = MQConstants.MQFMT_STRING;// 设置数据格式
			message.characterSet = 1208;
			try {
				queue = qmf.getMQQueue(queueName, openOptions); // 队列名称
				if (this.properties != null) {
					for (String key : properties.keySet()) {
						message.setStringProperty(key, (properties.get(key) != null ? String .valueOf(properties.get(key)) : ""));
					}
				}
				
				Message msg = MessageUtils.buildMessage(msgHead != null ? msgHead
						: new MessageHead(), action, subList.toArray(new Map[0]));
				
				logger.debug("开始执行第"+i+"个,类型为："+msg.getBody().getRows().get(0).getAction());
				
				String result = MessageUtils.marshal(msg);
				message.writeString(result);
				MQPutMessageOptions pmo = new MQPutMessageOptions();
				
				pmo.options = MQConstants.MQPMO_SYNCPOINT;// 效果上看，类似数据库的事务，称为同步点（SYNCPOINT）
				queue.put(message, pmo);
				MQQueueManager manager = queue.getConnectionReference();
				manager.commit();
//				logger.info(result + " \n Send the message successfully!");
			
			}catch (MQException e) {
				if (e.reasonCode == MQConstants.MQRC_CHANNEL_NOT_AVAILABLE
						|| e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN
						|| e.reasonCode == MQConstants.MQRC_CONNECTION_ERROR
						|| e.reasonCode == MQConstants.MQRC_CONNECTION_STOPPED
						|| e.reasonCode == MQConstants.MQRC_Q_MGR_QUIESCING) {
					qmf.setReconnect();
					logger.error("队列管理器不可用，需要重新创建队列管理器!");
				} else{
					throw e;
				}				
			} catch (Exception e) {
				logger.error("发送消息异常：\r\n{}", e);
			} finally {
				System.out.println("-------------------------------------------------------");
				System.out.println(queue);
				queue.close();
			}
		}
	}

	@Override
	public void postExecute() throws ComponentNotReadyException {
		super.postExecute();
		// this.closeMQ();
	}

	@Override
	public void preExecute() throws ComponentNotReadyException {
		// super.preExecute();
	}

	public static Node fromXML(TransformationGraph graph, Element xmlElement)
			throws XMLConfigurationException {
		ComponentXMLAttributes xattribs = new ComponentXMLAttributes(
				xmlElement, graph);
		try {
			return new MQSender(xattribs.getString(XML_ID_ATTRIBUTE));
		} catch (Exception ex) {
			throw new XMLConfigurationException(COMPONENT_TYPE + ":"
					+ xattribs.getString(XML_ID_ATTRIBUTE, " unknown ID ")
					+ ":" + ex.getMessage(), ex);
		}
	}

	@Override
	public ConfigurationStatus checkConfig(ConfigurationStatus status) {
		super.checkConfig(status);
		return status;
	}

	@Override
	public synchronized void free() {
		super.free();
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getCcsid() {
		return ccsid;
	}

	public void setCcsid(int ccsid) {
		this.ccsid = ccsid;
	}

	public String getQueueManagerName() {
		return queueManagerName;
	}

	public void setQueueManagerName(String queueManagerName) {
		this.queueManagerName = queueManagerName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
