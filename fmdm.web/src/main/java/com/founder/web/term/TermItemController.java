package com.founder.web.term;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictMain;
import com.founder.fmdm.entity.DictView;
import com.founder.fmdm.entity.ViewField;
import com.founder.fmdm.service.sysmgt.ViewSetService;
import com.founder.fmdm.service.term.TermItemListService;
import com.founder.fmdm.service.term.TermListService;
import com.founder.fmdm.service.term.TermStructService;
import com.founder.fmdm.service.term.dto.DictItemDto;
import com.founder.fmdm.service.term.dto.DictMainDto;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/term")
public class TermItemController extends AbstractController {
	@Autowired
	private LogUtils logUtils;
	
	@Autowired
	private TermItemListService termItemListService;

	@Autowired
	private TermListService termListService;
	
	@Autowired
	private TermStructService termStructService;
	
	@Autowired
	ViewSetService viewSetService;
	//release_state "c":现在,"h":历史,"a":待发布,"r":驳回,"d":待审批
	private static final String RELEASE_STATE_C = "c";
	private static final String RELEASE_STATE_D = "d";
	private static final String RELEASE_STATE_A = "a";
	private static final String RELEASE_STATE_R = "r";
	private static final String RELEASE_STATE_H = "h";
	//status "1":isShow,"2":isDefault,"3":isPrimary
	//待审批
	private final static String STATUS_0= "0";
	//待发布
	private final static String STATUS_1= "1";
	//已发布
	private final static String STATUS_2= "2";
	//待审批 待发布
	private final static String STATUS_3= "3";
	
	//编辑画面 0
	private final static String TITLE_0="0";
	//审批画面1
	private final static String TITLE_1="1";
	//发布画面2
	private final static String TITLE_2="2";
	
	private final static String Y ="Y";
	
	private final static String N ="N";
	/**
	 * 字典数据一览
	 * @param dictSearchCondition
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dictitemlist")
	public ModelAndView showPage(final HttpServletRequest request,final TermMainCondition dictSearchCondition, final ModelMap model,final HttpServletResponse response) throws Exception{
		SelectOptions options = getSelectOptions(dictSearchCondition);
		String dictId = dictSearchCondition.getDictId();
		String title = dictSearchCondition.getTitle();
		String scanType = "2";//查看
		String searchType = "0";//检索
		String editType = "1";//编辑
		//增加导出excel功能  根据此标记区分是导出还是查询 add by wp 
		String export = request.getParameter("export");
		String searchAll = dictSearchCondition.getSearchAll();
		String viewTypeId = request.getParameter("viewTypeId");
		String[] viewMsg = request.getParameter("viewTypeId").split(",");
		String viewType = viewMsg[0];
		String viewId = viewTypeId.substring(2);
		
//		String viewId = request.getParameter("viewTypeId").split(",")[1];
		/**
		 * @author li_zhong2
		 * 对于术语Id相同,视图类型也相同的视图，权限取并集
		 */
		Map<String,Integer> viewFlg = new HashMap<String,Integer>();
		for(int i=1;i<viewMsg.length;i++){
			DictView view = viewSetService.selectViewByViewId(viewMsg[i]);
			if(viewFlg.containsKey("addFlg")){
				if(viewFlg.get("addFlg")==0){
					viewFlg.remove("addFlg");
					viewFlg.put("addFlg", view.getAddFlg());
				}
			}else{
				viewFlg.put("addFlg", view.getAddFlg());
			}
			
			
			if(viewFlg.containsKey("approveFlg")){
				if(viewFlg.get("approveFlg")==0){
					viewFlg.remove("approveFlg");
					viewFlg.put("approveFlg", view.getApproveFlg());
				}
			}else{
				viewFlg.put("approveFlg", view.getApproveFlg());
			}
			
			
			if(viewFlg.containsKey("releaseFlg")){
				if(viewFlg.get("releaseFlg")==0){
					viewFlg.remove("releaseFlg");
					viewFlg.put("releaseFlg", view.getReleaseFlg());
				}
			}else{
				viewFlg.put("releaseFlg", view.getReleaseFlg());
			}
			
			
			if(viewFlg.containsKey("deleteFlg")){
				if(viewFlg.get("deleteFlg")==0){
					viewFlg.remove("deleteFlg");
					viewFlg.put("deleteFlg", view.getDeleteFlg());
				}
			}else{
				viewFlg.put("deleteFlg", view.getDeleteFlg());
			}
			
		}
		
