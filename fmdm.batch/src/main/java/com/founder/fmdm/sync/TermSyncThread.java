package com.founder.fmdm.sync;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jetel.graph.Result;
import org.jetel.graph.TransformationGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.fmdm.Constants;
import com.founder.fmdm.entity.SyncLog;
import com.founder.fmdm.entity.TermSync;
import com.founder.fmdm.service.term.TermSyncService;
import com.founder.fmdm.util.SyncUtil;
import com.founder.msg.utils.MessageHead;
import com.founder.util.StringUtil;

public class TermSyncThread implements Runnable {

	private Logger logger = LoggerFactory.getLogger(TermSyncThread.class);

	TermSyncService syncTermService;
	TermSync term;
	Properties sourcePro;
	Properties targetPro;
	int isMainSource = 1;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	public TermSyncThread() {
	}

	public TermSyncThread(TermSyncService syncTermService, TermSync term, Properties sourcePro, Properties targetPro,int isMainSource) {
		this.syncTermService = syncTermService;
		this.term = term;
		this.sourcePro = sourcePro;
		this.targetPro = targetPro;
		this.isMainSource = isMainSource;
	}

	@Override
	public void run() {
		logger.debug("===============================加速执行中。。。====================================");
		SyncLog entity = new SyncLog();
		String txt = null;
		TermSync t = new TermSync();
		TermSync t1 = new TermSync();
		try {
			// 创建同步日志
			// 变更术语同步状态
			t.setSyncId(term.getSyncId());
			t.setDictId(term.getDictId());
			entity.setStartTime(new Date());
			t.setStartTime(new Date());
			t.setSyncStatus(1);
			t.setUpdateCount(term.getUpdateCount());
			syncTermService.updateSyncTermStatus(t);
			
			//设置消息头属性
			logger.debug("set properties of message!");
			Map<String, Object> msgProperties = new HashMap<String, Object>();
/*			msgProperties.put("service_id", term.getDictId());
			msgProperties.put("domain_id", 0);
			msgProperties.put("order_dispatch_type_code", 0);
			msgProperties.put("exec_unit_id", 0);*/
		    //$Author :LSG
		    //$Date : 2014/10/16 14:27$
		    //[BUG]0049708 MODIFY BEGIN 
			// 消息头设置
			if (Constants.MSG_HEADER_TYPE_BDRM.equals(Constants.MSG_HEADER_TYPE)) {//7个消息头
				msgProperties.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
				msgProperties.put(Constants.HEADER_SERVICE_ID, term.getDictId());
				msgProperties.put(Constants.HEADER_APPLY_UNIT_ID, Constants.HEADER_APPLY_UNIT_ID_VALUE);
				msgProperties.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
				msgProperties.put(Constants.HEADER_HOSPITAL_ID, Constants.HEADER_HOSPITAL_ID_VALUE);
				msgProperties.put(Constants.HEADER_SEND_SYS_ID, Constants.HEADER_SEND_SYS_ID_VALUE);
				msgProperties.put(Constants.HEADER_EXTEND_SUB_ID, Constants.HEADER_EXTEND_SUB_ID_VALUE);
			}else if(Constants.MSG_HEADER_TYPE_JSRM.equals(Constants.MSG_HEADER_TYPE)) {//8个消息头
				msgProperties.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
				msgProperties.put(Constants.HEADER_SERVICE_ID, term.getDictId());
				msgProperties.put(Constants.HEADER_APPLY_UNIT_ID, Constants.HEADER_APPLY_UNIT_ID_VALUE);
				msgProperties.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
				msgProperties.put(Constants.HEADER_HOSPITAL_ID, Constants.HEADER_HOSPITAL_ID_VALUE);
				msgProperties.put(Constants.HEADER_SEND_SYS_ID, Constants.HEADER_SEND_SYS_ID_VALUE);
				// 消息头添加医嘱执行分类编码
				msgProperties.put(Constants.ORDER_EXEC_ID, Constants.ORDER_EXEC_ID_VALUE);
				
				msgProperties.put(Constants.HEADER_EXTEND_SUB_ID, Constants.HEADER_EXTEND_SUB_ID_VALUE);
			}else if(Constants.MSG_HEADER_TYPE_PKURM.equals(Constants.MSG_HEADER_TYPE)){//4个消息头
				msgProperties.put(Constants.HEADER_DOMAIN_ID, Constants.HEADER_DOMAIN_ID_VALUE);
				msgProperties.put(Constants.HEADER_SERVICE_ID, term.getDictId());
				msgProperties.put(Constants.HEADER_EXEC_UNIT, Constants.HEADER_EXEC_UNIT_VALUE);
				msgProperties.put(Constants.ORDER_DISPATCH_TYPE_ID, Constants.ORDER_DISPATCH_TYPE_CODE);
			}
			//[BUG]0049708 MODIFY End
			
			
			logger.debug("set head node of message!");
	        MessageHead msgHead = new MessageHead();
	        msgHead.setMsgId(term.getDictId());
	        msgHead.setMsgName(term.getDictName());
	        msgHead.setSourceSysCode(syncTermService.selectDatasourceSetById(term.getDatasourceId()).getSystemId());
	        msgHead.setTargetSysCode("S028");
	        msgHead.setVersion(Long.parseLong(dateFormat.format(new Date())));
	     
			List<String > keyList = syncTermService.selectSyncJoinKeys(term.getDictId());
			logger.debug("get joinKeys:"+keyList.toString());
			String[] joinKeys = keyList.toArray(new String[0]);
			
			if(joinKeys.length<1){
				throw new NullPointerException("joinKeys is empty!!!!!");
			}
			
			// 调用同步
			logger.debug("======================================create graph=================================");
			TransformationGraph graph = TermSyncProcessor.createSyncGraph(
					sourcePro, targetPro, term.getFromDatasourceSql(), term.getToDatasourceSql(),joinKeys,msgProperties,msgHead,isMainSource);
			logger.debug("=======================================excute graph=================================");
			Result result = TermSyncProcessor.excuteGraph(graph);
			String info = result.code()+":"+result.message();
			logger.debug("sync result:", info);
			logger.info(result.message());
			if(!"FINISHED_OK".equals(result.message())){
				txt = info;
			}
		} catch (Exception e) {
			logger.error("graph error:", e);
			e.printStackTrace();
			txt = this.exception(e.getCause());
			if(txt.length()>4000){
				txt = txt.substring(0, 4000);
			}
		}finally{
			t1 = new TermSync();
			t1.setSyncId(t.getSyncId());
			// 变更术语同步状态  0：成功;1：失败
			t1.setSyncStatus(0);
			t1.setSyncResult(txt != null ? 1 : 0);
			Date date = new Date();
			t1.setEndtime(date);
			logger.debug("同步结束时间："+date+"=================================================");
			entity.setEndTime(date);
			t1.setLogTxt(txt);
			t1.setDeleteBy(SyncUtil.getLocalIP());
			t1.setUpdateCount(t.getUpdateCount());
			//最后更新者设定为当前线程名称
			t1.setLastUpdateBy(Thread.currentThread().getName());
			syncTermService.updateSyncTermStatus(t1);
			logger.debug("当前服务器IP是："+SyncUtil.getLocalIP()+"	当前更新数据的线程为ID:"+ Thread.currentThread().getId() +"， 线程名称为：" + Thread.currentThread().getName());
			
			entity.setLogId(StringUtil.getUUID());
			entity.setDictId(term.getDictId());
			entity.setDictName(term.getDictName());
			//entity.setEndTime(new Date());
			entity.setSyncResult(txt != null ? 1 : 0);
			entity.setLogTxt(txt);
			syncTermService.createSyncLog(entity);
			entity = null;
		}
	}

	public static String exception(Throwable t){
        if(t == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            t.printStackTrace(new PrintStream(baos));
        }finally{
            try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return baos.toString();
    }
}
