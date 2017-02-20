package com.founder.web.rule;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fmdm.Constants;
import com.founder.fmdm.entity.RlmgModelType;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.service.rule.GroupManagerService;
import com.founder.fmdm.service.rule.RlmgModelService;
import com.founder.fmdm.service.rule.RuleManagerService;
import com.founder.fmdm.service.rule.dto.RuleListDto;
import com.founder.fmdm.service.sysmgt.WarningSettingService;
import com.founder.fmdm.service.sysmgt.dto.DepartmentDto;
import com.founder.web.dto.PagingDto;
import com.founder.web.rule.dto.RuleListDtoCondition;
import com.founder.web.term.AbstractController;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/rule")
public class RlmgRuleController extends AbstractController{
	
	protected static Logger logger = LoggerFactory.getLogger(RlmgRuleController.class);
    private static final int PAGE_COUNT = 10;
	
	@Autowired
	RuleManagerService ruleManagerService;

	@Autowired
	GroupManagerService groupManagerService;
	
	@Autowired
	RlmgModelService rlmgModelService;
	
	 @Autowired
	 WarningSettingService warningSettingService;
	 
	@RequestMapping("/ruleList")
	public ModelAndView modelAdd(final ModelMap model,RuleListDtoCondition ruleListDtoCondition) throws Exception{
		List<SystemCode> systemCodeList = ruleManagerService.selectRuleStatus();
		SelectOptions options = getSelectOptions(ruleListDtoCondition);
		model.addAttribute("ruleStatus",systemCodeList);
		model.addAttribute("page_title", "规则一览");
		List<RuleListDto> ruleList = initRuleTableCondition(ruleListDtoCondition,options);
		model.addAttribute("ruleList",ruleList);
		model.addAttribute("ruleListDtoCondition",ruleListDtoCondition);
		pageSetting(ruleListDtoCondition, options);
		return includeTemplate(model,"rule/ruleList");
	}
	@RequestMapping("/editRulePage")
	public ModelAndView initCopyRulePage(final ModelMap model,String ruleId) throws Exception{
		/**
		 *  加载规则组和模型事实数据以及部门数据
		 */
		model.clear();
		model.addAttribute("page_title", "编辑规则");
		model.addAttribute("opt", "edit");
		List<RlmgRulegroup> ruleGroupList = groupManagerService.selectInitGros();
		List<RlmgModelType> ruleModelList = rlmgModelService.selectInitModels();
		List deptList = warningSettingService.selectDeptDataFromDictDepartment("all");
		JSONArray departDtoJson = JSONArray.fromObject( deptList );
		String dictDepartmentJson = departDtoJson.toString();
		model.addAttribute("dictDepartmentJson", dictDepartmentJson);
		model.addAttribute("ruleGroup", ruleGroupList);
		model.addAttribute("ruleMode", ruleModelList);
		
		if(ruleId!=null && !"".equals(ruleId)){
			//规则
			RlmgRule rule = ruleManagerService.selectRuleByRuleId(ruleId);
			model.addAttribute("rule", rule);
			model.addAttribute("memo",rule.getMemo());
			List<DepartmentDto> department = warningSettingService.selectDeptDataFromDictDepartment(rule.getUnitId());
			model.addAttribute("deptName", department.get(0).getName());
			model.addAttribute("deptCode", department.get(0).getCode());
			//规则项目
			List<RlmgRuleDetail> itemList = ruleManagerService.selectById(ruleId);
			int i=0;
			for(RlmgRuleDetail item:itemList){
				if(item.getItemType()==0 ){
					i++;
					continue;
				}
				itemList.set(i, item);
				i++;
			}
			model.addAttribute("itemList", itemList);
			//规则数据
			Map<Integer,Object> dataMap = ruleManagerService.selectRuleDataByRuleId(ruleId,null);
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("ruleId", ruleId);
		}
		return includeTemplate(model,"rule/initCopyRulePage");
	}
	@RequestMapping("/editRuleDataPage")
	public ModelAndView initCopyRuleDataPage(final ModelMap model,String ruleId,String description) throws Exception{
		/**
		 *  加载规则数据
		 */
		model.clear();
//		model.addAttribute("page_title", "编辑规则数据");
		model.addAttribute("opt", "edit");
		if(ruleId!=null && !"".equals(ruleId)){
			//规则
			String[] ruleId1 = ruleId.split(",");
			RlmgRule rule = ruleManagerService.selectRuleByRuleId(ruleId1[0]);
			model.addAttribute("rule", rule);
			//规则项目
			List<RlmgRuleDetail> itemList = ruleManagerService.selectById(ruleId1[0]);
			int i=0;
			for(RlmgRuleDetail item:itemList){
				if(item.getItemType()==0 ){
					i++;
					continue;
				}
				itemList.set(i, item);
				i++;
			}
			model.addAttribute("itemList", itemList);
			//规则数据
			Map<Integer,Object> dataMap = ruleManagerService.selectRuleDataByRuleId(ruleId1[0],description);
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("page_title", "编辑模型");
			model.put("description", description);
		}
		return includeTemplate(model,"rule/initCopyRuleDataPage");
	}
	
