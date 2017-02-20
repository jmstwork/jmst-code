package com.founder.fmdm.sync;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jetel.component.DBInputTable;
import org.jetel.component.DataIntersection;
import org.jetel.component.ExtSort;
import org.jetel.connection.jdbc.DBConnectionImpl;
import org.jetel.data.Defaults;
import org.jetel.data.RecordKey;
import org.jetel.data.lookup.Lookup;
import org.jetel.data.lookup.LookupTable;
import org.jetel.database.sql.DBConnection;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.exception.PolicyType;
import org.jetel.graph.Edge;
import org.jetel.graph.Node;
import org.jetel.graph.Phase;
import org.jetel.graph.Result;
import org.jetel.graph.TransformationGraph;
import org.jetel.graph.runtime.EngineInitializer;
import org.jetel.graph.runtime.GraphRuntimeContext;
import org.jetel.lookup.DBLookupTable;
import org.jetel.main.runGraph;
import org.jetel.metadata.DataFieldMetadata;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.metadata.DataRecordParsingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.component.sender.MQSender;
import com.founder.msg.utils.MessageHead;

/**
 * 术语同步处理
 * 
 * @author zdy_zdy
 * 
 */
public class TermSyncProcessor {

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(TermSyncProcessor.class);
	
	/**
	 * 引擎初始化
	 */
	static {
		if (!EngineInitializer.isInitialized()) {
			initEngine(null);
		}
	}
	/**
	 * ETL 组件目录
	 */
	private static final String PLUGINS_KEY = "cloveretl_plugins";

	/**
	 * ETL 默认目录
	 */
	private static final String PLUGINS_DEFAULT_DIR = "..";
	/**
	 * cache graphs
	 */
	private static Map<String, TransformationGraph> syncGraph = new HashMap<String, TransformationGraph>();

	/**
	 * Get Process Graph
	 * 
	 * @param key
	 * @return graph
	 */
	public static TransformationGraph getGraph(String key) {

		return syncGraph.get(key);
	}

	/**
	 * Put Process Graph
	 * 
	 * @param key
	 * @param graph
	 */
	public static void putGraph(String key, TransformationGraph graph) {
		syncGraph.put(key, graph);
	}

	/**
	 * Remove Process Graph
	 * 
	 * @param key
	 */
	public static void removeGraph(String key) {
		syncGraph.remove(key);
	}

	/**
	 * 初始化引擎
	 * 
	 * @param defaultPropertiesFile
	 */
	public static void initEngine(String defaultPropertiesFile) {
		final String pluginsDir;

		final String pluginsProperty = System.getenv(PLUGINS_KEY);
		if (pluginsProperty != null) {
			pluginsDir = pluginsProperty;
		} else {
			pluginsDir = PLUGINS_DEFAULT_DIR;
		}
		logger.info("您ETL扩展组件的目录是："+pluginsDir);
		logger.info("您的环境设定有："+System.getenv());
		logger.info("请确认您的环境中是否配置了“cloveretl_plugins”，您的plugins目录是否正确。。。。。。。。。。。。。。。");
		logger.info("===================================== Init Engine...================================================");
		EngineInitializer.initEngine(pluginsDir, defaultPropertiesFile, null);
		EngineInitializer.forceActivateAllPlugins();
		logger.info("======================================= The End...==================================================");
	}

	/**
	 * 执行同步处理过程
	 * 
	 * @param graph
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws ComponentNotReadyException
	 */
	public static Result excuteGraph(TransformationGraph graph)
			throws InterruptedException, ExecutionException,
			ComponentNotReadyException {
		logger.debug("excuteGraph start........................................");
		GraphRuntimeContext runtimeContext = new GraphRuntimeContext();
		runtimeContext.setUseJMX(false);
		Future<org.jetel.graph.Result> result = null;
		result = runGraph.executeGraph(graph, runtimeContext);
		logger.debug("excuteGraph finish! The result:"+result.get());
		return result.get();
	}