		Map<String,Map> fieldFlagMap = new HashMap<String,Map>();
		for(int i=1;i<viewMsg.length;i++){
			List<ViewField> viewFields = viewSetService.selectViewsFieldsByViewIdList(viewMsg[i]);
			for(int j=0;j<viewFields.size();j++){
				String fieldId = viewFields.get(j).getFieldId();
				String editFlg = viewFields.get(j).getEditFlg().toString();
				String retrievalFlg = viewFields.get(j).getRetrievalFlg().toString();
				String itemFlg = viewFields.get(j).getItemFlg().toString();
				String itemOrder  = viewFields.get(j).getItemOrder().toString();
				Map<String,String> flgMap = new HashMap<String,String>();
				if(fieldFlagMap.containsKey(fieldId)){
					String oldEditFlg = fieldFlagMap.get(fieldId).get("editFlg").toString();
					String oldRetrievalFlg = fieldFlagMap.get(fieldId).get("retrievalFlg").toString();
					String oldItemFlg = fieldFlagMap.get(fieldId).get("itemFlg").toString();
					String oldItemOrder = fieldFlagMap.get(fieldId).get("itemOrder").toString();
					if(!"1".equals(oldEditFlg)){
						flgMap.remove("editFlg");
						flgMap.put("editFlg", editFlg);
					}else{
						flgMap.put("editFlg", oldEditFlg);
					}
					if(!"1".equals(oldRetrievalFlg)){
						flgMap.remove("retrievalFlg");
						flgMap.put("retrievalFlg", retrievalFlg);
					}else{
						flgMap.put("retrievalFlg", oldRetrievalFlg);
					}
					if(!"1".equals(oldItemFlg)){
						flgMap.remove("itemFlg");
						flgMap.put("itemFlg", itemFlg);
					}else{
						flgMap.put("itemFlg", oldItemFlg);
					}
					if(Integer.parseInt(itemOrder)>=Integer.parseInt(oldItemOrder)){
						flgMap.remove("itemOrder");
						flgMap.put("itemOrder", itemOrder);
					}else{
						flgMap.put("itemOrder", oldItemOrder);
					}
					fieldFlagMap.remove(fieldId);
					fieldFlagMap.put(fieldId, flgMap);
				}else{
					flgMap.put("editFlg", editFlg);
					flgMap.put("retrievalFlg", retrievalFlg);
					flgMap.put("itemFlg", itemFlg);
					flgMap.put("itemOrder", itemOrder);
					fieldFlagMap.put(fieldId, flgMap);
				}
			}
		}
		List<DictField> dictFieldList = termStructService.selectFieldInfoByDictId(dictId);
		for(int i=0;i<dictFieldList.size();i++){
			DictField df = dictFieldList.get(i);
			String fieldId = df.getFieldId();
			if(fieldFlagMap.containsKey(fieldId)){
				df.setFieldIsEdit("1".equals(fieldFlagMap.get(fieldId).get("editFlg").toString())?"Y":"N");
				df.setIsFilter("1".equals(fieldFlagMap.get(fieldId).get("retrievalFlg").toString())?"Y":"N");
				df.setIsShow("1".equals(fieldFlagMap.get(fieldId).get("itemFlg").toString())?"Y":"N");
				df.setDispOrder(fieldFlagMap.get(fieldId).get("itemOrder").toString());
				termStructService.updateFieldData(df);
			}
		}
		
		List<DictItemDto> dictItemDtoList = termItemListService.getDictFieldList(dictId,null);
		List<String> status=new ArrayList<String>();
		status.add(STATUS_0);
		status.add(STATUS_1);
		status.add(STATUS_2);
		status.add(STATUS_3);
		List<DictMainDto> dictMainList = termListService.getDictMainList(dictId,null, null, status);
		String dictName = dictMainList.get(0).getDictName();
		String tableName = dictMainList.get(0).getTableName();
		String isEdit = dictMainList.get(0).getIsEdit();
		dictSearchCondition.setDictName(dictName);
		dictSearchCondition.setIsEdit(isEdit);
		List<String> releaseStatusForList = new ArrayList<String>();
		