	/**
	 * 删除规则
	 * @param model
	 * @param ruleName
	 * @param ruleId
	 * @return
	 */
	@RequestMapping("/deleteRule")
	public @ResponseBody
	Object checkExitRule(final ModelMap model,String ruleId){
		int result = ruleManagerService.deleteRule(ruleId);
		model.addAttribute("status", result);
		return model;
	}
	 public List<RuleListDto> initRuleTableCondition(RuleListDtoCondition ruleListDtoCondition,SelectOptions options)
    		 throws Exception
    { 
    	RuleListDto ruleListDto = new RuleListDto();
    	ruleListDto.setCondRuleName(ruleListDtoCondition.getCondRuleName());
    	ruleListDto.setCondRuleGroName(ruleListDtoCondition.getCondRuleGroName());
    	ruleListDto.setCondModelName(ruleListDtoCondition.getCondModelName());
    	ruleListDto.setCondStatus(ruleListDtoCondition.getCondStatus());
    	List<RuleListDto>  modelList = ruleManagerService.selectReleaseRules(ruleListDto,options); 
        return modelList;
    }
	 public PagingDto pageSetting(PagingDto cond,SelectOptions options){
	    	if(cond.getJumpToPage() == null)
				cond.setCurrentPage(1);
			else
				cond.setCurrentPage(cond.getJumpToPage());
			cond.setJumpToPage(0);
			int size = (int) options.getCount();
			cond.setTotalSize(size);
			if(size % PAGE_COUNT == 0)
				cond.setTotalPage(size / (StringUtils.isEmpty(cond.getPageCount())?PAGE_COUNT:cond.getPageCount()));
			else
				cond.setTotalPage(size / (StringUtils.isEmpty(cond.getPageCount())?PAGE_COUNT:cond.getPageCount()) + 1);
			return cond;
	    }
	 public SelectOptions getSelectOptions(PagingDto opts)
	  	{
	  		int jumpToPage = opts.getJumpToPage() == null ? 1 : opts.getJumpToPage();
	  		int offset = (jumpToPage - 1) * (StringUtils.isEmpty(opts.getPageCount())?PAGE_COUNT:opts.getPageCount());
	  		SelectOptions options = SelectOptions.get().offset(offset).limit(StringUtils.isEmpty(opts.getPageCount())?PAGE_COUNT:opts.getPageCount()).count();

	  		return options;
	  	}
	   public ModelAndView handleMainBgPic(ModelAndView mav){
	    	 String mainBgPic = Constants.COMPANY_MAIN_PIC;
	         //web服务启动时，如果properties目录下存在定制的图片，会按照用户配置的路径复制到项目中，读取项目
	         if(!StringUtils.isEmpty(Constants.HOSPITAL_MAIN_PIC)){

	 			// 获取cdr项目所在的根路径
	 			String webPath = new File(this.getClass().getResource("/").getPath()).getAbsolutePath();
	 			// 处理windows与linux之间的差异
	 			if (webPath.contains("WEB-INF")) {
	 				webPath = webPath.substring(0, webPath.indexOf("WEB-INF"));
	 			}

	 			String picPath = Constants.HOSPITAL_PIC_FOLDER + "/"
	 					+ Constants.HOSPITAL_MAIN_PIC;

	 			File file = new File(webPath + picPath);
	 			if (file.exists() && file.isFile()) {
	 				mainBgPic  = picPath;
	 			}
	 		
	         }
	   
	        mav.addObject("mainBgPic",mainBgPic);
	    	
	    	return mav;
	    }
	   
	   
	    /**
	     * 提交前验证，只有未提交状态，才能进行规则审批！
	     * @param ruleIds
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "/checkStatus")
	    public @ResponseBody Object checkStatus(String ruleIds,ModelMap model)
	    {
	    	model.clear();
	    	String data;
	    	int resultSet=ruleManagerService.checkStatus(ruleIds);
	    	if(resultSet==1)
				data = "1";
			else
				data = "0";
			return data;
	    }
	    
		@RequestMapping(value = "/approveRules")
		public @ResponseBody Object approveRules(String ruleIds) throws Exception{
			String data;
			int resultSet = ruleManagerService.approveRules(ruleIds,SecurityContextHolder.getContext()
					.getAuthentication().getName());
			if(resultSet==1)
				data = "1";
			else
				data = "0";
			return data;
		}
}