	/**
	 * Create SyncGraph
	 * 
	 * @param sourceJdbc
	 * @param targetJdbc
	 * @param sourceSql
	 * @param targetSql
	 * @param primaryKeys
	 * @throws Exception
	 */
	public static TransformationGraph createSyncGraph(Properties sourceJdbc,
			Properties targetJdbc, String sourceSql, String targetSql,
			String[] primaryKeys, Map<String, Object> properties,  MessageHead msgHead, int isMainSource) throws Exception {
		if (!EngineInitializer.isInitialized()) {
			initEngine(null);
		}
		Phase _PHASE_0 = new Phase(0);
		Phase _PHASE_1 = new Phase(1);
		Phase _PHASE_2 = new Phase(2);
		// 转换模型
		TransformationGraph graph = new TransformationGraph();
		graph.addPhase(_PHASE_0);

		// 数据库连接（hie术语系统）
		DBConnection dbConOracle = new DBConnectionImpl("TargetConn",
				targetJdbc);
		dbConOracle.setName("TargetConn");
		dbConOracle.init();
		graph.addConnection(dbConOracle);

		// 数据库连接（源术语系统）
		DBConnection dbConMSSQL = new DBConnectionImpl("SourceConn", sourceJdbc);
		dbConMSSQL.setName("SourceConn");
		dbConMSSQL.init();
		graph.addConnection(dbConMSSQL);

		/*
		 * 生成元数据模型
		 */
		// 创建target参考查询
		LookupTable lookupTableOracle = new DBLookupTable("lookupOracle",
				dbConOracle, null, ("SELECT * FROM (" + targetSql + ") A WHERE 1=2"));
		lookupTableOracle.setName("lookupOracle");
		lookupTableOracle.init();
		lookupTableOracle.preExecute();
		// 创建source参考查询
		LookupTable lookupTableMsql = new DBLookupTable("lookupTableMsql",
				dbConMSSQL, null, ("SELECT * FROM (" + sourceSql + ") B WHERE 1=2"));
		lookupTableMsql.setName("lookupTableMsql");
		lookupTableMsql.init();
		lookupTableMsql.preExecute();

		// 设置查询数据模型(默认)
		DataRecordMetadata keyMetadata = new DataRecordMetadata(
				"db_key_metadata", DataRecordParsingType.DELIMITED);
		RecordKey key = new RecordKey(keyMetadata.getFieldNamesArray(),
				keyMetadata);
		// key.init();

		// 进行查询
		Lookup lookupOracle = lookupTableOracle.createLookup(key);
		lookupOracle.seek();
		
		Lookup lookupMsql = lookupTableMsql.createLookup(key);
		lookupMsql.seek();

		// 获得查询数据模型
		DataRecordMetadata metadataInOracle = lookupOracle.getLookupTable()
				.getMetadata();
		// 时间格式设置
		for(DataFieldMetadata field:metadataInOracle.getFields()){
			if("date" == field.getDataType().getName()){
				field.setFormatStr(Defaults.DEFAULT_DATETIME_FORMAT);
			}
		}
		DataRecordMetadata metadataInMsql = lookupMsql.getLookupTable()
				.getMetadata();
		// 时间格式设置
		for(DataFieldMetadata field:metadataInMsql.getFields()){
			if("date" == field.getDataType().getName()){
				field.setFormatStr(Defaults.DEFAULT_DATETIME_FORMAT);
			}
		}

		// ####################阶段0##############################
		// 数据库读入节点
		DBInputTable dBInputTableOracle = new DBInputTable(
				"dBInputTableOracle", dbConOracle.getName(), targetSql, true);
		dBInputTableOracle.setName("dBInputTableOracle");
		dBInputTableOracle.setPolicyType(PolicyType.STRICT);
		// 创建边线(oracle输入-排序1)
		Edge inEdgeOracle = new Edge("InEdgeOracle", metadataInMsql);
		// 添加oracle输入节点
		_PHASE_0.addNode(dBInputTableOracle);
		// 连接oracle输出边线
		dBInputTableOracle.addOutputPort(0, inEdgeOracle);
		// 添加(oracle输入-排序1)边线
		graph.addEdge(inEdgeOracle);

		// 排序1节点
		Node nodeSortOracle = new ExtSort("SorterOracle",
				metadataInOracle.getFieldNamesArray(), true);
		_PHASE_0.addNode(nodeSortOracle);
		// nodeSortOracle.setName("SorterOracle");
		// 连接排序1输入边线
		nodeSortOracle.addInputPort(0, inEdgeOracle);

		// 创建边线(排序1输出)
		Edge outEdgeOracleSort = new Edge("outEdgeOracleSort", metadataInMsql);
		nodeSortOracle.addOutputPort(0, outEdgeOracleSort);
		graph.addEdge(outEdgeOracleSort);

		// 数据库读入节点（his）
		DBInputTable dBInputTableMssql = new DBInputTable("dBInputTableMssql",
				dbConMSSQL.getName(), sourceSql, true);
		dBInputTableMssql.setName("dBInputTableMssql");
		dBInputTableMssql.setPolicyType(PolicyType.STRICT);
		// dBInputTableMssql.setName("dBInputTableMssql");
		_PHASE_0.addNode(dBInputTableMssql);
		// 创建边线(his输入-排序1)
		Edge inEdgeMSSQL = new Edge("InEdgeMSSQL", metadataInMsql);
		// 设置边线
		dBInputTableMssql.addOutputPort(0, inEdgeMSSQL);
		graph.addEdge(inEdgeMSSQL);

		// 创建his排序节点1
		Node nodeSortMSSQL = new ExtSort("SorterMSSQL",
				metadataInOracle.getFieldNamesArray(), true);
		// nodeSortMSSQL.setName("SorterMSSQL");
		_PHASE_0.addNode(nodeSortMSSQL);
		nodeSortMSSQL.addInputPort(0, inEdgeMSSQL);
		// create out edge
		Edge outEdgeMSSQLSort = new Edge("outEdgeMSSQLSort", metadataInMsql);
		nodeSortMSSQL.addOutputPort(0, outEdgeMSSQLSort);
		graph.addEdge(outEdgeMSSQLSort);

		// ####################阶段1################################
		graph.addPhase(_PHASE_1);
		// 创建交叉节点
		DataIntersection dataIntersection0 = new DataIntersection("DataComp0",
				metadataInOracle.getFieldNamesArray(), new TransFormUnit());
		dataIntersection0.setName("DataComp0");
		dataIntersection0.setSlaveOverrideKey(metadataInOracle
				.getFieldNamesArray());
		_PHASE_1.addNode(dataIntersection0);
		// 设置输入端口
		dataIntersection0.addInputPort(0, outEdgeMSSQLSort);
		dataIntersection0.addInputPort(1, outEdgeOracleSort);

		// create out edge
		Edge phase2OutEdge0 = new Edge("phase2OutEdge0", metadataInMsql);
		Edge phase2OutEdge1 = new Edge("phase2OutEdge1", metadataInMsql);
		Edge phase2OutEdge2 = new Edge("phase2OutEdge2", metadataInMsql);
		// set out edge
		dataIntersection0.addOutputPort(0, phase2OutEdge0);
		dataIntersection0.addOutputPort(1, phase2OutEdge1);
		dataIntersection0.addOutputPort(2, phase2OutEdge2);
		graph.addEdge(phase2OutEdge0);
		graph.addEdge(phase2OutEdge1);
		graph.addEdge(phase2OutEdge2);

		// create sort
		Node phase2NodeSort0 = new ExtSort("phase2NodeSort0",
				metadataInOracle.getFieldNamesArray(), true);
		phase2NodeSort0.setName("phase2NodeSort0");
		_PHASE_1.addNode(phase2NodeSort0);

		Node phase2NodeSort1 = new ExtSort("phase2NodeSort1",
				metadataInOracle.getFieldNamesArray(), true);
		phase2NodeSort1.setName("phase2NodeSort1");
		_PHASE_1.addNode(phase2NodeSort1);

		Node phase2NodeSort2 = new ExtSort("phase2NodeSort2",
				metadataInOracle.getFieldNamesArray(), true);
		phase2NodeSort2.setName("phase2NodeSort2");
		_PHASE_1.addNode(phase2NodeSort2);

		// set edge from dataIntersection0 to sorts
		phase2NodeSort0.addInputPort(0, phase2OutEdge0);
		phase2NodeSort1.addInputPort(0, phase2OutEdge1);
		phase2NodeSort2.addInputPort(0, phase2OutEdge2);
		// create both file
//		Node phase2NodeWrite0 = new DataWriter("phase2DataWriter0",
//				"DICT_CHECK_ITEM.csv", "UTF-8", false);
//		phase2NodeWrite0.setName("phase2DataWriter0");
		//_PHASE_1.addNode(phase2NodeWrite0);

		// create outedge
		Edge phase2OutEdgeWriter0 = new Edge("phase2OutEdgeWriter0",
				metadataInMsql);
		Edge phase2OutEdgeWriter1 = new Edge("phase2OutEdgeWriter1",
				metadataInMsql);
		Edge phase2OutEdgeWriter2 = new Edge("phase2OutEdgeWriter2",
				metadataInMsql);
		// set outedge from sort1 to phase2DataWriter0
		phase2NodeSort1.addOutputPort(0, phase2OutEdgeWriter1);
		//phase2NodeWrite0.addInputPort(0, phase2OutEdgeWriter1);
		// set outedge from sort to dataIntersection1

		phase2NodeSort0.addOutputPort(0, phase2OutEdgeWriter0);
		phase2NodeSort2.addOutputPort(0, phase2OutEdgeWriter2);
		graph.addEdge(phase2OutEdgeWriter0);
		graph.addEdge(phase2OutEdgeWriter1);
		graph.addEdge(phase2OutEdgeWriter2);

		// ##############PHASE2######################################
		graph.addPhase(_PHASE_2);
		// create DataIntersection
		DataIntersection dataIntersection1 = new DataIntersection("DataComp1",
				primaryKeys, new TransFormUnit());
		dataIntersection1.setName("DataComp1");
		dataIntersection1.setSlaveOverrideKey(primaryKeys);
		_PHASE_2.addNode(dataIntersection1);

		// set in edge
		dataIntersection1.addInputPort(0, phase2OutEdgeWriter0);
		dataIntersection1.addInputPort(1, phase2OutEdgeWriter2);

		// create out edge
		Edge phase3OutEdge0 = new Edge("phase3OutEdge0", metadataInMsql);
		Edge phase3OutEdge1 = new Edge("phase3OutEdge1", metadataInMsql);
		Edge phase3OutEdge2 = new Edge("phase3OutEdge2", metadataInMsql);
		// set out edge
		dataIntersection1.addOutputPort(0, phase3OutEdge0);
		dataIntersection1.addOutputPort(1, phase3OutEdge1);
		dataIntersection1.addOutputPort(2, phase3OutEdge2);
		
		graph.addEdge(phase3OutEdge0);
		graph.addEdge(phase3OutEdge1);
		graph.addEdge(phase3OutEdge2);
		
		// create mqSender
		Node mqSender1 = new MQSender("mqSender1",properties,msgHead,"update");
		mqSender1.setName("mqSender1");
		_PHASE_2.addNode(mqSender1);
		mqSender1.addInputPort(0, phase3OutEdge1);
		
		if(isMainSource == 1){
			
			Node mqSender0 = new MQSender("mqSender0",properties,msgHead,"insert");
			mqSender0.setName("mqSender0");
			_PHASE_2.addNode(mqSender0);
			mqSender0.addInputPort(0, phase3OutEdge0);
			
			
			Node mqSender2 = new MQSender("mqSender2",properties,msgHead,"delete");
			mqSender2.setName("mqSender2");
			_PHASE_2.addNode(mqSender2);
			mqSender2.addInputPort(0, phase3OutEdge2);
			
		}

		return graph;
	}