		//title 0 编辑 1 审批 2发布
		if(TITLE_0.equals(title)){
			dictSearchCondition.setTitleName("术语项目列表（术语名："+dictName+"）");
			model.addAttribute("page_title", "术语项目列表（术语名："+dictName+"）");
		}else if(TITLE_1.equals(title)){
			dictSearchCondition.setTitleName("变更详细（术语名："+dictName+"）");
			model.addAttribute("page_title", "变更详细（术语名："+dictName+"）");
		}else if(TITLE_2.equals(title)){
			dictSearchCondition.setTitleName("变更详细（术语名："+dictName+"）");
			model.addAttribute("page_title", "变更详细（术语名："+dictName+"）");
		}
		//查询字段名
		List<String> dictNameIsSearchShowList = new ArrayList<String>();
		//显示字段名DTO
		List<DictItemDto> dictNameIsShowList = new ArrayList<DictItemDto>();
		//查询字段名DTO
		List<DictItemDto> dictNameIsFilterList = new ArrayList<DictItemDto>();
		//主键字段名DTO
		List<DictItemDto> dictNameIsPrimaryList = new ArrayList<DictItemDto>();
		//存储查询字段的顺序及字段信息,按照fieldOrders的key进行排序，然后放到dictNameIsFilterList里面。
		Map<Integer,DictItemDto> fieldOrders = new TreeMap<Integer,DictItemDto>();
		for(int i=0;i<dictItemDtoList.size();i++){
			if(Y.equals(dictItemDtoList.get(i).getIs_show())){
				dictNameIsSearchShowList.add(dictItemDtoList.get(i).getField_name());
				dictNameIsShowList.add(dictItemDtoList.get(i));
			}
			if(Y.equals(dictItemDtoList.get(i).getIs_filter())){
				String fieldId = dictItemDtoList.get(i).getField_id();
				Integer fieldOrder = termItemListService.selectFilterOrderByViewIdAndFieldId(viewId,fieldId);
				fieldOrders.put(fieldOrder, dictItemDtoList.get(i));
//				dictNameIsFilterList.add(dictItemDtoList.get(i));
			}
			if(Y.equals(dictItemDtoList.get(i).getIs_primary())){
				dictNameIsPrimaryList.add(dictItemDtoList.get(i));
			}
			
		}  
		//将fieldOrders中按照key值升序排列好的值依次放到dictNameIsFilterList中
		for(DictItemDto k : fieldOrders.values()){
			dictNameIsFilterList.add(k);
		}
		//查询表单值 
		Map<String,Object> mapSearchValue = new HashMap<String,Object>();
		//查询表单值type
		Map<String,Object> mapSearchType = new HashMap<String,Object>();
		if(!"export".equals(export)){
			 	for(int i =0;i<dictNameIsFilterList.size();i++){
			 		String key = dictNameIsFilterList.get(i).getField_name();
			 		String value = request.getParameter(key);
			 		String type = dictNameIsFilterList.get(i).getField_type();
			 		if(value!=null){
			 			value=value.trim();
			 		}
			 		if(value!=null && !"".equals(value)){
			 			mapSearchValue.put(key, value);
			 			mapSearchType.put(key, type);
			 		}
			 	}
			 	dictSearchCondition.setMapSearch(mapSearchValue);
			 	if("on".equals(searchAll)){
			 		if(TITLE_0.equals(title)){
			 			releaseStatusForList = new ArrayList<String>();
			 			releaseStatusForList.add(RELEASE_STATE_D);
						releaseStatusForList.add(RELEASE_STATE_R);
						releaseStatusForList.add(RELEASE_STATE_A);
			 		}else if(TITLE_1.equals(title)){
			 			releaseStatusForList = new ArrayList<String>();
			 			releaseStatusForList.add(RELEASE_STATE_D);
			 		}else if(TITLE_2.equals(title)){
			 			releaseStatusForList = new ArrayList<String>();
			 			releaseStatusForList.add(RELEASE_STATE_A);
			 		}
			 		
			 	}else{
			 		releaseStatusForList = new ArrayList<String>();
		 			releaseStatusForList.add(RELEASE_STATE_C);
		 			releaseStatusForList.add(RELEASE_STATE_D);
					releaseStatusForList.add(RELEASE_STATE_R);
					releaseStatusForList.add(RELEASE_STATE_A);
			 	}
		}else{
			releaseStatusForList = new ArrayList<String>();
 			releaseStatusForList.add(RELEASE_STATE_C);
		}
		List<Map<String,Object>> dictDataList = new ArrayList<Map<String,Object>>();
		dictDataList = termItemListService.getDictDataForSearchList(tableName, dictNameIsSearchShowList,releaseStatusForList,mapSearchValue,mapSearchType,options,export);
		//查询条件分行,增加导出excel的判断 fixed by wp
		if(!"export".equals(export)){
			List<List<DictItemDto>> dictFilterShow = new ArrayList<List<DictItemDto>>();
			int rowNum = dictNameIsFilterList.size()/5;
			int rest = dictNameIsFilterList.size()%5;
			if(rowNum==0){
				dictFilterShow.add(dictNameIsFilterList);
			}else{
				for(int i =0;i<rowNum;i++){
					List<DictItemDto> newDict = new ArrayList<DictItemDto>();
					newDict.add(dictNameIsFilterList.get(i*5));
					newDict.add(dictNameIsFilterList.get(i*5+1));
					newDict.add(dictNameIsFilterList.get(i*5+2));
					newDict.add(dictNameIsFilterList.get(i*5+3));
					newDict.add(dictNameIsFilterList.get(i*5+4));
					dictFilterShow.add(newDict);
				}
				List<DictItemDto> restDict = new ArrayList<DictItemDto>();
				for(int i =0;i<rest;i++){
					restDict.add(dictNameIsFilterList.get(i+5*rowNum));
				}
				dictFilterShow.add(restDict);
			}
			pageSetting(dictSearchCondition, options);
			model.addAttribute("dictNameIsFilterList", dictFilterShow);
		}
		
