package com.founder.web.term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.service.dto.DefaultUserDetails;
import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.service.sysmgt.ViewSetService;
import com.founder.fmdm.service.sysmgt.dto.MenuDto;
import com.founder.fmdm.service.sysmgt.dto.RoleViewsDto;
import com.founder.fmdm.service.term.TermListService;
import com.founder.fmdm.service.term.dto.DictMainDto;
import com.founder.fmdm.service.term.dto.ViewsDto;
import com.founder.util.StringUtil;

@Controller
@RequestMapping("/term")
public class TermListController extends AbstractController {
	@Autowired
	private TermListService dictListService;
	@Autowired
	ViewSetService viewSetService;
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
	
	List<String> tableNames;
	
	Map<String,String> viewTypeMap;
	
	
	
	@RequestMapping(value="/dictlist")
	public ModelAndView showPage(final TermMainCondition termMainCondition, final ModelMap model,String title) throws Exception{
		
		tableNames = new ArrayList<String>();
		viewTypeMap = new HashMap<String,String>();
		//获得角色
		Authentication authentication = SecurityContextHolder.getContext()
						.getAuthentication();
		DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
		String userName = authentication.getName();
		getRole(userDetails.getRoles());
		SelectOptions options = getSelectOptions(termMainCondition);
		List<String> statusForshow=new ArrayList<String>();
		//title 0 编辑 1 审批 2发布
		if(TITLE_0.equals(title)){
			statusForshow.add(STATUS_0);
			statusForshow.add(STATUS_1);
			statusForshow.add(STATUS_2);
			statusForshow.add(STATUS_3);
			termMainCondition.setTitleName("术语列表");
			model.addAttribute("page_title", "术语列表");
			termMainCondition.setTitle(title);
			model.addAttribute("jumpToItem","术语项目列表");
		}else if(TITLE_1.equals(title)){
			termMainCondition.setTitleName("术语待审批列表");
			model.addAttribute("page_title", "术语待审批列表");
			termMainCondition.setTitle(title);
			statusForshow.add(STATUS_0);
			statusForshow.add(STATUS_3);
			model.addAttribute("jumpToItem","变更详细");
		}else if(TITLE_2.equals(title)){
			termMainCondition.setTitleName("术语待发布列表");
			model.addAttribute("page_title", "术语待发布列表");
			termMainCondition.setTitle(title);
			statusForshow.add(STATUS_1);
			statusForshow.add(STATUS_3);
			model.addAttribute("jumpToItem","变更详细");
		}
		
		String serviceId=termMainCondition.getServiceId();
		if(serviceId !=null){
			serviceId= serviceId.trim().toUpperCase();
		}
    	String dictName=termMainCondition.getDictName();
    	if(dictName !=null){
    		dictName= dictName.trim();
		}
    	String statusFromPage =	termMainCondition.getStatus();
    	List<String> statusFromPageList=new ArrayList<String>();
    	if(STATUS_0.equals(statusFromPage)){
    		statusFromPageList.add(STATUS_0);
    		statusFromPageList.add(STATUS_3);
    	}else if(STATUS_1.equals(statusFromPage)){
			statusFromPageList.add(STATUS_1);
			statusFromPageList.add(STATUS_3);
    	}else if(STATUS_2.equals(statusFromPage)){
    		statusFromPageList.add(STATUS_2);
    	}else if(STATUS_3.equals(statusFromPage)){
			statusFromPageList.add(STATUS_3);
    	}else{
    		statusFromPageList.add(STATUS_0);
			statusFromPageList.add(STATUS_1);
			statusFromPageList.add(STATUS_2);
			statusFromPageList.add(STATUS_3);
    	}
    	List<DictMainDto> dictMainList=null;
		if ("admin".equals(userName)) {
			if (statusFromPage != null && !"".equals(statusFromPage)) {
				dictMainList = dictListService.getAdminDictMainList(null, serviceId, dictName, statusFromPageList, options);
			}else{
				dictMainList = dictListService.getAdminDictMainListByName(null, serviceId, dictName, statusForshow, options);
			}
		} else {
			if (statusFromPage != null && !"".equals(statusFromPage)) {
				dictMainList = dictListService.getDictMainList1(null, serviceId, dictName, statusFromPageList, options,
						tableNames);
			} else {
				// dictMainList =
				// dictListService.getDictMainList(null,serviceId, dictName,
				// statusForshow,options);
				if (tableNames.size() > 0) {
					dictMainList = dictListService.getDictMainListByName(null, serviceId, dictName, statusForshow,
							options, tableNames);
					dictMainList = getViewTypeId(dictMainList);
				}
			}
		}
		List<Map<String,Object>> dictStatusList = dictListService.getDictStatus();
		pageSetting(termMainCondition, options);
        model.put("termMainCondition", termMainCondition);
        model.addAttribute("dictMainList", dictMainList);
        model.addAttribute("dictStatusList", dictStatusList);
        model.addAttribute("userName", userName);
		return includeTemplate(model,"term/dictlist");
	}
	/**
	 * 获得角色和视图
	 * @param roles
	 */
	private void getRole(List<RoleViewsDto> roles) {
		if(roles != null){
			for(int i=0;i<roles.size();i++){
				List<ViewsDto> dictView = roles.get(i).getViews();
				for(int j=0;j<dictView.size();j++){
					String tablename = dictView.get(j).getTableName();
					String viewType = dictView.get(j).getViewType();
					String viewId = dictView.get(j).getViewId();
					if(!tableNames.contains(tablename)){
						tableNames.add(tablename);
					}
					if(viewTypeMap.containsKey(tablename)){
						//map里面原来的视图类型type1
						String type1 = viewTypeMap.get(tablename).split(",")[0];
						//原来的type1如果是编辑类型，只有当前的viewType是1的时候才需要将当前的viewId拼接在后面
						//原来的type1如果是检索类型，只有当前的viewType是0的时候才需要将当前的viewId拼接在后面
						//原来的type1如果是查看类型，只有当前的viewType是2的时候才需要将当前的viewId拼接在后面
						if("1".equals(type1)){
							if("1".equals(viewType)){
								String viewIds = viewTypeMap.get(tablename).split(",")[1];
								viewTypeMap.remove(tablename);
								viewTypeMap.put(tablename,viewType+","+viewIds+","+viewId);
							}
						}else if("0".equals(type1)){
							if("1".equals(viewType)){
								viewTypeMap.remove(tablename);
								viewTypeMap.put(tablename, viewType+","+viewId);
							}else if("0".equals(viewType)){
								String viewIds = viewTypeMap.get(tablename).split(",")[1];
								viewTypeMap.remove(tablename);
								viewTypeMap.put(tablename,viewType+","+viewIds+","+viewId);
							}
						}else if("2".equals(type1)){
							if("1".equals(viewType)||"0".equals(viewType)){
								viewTypeMap.remove(tablename);
								viewTypeMap.put(tablename, viewType+","+viewId);
							}else if("2".equals(viewType)){
								String viewIds = viewTypeMap.get(tablename).split(",")[1];
								viewTypeMap.remove(tablename);
								viewTypeMap.put(tablename,viewType+","+viewIds+","+viewId);
							}
						}
					}else{
						viewTypeMap.put(tablename, viewType+","+viewId);
					}
				}
			}
		}
	}
	