	/**
	 * 执行错误做成
	 * 
	 * @param throwable
	 */
	protected static void rethrowRuntime(Throwable throwable) {

		if (throwable == null) {
			return;
		}
		if (throwable instanceof RuntimeException) {
			throw (RuntimeException) throwable;
		} else if (throwable instanceof Error) {
			throw (Error) throwable;
		} else {
			throw new RuntimeException(throwable);
		}
	}

	/*public static void main(String... args) {

		Properties p1 = new Properties();
		p1.put(DBConnection.XML_DATABASE_ATTRIBUTE, "MSSQL");
		p1.put(DBConnection.XML_PASSWORD_ATTRIBUTE, "sa.123");
		p1.put(DBConnection.XML_USER_ATTRIBUTE, "sa");
		p1.put(DBConnection.XML_JDBC_SPECIFIC_ATTRIBUTE, "MSSQL");
		p1.put(DBConnection.XML_DBURL_ATTRIBUTE,
				"jdbc:jtds:sqlserver://10.8.4.73:1433/chisdb_bjrm_mz");

		Properties p2 = new Properties();
		p2.put(DBConnection.XML_DATABASE_ATTRIBUTE, "ORACLE");
		p2.put(DBConnection.XML_PASSWORD_ATTRIBUTE, "123");
		p2.put(DBConnection.XML_USER_ATTRIBUTE, "testuser");
		p2.put(DBConnection.XML_JDBC_SPECIFIC_ATTRIBUTE, "ORACLE");
		p2.put(DBConnection.XML_DBURL_ATTRIBUTE,
				"jdbc:oracle:thin:@10.8.4.53:1521:ggfw");

//		String targetSql = "select CODE,NAME,PY_CODE"
//				+ ",DEPT_SN"
//				+ ",IN_PAGENO"
//				+ ",OUT_PAGENO"
//				+ ",BATCH_FLAG"
//				+ ",PANDIAN_PAGENO"
//				+ ",INPLAN_PAGENO"
//				+ ",REPRICE_PAGENO"
//				+ ",CAIGOUDAYS"
//				+ " from dict_medical_storageroom t where t.item_version = (select max(t1.item_version) from dict_medical_storageroom t1 where t1.code = t.code)";
//		String sourceSql = "SELECT group_no AS CODE,dept_name AS NAME,py_code AS PY_CODE"
//				+ ",dept_sn AS DEPT_SN"
//				+ ",in_pageno AS IN_PAGENO"
//				+ ",out_pageno AS OUT_PAGENO"
//				+ ",batch_flag AS BATCH_FLAG"
//				+ ",pandian_pageno AS PANDIAN_PAGENO"
//				+ ",inplan_pageno AS INPLAN_PAGENO"
//				+ ",reprice_pageno AS REPRICE_PAGENO"
//				+ ",caigoudays AS CAIGOUDAYS"
//				+ " FROM yp_group_name";

		
//		String targetSql = "select CODE,NAME,PY_CODE from DICT_CHARGE_TYPE t where t.item_version=(select max(t1.item_version) from DICT_CHARGE_TYPE t1 where t1.code=t.code)";
//		String sourceSql = "select code as CODE,name as NAME,py_code as PY_CODE from zd_charge_type";

		String targetSql = "select CODE, NAME, PY_CODE from dict_drug_mark t where t.item_version = (select max(t1.item_version) from dict_drug_mark t1 where t1.code = t.code) ";
		String sourceSql = "SELECT code AS CODE, name AS NAME, py_code AS PY_CODE FROM yp_zd_poison_class ";
		
		String[] primaryKeys = { "CODE" };
		try {
			TransformationGraph graph = TermSyncProcessor.createSyncGraph(p1, p2,
					sourceSql, targetSql, primaryKeys,null,null);

			TermSyncProcessor.excuteGraph(graph);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
}