		model.put("dictSearchCondition", dictSearchCondition);
		model.addAttribute("dictItemDtoList", dictItemDtoList);
		model.addAttribute("dictNameIsSearchShowList", dictNameIsSearchShowList);
		//显示字段查询结果和字段
		model.addAttribute("dictNameIsShowList", dictNameIsShowList);
		model.addAttribute("dictDataList", dictDataList);
//		//将父页面的视图类型(viewType)传给子页面
		if("export".equals(export)){
			//根据title的不懂，在导出excel1时，log的日志也不同
			if(TITLE_0.equals(title)){
				logUtils.insertLog("00605", "00605003", "导出术语项目excel[{}]", dictName);
			}else if(TITLE_1.equals(title)){
				logUtils.insertLog("00607", "00607002", "导出术语项目excel[{}]", dictName);
			}else if(TITLE_2.equals(title)){
				logUtils.insertLog("00608", "00608001", "导出术语项目excel[{}]", dictName);
			}
			return exportOnymExcel(request,response,dictName,dictNameIsShowList,dictDataList);
		}
		//视图类型为编辑时的按钮控制
		model.addAttribute("viewFlg", viewFlg);
		model.addAttribute("viewType", viewType);
		model.addAttribute("viewId", viewId);
		if(editType.equals(viewType)){
			return includeTemplate(model,"term/dictitemeditlist");
		}else if(searchType.equals(viewType)){
			return includeTemplate(model,"term/dictitemsearchlist");
		}else{
			return includeTemplate(model,"term/dictitemscanlist");
		}
	}
	/**
	 * 超级管理员字典数据一览
	 * @param dictSearchCondition
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/admindictitemlist")
	public ModelAndView showAdminPage(final HttpServletRequest request,final TermMainCondition dictSearchCondition, final ModelMap model,final HttpServletResponse response) throws Exception{
		SelectOptions options = getSelectOptions(dictSearchCondition);
		String dictId = dictSearchCondition.getDictId();
		String title = dictSearchCondition.getTitle();
		String scanType = "2";//查看
		String searchType = "0";//检索
		String editType = "1";//编辑
		//增加导出excel功能  根据此标记区分是导出还是查询 add by wp 
		String export = request.getParameter("export");
		String searchAll = dictSearchCondition.getSearchAll();
		String viewTypeId = request.getParameter("viewTypeId");
		String viewId = null;
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Map<String,Integer> viewFlg = new HashMap<String,Integer>();
		
		List<DictItemDto> dictItemDtoList = termItemListService.getDictFieldList(dictId,null);
		List<String> status=new ArrayList<String>();
		status.add(STATUS_0);
		status.add(STATUS_1);
		status.add(STATUS_2);
		status.add(STATUS_3);
		List<DictMainDto> dictMainList = termListService.getDictMainList(dictId,null, null, status);
		String dictName = dictMainList.get(0).getDictName();
		String tableName = dictMainList.get(0).getTableName();
		String isEdit = dictMainList.get(0).getIsEdit();
		dictSearchCondition.setDictName(dictName);
		dictSearchCondition.setIsEdit(isEdit);
		List<String> releaseStatusForList = new ArrayList<String>();
		
		//title 0 编辑 1 审批 2发布
		if(TITLE_0.equals(title)){
			dictSearchCondition.setTitleName("术语项目列表（术语名："+dictName+"）");
			model.addAttribute("page_title", "术语项目列表（术语名："+dictName+"）");
		}else if(TITLE_1.equals(title)){
			dictSearchCondition.setTitleName("变更详细（术语名："+dictName+"）");
			model.addAttribute("page_title", "变更详细（术语名："+dictName+"）");
		}else if(TITLE_2.equals(title)){
			dictSearchCondition.setTitleName("变更详细（术语名："+dictName+"）");
			model.addAttribute("page_title", "变更详细（术语名："+dictName+"）");
		}
		//查询字段名
		List<String> dictNameIsSearchShowList = new ArrayList<String>();
		//显示字段名DTO
		List<DictItemDto> dictNameIsShowList = new ArrayList<DictItemDto>();
		//查询字段名DTO
		List<DictItemDto> dictNameIsFilterList = new ArrayList<DictItemDto>();
		//主键字段名DTO
		List<DictItemDto> dictNameIsPrimaryList = new ArrayList<DictItemDto>();
		for(int i=0;i<dictItemDtoList.size();i++){
			if(Y.equals(dictItemDtoList.get(i).getIs_show())){
				dictNameIsSearchShowList.add(dictItemDtoList.get(i).getField_name());
				dictNameIsShowList.add(dictItemDtoList.get(i));
			}
			if(Y.equals(dictItemDtoList.get(i).getIs_filter())){
				dictNameIsFilterList.add(dictItemDtoList.get(i));
			}
			if(Y.equals(dictItemDtoList.get(i).getIs_primary())){
				dictNameIsPrimaryList.add(dictItemDtoList.get(i));
			}
			
		}  
		//查询表单值 
		Map<String,Object> mapSearchValue = new HashMap<String,Object>();
		//查询表单值type
		Map<String,Object> mapSearchType = new HashMap<String,Object>();
		if(!"export".equals(export)){
			for(int i =0;i<dictNameIsFilterList.size();i++){
				String key = dictNameIsFilterList.get(i).getField_name();
				String value = request.getParameter(key);
				String type = dictNameIsFilterList.get(i).getField_type();
				if(value!=null){
					value=value.trim();
				}
				if(value!=null && !"".equals(value)){
					mapSearchValue.put(key, value);
					mapSearchType.put(key, type);
				}
			}
			dictSearchCondition.setMapSearch(mapSearchValue);
			if("on".equals(searchAll)){
				if(TITLE_0.equals(title)){
					releaseStatusForList = new ArrayList<String>();
					releaseStatusForList.add(RELEASE_STATE_D);
					releaseStatusForList.add(RELEASE_STATE_R);
					releaseStatusForList.add(RELEASE_STATE_A);
				}else if(TITLE_1.equals(title)){
					releaseStatusForList = new ArrayList<String>();
					releaseStatusForList.add(RELEASE_STATE_D);
				}else if(TITLE_2.equals(title)){
					releaseStatusForList = new ArrayList<String>();
					releaseStatusForList.add(RELEASE_STATE_A);
				}
				
			}else{
				releaseStatusForList = new ArrayList<String>();
				releaseStatusForList.add(RELEASE_STATE_C);
				releaseStatusForList.add(RELEASE_STATE_D);
				releaseStatusForList.add(RELEASE_STATE_R);
				releaseStatusForList.add(RELEASE_STATE_A);
			}
		}else{
			releaseStatusForList = new ArrayList<String>();
			releaseStatusForList.add(RELEASE_STATE_C);
		}
		List<Map<String,Object>> dictDataList = new ArrayList<Map<String,Object>>();
		dictDataList = termItemListService.getDictDataForSearchList(tableName, dictNameIsSearchShowList,releaseStatusForList,mapSearchValue,mapSearchType,options,export);
		//查询条件分行,增加导出excel的判断 fixed by wp
		if(!"export".equals(export)){
			List<List<DictItemDto>> dictFilterShow = new ArrayList<List<DictItemDto>>();
			int rowNum = dictNameIsFilterList.size()/4;
			int rest = dictNameIsFilterList.size()%4;
			if(rowNum==0){
				dictFilterShow.add(dictNameIsFilterList);
			}else{
				for(int i =0;i<rowNum;i++){
					List<DictItemDto> newDict = new ArrayList<DictItemDto>();
					newDict.add(dictNameIsFilterList.get(i*4));
					newDict.add(dictNameIsFilterList.get(i*4+1));
					newDict.add(dictNameIsFilterList.get(i*4+2));
					newDict.add(dictNameIsFilterList.get(i*4+3));
					dictFilterShow.add(newDict);
				}
				List<DictItemDto> restDict = new ArrayList<DictItemDto>();
				for(int i =0;i<rest;i++){
					restDict.add(dictNameIsFilterList.get(i+4*rowNum));
				}
				dictFilterShow.add(restDict);
			}
			pageSetting(dictSearchCondition, options);
			model.addAttribute("dictNameIsFilterList", dictFilterShow);
		}
		
		model.put("dictSearchCondition", dictSearchCondition);
		model.addAttribute("dictItemDtoList", dictItemDtoList);
		model.addAttribute("dictNameIsSearchShowList", dictNameIsSearchShowList);
		//显示字段查询结果和字段
		model.addAttribute("dictNameIsShowList", dictNameIsShowList);
		model.addAttribute("dictDataList", dictDataList);
		//model.addAttribute("mapSearch", mapSearch);
//		//将父页面的视图类型(viewType)传给子页面
//		model.addAttribute("viewType", viewType);
		if("export".equals(export)){
			//根据title的不懂，在导出excel1时，log的日志也不同
			if(TITLE_0.equals(title)){
				logUtils.insertLog("00605", "00605003", "导出术语项目excel[{}]", dictName);
			}else if(TITLE_1.equals(title)){
				logUtils.insertLog("00607", "00607002", "导出术语项目excel[{}]", dictName);
			}else if(TITLE_2.equals(title)){
				logUtils.insertLog("00608", "00608001", "导出术语项目excel[{}]", dictName);
			}
			return exportOnymExcel(request,response,dictName,dictNameIsShowList,dictDataList);
		}
		//视图类型为编辑时的按钮控制
//		DictView view = viewSetService.selectViewByViewId(viewId);
		model.addAttribute("viewFlg", viewFlg);
		model.addAttribute("viewType", null);
		model.addAttribute("viewId", viewId);
		model.addAttribute("userName", userName);
		return includeTemplate(model,"term/dictitemlist");
	}
	
	/**
	 * 导出excel
	 * author wp
	 */
	public ModelAndView exportOnymExcel(HttpServletRequest req, HttpServletResponse res,String dictName,
			List<DictItemDto> dictNameIsShowList,List<Map<String,Object>> dictDataList){
		String destFilePath = req.getSession().getServletContext().getRealPath("/");
		OutputStream os = null;
		InputStream is  =null;
		try {
		    if (!destFilePath.endsWith(java.io.File.separator)) {
		    	destFilePath = destFilePath + java.io.File.separator;
			    }
			res.setHeader("Content-Disposition", "attachment;filename="+new String(dictName.getBytes("GBK"), "iso8859-1")+".xls");  
			res.setContentType("application/vnd.ms-excel");  
			os=res.getOutputStream();
			List<String> columnNames = new ArrayList<String>();
			List<String> fieldTypes = new ArrayList<String>();
			List<String> fieldNames = new ArrayList<String>();
    	    for(DictItemDto dictItemDto : dictNameIsShowList){
    	    	columnNames.add(dictItemDto.getField_desc());
    	    	fieldTypes.add(dictItemDto.getField_type());
    	    	fieldNames.add(dictItemDto.getField_name());
    	    }
    	    List<List<Object>> allrowdata = new ArrayList<List<Object>>();
    	    //对应页面判断进行转换
            for(Map<String,Object> dataList:dictDataList){
            	List<Object> rowdata = new ArrayList<Object>();
            	for(int i=0;i<fieldTypes.size();i++){
                    String fieldType = fieldTypes.get(i).toString();
            		if("NUMBER(1)".equals(fieldType)){
            			if(Integer.parseInt(dataList.get(fieldNames.get(i)).toString())==0){
            				dataList.put(fieldNames.get(i),'否');
            			}else{
            				dataList.put(fieldNames.get(i),'是');
            			}
            		}
            		rowdata.add(dataList.get(fieldNames.get(i)));
            	}
            	allrowdata.add(rowdata);
            }
    	    is = new BufferedInputStream(new FileInputStream(destFilePath+"template/termdicttemplate.xls"));
    	    Transformer transformer = TransformerFactory.createTransformer(is, os);
    	    AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
    	    List<Area> xlsAreaList = areaBuilder.build();
    	    Area xlsArea = xlsAreaList.get(0);
    	    Context context = new Context();
    	    context.putVar("headers", columnNames);
    	    context.putVar("data", allrowdata);
    	    context.putVar("dictName", dictName);
    	    xlsArea.applyAt(new CellRef("Sheet1!A1"), context);
    	    transformer.write();
    	    is.close();
    	    os.close();

		} catch (Exception e) {
			
		}finally{
			if (is!=null){try {is.close();} catch (IOException e) {}}  
			if (os!=null){try {os.close();} catch (IOException e) {}}   
		}
		return null;
	}
	
	
	/**
	 * 字典更新画面初始化
	 * @param model
	 * @param uni_key
	 * @param dictId
	 * @return
	 */
	@RequestMapping(value="/dictDataEditshow", method=RequestMethod.POST)
	public ModelAndView dictDataEditShow(final ModelMap model,String uni_key,String dictId,String title){
		//String dictId = dictSearchCondition.getDictId();
		List<DictItemDto> termItemList = termItemListService.getDictFieldList(dictId,"2");
		model.addAttribute("termItemDtoList", termItemList);
		//String keyPrimary = dictSearchCondition.getKeyPrimary();
		List<String> releaseStatusForList = new ArrayList<String>();
		releaseStatusForList.add(RELEASE_STATE_C);
		releaseStatusForList.add(RELEASE_STATE_D);
		releaseStatusForList.add(RELEASE_STATE_R);
		releaseStatusForList.add(RELEASE_STATE_A);
		Map<String,Object> termDataMap = termItemListService.getTermListMapByUniKey(termItemList,releaseStatusForList,uni_key);
		model.addAttribute("termDataMap",termDataMap);
		model.addAttribute("dictId",dictId);
		model.addAttribute("uni_key",uni_key);
		model.addAttribute("title",title);
//		model.addAttribute("length",length);
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if("admin".equals(userName)){
			if(uni_key != null && !"".equals(uni_key)){
				model.addAttribute("editStatus","u");
				model.addAttribute("titleDesc","术语项目编辑");
			}else{
				model.addAttribute("editStatus","a");
				model.addAttribute("titleDesc","术语项目新增");
			}
			ModelAndView mav = new ModelAndView("term/admindictitemedit", model);
			return mav;
		}else{
		     if(uni_key != null && !"".equals(uni_key)){
		     	model.addAttribute("editStatus","u");
		     	model.addAttribute("titleDesc","术语项目编辑");
		     	ModelAndView mav = new ModelAndView("term/dictitemedit", model);
		     	return mav;
		     }else{
		     	model.addAttribute("editStatus","a");
		     	model.addAttribute("titleDesc","术语项目新增");
		     	ModelAndView mav = new ModelAndView("term/dictitemadd", model);
		     	return mav;
		     }
		}
	}

	
	/**
	 * 执行字典数据更新
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/termDataEdit", method=RequestMethod.POST)
	public ModelAndView termDataEdit(final HttpServletRequest request,final ModelMap model){
		Enumeration<String> keyMap =   request.getParameterNames();
		String viewTypeId = request.getParameter("viewTypeId");
		List<String> keyList = new ArrayList<String>();
 		while(keyMap.hasMoreElements()){
 			keyList.add(keyMap.nextElement());
 		}
		
		Map<String,Object> dataMap =new HashMap<String,Object>();
		Map<String,Object> statusMap = new HashMap<String,Object>();
		for(int i=0;i<keyList.size();i++){
			if("dictId".equals(keyList.get(i)) || "editStatus".equals(keyList.get(i)) || "uni_key".equals(keyList.get(i))|| "title".equals(keyList.get(i))){
				statusMap.put(keyList.get(i), request.getParameter(keyList.get(i)));
			}else{
				dataMap.put(keyList.get(i), request.getParameter(keyList.get(i)));
			}
		}
		int status=0;
		String releaseStatus = (String)request.getParameter("release_status");
		String uuid = this.getUUID();
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		String userNo = auth.getName();
		statusMap.put("new_uni_key", uuid);
		statusMap.put("new_user_no", userNo);
		if(!"0".equals(statusMap.get("title"))){
			statusMap.put("searchAll","on");
		}
		List<String> releaseStatusForList = new ArrayList<String>();
		releaseStatusForList.add(RELEASE_STATE_C);
		releaseStatusForList.add(RELEASE_STATE_D);
		releaseStatusForList.add(RELEASE_STATE_R);
		releaseStatusForList.add(RELEASE_STATE_A);
		//执行更新， 已经在Service方法里更新dict_main状态
		status = termItemListService.editTermData(statusMap,dataMap,releaseStatusForList);
		//termItemListService.getAndUpdateReleaseStatusCount(tableName, dictId, userNo);
		model.addAttribute("status",status);
		model.addAttribute("dictId",statusMap.get("dictId"));
		model.addAttribute("title",statusMap.get("title"));
		model.addAttribute("searchAll",statusMap.get("searchAll"));
		if("admin".equals(userNo)){
			ModelAndView mav = new ModelAndView("redirect:/term/admindictitemlist.html", model);
			return mav;
		}else{
		    ModelAndView mav = new ModelAndView("redirect:/term/dictitemlist.html?viewTypeId="+viewTypeId, model);
		    return mav;
		}
	}
	
	/**
	 * 数据比对
	 * @param model
	 * @param uni_key
	 * @param dictId
	 * @param title
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dictitemeditdetail")
	public ModelAndView termDataCompare(final ModelMap model,String viewType,String viewId,String uni_key,String dictId,String title) throws Exception{
		List<String> releaseStatusForList = new ArrayList<String>();
		releaseStatusForList.add(RELEASE_STATE_C);
		releaseStatusForList.add(RELEASE_STATE_D);
		releaseStatusForList.add(RELEASE_STATE_R);
		releaseStatusForList.add(RELEASE_STATE_A);
		releaseStatusForList.add(RELEASE_STATE_H);
		List<Map<String,Object>> differentData = termItemListService.getTermDataCompare(uni_key, dictId, title,releaseStatusForList);
		List<DictItemDto> termItemList =  termItemListService.getDictFieldList(dictId,"1");
		DictMain dm = termItemListService.selectDictMainByDictId(dictId);
		model.addAttribute("uni_key", uni_key);
		model.addAttribute("dictId", dictId);
		model.addAttribute("title", title);
		model.addAttribute("viewType",viewType);
		model.addAttribute("viewId",viewId);
		model.addAttribute("differentData", differentData);
		model.addAttribute("termItemList", termItemList);
		model.addAttribute("titleName", "变更比较（术语名："+dm.getDictName()+"）");
		return includeTemplate(model,"term/dictitemeditdetail");
	}
	
	/**
	 * 审批。发布
	 * @param model
	 * @param uni_keys
	 * @param dictId
	 * @param title
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/termStateEdit", method = RequestMethod.POST)
	public @ResponseBody
	Object termStateEdit(final ModelMap model,String uni_keys,String dictId,String title,String status,String comment){
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		String userNo = auth.getName();
		List<DictItemDto> termItemDtoList = termItemListService.getDictFieldList(dictId,null);
		String tableName = termItemDtoList.get(0).getTable_name();
		List<String> uniKeys = new ArrayList<String>();
		String[] keys = uni_keys.split(",");
		for(int i=0; i<keys.length;i++){
			uniKeys.add(keys[i]);
		}
		//执行审批，发布
		int status1 = termItemListService.releaseStatusUpdate(uniKeys,tableName, dictId, status, userNo,comment);
		//更新dict_main状态
		int status2 = termItemListService.getAndUpdateReleaseStatusCount(tableName, dictId, userNo);
		boolean ifSuccess = true;
		if(status1==0 && status2==0){
			ifSuccess= false;
		}
		model.addAttribute("dictId",dictId);
		model.addAttribute("title",title);
		model.addAttribute("ifSuccess",ifSuccess);
		return model;
	}
	/**
	 * @author li_zhong2
	 * 判断该账户是否具有所有字段的编辑权限，如果没有就提示权限不足，无法删除。
	 */
	@RequestMapping(value="/checkAuthority", method = RequestMethod.POST)
	public @ResponseBody
	Object checkAuthority(final ModelMap model,String dictId) throws ParseException{
		boolean status = true;
		List<DictItemDto> termItemList = termItemListService.getDictFieldList(dictId,"2");
		for(int i=0;i<termItemList.size();i++){
			String isEdit = termItemList.get(i).getField_is_edit();
			if("N".equals(isEdit)){
				status = false;
			}
		}
		model.addAttribute("status",status);
		return model;
	}
	/**
	 * 执行字典数据删除
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/termDataDelete", method = RequestMethod.POST)
	public @ResponseBody
	Object termDataDelete(final ModelMap model,String uniKey, String dictId,String title) throws ParseException{
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		String userNo = auth.getName();
		List<String> releaseStatusForList = new ArrayList<String>();
		releaseStatusForList.add(RELEASE_STATE_C);
		releaseStatusForList.add(RELEASE_STATE_D);
		releaseStatusForList.add(RELEASE_STATE_R);
		releaseStatusForList.add(RELEASE_STATE_A);
		releaseStatusForList.add(RELEASE_STATE_H);
		String uuid = this.getUUID();
		int status = termItemListService.deleteTermData(uniKey, userNo, releaseStatusForList, dictId,uuid,title);
		model.addAttribute("status",status);
		model.addAttribute("dictId",dictId);
		model.addAttribute("title",title);
		return model;
	}
	
	/**
	 * 唯一性校验
	 * @param dictId
	 * @param primaryKeys
	 * @return 如果不唯一返回false， 如果唯一返回true
	 */
	@RequestMapping(value="/checkUniPrimaryKey", method = RequestMethod.POST)
	public @ResponseBody
	Object checkUniPrimaryKey(final ModelMap model,String dictId,String primaryKeys) throws ParseException{
		boolean status = true;
		status = termItemListService.checkUniPrimaryKey(dictId,primaryKeys);
		model.addAttribute("status",status);
		return model;
	}
}
