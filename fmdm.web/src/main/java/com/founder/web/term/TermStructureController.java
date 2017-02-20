package com.founder.web.term;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.doma.jdbc.SelectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.DictCodeMap;
import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.entity.DictSql;
import com.founder.fmdm.entity.DictView;
import com.founder.fmdm.entity.ViewField;
import com.founder.fmdm.service.sysmgt.ViewSetService;
import com.founder.fmdm.service.term.TermStructService;
import com.founder.fmdm.service.term.TermSyncService;
import com.founder.fmdm.service.term.dto.DictCodeMapDto;
import com.founder.fmdm.service.term.dto.DictFieldDto;
import com.founder.fmdm.service.term.dto.DictMainDto;
import com.founder.fmdm.service.term.dto.TermStructDto;
import com.founder.web.util.ExcelUtil;
import com.founder.web.util.FileManager;

@Controller
@RequestMapping("/term")
public class TermStructureController extends AbstractController {
	@Autowired
	private TermStructService termStructureService;
	
	@Autowired
	LogUtils logUtils;

	@Autowired
	private TermSyncService syncTermService;
	
	@Autowired
	ViewSetService viewSetService;
	
	private static Logger logger = LoggerFactory.getLogger(TermStructureController.class);
	
	private static final String LOG_ACTION_MODIFY ="编辑术语结构";

	@RequestMapping(value = "/termStructList")
	public ModelAndView showTermStuctList(final TermSearchCondition cond,
			final ModelMap model) throws Exception {
		model.addAttribute("page_title", "术语结构列表");
		SelectOptions options = getSelectOptions(cond);

		// 获取术语结构列表信息
		List<TermStructDto> tslist = new ArrayList<TermStructDto>();
		String serviceID=null;
		if(cond.getServiceId() != null){
			serviceID = cond.getServiceId().toUpperCase();
		}
		tslist = termStructureService.selectTermStructList(serviceID,
				cond.getDictName(), cond.getTypeCd(), cond.getIsSame(),options);

		// 获取结构类型下拉列表信息
		List<Map<String, Object>> typelist = new ArrayList<Map<String, Object>>();
		typelist = termStructureService.selectTermTypeList();

		model.addAttribute("tslist", tslist);
		model.addAttribute("typelist", typelist);

		pageSetting(cond, options);

		return includeTemplate(model, "term/termstructlist");
	}
	
	/**
	 * 术语映射列表查询
	 * author wp
	 */
	@RequestMapping(value = "/termMappingList")
	public ModelAndView showTermMappingList(final MapCodeSearchCondition cond,
			final ModelMap model) throws Exception {
		model.addAttribute("page_title", "术语映射列表");
		SelectOptions options = getSelectOptions(cond);
		List<DictCodeMapDto> tslist = new ArrayList<DictCodeMapDto>();
		tslist = termStructureService.selectMappingCodeList(cond.getSourceTable(),
				cond.getSourceName(), cond.getTargetTable(), cond.getTargetName(),options);

		model.addAttribute("tslist", tslist);
	
		pageSetting(cond, options);

		return includeTemplate(model, "term/termmappinglist");
	}
	
	/**
	 * 新增术语映射
	 * author wp
	 */
	@RequestMapping(value = "/addMappingCode")
	public ModelAndView showTermstructadd(final MapCodeSearchCondition cond,
			final ModelMap model,final HttpServletRequest request) throws Exception {
		SelectOptions options = getSelectOptions(cond);
		String operation = request.getParameter("operation");
//		//获得目标源字段
//		if(request.getParameter("sourceCodeContent")!=null){
//			String sourceCodeContent = new String(request.getParameter("sourceCodeContent").getBytes("iso-8859-1"),"utf-8");
//			cond.setSourceCodeContent_y(sourceCodeContent);
//		}
//		//获得映射字段
//		if(request.getParameter("targetCodeContent")!=null){
//			String targetCodeContent = new String(request.getParameter("targetCodeContent").getBytes("iso-8859-1"),"utf-8");
//			cond.setTargetCodeContent_y(targetCodeContent);
//		}
		if("fix".equals(operation)){
			model.addAttribute("page_title", "术语映射修改");
		}else{
			model.addAttribute("page_title", "术语映射新增");
		}
		String redict = request.getParameter("redict");
		if("redict".equals(redict)){
			if(checkStr(cond.getSourceName())==1){
				cond.setSourceName(new String(cond.getSourceName().getBytes("iso-8859-1"),"utf-8"));//表明
			}
			if(checkStr(cond.getTargetName())==1){
				cond.setTargetName(new String(cond.getTargetName().getBytes("iso-8859-1"),"utf-8"));//表明
			}
		}
		List<DictCodeMapDto> dfList = new ArrayList<DictCodeMapDto>();
		dfList = termStructureService.selectMappingCodeAllList(cond.getSourceTable(),
				cond.getSourceName(), cond.getTargetTable(), cond.getTargetName(),options);
		
		model.addAttribute("dfList", dfList);
		model.addAttribute("operation", operation);
		
		pageSetting(cond, options);
	    
		return includeTemplate(model, "term/mappingcodeadd");
	}
	