	public List<DictMainDto> getViewTypeId(List<DictMainDto> dictMainList){
		List<DictMainDto> newDictMainList = new ArrayList<DictMainDto>();
		if(dictMainList.size()>0){
//			for(int i=0;i<dictMainList.size();i++){
//				String tableName = dictMainList.get(i).getTableName();
//				if(!"".equalsIgnoreCase(viewTypeMap.get(tableName))&&viewTypeMap.get(tableName)!=null){
//					DictMainDto dictMainDto = new DictMainDto();
//					dictMainDto = dictMainList.get(i);
//					dictMainDto.setViewType(viewTypeMap.get(tableName));
//					dictMainList.remove(i);
//					dictMainList.add(dictMainDto);
//				}
//			}
			Iterator<DictMainDto> iter = dictMainList.iterator(); 
			while(iter.hasNext()){  
				DictMainDto dmd = iter.next();  
				String tableName = dmd.getTableName();
				if(!"".equalsIgnoreCase(viewTypeMap.get(tableName).split(",")[0])&&viewTypeMap.get(tableName).split(",")[0]!=null){
					dmd.setViewTypeId(viewTypeMap.get(tableName));
					newDictMainList.add(dmd);
					iter.remove();
				}
			}
		}
		return newDictMainList;
	}
}