    /**
	 * 术语映射新增修改操作
	 * author wp
	 */
	@RequestMapping(value = "/saveorfixMappingCode")
	public ModelAndView saveorfixMappingCode(MapCodeSearchCondition cond,
			ModelMap model,HttpServletRequest request) {
		model.clear();
		try{
			String operation = request.getParameter("operation");
			if("fix".equals(operation) || (cond.getTargetCode_y() !=null && !"".equals(cond.getTargetCode_y())) ){
				String sourceTable = cond.getSourceTable();
				String targetTable = cond.getTargetTable();
				String sourceCode_y = cond.getSourceCode_y();
				String targetCode_y = cond.getTargetCode_y();
				String targetCode = request.getParameter("targetCode");
				termStructureService.updateDictCodeMap(sourceTable,targetTable,sourceCode_y,targetCode_y,targetCode);
			}else{
				saveMappingCode(cond,model,null,null,null,null);
			}
			//return showTermstructadd(cond,model,request);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}
		return null;
	}
        /**
		 * 新增实际操作
		 * author wp
		 */
		@Transactional 
		public void saveMappingCode(final MapCodeSearchCondition cond,
				final ModelMap model,String sourceTableName,String targetTableName,String sourceCodeName, String targetCodeName){
				try {
					model.clear();
					DictCodeMap dictCodeMap = new DictCodeMap();
					SecurityContext ctx = SecurityContextHolder.getContext();
					Authentication auth = ctx.getAuthentication();
					String userNo = auth.getName();
					dictCodeMap.setSrcUniKey(cond.getSrcUniKey());
					dictCodeMap.setTargetTable(cond.getTargetTable());
					dictCodeMap.setTarUniKey(cond.getTarUniKey());
					dictCodeMap.setSourceTable(cond.getSourceTable());
					dictCodeMap.setDeleteFlg("0");
					dictCodeMap.setCreateBy(userNo);
					dictCodeMap.setCreateTime(new Date());
					dictCodeMap.setLastUpdateTime(new Date());
					dictCodeMap.setLastUpdateBy(userNo);
					dictCodeMap.setUniKey(getUUID());
					
					termStructureService.insertDictCodeMap(dictCodeMap);
					//插入添加映射的日志
					if("".equals(cond.getSourceName())&&"".equals(cond.getTargetName())){
						this.insertMapAddLog(sourceTableName, sourceCodeName, targetTableName, targetCodeName);
					}else{
						this.insertMapAddLog(cond.getSourceName(), cond.getSourceCodeContent_y(), 
								cond.getTargetName(), cond.getTargetCodeContent_y());
					}
					
				} catch (Exception e) {
				   logger.debug(e.getMessage());
					model.put("status", -1);
				}
		}
		
	    /**
		 * 术语映射删除操作
		 * author wp
		 */
		@RequestMapping(value = "/deleteMappingCode")
		public ModelAndView deleteMappingCode(final MapCodeSearchCondition cond,
				final ModelMap model,final HttpServletRequest request) throws Exception {
			String delflag = request.getParameter("all");
//			SecurityContext ctx = SecurityContextHolder.getContext();
//			Authentication auth = ctx.getAuthentication();
			//String delpeople = auth.getName();
			String sourceTable=request.getParameter("sourceTable1");
			String targetTable=request.getParameter("targetTable1");
			//code改为unikey的值
			String sourceCode=request.getParameter("sourceCode1");
			String targetCode=request.getParameter("targetCode1");
			termStructureService.updateDictCodeMapDel(sourceTable,targetTable,sourceCode,targetCode);
			//插入删除日志
			this.insertMapDeleteLog(sourceTable, sourceCode, targetTable, targetCode);
			if("all".equals(delflag)){
				return showTermMappingList(cond,model);
			}else{
				String operation=request.getParameter("operation");
				if(operation != null && !"".equals(operation)){
					String sourceName=request.getParameter("sourceName1");
					String targetName=request.getParameter("targetName1");
					if(checkStr(sourceName)==1){
						cond.setSourceName(new String(sourceName.getBytes("iso-8859-1"),"utf-8"));
					}else{
						cond.setSourceName(sourceName);
					}
					
					if(checkStr(targetName)==1){
						cond.setTargetName(new String(targetName.getBytes("iso-8859-1"),"utf-8"));
					}else{
						cond.setTargetName(targetName);
					}

					cond.setTargetTable(targetTable);
					
					cond.setSourceTable(sourceTable);
				}
				return showTermstructadd(cond,model,request);
			}
		}
		
		
		public int checkStr(String str){
			int a=0;
			if (str  ==  null || "".equals(str)){
				return  a;
			}else{
				byte  b[];
				try {
					b  =  str.getBytes("ISO8859_1");
					for(int i=0;i<b.length;i++){
						 byte  b1  =  b[i];
						 if(b1  ==  63){
							 break;
						 }else if(b1 < 0){
							 a = 1;
							 break;
						 }
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return a;
		}
		
		
		 /**
		 * autocomplete联想输入操作ajax(原表、目标表)
		 * author wp
		 */
		@RequestMapping(value = "/selectNodeInfo")
		public ModelAndView selectNodeInfo(HttpServletRequest request,HttpServletResponse respnse) {
			try{
				PrintWriter out= respnse.getWriter();
				String sourceTable = URLDecoder.decode(request.getParameter("q"),"UTF-8");
				int limit = 10;
				int pageIndex = 0;
				if (request.getParameter("limit") != null) {
		            limit = Integer.parseInt(request.getParameter("limit"));
		        }
				String pageIndexStr = request.getParameter("pageIndex");

		        if (pageIndexStr != null) {
		            pageIndex = Integer.parseInt(pageIndexStr);
		        }

		        Map<String,List<DictMainDto>> info = FindByCondition(sourceTable,pageIndex,limit);
		        int dataCount = 0;
		        if(info.get("num").size() > 0){
		        	dataCount = info.get("num").size();
		        }
		        List<DictMainDto> dataList = info.get("detail");
		       
		        StringBuffer json = new StringBuffer();

		        json.append("{");
		        json.append("\"dataCount\":" + dataCount + ",");
		        json.append("\"dt\":[");
		        
		        StringBuilder list = new StringBuilder();
		         for(DictMainDto d:dataList) {
		            if (list.length()!=0) {
		                list.append(",");
		            }
		            list.append("{");
		            list.append("\"sourceTable\":\"" + d.getTableName() + "\",");
		            list.append("\"sourceName\":\"" + d.getDictName() + "\"");
		            list.append("}");
		        }
		        json.append(list);
		        json.append("]");
		        json.append("}");
		        
		        out.write(json.toString());
                
			}catch(Exception e){
				
			}
			return null;
		}
		
		 /**
		 * autocomplete联想输入操作ajax(原表、目标表)
		 * author wp
		 */
		public Map<String,List<DictMainDto>> FindByCondition(String sourceTable,int pageIndex,int pageSize){
	        List<DictMainDto> dictList = new ArrayList<DictMainDto>();
	        List<DictMainDto> dictListRn = new ArrayList<DictMainDto>();
	        dictList = termStructureService.selectOptionInfo(sourceTable);
	        if(dictList != null && !dictList.isEmpty()){
	        	for (int i = 0; i < dictList.size(); i++) {
		            if (i>=(pageIndex-1)*pageSize && i<pageIndex*pageSize) {
		            	DictMainDto dict = new DictMainDto();
		                dict.setDictName(dictList.get(i).getDictName());
		                dict.setTableName(dictList.get(i).getTableName());
		                dictListRn.add(dict);
		            }
		        }
	        }
	        Map<String,List<DictMainDto>> info = new HashMap<String,List<DictMainDto>>();
	        info.put("num",dictList);
	        info.put("detail",dictListRn);
	        return info;
	    }
		
		
		 /**
		 * autocomplete联想输入操作ajax(编码、编码名称)
		 * author wp
		 */
		@RequestMapping(value = "/selectCodeNodeInfo")
		public ModelAndView selectCodeNodeInfo(HttpServletRequest request,HttpServletResponse respnse) {
			try{
				PrintWriter out= respnse.getWriter();
				String code = request.getParameter("q");
				String table = request.getParameter("table");
				int limit = 10;
				int pageIndex = 0;
				if (request.getParameter("limit") != null) {
		            limit = Integer.parseInt(request.getParameter("limit"));
		        }
				String pageIndexStr = request.getParameter("pageIndex");

		        if (pageIndexStr != null) {
		            pageIndex = Integer.parseInt(pageIndexStr);
		        }

		        Map<String,List<Map<String, Object>>> info = FindCodeByCondition(table,code,pageIndex,limit);
		        int dataCount = 0;
		        if(info.get("num").size() > 0){
		        	dataCount = info.get("num").size();
		        }
		        List<Map<String, Object>> dataList = info.get("detail");
		       
		        StringBuffer json = new StringBuffer();

		        json.append("{");
		        json.append("\"dataCount\":" + dataCount + ",");
		        json.append("\"dt\":[");
		        
		        StringBuilder list = new StringBuilder();
		         for(Map<String, Object> d:dataList) {
		            if (list.length()!=0) {
		                list.append(",");
		            }
		            list.append("{");
		            list.append("\"sourceTable\":\"" + d.get("code") + "\",");
		            list.append("\"sourceName\":\"" + d.get("name") + "\",");
		            list.append("\"version\":\"" + d.get("version") + "\",");
		            list.append("\"unikey\":\"" + d.get("unikey") + "\"");
		            list.append("}");
		        }
		        json.append(list);
		        json.append("]");
		        json.append("}");
		        
		        out.write(json.toString());
                
			}catch(Exception e){
				
			}
			return null;
		}
		
		 /**
		 * autocomplete联想输入操作ajax(编码、编码名称)
		 * author wp
		 */
		public Map<String,List<Map<String, Object>>> FindCodeByCondition(String table,String code,int pageIndex,int pageSize){
	        List<Map<String, Object>> dictList = new ArrayList<Map<String, Object>>();
	        List<Map<String, Object>> dictListRn = new ArrayList<Map<String, Object>>();
	        dictList = termStructureService.selectCodeOptionInfo(table,code);
	        if(dictList != null && !dictList.isEmpty()){
	        	for (int i = 0; i < dictList.size(); i++) {
		            if (i>=(pageIndex-1)*pageSize && i<pageIndex*pageSize) {
		            	Map<String, Object> dict = new HashMap<String, Object>();
		                dict.put("code",dictList.get(i).get("code"));
		                dict.put("name",dictList.get(i).get("name"));
		                //新增版本号
		                dict.put("version",dictList.get(i).get("version"));
		                dict.put("unikey",dictList.get(i).get("unikey"));
		                dictListRn.add(dict);
		            }
		        }
	        }
	        Map<String,List<Map<String, Object>>> info = new HashMap<String,List<Map<String, Object>>>();
	        info.put("num",dictList);
	        info.put("detail",dictListRn);
	        return info;
	    }
		
		
		 /**
		 * 导入excel数据到数据库
		 * author wp
		 */
		@Transactional
		@RequestMapping(value = "/importexceldata")
		public ModelAndView importexceldata(HttpServletRequest request, HttpServletResponse response,MapCodeSearchCondition cond,ModelMap model) throws Exception {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out= response.getWriter();
			String filepath = FileManager.onlyUpload(request);
			DictCodeMap dictCodeMap=null;
			try {
				Map<Integer,DictCodeMap> map = new HashMap<Integer,DictCodeMap>();
				map = ExcelUtil.readExcelFile(filepath);
				if(map==null||map.size()<=0){
					throw new Exception("Excel内容为空");
				}
				for(Map.Entry<Integer,DictCodeMap> entry : map.entrySet()) {
					dictCodeMap = new DictCodeMap();
					dictCodeMap = entry.getValue();
					int isExist = termStructureService.insertDictCodeMap(dictCodeMap);
					if(isExist>0){//如果dict_code_map表中没有数据
						String sourceTableName = termStructureService.selectDictNameByTableName(dictCodeMap.getSourceTable());
						String targetTableName = termStructureService.selectDictNameByTableName(dictCodeMap.getTargetTable());
						String sourceCodeName = termStructureService.selectCodeNameByTableNameAndUnikey(dictCodeMap.getSourceTable(),
												dictCodeMap.getSrcUniKey());
						String targetCodeName = termStructureService.selectCodeNameByTableNameAndUnikey(dictCodeMap.getTargetTable(),
												dictCodeMap.getTarUniKey());
						logUtils.insertLog("00704", "00704001", "导入术语映射列[{}]-[{}]=>[{}]-[{}]",
									sourceTableName, sourceCodeName, targetTableName, targetCodeName);
					}
				}
			
				out.write("导入数据成功！");
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				if(e.toString().contains("违反唯一约束条件")){
					out.print("重复导入数据！");
				}else{
					out.print("导入数据失败！");
				}
				out.flush();
				out.close();
			}finally {
			    FileManager.deleteFile(filepath);
			}
			return null;
		}
		
		
		/**
		 * 对数据库的数据进行check
		 * author wp
		 */
		@RequestMapping(value = "/checkCodedata")
		public @ResponseBody Object checkCodedata(MapCodeSearchCondition cond,ModelMap model,HttpServletRequest request) {
			model.clear();
			try{
				String operation = request.getParameter("operation");
				String targetCode = request.getParameter("targetCode");
				String targetCodeContent = request.getParameter("targetCodeContent");
				String sourceCode = request.getParameter("sourceCode");
				String sourceCodeContent = request.getParameter("sourceCodeContent");
				String srcUniKey = request.getParameter("srcUniKey");
				String tarUniKey = request.getParameter("tarUniKey");
				
				String sourceTableName = request.getParameter("sourceTableName");
				String targetTableName = request.getParameter("targetTableName");

				String sourceCodeName = request.getParameter("sourceItemName");
				String targetCodeName = request.getParameter("targetItemName");
				
				
				String targetTable = cond.getTargetTable();
				String sourceTable = cond.getSourceTable();
				if("fix".equals(operation) || (cond.getTarUniKey_y()!=null && !"".equals(cond.getTarUniKey_y())) ){//修改操作
					model.put("opt","修改");
					//检验目标表、表名称是否存在
					String srcUniKey_y = cond.getSrcUniKey_y();
					String tarUniKey_y = cond.getTarUniKey_y();
					int a = termStructureService.selectCodeName(targetTable,targetCode,targetCodeContent);
					if(a==0){
						 //编码或者编码名称不存在
						 model.put("status", 2);
					}else{
						
						 termStructureService.updateDictCodeMap(sourceTable,targetTable,srcUniKey_y,tarUniKey_y,tarUniKey);
						 //插入编辑映射的详细日志
						 this.insertMapEditLog(sourceTable, srcUniKey_y, targetTable, tarUniKey_y);
						 model.put("status", 0);
					}
				}else{//新增
					int b = termStructureService.selectCodeName(sourceTable,sourceCode,sourceCodeContent);
					int m = termStructureService.selectCodeName(targetTable,targetCode,targetCodeContent);
					if(b!=0 && m!=0){
						int n = termStructureService.selectDataIsHaveExist(sourceTable,targetTable,srcUniKey);
						if(n==1){
						  //重复数据
						   model.put("status", 1);
						}else{
							cond.setTargetCodeContent_y(targetCodeContent);
							cond.setSourceCodeContent_y(sourceCodeContent);
						   saveMappingCode(cond,model,sourceTableName,targetTableName,sourceCodeName,targetCodeName);
						   model.put("status", 0);
						   model.put("opt","新增");
						}
					}else{
						   //编码或者编码名称不存在
						   model.put("status", 2);
					}
				}
			}catch(Exception e){
				model.put("status", -1);
			}
			return model;
		}
		
		/*
		 * 插入：对术语映射编辑，的日志
		 * */
		private void insertMapEditLog(String sourceTable, String srcUnikey,String targetTable, String tarUnikey){
			if(sourceTable==null||srcUnikey==null||targetTable==null||tarUnikey==null
					||"".equals(sourceTable)||"".equals(srcUnikey)||"".equals(targetTable)||"".equals(tarUnikey)){
				return;
			}
			String sourceTableName = termStructureService.selectDictNameByTableName(sourceTable);
			String targetTableName = termStructureService.selectDictNameByTableName(targetTable);
			String sourceCodeName = termStructureService.selectCodeNameByTableNameAndUnikey(sourceTable, srcUnikey);
			String targetCodeName = termStructureService.selectCodeNameByTableNameAndUnikey(targetTable, tarUnikey);
			logUtils.insertLog("00704", "00704002", "编辑术语映射列[{}]-[{}]=>[{}]-[{}]",
					sourceTableName,sourceCodeName,targetTableName,targetCodeName);
		}
		/*
		 * 插入：对术语映射新增，的日志
		 * */
		private void insertMapAddLog(String sourceTableName, String sourceCodeName,String targetTableName, String targetCodeName){
			if(sourceTableName==null||sourceCodeName==null||targetTableName==null||targetCodeName==null){
				return;
			}
			logUtils.insertLog("00704", "00704000", "新增术语映射列[{}]-[{}]=>[{}]-[{}]",
					sourceTableName, sourceCodeName, targetTableName, targetCodeName);
		}
		
		/*
		 * 插入：对术语映射删除，的日志
		 * */
		private void insertMapDeleteLog(String sourceTable, String srcUnikey,String targetTable, String tarUnikey){
			if(sourceTable==null||targetTable==null||"".equals(sourceTable)||"".equals(targetTable)){
				return;
			}
			String sourceTableName = termStructureService.selectDictNameByTableName(sourceTable);
			String targetTableName = termStructureService.selectDictNameByTableName(targetTable);
			if(srcUnikey==null&&tarUnikey==null){//删除映射表
				logUtils.insertLog("00703", "00703000", "删除术语映射表[{}]=>[{}]", sourceTableName, targetTableName);
			}else{//删除单个映射
				String sourceCodeName = termStructureService.selectCodeNameByTableNameAndUnikey(sourceTable, srcUnikey);
				String targetCodeName = termStructureService.selectCodeNameByTableNameAndUnikey(targetTable, tarUnikey);
				logUtils.insertLog("00704", "00704003", "删除术语映射列[{}]-[{}]=>[{}]-[{}]", 
						sourceTableName, sourceCodeName, targetTableName, targetCodeName);
			}
		}
		
		/**
		 * 对数据库的数据进行check
		 * author wp
		 */
		@RequestMapping(value = "/checkTabledata")
		public @ResponseBody Object checkTabledata(MapCodeSearchCondition cond,ModelMap model,HttpServletRequest request) {
			model.clear();
			try{
				String sourceTable = request.getParameter("sourceTable");
				String targetTable = request.getParameter("targetTable");
				int a = termStructureService.selectTableName(sourceTable);
				int b = termStructureService.selectTableName(targetTable);
				if(a==1 && b==1){
					//正常
					model.put("status", 1);
				}else{
					//不正常
					model.put("status", 0);
				}
			}catch(Exception e){
				model.put("status", -1);
			}
			return model;
		}
		
		
	
	@RequestMapping(value = "/addTermStruct", method = RequestMethod.GET)
	public ModelAndView showTermstructadd(final TermSearchCondition cond,
			final ModelMap model) throws Exception {
		model.addAttribute("page_title", "术语结构新增");
		// 获取结构类型下拉列表信息
		List<Map<String, Object>> fieldTypeList = new ArrayList<Map<String, Object>>();
		fieldTypeList = termStructureService.selectFieldTypeList();
		model.addAttribute("fieldTypeList", fieldTypeList);

		// 获取术语结构类型
		List<Map<String, Object>> termTypeList = new ArrayList<Map<String, Object>>();
		termTypeList = termStructureService.selectTermTypeList();
		model.addAttribute("termTypeList", termTypeList);

		// 获取提供系统
		List<Map<String, Object>> sysList = syncTermService.getSysList();
		model.addAttribute("sysList", sysList);

		// 如果不是修改链接，则赋值空
		if (!model.containsKey("termStruct")) {
			DictMain dm = new DictMain();
			model.put("termStruct", dm);
		}
		return includeTemplate(model, "term/termstructadd");
	}
	

	/**
	 * 术语结构新增操作
	 * 
	 * @param cond
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveTermStruct")
	public @ResponseBody Object justSaveTermStruct(final TermSearchCondition cond,
			final ModelMap model) {
		model.clear();
		try{
			saveTermStruct(cond,model);
			model.put("status", 1);
			model.put("autoOpen", false);
		}catch(Exception e){
			model.put("status", -1);
		}
		return model;
	}

	/**
	 * 保存并浏览SQL操作
	 * @param cond
	 * @param model
	 */
	@RequestMapping(value = "/saveAndViewSQL")
	public @ResponseBody Object saveAndViewSQL(final TermSearchCondition cond,
			final ModelMap model) {
		try
		{
			model.clear();
			saveTermStruct(cond,model);
			model.put("status", 1);
			model.put("autoOpen", true);
		}catch(Exception e){
			model.put("status", -1);
		}
		return model;
	}
	
	/**
	 * 术语结构删除操作
	 * 
	 * @param cond
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/deleteTermStruct")
	public ModelAndView deleteTermStruct(final TermSearchCondition cond,
			final ModelMap model) throws Exception {
		List<DictMain> dmList = termStructureService.selectTermStructById(cond
				.getDictId());
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth != null) {
			if (dmList.size() == 1) {
				for (DictMain dm : dmList) {
					dm = dmList.get(0);
					dm.setDeleteFlg(1);
					dm.setLastUpdateBy(auth.getName());
					dm.setLastUpdateTime(new Date());
					dm.setDeleteBy(auth.getName());
					dm.setDeleteTime(new Date());
					termStructureService.updateMainData(dm);
					
					//处理dict_field删除字段
					List<DictField> dfList = termStructureService.selectFieldInfoByDictId(cond.getDictId());
					for (DictField df : dfList) {
						df.setLastUpdateBy(auth.getName());
						df.setLastUpdateTime(new Date());
						df.setDeleteBy(auth.getName());
						df.setDeleteTime(new Date());
						df.setDeleteFlg(1);
						termStructureService.updateFieldData(df);
					}
					
					//如果是deletaAll，需要连同物理表一起删除
					if("deleteAll".equalsIgnoreCase(cond.getOperation())){
						String sql = "drop table " + dm.getTableName();
						termStructureService.executeTable(sql);
						model.addAttribute("backMsg", "删除术语结构和物理表成功!");
					}else{
						model.addAttribute("backMsg", "删除术语结构成功!");
					}
				}
			}
		}
		logUtils.insertLog("00602", "00602003", "删除术语[{}]", dmList.get(0).getDictName());
		return showTermStuctList(cond, model);
	}

	@RequestMapping(value = "/editTermStruct")
	public ModelAndView editTermStruct(final TermSearchCondition cond,
			final ModelMap model) throws Exception {
		model.clear();
		// 查dict_main表的信息
		List<DictMain> dmList = termStructureService.selectTermStructById(cond
				.getDictId());
		
		// 查询dict_field表信息
		List<DictFieldDto> dfList = termStructureService
				.selectFieldByDictId(cond.getDictId());
		int tableIsExist = 0;
		for (DictMain dm : dmList) {
			model.put("termStruct", dm);
			tableIsExist = termStructureService.selectTableIsExist(dm.getTableName());
		}
		model.put("dfList", dfList);
		model.addAttribute("operation", "edit");
		model.addAttribute("autoOpen", cond.getAutoOpen());
		model.put("tableIsExist", tableIsExist);
		return showTermstructadd(cond, model);
	}

	public void saveFieldData(String[] tdArr, String userNo, String uuid,String fieldId,String dropColumn) throws ParseException {
		boolean doSave = false;
		//新增字段时，不能插入重复，需要校验模型中是否已经存在相同字段
		int result = termStructureService.selectFieldNameIsExist(tdArr[2],uuid);
		
		//对于删除a，新增a，bug0039959，应该先查询除所有删除的fieldName，然后再比对新增的是否在删除中，那么这样的场景是允许新增的
		Map<Object, Object> fieldMap = new HashMap<Object, Object>();
		if(!StringUtils.isEmpty(dropColumn)){
			String[] dropColumnArr = dropColumn.split("&");
			for(int m=0;m<dropColumnArr.length;m++){
				String[] column = dropColumnArr[m].split("@");
				List<DictField> dfList = termStructureService.selectFieldByFieldId(column[0]);
				for(DictField df : dfList){
					fieldMap.put(df.getFieldName(), df.getFieldName());
				}
				
			}
		}
		
		//1.物理表不存在;2.新增的，未即将删除的
		doSave = result!=1 || fieldMap.containsKey(tdArr[2]);
		
		if(doSave){
			DictField df = new DictField();
			df.setDictId(uuid);
			if(!"noSql".equalsIgnoreCase(fieldId)){
				df.setFieldId(fieldId);
			}else{
				df.setFieldId(this.getUUID());
			}
			df.setDispOrder(tdArr[1]);
			df.setFieldName(tdArr[2]);
			df.setFieldDesc(tdArr[3]);
			df.setFieldType(tdArr[5]);
			df.setLength(tdArr[6]);
			df.setPrecision(tdArr[7]);
			if(tdArr[5].indexOf("NUMBER")==0){
				df.setDefaultValue(tdArr[8]);
			}else{
				if(StringUtils.isEmpty(tdArr[8])){
					df.setDefaultValue("");
				}else{
					df.setDefaultValue("'"+tdArr[8]+"'");
				}
			}
			if ("×".equalsIgnoreCase(tdArr[9].trim())) {
				tdArr[9] = "N";
			} else {
				tdArr[9] = "Y";
			}
			if ("×".equalsIgnoreCase(tdArr[10].trim())) {
				tdArr[10] = "N";
			} else {
				tdArr[10] = "Y";
			}
			if ("×".equalsIgnoreCase(tdArr[11].trim())) {
				tdArr[11] = "N";
			} else {
				tdArr[11] = "Y";
			}
			if ("×".equalsIgnoreCase(tdArr[12].trim())) {
				tdArr[12] = "N";
			} else {
				tdArr[12] = "Y";
			}
			df.setIsMust(tdArr[9]);
			df.setIsPrimary(tdArr[10]);
			df.setIsShow(tdArr[11]);
			df.setIsFilter(tdArr[12]);
			
			df.setDeleteFlg(0);
			//判读是不是系统默认添加字段
			df.setIsDefault("N");

			df.setCreateBy(userNo);
			df.setLastUpdateBy(userNo);
			df.setCreateTime(new Date());
			df.setLastUpdateTime(new Date());
			termStructureService.saveFieldData(df);
			
			//将存在待执行的SQL存入dict_sql表中
			if(tdArr[13].split("&").length==3 && !StringUtils.isEmpty(tdArr[13].split("&")[1])){
				//如果存在有未执行的sql,那么直接将其更新成最新的sql
				String[] arr = tdArr[13].split("&");
				String fId = arr[0];
				String sql = arr[1];
				String optTime = arr[2];
				DictSql ds = new DictSql();
				List<DictSql> sqlList = termStructureService.selectSqlById(uuid,fId,1);
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = sim.parse(optTime);
				if(sqlList.size()>0){
					ds = sqlList.get(0);
					//有一种情况应该除外，那就是列还没有被加入到物理表，就不应该被替换掉
					ds.setDictSql(sql);
					ds.setLastUpdateBy(userNo);
					ds.setLastUpdateTime(date);
					termStructureService.updateSqlData(ds);
				}else{
					ds.setSqlId(this.getUUID());
					ds.setDictId(uuid);
					ds.setFieldId(fId);
					ds.setDictSql(sql);
					//1：确认过；0：未确认
					ds.setOptStatus(1);
					ds.setCreateBy(userNo);
					ds.setCreateTime(date);
					ds.setLastUpdateBy(userNo);
					ds.setLastUpdateTime(date);
					ds.setDeleteFlg(0);
					termStructureService.saveSqlData(ds);
				}
			}
		}
	}
	
	/**
	 * 把多条细小的日志合并并插入
	 * @param logs 编辑日志的集合
	 */
	private void insertAndMergeModifyLog(List<String> logs,String dictName){
		String mergeLog = this.getMergeLogByActionType(logs, dictName, LOG_ACTION_MODIFY);
		if(mergeLog!=null)
			logUtils.insertLog("00602", "00602002", "{}",mergeLog);
	}
	
	/**
	 * 
	 * @param logs 日志集合
	 * @param dictName 术语名称
	 * @param actionType 修改动作类型：新增，编辑，删除
	 * @return 融合后的长日志
	 */
	private String getMergeLogByActionType(List<String> logs, String dictName,String actionType){
		if(logs.size()>0){
			StringBuffer mergeLog = new StringBuffer(actionType+"["+dictName+"]").append("@");
			String mergeLogContent = logUtils.mergeLog(logs);
			mergeLog.append(mergeLogContent);
			return mergeLog.toString();
		}
		return null;
	}
	/**
	 * 
	 * @param 术语基本信息数据对
	 * @param 术语结构信息数据对
	 * @return 融合后的数据对
	 */
	private List<String> mergetDataPair(List<String> basic, List<String> structure){
		for(String str : structure){
			basic.add(str);
		}
		structure = null;
		return basic;
	}
	
	
	//根据前台传来的术语结构内容的字符串，和数据库查询的元数据，生成变更的数据对
	/*
	 * @param 原数据的List<DictField>
	 * @param 描述新数据的字符串
	 * */
	
	private List<String> getComplexDictFiledModifyDataPair(DictField dictField, String[] newArr){
		List<String> dataPair = new ArrayList<String>();
		String[] oldArr = new String[13];
		oldArr[2] = dictField.getFieldName();
		oldArr[3] = dictField.getFieldDesc();
		oldArr[5] = dictField.getFieldType();
		oldArr[6] = dictField.getLength()==null?"":dictField.getLength();
		oldArr[7] = dictField.getPrecision()==null?"":dictField.getPrecision();
		oldArr[8] = dictField.getDefaultValue()==null?"":dictField.getDefaultValue();
		oldArr[9] = dictField.getIsMust();
		oldArr[10] = dictField.getIsPrimary();
		oldArr[11] = dictField.getIsShow();
		oldArr[12] = dictField.getIsFilter();
		
		//每个选项的名称映射数组
		String[] itemNames = {"","","列名","注解","","类型","长度","精度","缺省值","必须项","唯一标识","列表显示","索引条件"};
		if(oldArr!=null&&newArr!=null){
			for(int i = 2; i < 13; i++){
				if(i==4)
					continue;
				if(!oldArr[i].equals(newArr[i])){
					oldArr[i] = "".equals(oldArr[i])?"暂无":oldArr[i];
					newArr[i] = "".equals(newArr[i])?"暂无":newArr[i];
					if(i==5){
						oldArr[i] = this.getTypeNameByCode(oldArr[i]);
						newArr[i] = this.getTypeNameByCode(newArr[i]);
					}
					dataPair.add("编辑："+oldArr[2]+"-"+itemNames[i]+"："+oldArr[i]+"=>"+newArr[i]);
				}
			}
		}
		return dataPair;
	}
	
	//从数据库取出来的dictfield对象的类型是数据库的类型，和前端显示不一致，所以需要转换，有以下关系
	//数值型：NUMBER
	//字符型：VARCHAR2
	//布尔型：NUMBER(1)
	//时间型：DATE
	private String getTypeNameByCode(String typeCode){
		if(typeCode.equals("NUMBER")){
			return "数值型";
		}else if(typeCode.equals("VARCHAR2")){
			return "字符型";
		}else if(typeCode.equals("NUMBER(1)")){
			return "布尔型";
		}else if(typeCode.equals("DATE")){
			return "时间型";
		}
		return "暂无";
	}
	
	private List<String> getDictModifyDataPair(DictMain oldDict, DictMain newDict){
		List<String> dataPair = new ArrayList<String>();
		if(!oldDict.getDictName().equals(newDict.getDictName())){
			dataPair.add("编辑结构名称："+oldDict.getDictName()+"=>"+newDict.getDictName());
		}
		if(!oldDict.getSysId().equals(newDict.getSysId())){
			String oldSysName = termStructureService.selectSysNameById(oldDict.getSysId());
			String newSysName = termStructureService.selectSysNameById(newDict.getSysId());
			dataPair.add("编辑主数据源："+ oldSysName+"=>"+newSysName);
		}
		if(!oldDict.getServiceId().equals(newDict.getServiceId())){
			dataPair.add("编辑术语ID："+oldDict.getServiceId()+"=>"+newDict.getServiceId());
		}
		if(!oldDict.getTypeCd().equals(newDict.getTypeCd())){
			String oldTypeName = termStructureService.selectTypeNameByCd(oldDict.getTypeCd());
			String newTypeName = termStructureService.selectTypeNameByCd(newDict.getTypeCd());
			dataPair.add("编辑结构类型："+oldTypeName+"=>"+newTypeName);
		}
		return dataPair;
	}
	
	/*
	 * 因为前台传来的有关dictmain的数据都在termsearchcondition中，
	 * 所以需要手动把有关dictmain的信息装载进dictmain中，
	 * 产生新的dictmain对象，以便和数据库中的旧数据进行比较
	 * */
	private DictMain installNewDictMain(TermSearchCondition cond){
		DictMain newDict = new DictMain();
		newDict.setDictName(cond.getDictName());//结构名称
		newDict.setSysId(cond.getSysId());//主数据源的id
		newDict.setServiceId(cond.getServiceId());
		newDict.setTypeCd(cond.getTypeCd());
		return newDict;
	}
	
	//保存和保存并浏览SQL两个操作
	@Transactional 
	public @ResponseBody Object saveTermStruct(final TermSearchCondition cond,
			final ModelMap model){
		try{
			String dictName = cond.getDictName();//术语名称，日志插入时候使用
			List<String> modifyDictDataPair = new ArrayList<String>();//编辑产生的数据对
			List<String> addDictDataPair = new ArrayList<String>();//新增的数据对
			List<String> deleteDictDataPair = new ArrayList<String>();//删除的数据对
			
			model.clear();
			String uuid = this.getUUID();
			//将uuid存入model中，用于发起新请求，从新增保存成功后，跳转到编辑页面，避免重复新增,这个很重要！
			model.put("dictId", uuid);
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			String userNo = auth.getName();
	
			// 存入dict_main表
			DictMain dm = new DictMain();
			boolean isEdit = false;
			
			dm.setLockStatus(0);
			if(!StringUtils.isEmpty(cond.getDictId())){
				isEdit = true;
			}
			if (isEdit) {
				// 查询主表信息
				dm = termStructureService.selectTermStructById(cond.getDictId()).get(0);//旧的dict对象
				
				//进行比较，查看有没有不同，有不同，则为修改了，需要记录日志
				DictMain newDict = this.installNewDictMain(cond);//新的dict对象
				modifyDictDataPair.addAll(this.getDictModifyDataPair(dm, newDict));//查看旧数据和新数据是否有不同
				
				model.put("dictId", dm.getDictId());
			}
			dm.setServiceId(cond.getServiceId().toUpperCase());
			dm.setDictName(cond.getDictName());
			dm.setTableName(cond.getTableName());
			dm.setTypeCd(cond.getTypeCd());
			dm.setSysId(cond.getSysId());
	
			if (isEdit) {//编辑
				if("S028".equalsIgnoreCase(cond.getSysId().trim())){
					dm.setIsEdit("Y");
				}else{
					dm.setIsEdit("N");
				}
				dm.setLastUpdateBy(userNo);
				dm.setLastUpdateTime(new Date());
				termStructureService.updateMainData(dm);
	
				// 对field表的修改操作，分为三类：修改原有字段，新增字段，删除原有字段
				List<DictFieldDto> dfList = termStructureService//查询字典中字段的消息
						.selectFieldByDictId(cond.getDictId());
				Map<Object, Object> map = new HashMap<Object, Object>();
				for (DictFieldDto df : dfList) {
					map.put(df.getFieldId(),df.getFieldName());//把fileid和字段在表中的列名放入map
				}
				
				if (!StringUtils.isEmpty(cond.getParamData())) {
					String[] trArr = cond.getParamData().split("@");
					for (int j = 0; j < trArr.length; j++) {
						String[] tdArr = trArr[j].split("#");//新的记录new
						// 不包含在map1中的，说明是新增加的字段，执行新增操作
						
						if (!map.containsKey(tdArr[0])) {
							saveFieldData(tdArr, userNo, cond.getDictId(),tdArr[13].split("&")[0],cond.getDropColumn());
							//在新增日志数据对中添加数据
							addDictDataPair.add("新增：列名："+tdArr[2]+"-注释："+tdArr[3]);
						} else {
							// 修改字段，执行修改操作，并将其从map1中删除
							// 从数据库中查询该记录的全部数据
							List<DictField> dfList2 = termStructureService
									.selectFieldByFieldId(tdArr[0]);
							DictField oldDict = termStructureService
									.selectFieldByFieldId(tdArr[0]).get(0);//旧的数据old,必须重新查询一个
							for (DictField df : dfList2) {
								df.setDispOrder(tdArr[1]);
								df.setFieldName(tdArr[2]);
								df.setFieldDesc(tdArr[3]);
								df.setFieldType(tdArr[5]);
								df.setLength(tdArr[6]);
								df.setPrecision(tdArr[7]);
								if(tdArr[5].indexOf("NUMBER")==0){
									df.setDefaultValue(tdArr[8]);
								}else{
									if(StringUtils.isEmpty(tdArr[8])){
										df.setDefaultValue("");
									}else{
										df.setDefaultValue("'"+tdArr[8]+"'");
									}
								}
								
								if ("×".equalsIgnoreCase(tdArr[9].trim())) {
									tdArr[9] = "N";
								} else {
									tdArr[9] = "Y";
								}
								if ("×".equalsIgnoreCase(tdArr[10].trim())) {
									tdArr[10] = "N";
								} else {
									tdArr[10] = "Y";
								}
								if ("×".equalsIgnoreCase(tdArr[11].trim())) {
									tdArr[11] = "N";
								} else {
									tdArr[11] = "Y";
								}
								if ("×".equalsIgnoreCase(tdArr[12].trim())) {
									tdArr[12] = "N";
								} else {
									tdArr[12] = "Y";
								}
								df.setIsMust(tdArr[9]);
								df.setIsPrimary(tdArr[10]);
								df.setIsShow(tdArr[11]);
								df.setIsFilter(tdArr[12]);
								df.setLastUpdateBy(userNo);
								df.setLastUpdateTime(new Date());
								
								//术语结构，详细的修改数据对，如：字段类型的修改，长度的修改，等
								List<String> dictStructureModify = getComplexDictFiledModifyDataPair(oldDict, tdArr);
								modifyDictDataPair = this.mergetDataPair(modifyDictDataPair, dictStructureModify);

								//修改表结构的语句
								termStructureService.updateFieldData(df);
								
								
								//将存在待执行的SQL存入dict_sql表中
								if(tdArr[13].split("&").length==3 && !StringUtils.isEmpty(tdArr[13].split("&")[1])){
									String[] arr = tdArr[13].split("&");
									String fieldId = arr[0];
									String dictSql = arr[1];
									String optTime = arr[2];
									try{
										SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
										Date date = sim.parse(optTime);
										//如果存在有未执行的sql,那么直接将其更新成最新的sql
										List<DictSql> sqlList = termStructureService.selectSqlById(cond.getDictId(),fieldId,1);
										DictSql ds = new DictSql();
										if(sqlList.size()>0){
											ds = sqlList.get(0);
											//有一种情况应该除外，那就是列还没有被加入到物理表，就不应该被替换掉
											ds.setDictSql(dictSql);
											ds.setLastUpdateBy(userNo);
											ds.setLastUpdateTime(date);
											termStructureService.updateSqlData(ds);
										}else{
											ds.setSqlId(this.getUUID());
											ds.setDictId(cond.getDictId());
											ds.setFieldId(fieldId);
											ds.setDictSql(dictSql);
											ds.setOptStatus(1);
											ds.setCreateBy(userNo);
											ds.setCreateTime(date);
											ds.setLastUpdateBy(userNo);
											ds.setLastUpdateTime(date);
											ds.setDeleteFlg(0);
											termStructureService.saveSqlData(ds);
										}
									}catch(Exception e){
										logger.debug(e.getMessage());
									}
								}
							}
							map.remove(tdArr[0]);
						}
					}
					
					// 循环map1,剩下的为删除操作
					Iterator<Object> it1 = map.keySet().iterator();
					while (it1.hasNext()) {
						Object key = it1.next();
						List<DictField> dfList2 = termStructureService
								.selectFieldByFieldId(key.toString());
						
						for (DictField df : dfList2) {
							df.setDeleteFlg(1);
							df.setDeleteBy(userNo);
							df.setDeleteTime(new Date());
							df.setLastUpdateBy(userNo);
							df.setLastUpdateTime(new Date());
							termStructureService.updateFieldData(df);
							//添加删除术语结构的数据对日志
							deleteDictDataPair.add("删除：列名："+df.getFieldName()+"-注释："+df.getFieldDesc());
							//bug0039792
							List<Map<String,Object>>  tableList = termStructureService.selectTableStruct(cond.getTableName());
							Map<Object,Object> tableMap = new HashMap<Object,Object>();
							if(tableList.size()>0){
								for(int j=0;j<tableList.size();j++){
									Object key1 = tableList.get(j).get("field_name");
									Object value1 = tableList.get(j).get("field_name");
									tableMap.put(key1, value1);
								}
							}
						
							List<DictSql> sqlList = termStructureService.selectSqlById("", key.toString(), 1);
							for(DictSql ds : sqlList){
								if(ds.getDictSql().indexOf("add")>0 && !tableMap.containsKey(df.getFieldName())){
									ds.setDeleteBy(userNo);
									ds.setDeleteTime(new Date());
									ds.setLastUpdateBy(userNo);
									ds.setLastUpdateTime(new Date());
									ds.setDeleteFlg(1);
									termStructureService.updateSqlData(ds);
								}
							}
						}
					}
					
					//增加删除的sql 注意前提：如果表中不存在该列信息，就不应该生成删除列的sql,无意义！
					if(!StringUtils.isEmpty(cond.getDropColumn())){
						String[] dropColumnArr = cond.getDropColumn().split("&");
						for(int m=0;m<dropColumnArr.length;m++){
							String[] column = dropColumnArr[m].split("@");
							DictSql ds = new DictSql();
							ds.setSqlId(this.getUUID());
							ds.setDictId(cond.getDictId());
							ds.setFieldId(column[0]);
							ds.setDictSql(column[1]);
							
							SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Date date = sim.parse(column[2]);
							//1：确认过；0：未确认
							ds.setOptStatus(1);
							ds.setCreateBy(userNo);
							ds.setCreateTime(date);
							ds.setLastUpdateBy(userNo);
							ds.setLastUpdateTime(date);
							ds.setDeleteFlg(0);
							termStructureService.saveSqlData(ds);
							
						}
					}
					
				}
				
				//针对索引的操作
				String[] indexArr = cond.getIndexNames().split(",");
				Map<Object,Object> aMap = new HashMap<Object,Object>();
				for(int g=0;g<indexArr.length;g++){
					aMap.put(indexArr[g].toUpperCase(), indexArr[g].toUpperCase());
				}
				
				List<Map<String,Object>> indexList = termStructureService.selectIndexInfo(cond.getTableName());
				Map<Object,Object> indexMap = new HashMap<Object,Object>();
				String tableIndex = "";
				if(indexList.size()>0){
					String key = "";
					for(int i=0;i<indexList.size();i++){
						key = indexList.get(i).get("column_name").toString().toUpperCase();
						if(aMap.containsKey(key)){
							aMap.remove(key);
						}else{
							indexMap.put(key, key);
						}
						tableIndex += key +",";
					}
				}
				
				/*List<DictFieldDto> allFieldList = termStructureService.selectFieldByDictId(cond.getDictId());
				Map<Object,Object> dfMap = new HashMap<Object,Object>();
				String indexNames = "";
				for(DictFieldDto dto:allFieldList){
					if("Y".equalsIgnoreCase(dto.getIsPrimary())){
						dfMap.put(dto.getFieldName().toUpperCase(), dto.getFieldName().toUpperCase());
						indexNames += dto.getFieldName() +",";
					}
				}
				indexNames = indexNames.substring(0, indexNames.length()-1);*/
				
				
				
				boolean dropIndex = true;
				if(!StringUtils.isEmpty(cond.getDropColumn())){
					String[] arr = tableIndex.split(",");
					for(int u=0; u<arr.length;u++){
						if(cond.getDropColumn().toLowerCase().contains("drop column " + arr[u].toLowerCase())){
							  dropIndex = false;
						}
					}
				}
				
				//删除索引中的列时,即使新建一个相同的，索引也会被自动删除，需要重新补上索引的创建;
				if(!aMap.isEmpty() ||!indexMap.isEmpty() ||!dropIndex){
					String indexSql = "";
					List<Map<String,Object>> list = termStructureService.selectIndexInfo(cond.getTableName());
					Map<String,Object> map2 = new HashMap<String,Object>();
					
					for(Map<String,Object> map3:list){
						map2.put(map3.get("column_name").toString(), map3.get("column_name"));
					}
					//
					List<DictSql> list2 = termStructureService.selectSqlById(cond.getDictId(),"",1);
					for(DictSql entity : list2){
						if(entity.getDictSql().contains("drop column")){
							String columnName = entity.getDictSql().substring(entity.getDictSql().indexOf("column")+6, entity.getDictSql().length()-1).replaceAll(" ", "");
							if(map2.containsKey(columnName.toUpperCase())){
								dropIndex = false;
							}
						}
					}
					
					//针对历史数据，没有该索引的请看看下，不应该生成drop index的sql命令
					if(dropIndex && list.size()>0){
						//Author:liu_hongjie
			            // Date : 2014/9/9 15:58
			            //[BUG]0047781 ADD BEGIN						
						List<Map<String,Object>> indexNamelist = termStructureService.selectIndexNameInfo(cond.getTableName());	
						if(indexNamelist.size()>0){
							String key = "";
							for(int i=0;i<indexNamelist.size();i++){
								key = indexNamelist.get(i).get("column_name").toString().toUpperCase();
								if(key.contains("INDEX_")){
									indexSql = "drop index index_" + cond.getTableName() + ";";	
								}else if(key.contains("IDX_")){
									indexSql = "drop index idx_" + cond.getTableName() + ";"; 
								}
							}		
						}
						//[BUG]0047781 ADD END
					}
					//indexSql += "create index index_" + cond.getTableName() + " on " + cond.getTableName() + "(" + cond.getIndexNames() + ")";
					indexSql += "create index idx_" + cond.getTableName() + " on " + cond.getTableName() + "(" + cond.getIndexNames() + ")";
					
					List<DictSql> dsList = termStructureService.selectSqlById(cond.getDictId(), "INDEX"+cond.getTableName().toUpperCase(), 1);
					//如果有存在未执行的索引sql,修改此sql
					if(dsList.size()>0){
						for(DictSql ds : dsList){
							indexSql = "";
							//Author:liu_hongjie
				            // Date : 2014/9/9 15:58
				            //[BUG]0047781 ADD BEGIN
//							if(!ds.getDictSql().contains("drop index index_")){
//								indexSql += "create index index_" + cond.getTableName() + " on " + cond.getTableName() + "(" + cond.getIndexNames() + ")";
//							}else{
//								if(dropIndex){
//									indexSql = "drop index index_" + cond.getTableName() + ";"; 
//								}
//								indexSql += "create index index_" + cond.getTableName() + " on " + cond.getTableName() + "(" + cond.getIndexNames() + ")";
//							}
							if(dropIndex){
								if(ds.getDictSql().contains("drop index index_")){
								  indexSql = "drop index index_" + cond.getTableName() + ";"; 
								}
								if(ds.getDictSql().contains("drop index idx_")){
								  indexSql = "drop index idx_" + cond.getTableName() + ";"; 
								}
							}
							indexSql += "create index idx_" + cond.getTableName() + " on " + cond.getTableName() + "(" + cond.getIndexNames() + ")";
//							
							//[BUG]0047781 ADD END
							
							ds.setDictSql(indexSql);
							ds.setLastUpdateBy(userNo);
							ds.setLastUpdateTime(new Date());
							termStructureService.updateSqlData(ds);
						}
					}else{
						//不存在则创建一个
						DictSql ds = new DictSql();
						ds.setSqlId(this.getUUID());
						ds.setDictId(cond.getDictId());
						ds.setFieldId("INDEX"+cond.getTableName().toUpperCase());
						ds.setDictSql(indexSql);
						//1：确认过；0：未确认
						ds.setOptStatus(1);
						ds.setCreateBy(userNo);
						ds.setCreateTime(new Date());
						ds.setLastUpdateBy(userNo);
						ds.setLastUpdateTime(new Date());
						ds.setDeleteFlg(0);
						termStructureService.saveSqlData(ds);
					}
					
				}else{
					//最后一道防线是看是否存在待执行的index语句，如果存在，将其删除
					List<DictSql> dsList = termStructureService.selectSqlById(cond.getDictId(), "INDEX"+cond.getTableName().toUpperCase(), 1);
					if(dsList.size()>0){
						for(DictSql ds : dsList){
							ds.setLastUpdateBy(userNo);
							ds.setLastUpdateTime(new Date());
							ds.setDeleteBy(userNo);
							ds.setDeleteTime(new Date());
							ds.setDeleteFlg(1);
							termStructureService.updateSqlData(ds);
						}
					}
				}
				
				//对于更新操作，是需要校验一致不一致的，新增操作物理表不存在肯定是不一致的。
				compareMethod(cond,model);
				
				//把记录术语新增，编辑，和删除的日志数据对，拼成一个
				
				addDictDataPair.addAll(modifyDictDataPair);
				addDictDataPair.addAll(deleteDictDataPair);
				//插入组合后的数据对，形成日志
				this.insertAndMergeModifyLog(addDictDataPair, dictName);
			} else {
				dm.setDictId(uuid);
				if("S028".equalsIgnoreCase(cond.getSysId().trim())){
					dm.setIsEdit("Y");
				}else{
					dm.setIsEdit("N");
				}
				//默认为2:已发布
				dm.setStatus("2");
				dm.setIsSame("N");
				dm.setDeleteFlg(0);
				dm.setItemVersion(1);
				dm.setCreateBy(userNo);
				dm.setLastUpdateBy(userNo);
				dm.setCreateTime(new Date());
				dm.setLastUpdateTime(new Date());
				termStructureService.saveMainData(dm);
	
				// 存入dict_field表
				// 共同字段，后台默认加入
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("create_by", "创建人");
				map.put("create_time", "创建时间");
				map.put("update_count", "修改次数");
				map.put("last_update_by", "最后修改人");
				map.put("last_update_time", "最后修改时间");
				map.put("delete_by", "删除人");
				map.put("delete_time", "删除时间");
				map.put("delete_flg", "删除标识");
				map.put("opt_status", "操作状态");
				map.put("release_status", "发布状态");
				map.put("item_version", "版本号");
				map.put("uni_key", "查询唯一主键");
				
				//解析前台传来的表结构字符串
				if (!StringUtils.isEmpty(cond.getParamData())) {
					String[] trArr = cond.getParamData().split("@");
					for (int i = 0; i < trArr.length; i++) {
						String[] tdArr = trArr[i].split("#");
						//保存字段结构,存在dict_field
						saveFieldData(tdArr, userNo, uuid,this.getUUID(),null);
					}
	
					// 插入默认的字段
					Iterator<Object> it = map.keySet().iterator();
					while (it.hasNext()) {
						DictField df = new DictField();
						df.setDictId(uuid);
						df.setFieldId(this.getUUID());
						df.setDispOrder("0");
						df.setIsDefault("Y");
						df.setIsMust("N");
						df.setIsPrimary("N");
						df.setIsShow("N");
						df.setIsFilter("N");
						df.setDeleteFlg(0);
						df.setCreateBy(userNo);
						df.setCreateTime(new Date());
						
						Object key = it.next();
						df.setFieldName(key.toString());
						df.setFieldDesc(map.get(key).toString());
						//Author:liu_hongjie
			            // Date : 2014/9/9 13:58
			            //[BUG]0048840 ADD BEGIN
						//将“NVARCHAR2”改为“VARCHAR2”
						if (key.toString().indexOf("by") > 0) {
							df.setFieldType("VARCHAR2");
							df.setLength("50");
						}else if("uni_key".equalsIgnoreCase(key.toString().trim())){
							df.setIsMust("Y");
							df.setFieldType("VARCHAR2");
							df.setLength("50");
						} else if (key.toString().indexOf("time") > 0) {
							df.setFieldType("DATE");
						} else if (key.toString().indexOf("status") > 0
								|| key.toString().indexOf("flg") > 0) {
							df.setFieldType("VARCHAR2");
							df.setLength("1");
						}else {
							//update_count,item_version
							df.setFieldType("NUMBER");
							df.setLength("10");
							df.setPrecision("0");
						}
						//[BUG]0048840 ADD END
						termStructureService.saveFieldData(df);
					}
				}
			}
			if (!isEdit) {//如果是新增
				logUtils.insertLog("00604", "00604001", "新增术语结构[{}]", cond.getDictName());
			}
			model.put("status", 1);
		}catch(Exception e){
			logger.debug(e.getMessage());
			model.put("status", 0);
		}
		// 新增后跳转到list页面，需要清空查询条件，避免执行搜索操作！
		cond.setServiceId("");
		cond.setDictName("");
		cond.setTypeCd("");
		return model;
	}
	
	
	@RequestMapping(value = "/compareStruct")
	public ModelAndView  compareStruct(final TermSearchCondition cond,final ModelMap model) {
		try{
			compareMethod(cond,model);
		}catch(Exception e){
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("term/termStructCompare", model);
		return mav;
	}
	
	//术语结构列表，修改术语表结构后，执行sql语句的函数
	//当sql执行失败的时候，插入视图的操作也会回滚。
	@Transactional     
	@RequestMapping(value = "/doExecuteSql")
	public @ResponseBody Object  doExecuteSQL(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		int errorResult = 0;
		int rightResult = 0;
		String createSql = "";
		String sql = "";
		String[] contentArr = {};
		StringBuffer resultList = new StringBuffer();
		int exist = termStructureService.selectTableIsExist(cond.getTableName());
		if(exist!=1 || cond.getSqlContentArr().indexOf("create table")<0){
			String sqlContent = cond.getSqlContentArr();
			String[] idArr = {};
			//友好的提示很重要
			if(sqlContent.indexOf("create table")<0){ //非create table语句，前端提示：执行sql成功，物理表结构已更新!
				model.put("flag", 1);
				idArr = cond.getSqlIdArr().split("@");
				contentArr = sqlContent.split("@");
			}else{
				model.put("flag", 0);  //create table语句，前端提示：创建数据表XXX成功!
				contentArr = sqlContent.split(";");
			}
			
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			String userNo = auth.getName();
			
			for(int i=0;i<contentArr.length;i++){
				if(exist!=1){
					//为了保障反馈结果是，create table 也是分行展示的
					if(i==0){
						createSql = contentArr[i];
					}else{
						createSql = contentArr[i].replaceFirst("<br/>", "");
					}
				}
				sql = contentArr[i].replaceAll("<br/>", "").replaceAll(";", "");
		
				if(!StringUtils.isEmpty(sql)){
					try{
						termStructureService.executeTable(sql);
						//返回执行结果
						resultList.append("<tr><td>");
						if(exist!=1){
							resultList.append(createSql);
						}else{
							resultList.append(sql);
						}
						resultList.append(";</td></tr>");
						resultList.append("@<tr><td style=\"color:#35ab25;\">");
						resultList.append("<img src=\"../images/btn/start.png\" />执行成功!");
						resultList.append("</td></tr>");
						rightResult = 1;
						//对于createTable是不进行sql的删除的，一次只删除一条记录，根据sql_id
						if(sqlContent.indexOf("create table")<0 && sqlContent.indexOf("add constraint")<0){
							//如果是第一次创建，页面是不存在dictId的，需要去dictMain表查询
							if(StringUtils.isEmpty(cond.getDictId())){
								cond.setDictId(termStructureService.selectDictIdByTableName(cond.getTableName()));
							}
							//将dict_id对应的所有sql全部删除;
							List<DictSql> dsList = termStructureService.selectSqlById(cond.getDictId(),idArr[i],1);	
							for(DictSql ds : dsList){
								ds.setDeleteFlg(1);
								ds.setLastUpdateBy(userNo);
								ds.setLastUpdateTime(new Date());
								ds.setDeleteBy(userNo);
								ds.setDeleteTime(new Date());
								termStructureService.updateSqlData(ds);
							}
						}
					}catch(Exception e){
						logger.error(e.getMessage());
						List<DictSql> dsList = termStructureService.selectSqlById(cond.getDictId(),idArr[i],1);	
						for(DictSql ds : dsList){
							ds.setDeleteFlg(1);
							//-1存在异常
							ds.setOptStatus(-1);
							ds.setLastUpdateBy(userNo);
							ds.setLastUpdateTime(new Date());
							ds.setDeleteBy(userNo);
							ds.setDeleteTime(new Date());
							termStructureService.updateSqlData(ds);
						}
						resultList.append("<tr><td>");
						resultList.append(sql);
						resultList.append(";</td></tr>");
						resultList.append("@<tr><td style=\"color:red;\">");
						resultList.append("<img src=\"../images/deleterow.png\" />执行失败!");
						resultList.append(e.getMessage().subSequence(e.getMessage().indexOf("Exception:")+10, e.getMessage().indexOf("SQL=[")));
						resultList.append("</td></tr>");
						errorResult = 1;
						model.put("flag", -1);
					}
				}
			}
			String dictName = termStructureService.selectDictNameByTableName(cond.getTableName());
			logUtils.insertLog("00602", "00602006", "术语结构[{}]：执行SQL", dictName);
			//校验是否一致！！
			compareMethod(cond,model);
			//当术语结构更新的时候，视图需同步更新
			int result = 0;
			if(sqlContent.split("[(]")[0].indexOf("add")>0){
			  //新增列
			  List<DictView> views = viewSetService.selectViewByDictIdandViewType(cond.getDictId(),null);
			  String[] fieldIds = cond.getSqlIdArr().split("@");
			  for(int i=0;i<views.size();i++){
				for(int j=0;j<fieldIds.length/2;j++){
					ViewField vf = new ViewField();
					vf.setFieldId(fieldIds[2*j+1]);
					vf.setViewFieldId(getUUID());
					vf.setViewId(views.get(i).getViewId());
					vf.setEditFlg(0);
					vf.setItemFlg(0);
					vf.setItemOrder(0);
					vf.setFieldOrder(0);
					vf.setOrderFlg(0);
					vf.setRetrievalFlg(0);
					vf.setDeleteFlg(0);
					vf.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
					vf.setCreateTime(new Date());
					result = viewSetService.addViewsFields(vf);	
			  	}
			  }
			} else if (sqlContent.split("[(]")[0].indexOf("drop")>0) {
				// 删除列
				List<DictView> views = viewSetService.selectViewByDictIdandViewType(cond.getDictId(), null);
				String[] fieldId = cond.getSqlIdArr().split("@");
				for (int i = 0; i < views.size(); i++) {
					String viewId = views.get(i).getViewId();
					ViewField vf= viewSetService.selectFieldByViewIdAndFieldId(viewId,fieldId[0]);
					viewSetService.deleteViewFieldByViewFieldId(vf.getViewFieldId());
				}
			}
		}else{
			//物理表已存在
			model.put("flag", 2);
		}
		
		//存在成功，也存在异常的sql
		if(errorResult==1 && rightResult==1){
			model.put("flag", -2);
		}
		model.put("resultList", resultList);
		return model;
	}
	
	
	public @ResponseBody Object compareMethod(final TermSearchCondition cond,final ModelMap model){
		//取术语结构对应的字段信息
		List<DictMain> ts = termStructureService.selectTermStructById(cond.getDictId());
		String tableName = "";
		String dictName = "";
		for(DictMain dm:ts){
			dictName = dm.getDictName();
			tableName = dm.getTableName();
		}
		
		//根据dictId获取术语结构模型信息
		List<Map<String,Object>>  tsList = termStructureService.selectTermStruct(cond.getDictId());
		if(tsList.size()>0){
			Map<Object,Object> map1 = new HashMap<Object,Object>();
			for(int i=0;i<tsList.size();i++){
				if(!StringUtils.isEmpty(tsList.get(i).get("field_name"))&&!StringUtils.isEmpty(tsList.get(i).get("field_type"))){
					map1.put(tsList.get(i).get("field_name").toString().toUpperCase(), tsList.get(i).get("field_type").toString().toUpperCase());
				}
				
			}
			List<Map<Object,Object>> resultList = new ArrayList<Map<Object,Object>>();
			//取物理表对应的字段信息
			//先判断表是否存在
			int exist = termStructureService.selectTableIsExist(tableName.toUpperCase());
			if(exist==1){
				List<Map<String,Object>>  tableList = termStructureService.selectTableStruct(tableName);
				Map<Object,Object> map2 = new HashMap<Object,Object>();
				if(tableList.size()>0){
					for(int j=0;j<tableList.size();j++){
						map2 = new HashMap<Object,Object>();
						Object key = tableList.get(j).get("field_name");
						Object value = tableList.get(j).get("field_type");
						Object defaultValue = tableList.get(j).get("default_value");
						value = value + "," + (defaultValue==null?"-1":defaultValue.toString().replaceAll("timestamp  ", ""));
						String[] tArr = value.toString().split(",");
						if(map1.containsKey(key)){
							String[] sArr = map1.get(key).toString().split(",");
							if(!map1.get(key).toString().trim().equalsIgnoreCase(value.toString().replaceAll("''", "-1").replaceAll("timestamp ", "").trim())){
								map2.put("sfieldname", key.toString().toUpperCase()+": ");
								for(int m=0;m<sArr.length;m++){
									map2.put("sfieldtype",("-1".equalsIgnoreCase(sArr[0].toUpperCase().trim())?"空":sArr[0].toUpperCase())+", ");
									map2.put("slength",("-1".equalsIgnoreCase(sArr[1].toUpperCase().trim())?"空":sArr[1].toUpperCase())+", ");
									map2.put("sprecision",("-1".equalsIgnoreCase(sArr[2].toUpperCase().trim())?"空":sArr[2].toUpperCase())+", ");
									map2.put("sismust",("Y".equalsIgnoreCase(sArr[3].toUpperCase().trim())?"√":"×")+", ");
									map2.put("sdefaultvalue","-1".equalsIgnoreCase(sArr[4].toUpperCase().trim())?"空":sArr[4].toUpperCase());
								}
								
								map2.put("tfieldname", key.toString().toUpperCase()+": ");
								for(int n=0;n<tArr.length;n++){
									map2.put("tfieldtype",("-1".equalsIgnoreCase(tArr[0].toUpperCase().trim())?"空":tArr[0].toUpperCase())+", ");
									map2.put("tlength",("-1".equalsIgnoreCase(tArr[1].toUpperCase().trim())?"空":tArr[1].toUpperCase())+", ");
									map2.put("tprecision",("-1".equalsIgnoreCase(tArr[2].toUpperCase().trim())?"空":tArr[2].toUpperCase())+", ");
									map2.put("tismust",("Y".equalsIgnoreCase(tArr[3].toUpperCase().trim())?"√":"×")+", ");
									map2.put("tdefaultvalue",StringUtils.isEmpty(tArr[4].toUpperCase().replaceAll("' '", "").replaceAll("timestamp ", "").trim())?"空":tArr[4].toUpperCase());
								}
								resultList.add(map2);
								
								//修改操作后，待执行的sql通过对比生成
								
							}
							map1.remove(key);
						}else{
							map2.put("sfieldname", "");
							map2.put("sfieldtype","");
							map2.put("slength","");
							map2.put("sprecision","");
							map2.put("sismust","");
							map2.put("sdefaultvalue","");

							map2.put("tfieldname", key.toString().toUpperCase()+": ");
							for(int n=0;n<tArr.length;n++){
								map2.put("tfieldtype",("-1".equalsIgnoreCase(tArr[0].toUpperCase().trim())?"空":tArr[0].toUpperCase())+", ");
								map2.put("tlength",("-1".equalsIgnoreCase(tArr[1].toUpperCase().trim())?"空":tArr[1].toUpperCase())+", ");
								map2.put("tprecision",("-1".equalsIgnoreCase(tArr[2].toUpperCase().trim())?"空":tArr[2].toUpperCase())+", ");
								map2.put("tismust",("Y".equalsIgnoreCase(tArr[3].toUpperCase().trim())?"√":"×")+", ");
								map2.put("tdefaultvalue",StringUtils.isEmpty(tArr[4].toUpperCase().replaceAll("' '", "").replaceAll("timestamp ", "").trim())?"空":tArr[4].toUpperCase());
							}
							resultList.add(map2);
						}
					}
				}
				Iterator<Object> it = map1.keySet().iterator();
				while (it.hasNext()) {
					Object key2 = it.next();
					map2 = new HashMap<Object,Object>();
					map2.put("sfieldname", key2.toString().toUpperCase()+": ");
					
					String[] sArr = map1.get(key2).toString().split(",");
					for(int m=0;m<sArr.length;m++){
						map2.put("sfieldtype",("-1".equalsIgnoreCase(sArr[0].toUpperCase().trim())?"空":sArr[0].toUpperCase())+", ");
						map2.put("slength",("-1".equalsIgnoreCase(sArr[1].toUpperCase().trim())?"空":sArr[1].toUpperCase())+", ");
						map2.put("sprecision",("-1".equalsIgnoreCase(sArr[2].toUpperCase().trim())?"空":sArr[2].toUpperCase())+", ");
						map2.put("sismust",("Y".equalsIgnoreCase(sArr[3].toUpperCase().trim())?"√":"×")+", ");
						map2.put("sdefaultvalue","-1".equalsIgnoreCase(sArr[4].toUpperCase().trim())?"空":sArr[4].toUpperCase());
					}
					
					map2.put("tfieldname", "");
					map2.put("tfieldtype","");
					map2.put("tlength","");
					map2.put("tprecision","");
					map2.put("tismust","");
					map2.put("tdefaultvalue","");
					
					resultList.add(map2);
				}
				//根据校验结果，更新dict_main表的is_same字段
				List<DictMain> entityList = termStructureService.selectTermStructById(cond.getDictId());
				DictMain  entity = entityList.get(0);
				if(resultList.size()==0){
					//无差异
					model.addAttribute("status", 1);
					entity.setIsSame("Y");
				}else{
					//有差异
					model.addAttribute("status", 2);
					entity.setIsSame("N");
				}
				termStructureService.updateMainData(entity);
				model.addAttribute("resultList", resultList);
			}else{
				//物理表不存在，有术语结构
				model.addAttribute("status", 0);
			}
		}else{
			//无属于结构，dict_main有数据，但是dict_field无数据
			model.addAttribute("status", 3);
		}
		
		model.addAttribute("dictId",cond.getDictId());
		model.addAttribute("dictName", dictName);
		model.addAttribute("tableName", tableName);
		return model;
	}
	
	
	
	@RequestMapping(value = "/selectValueIsExist")
	public @ResponseBody Object  selectValueIsExist(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		//校验时，分新增和编辑两种情况，如果新增dictId为空，编辑不为空，编辑时校验需要先查一该术语结构的信息，同名是可以的
		if(!StringUtils.isEmpty(cond.getDictId())){
			DictMain dm = termStructureService.selectTermStructById(cond.getDictId()).get(0);
			if(!StringUtils.isEmpty(cond.getDictName())){
				if(dm.getDictName().equalsIgnoreCase(cond.getDictName())){
					model.put("status", 0);
					return model;
				}
			}else if(!StringUtils.isEmpty(cond.getTableName())){
				if(dm.getTableName().equalsIgnoreCase(cond.getTableName())){
					model.put("status", 0);
					return model;
				}
				
			}else if(!StringUtils.isEmpty(cond.getServiceId())){
				if(dm.getServiceId().equalsIgnoreCase(cond.getServiceId())){
					model.put("status", 0);
					return model;
				}
			}
		}
		int a = termStructureService.selectValueIsExist(cond.getDictName(),cond.getTableName(),cond.getServiceId());
		if(!StringUtils.isEmpty(cond.getTableName())){
			int b = termStructureService.selectTableIsExist(cond.getTableName());
			if(a>0 || b>0){
				if(a>0&&b>0){
					model.put("status", 3); //术语结构和物理表都存在该表名
				}else if(a>0){
					model.put("status", 1); //仅术语结构存在对应表名
				}else if(b>0){
					model.put("status", 2); //物理表存在对应表名
				}
			}else{
				model.put("status", 0);
			}
		}else{
			if(a>0){
				model.put("status", 1);
			}else{
				model.put("status", 0);
			}
		}
		return model;
	}
	
	@RequestMapping(value = "/selectExecuteSql")
	public @ResponseBody Object  selectExecuteSql(final TermSearchCondition cond,final ModelMap model) {
	
		//对于删除a，新增a，bug0039959，应该先查询除所有删除的fieldName，然后再比对新增的是否在删除中，那么这样的场景是允许新增的
		Map<Object, Object> fieldMap = new HashMap<Object, Object>();
		if(!StringUtils.isEmpty(cond.getDropColumn())){
			String[] dropColumnArr = cond.getDropColumn().split("&");
			for(int m=0;m<dropColumnArr.length;m++){
				String[] column = dropColumnArr[m].split("@");
				List<DictField> dfList = termStructureService.selectFieldByFieldId(column[0]);
				for(DictField df : dfList){
					fieldMap.put(df.getFieldName(), df.getFieldName());
				}
				
			}
		}
				
		model.clear();
		model.put("warnMsg", "");
		//判断是修改字段还是新增字典，新增时cond.getFieldId()为空
		StringBuffer sql = new StringBuffer();
		//首先判断修改的该字段是否在物理表中存在
		List<Map<String,Object>> tableList = termStructureService.selectTableStruct(cond.getTableName());
		Map<Object,Object> tableFields = new HashMap<Object,Object>();
		for(Map<String,Object> map :tableList){
			tableFields.put(map.get("field_name"), map.get("nullable"));
		}
		
		
		if(!StringUtils.isEmpty(cond.getFieldId())){
			List<DictField> list = termStructureService.selectFieldByFieldId(cond.getFieldId());
			DictField entity = list.get(0);
			
			boolean type = !cond.getFieldType().equalsIgnoreCase(entity.getFieldType()==null?"":entity.getFieldType());
			boolean desc = !cond.getFieldDesc().equalsIgnoreCase(entity.getFieldDesc()==null?"":entity.getFieldDesc());
			boolean length = !cond.getLength().equalsIgnoreCase(entity.getLength()==null?"":entity.getLength());
			boolean precision = !cond.getPrecision().equalsIgnoreCase(entity.getPrecision()==null?"":entity.getPrecision());
			boolean isMust = !cond.getIsMust().equalsIgnoreCase(entity.getIsMust()==null?"":entity.getIsMust());
			boolean defaultValue = !cond.getDefaultValue().equalsIgnoreCase(entity.getDefaultValue()==null?"":entity.getDefaultValue().replace("'", ""));
			
			boolean dif = type || length || precision || defaultValue || isMust;
			int columnExist = termStructureService.selectColumnIsExistInTable(cond.getFieldName(), cond.getTableName());
			
			if(tableFields.containsKey(entity.getFieldName().toUpperCase())){
				int result = termStructureService.selectDataIsExist(entity.getFieldName(),cond.getTableName());
				if(result>0){
					if(dif){
						model.put("warnMsg", "当下修改未保存的内容可能在SQL执行时存在风险!");
					}
				}
				
				if(dif){
					//在执行修改操作时，先判定该列是否在物理表中存在，如果不存在，那么修改sql不应该出现
					int exist = termStructureService.selectColumnIsExistInTable(cond.getFieldName(), cond.getTableName());
					if(exist>0){
						sql.append("alter table ");
						sql.append(cond.getTableName());
						sql.append(" modify (");
						sql.append(cond.getFieldName());
						sql.append(" ");
						sql.append(cond.getFieldType());
						if(!("NUMBER(1)".equalsIgnoreCase(cond.getFieldType())||"DATE".equalsIgnoreCase(cond.getFieldType()))){
							sql.append(" (");
							sql.append(cond.getLength());
							if(!StringUtils.isEmpty(cond.getPrecision())){
								sql.append(",");
								sql.append(cond.getPrecision());
							}else{
								sql.append(" ");
							}
							sql.append(") ");
						}
						
						sql.append(" default ");
						if(!StringUtils.isEmpty(cond.getDefaultValue())){
							
							if(cond.getFieldType().indexOf("BER")>0){
								sql.append(cond.getDefaultValue());
							}else{
								if("DATE".equalsIgnoreCase(cond.getFieldType())){
									sql.append(" timestamp ");
								}
								sql.append(" '");
								sql.append(cond.getDefaultValue());
								sql.append("' ");
							}
						}else{
							sql.append("''");
						}
						
						if(!StringUtils.isEmpty(cond.getIsMust())){
							//如果修改的字段本身就是not null，那么就不要重复not null 执行时会报错！ 注意：判断是由于取nullable，意思相反，然后再新增相同的列，那么如果是Y，还是应该为not null
							if("Y".equalsIgnoreCase(tableFields.get(entity.getFieldName().toUpperCase()).toString()) || fieldMap.containsKey(cond.getFieldName())){
								if("Y".equalsIgnoreCase(cond.getIsMust())){
									sql.append(" not null ");
								}
							}else{
								if("N".equalsIgnoreCase(cond.getIsMust())){
									sql.append(" null ");
								}
							}
						}
						
						sql.append(" );\n ");
						
						if(desc || columnExist==0){
							sql.append("comment on column ");
							sql.append(cond.getTableName());
							sql.append(".");
							sql.append(cond.getFieldName());
							sql.append(" IS '");
							sql.append(cond.getFieldDesc());
							sql.append("';\n");
						}
						
					}
				}else{
					if(desc || columnExist==0){
						sql.append("comment on column ");
						sql.append(cond.getTableName());
						sql.append(".");
						sql.append(cond.getFieldName());
						sql.append(" is '");
						sql.append(cond.getFieldDesc());
						sql.append("';\n");
					}
					
				}
			}else{
				sql.append("alter table ");
				sql.append(cond.getTableName());
				sql.append(" add (");
				sql.append(cond.getFieldName());
				sql.append(" ");
				sql.append(cond.getFieldType());
				
				if(!("NUMBER(1)".equalsIgnoreCase(cond.getFieldType())||"DATE".equalsIgnoreCase(cond.getFieldType()))){
					sql.append(" (");
					sql.append(cond.getLength());
					if(!StringUtils.isEmpty(cond.getPrecision())){
						sql.append(",");
						sql.append(cond.getPrecision());
					}
					sql.append(") ");
				}
				
				sql.append(" default ");
				if(!StringUtils.isEmpty(cond.getDefaultValue())){
					if(cond.getFieldType().indexOf("BER")>0){
						sql.append(cond.getDefaultValue());
					}else{
						if("DATE".equalsIgnoreCase(cond.getFieldType())){
							sql.append(" timestamp ");
						}
						sql.append(" '");
						sql.append(cond.getDefaultValue());
						sql.append("' ");
					}
				}else{
					sql.append("''");
				}
				
				if(!StringUtils.isEmpty(cond.getIsMust())){
					//如果修改的字段本身就是not null，那么就不要重复not null 执行时会报错！ 注意：判断是由于取nullable，意思相反，然后再新增相同的列，那么如果是Y，还是应该为not null
					if(tableFields.containsKey(entity.getFieldName().toUpperCase())){
						if("Y".equalsIgnoreCase(tableFields.get(entity.getFieldName().toUpperCase()).toString()) || fieldMap.containsKey(cond.getFieldName())){
							if("Y".equalsIgnoreCase(cond.getIsMust())){
								sql.append(" not null ");
							}
						}else{
							if("N".equalsIgnoreCase(cond.getIsMust())){
								sql.append(" null ");
							}
						}
					}else{
						if("Y".equalsIgnoreCase(cond.getIsMust())){
							sql.append(" not null ");
						}else if("N".equalsIgnoreCase(cond.getIsMust())){
							sql.append(" null ");
						}
					}
				}
				
				sql.append(" );\n ");
				
				//如果该列在表中不存在
				
				if(desc || columnExist==0){
					sql.append("comment on column ");
					sql.append(cond.getTableName());
					sql.append(".");
					sql.append(cond.getFieldName());
					sql.append(" is '");
					sql.append(cond.getFieldDesc());
					sql.append("';\n");
				}
				
			}
		}else{
			//新增一个字段的sql
			sql.append("alter table ");
			sql.append(cond.getTableName());
			sql.append(" add (");
			sql.append(cond.getFieldName());
			sql.append(" ");
			sql.append(cond.getFieldType());
			
			if(!"NUMBER(1)".equalsIgnoreCase(cond.getFieldType())&&!"DATE".equalsIgnoreCase(cond.getFieldType())){
				sql.append(" ( ");
				sql.append(cond.getLength());
				if(!StringUtils.isEmpty(cond.getPrecision())){
					sql.append(",");
					sql.append(cond.getPrecision());
				}
				sql.append(" ) ");
			}
			
			sql.append(" default ");
			if(!StringUtils.isEmpty(cond.getDefaultValue())){
				if(cond.getFieldType().indexOf("BER")>0){
					sql.append(cond.getDefaultValue());
				}else{
					if("DATE".equalsIgnoreCase(cond.getFieldType())){
						sql.append(" timestamp ");
					}
					sql.append(" '");
					sql.append(cond.getDefaultValue());
					sql.append("' ");
				}
			}else{
				sql.append("''");
			}
			
			if(!StringUtils.isEmpty(cond.getIsMust())){
				//如果修改的字段本身就是not null，那么就不要重复not null 执行时会报错！ 注意：判断是由于取nullable，意思相反,如果先删除一列，然后再新增相同的列，那么如果是Y，还是应该为not null
				if(tableFields.containsKey(cond.getFieldName().toUpperCase())){
					if("Y".equalsIgnoreCase(tableFields.get(cond.getFieldName().toUpperCase()).toString()) || fieldMap.containsKey(cond.getFieldName())){
						if("Y".equalsIgnoreCase(cond.getIsMust())){
							sql.append(" not null ");
						}
					}else{
						if("N".equalsIgnoreCase(cond.getIsMust())){
							sql.append(" null ");
						}
					}
				}else{
					if("Y".equalsIgnoreCase(cond.getIsMust())){
						sql.append(" not null ");
					}else if("N".equalsIgnoreCase(cond.getIsMust())){
						sql.append(" null ");
					}
				}
			}
			
			sql.append(");\n");
			
			sql.append("comment on column ");
			sql.append(cond.getTableName());
			sql.append(".");
			sql.append(cond.getFieldName());
			sql.append(" is '");
			sql.append(cond.getFieldDesc());
			sql.append("';\n");
			
		}
		if(StringUtils.isEmpty(cond.getFieldId())){
			cond.setFieldId(this.getUUID());
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.put("sql", cond.getFieldId() + "&" + sql.toString() + "&" + sdf.format(new Date()));
		return model;
	}
	
	
	@RequestMapping(value = "/selectDataIsExist")
	public @ResponseBody Object  selectDataIsExist(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		//首先判断修改的该字段是否在物理表中存在
		List<Map<String,Object>> tableList = termStructureService.selectTableStruct(cond.getTableName());
		Map<Object,Object> tableFields = new HashMap<Object,Object>();
		for(Map<String,Object> map :tableList){
			tableFields.put(map.get("field_name"), map.get("field_name"));
		}
		//删除时注意判断以下三种情况：1:默认表中有该字段，无数据,返回0；2：表中有该字段，有数据，返回1；3：表中根本没有该字段，返回-1；
		int result = 0;
		if(tableFields.containsKey(cond.getFieldName().toUpperCase())){
			result = termStructureService.selectDataIsExist(cond.getFieldName(),cond.getTableName());
		}else{
			result = -1;
		}
		model.put("result", result);
		return model;
	}
	
	@RequestMapping(value = "/selectFieldIsExist")
	public @ResponseBody Object  selectFieldIsExist(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		//首先判断修改的该字段是否在物理表中存在
		boolean deleteFlg = false;
		if(!StringUtils.isEmpty(cond.getDropColumn())){
			String param = "drop column " + cond.getFieldName() + ";";
			if(cond.getDropColumn().toLowerCase().contains(param.toLowerCase())){
				deleteFlg = true;
			}
		}
		int result = termStructureService.selectFieldIsExist(cond.getDictId(),cond.getFieldName());
		if(deleteFlg && result==1){
			model.put("result", 0);
		}else{
			model.put("result", result);
		}
		return model;
	}
	
	@RequestMapping(value = "/selectAllExecuteSql")
	public @ResponseBody Object  selectAllExecuteSql(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		List<DictSql> list = termStructureService.selectSqlById(cond.getDictId(),"",1);
		StringBuffer  sql = new StringBuffer();
		StringBuffer  idArr = new StringBuffer();
		StringBuffer  sqlArr = new StringBuffer();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				DictSql ds = list.get(i);
				if(!StringUtils.isEmpty(ds.getDictSql())){
					sql.append("<tr>");
					sql.append("<td>");
					if((ds.getDictSql().contains("comment on column") || ds.getDictSql().contains("index_"))
							&&( (ds.getDictSql().split(";").length==3&&StringUtils.isEmpty(ds.getDictSql().split(";")[2]))
							    || ds.getDictSql().split(";").length==2)){
						sql.append(ds.getDictSql().split(";")[0]);
						sql.append(";</td></tr><tr><td>");
						sql.append(ds.getDictSql().split(";")[1]);
						sql.append(";");
						
						sqlArr.append(ds.getDictSql().split(";")[0]);
						sqlArr.append(";@");
						sqlArr.append(ds.getDictSql().split(";")[1]);
						sqlArr.append(";");
						
						idArr.append(ds.getFieldId());
						idArr.append("@");
						idArr.append(ds.getFieldId());
						
					}else if((ds.getDictSql().contains("comment on column") || ds.getDictSql().contains("index_"))
							&&( (ds.getDictSql().split(";").length==4&&StringUtils.isEmpty(ds.getDictSql().split(";")[3]))
								||  ds.getDictSql().split(";").length==3)){
						sql.append(ds.getDictSql().split(";")[0]);
						sql.append(";</td></tr><tr><td>");
						sql.append(ds.getDictSql().split(";")[1]);
						sql.append(";</td></tr><tr><td>");
						sql.append(ds.getDictSql().split(";")[2]);
						sql.append(";");
						
						sqlArr.append(ds.getDictSql().split(";")[0]);
						sqlArr.append(";@");
						sqlArr.append(ds.getDictSql().split(";")[1]);
						sqlArr.append(";@");
						sqlArr.append(ds.getDictSql().split(";")[2]);
						sqlArr.append(";");
						
						idArr.append(ds.getFieldId());
						idArr.append("@");
						idArr.append(ds.getFieldId());
						idArr.append("@");
						idArr.append(ds.getFieldId());
					}else{
						sql.append(ds.getDictSql());
						idArr.append(ds.getFieldId());
						sqlArr.append(ds.getDictSql());
					}
					if((ds.getDictSql().contains("drop column") || ds.getDictSql().contains(";"))){
						sql.append("</td></tr>@");
					}else{
						sql.append(";</td></tr>@");
					}
				}
				//sqlArr.append(ds.getDictSql());
				if(i!=list.size()-1 ||list.size()==1){
					idArr.append("@");
					sqlArr.append("@");
				}
			}
			if(StringUtils.isEmpty(sql)){
				sql.append("术语结构无变动、无SQL预览!");
			}
		}else{
			sql.append("术语结构无变动、无SQL预览!");
		}
		model.put("sql", sql.toString());
		model.put("idArr", idArr.toString());
		//为了保障;存在,将可能丢失;的地方重新部署上;
		
		model.put("sqlArr", sqlArr.toString().replaceAll(";", "").replaceAll("@", ";@"));
		return model;
	}
	
	@RequestMapping(value = "/authorityConfirm")
	public @ResponseBody Object  authorityConfirm(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		List<DictMain> list = termStructureService.selectTermStructById(cond.getDictId());
		
		if(list.size()>0){
			for(DictMain dm : list){
				if("edit".equalsIgnoreCase(cond.getOperation())){
					//0：无同步信息 1：未启动状态  2：启动执行中 3：启动待执行
					if(dm.getLockStatus()==0 ||dm.getLockStatus()==1){
						model.put("result", 1);
					}else{
						model.put("result", 0);
					}
				}else if("delete".equalsIgnoreCase(cond.getOperation())){
					if(dm.getLockStatus()==0 ||dm.getLockStatus()==1){
						int tableIsExist = termStructureService.selectTableIsExist(cond.getTableName());
						if(tableIsExist==1){
							model.put("result", 1);
						}else{
							model.put("result", 2);
						}
					}else{
						model.put("result", 0);
					}
					//$Author :LZ
					//$Date : 2016/9/28 15:15$
					//[BUG]0082730 MODIFY BEGIN 
					//查看该术语是否用于术语映射
					int termIsMapping = termStructureService.selectTermIsMapping(cond.getTableName());
					if(termIsMapping==1){
						model.put("result", 3);
					}
					//[BUG]0082730 MODIFY END
				}
				
			}
		}
		return model;
	}
	
	
	@RequestMapping(value = "/mgErrorSql")
	public ModelAndView  mgErrorSql(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		//0:未确认 1：已确认  -1：执行sql异常  2：归档
		List<DictSql> list = termStructureService.selectSqlById("",cond.getDictId(), -1);
		model.put("list", list);
		ModelAndView mav = new ModelAndView("term/termStructMgErrorSql", model);
		return mav;
	}
	
	@RequestMapping(value = "/selectIsOracleKeyWord")
	public @ResponseBody Object  selectIsOracleKeyWord(final TermSearchCondition cond,final ModelMap model) {
		model.clear();
		int result = termStructureService.selectIsOracleKeyWord(cond.getFieldName());
		model.put("result", result);
		return model;
	}
	
	@RequestMapping(value = "/selectOneDeleteSql")
	public @ResponseBody Object selectOneDeleteSql(final TermSearchCondition cond,
			final ModelMap model) {
		//在执行修改操作时，先判定该列是否在物理表中存在，如果不存在，那么修改sql不应该出现
		int exist = termStructureService.selectColumnIsExistInTable(cond.getFieldName(), cond.getTableName());
		if(exist>0){
			model.clear();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String deleteSql = cond.getFieldId() + "@ alter table " + cond.getTableName() + " drop column " + cond.getFieldName() + ";@" + sdf.format(new Date()) + "&";
			model.put("deleteSql", deleteSql);
		}
		return model;
	}
}
