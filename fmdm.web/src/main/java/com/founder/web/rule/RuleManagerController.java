package com.founder.web.rule;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fmdm.entity.DictDepartment;
import com.founder.fmdm.entity.RlmgModelDetail;
import com.founder.fmdm.entity.RlmgModelType;
import com.founder.fmdm.entity.RlmgRule;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.service.rule.GroupManagerService;
import com.founder.fmdm.service.rule.RlmgModelService;
import com.founder.fmdm.service.rule.RuleManagerService;
import com.founder.fmdm.service.rule.dto.RuleDto;
import com.founder.fmdm.service.rule.dto.RuleListDto;
import com.founder.fmdm.service.sysmgt.ConstantsDef;
import com.founder.fmdm.service.sysmgt.WarningSettingService;
import com.founder.fmdm.service.sysmgt.dto.DepartmentDto;
import com.founder.web.rule.dto.RuleListDtoCondition;
import com.founder.web.term.AbstractController;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/rule")
public class RuleManagerController extends AbstractController{
	
	@Autowired
	GroupManagerService groupManagerService;
	@Autowired
	RlmgModelService rlmgModelService;
    @Autowired
    WarningSettingService warningSettingService;
	@Autowired
	RuleManagerService ruleManagerService;
	
	@RequestMapping("/checkExitRul")
	public @ResponseBody
	Object checkExitRule(final ModelMap model,String ruleName,String ruleId){
		String ruleName1 = null;
		try {
			ruleName1 = new String(ruleName.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int result = ruleManagerService.checkExistRule(ruleName1,ruleId);
		model.addAttribute("resultSet", result);
		return model;
	}
	
	@RequestMapping("/initCreateGuidePage")
	public ModelAndView initCreateGuidePage(final ModelMap model,RuleListDtoCondition ruleListDtoCondition) throws Exception{
		//规则状态
		List<SystemCode> systemCodeList = ruleManagerService.selectRuleStatus();
		model.addAttribute("ruleStatus",systemCodeList);
		model.addAttribute("page_title", "新建规则");
		List<RuleListDto> ruleList = initRuleTableCondition(ruleListDtoCondition);
		model.addAttribute("ruleList",ruleList);
		model.addAttribute("ruleListDto",ruleListDtoCondition);
		return includeTemplate(model,"rule/initCreateRuleGuidePage");
	}
	/**
     * 复制方式规则列表查询
     * @param mav
     * @return
     */
    public List<RuleListDto> initRuleTableCondition(RuleListDtoCondition ruleListDtoCondition)
    		 throws Exception
    {
    	SelectOptions options = getSelectOptions(ruleListDtoCondition);
    	RuleListDto ruleListDto = new RuleListDto();
    	ruleListDto.setCondRuleName(ruleListDtoCondition.getCondRuleName());
    	ruleListDto.setCondRuleGroName(ruleListDtoCondition.getCondRuleGroName());
    	ruleListDto.setCondModelName(ruleListDtoCondition.getCondModelName());
    	ruleListDto.setCondStatus(ruleListDtoCondition.getCondStatus());
    	List<RuleListDto>  modelList = ruleManagerService.selectReleaseRules(ruleListDto,options); 
	    pageSetting(ruleListDtoCondition, options);
        return modelList;
    }
	/**
	 * copy方式规则列表
	 * @return
	 */
	/*@RequestMapping("/initRuleTable")
	public ModelAndView initRuleTable(RuleListDto ruleListDto){
		ModelAndView mav = new ModelAndView();
		*//**
		 * 加载规则列表
		 * 
		 *//*
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		List<RuleListDto> rulelist = ruleManagerService.selectReleaseRules(ruleListDto, pagingContext);
		List<Object> resultList = new ArrayList<Object>();
		Map<String, Object> totle = new HashMap<String, Object>();

		totle.put("_total", pagingContext.getTotalRowCnt());

		resultList.add(totle);
		resultList.addAll(rulelist);

		mav.addObject("resultSet", resultList.toArray());
		mav.setViewName("springJsonView");
		return mav;
	}*/
	
	
	@RequestMapping("/initCopyRulePage")
	public ModelAndView initCopyRulePage(final ModelMap model,String ruleId,String type) throws Exception{
		/**
		 *  加载规则组和模型事实数据以及部门数据
		 */
		model.addAttribute("page_title", "新建规则");
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
			if(null != type){
				model.addAttribute("createType", type);
			}
		}
		return includeTemplate(model,"rule/initCopyRulePage");
	}
	/**
	 * 保存copy创建规则
	 * @param ruleDto
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/saveCopyRule")
	public @ResponseBody
	Object saveCopyRule(final ModelMap model,RuleDto ruleDto) throws UnsupportedEncodingException{
	  
		String opt = ruleDto.getOpt();
		ruleDto = decodeRuleDto(ruleDto);
		int result =0;
		try{
			if("edit".equals(opt)){
				ruleManagerService.updateRule(ruleDto);
			}else{
				ruleManagerService.insertRule(ruleDto);
			}
			
			result =  ConstantsDef.OPERATION_SUCCESS;
		}catch(Exception e){
			result = ConstantsDef.OPERATION_FAILURE;
			e.printStackTrace();
		}
		model.clear();
		model.addAttribute("resultSet", result);
		return model;
	}
	
	@RequestMapping("/initCreateRulePage")
	public ModelAndView initCreateRulePage(final ModelMap model) throws Exception{
		/**
		 *  加载规则组和模型事实数据以及部门数据
		 */
		List<RlmgRulegroup> ruleGroupList = groupManagerService.selectInitGros();
		List<RlmgModelType> ruleModelList = rlmgModelService.selectInitModels();
		List <DepartmentDto> deptList= warningSettingService.selectDeptDataFromDictDepartment("all");
		JSONArray departDtoJson = JSONArray.fromObject( deptList );
		String dictDepartmentJson = departDtoJson.toString();
		model.addAttribute("dictDepartmentJson", dictDepartmentJson);
		model.addAttribute("ruleGroup", ruleGroupList);
		model.addAttribute("ruleMode", ruleModelList);
		model.addAttribute("page_title", "新建规则");
		return includeTemplate(model,"rule/createRule");
	}
	
	@RequestMapping("/rlmg/initEditRulePage")
	public ModelAndView initEditRulePage(String ruleId){
		ModelAndView mav = new ModelAndView();
		/**
		 *  加载规则组和模型事实数据以及部门数据
		 */
		List<RlmgRulegroup> ruleGroupList = groupManagerService.selectInitGros();
		List<RlmgModelType> ruleModelList = rlmgModelService.selectInitModels();
		List<DepartmentDto>  deptList = warningSettingService.selectDeptDataFromDictDepartment("all");
		JSONArray departDtoJson = JSONArray.fromObject( deptList );
		String dictDepartmentJson = departDtoJson.toString();
		mav.addObject("dictDepartmentJson", dictDepartmentJson);
		mav.addObject("ruleGroup", ruleGroupList);
		mav.addObject("ruleMode", ruleModelList);
		
		if(ruleId!=null && !"".equals(ruleId)){
			//规则
			RlmgRule rule = ruleManagerService.selectRuleByRuleId(ruleId);
			mav.addObject("rule", rule);
			List<DepartmentDto> department = warningSettingService.selectDeptDataFromDictDepartment(rule.getUnitId());
			mav.addObject("deptName", department.get(0).getName());
			//规则项目
			List<RlmgRuleDetail> itemList = ruleManagerService.selectById(ruleId);
			int i=0;
			for(RlmgRuleDetail item:itemList){
				if(item.getItemType()==0 ){
					i++;
					continue;
				}
//				String value = item.getDefaultValue();
//				String defaultV = "";
//				if(value!=null &&!"".equals(value.trim())){
//					String[] vArray = value.split(",",-1);
//					for(int j=0;j<vArray.length;j++){
//						if(j==0){
//							defaultV = "<input  name='val' type='hidden' value='"+vArray[j]+"' />";
//						}else{
//							defaultV += "<input  name='val' type='hidden' value='"+vArray[j]+"' />";
//						}
//					}
//				}
//				item.setItemExpression(item.getItemExpression()+defaultV);
				itemList.set(i, item);
				i++;
			}
			mav.addObject("itemList", itemList);
			//统计规则数据字段个数，用于记录日志
			Map<Integer,Object> dataMap = ruleManagerService.selectRuleDataByRuleId(ruleId,null);
			mav.addObject("fieldSize", dataMap.toString().split("=\\{")[1].split("RlmgRuleData").length-1);
			mav.addObject("dataMap", dataMap);
		}
		mav.setViewName("/rule/editRule");
		return mav;
	}
	
	@RequestMapping("/initConditionPage")
	public ModelAndView initConditionPage(String isCon,String modelId,String exe ,String desc) throws UnsupportedEncodingException{
		isCon=decodeString(isCon);
		exe=decodeString(exe);
		desc=decodeString(desc);
		ModelAndView mav = new ModelAndView();
		//isCon==0则条件为自由编辑
		if(!"2".equals(isCon)){
			/**
			 * 初始化规则字段数据
			 */
			List<RlmgModelDetail> itemList = rlmgModelService.selectInitItems(modelId);
			mav.addObject("itemList", itemList);
			if(exe!=null){
				String[] strArr = exe.split(":",-1);
				String[] starray = splitRools(strArr[0]);
				String[] valueArray = strArr[1].split(",",-1);
				int m = 0;
				String result = "";
				for(int i = 0 ; i<starray.length; i++){
					if(("{"+String.valueOf(m)+"}").equals(starray[i]) || ("\"{"+String.valueOf(m)+"}\"").equals(starray[i])){
						starray[i] = (m>=valueArray.length)?"":valueArray[m];
						m++;
					}
					if(i==0){
						result = starray[i];
					}else{
						if(starray[i].contains("&") || starray[i].contains("|")){
							result = result +"#"+ starray[i]+"#";
						}else{
							result = result.endsWith("#")?(result+starray[i]):(result+":"+ starray[i]);
						}
					}
				}
				mav.addObject("exe", result);
			}
			mav.addObject("isCon", 1);
		}else{
			mav.addObject("exe", exe.split(":",-1)[0]);
			mav.addObject("isCon", isCon);
		}
		mav.addObject("desc", desc);
		mav.setViewName("/rule/initConditionPage");
		return mav;
	}
	
	@RequestMapping("/initResultPage")
	public ModelAndView initResultPage(String modelId,String desc,String exe) throws UnsupportedEncodingException{
		exe=decodeString(exe);
		desc=decodeString(desc);
		ModelAndView mav = new ModelAndView();
		/**
		 * 初始化规则字段数据
		 */
		List<RlmgModelDetail> itemList = rlmgModelService.selectInitItems(modelId);
		mav.addObject("itemList", itemList);
		if(exe!=null&&!"".equals(exe)){
			String[] strArr = exe.split(":",-1);
			String[] starray = splitRools(strArr[0]);
			String result = "";
			for(int i = 0 ; i<starray.length; i++){
				if(starray[i].contains("{0}")){
					starray[i] = strArr[1];
				}
				if(i==0){
					result = starray[i];
				}else{
					result = result+":"+ starray[i];
				}
			}
			mav.addObject("desc", desc);
			mav.addObject("exe", result);
		}
		mav.setViewName("/rule/initResultPage");
		return mav;
	}
	
	@RequestMapping("/createRule")
	public @ResponseBody
	Object createRule(final ModelMap model,RuleDto ruleDto) throws UnsupportedEncodingException{
		ruleDto = decodeRuleDto(ruleDto);
		int result =0;
		try{
			ruleManagerService.insertRule(ruleDto);
			result =  ConstantsDef.OPERATION_SUCCESS;
		}catch(Exception e){
			result = ConstantsDef.OPERATION_FAILURE;
			e.printStackTrace();
		}
		model.clear();
		model.addAttribute("resultSet", result);
		return model;
	}
	
	@RequestMapping("/rlmg/saveEditRule")
	public ModelAndView editRule(RuleDto ruleDto){
		int result =0;
		ModelAndView mav = new ModelAndView();
		try{
			ruleManagerService.updateRule(ruleDto);

			result =  ConstantsDef.OPERATION_SUCCESS;
		}catch(Exception e){
			result = ConstantsDef.OPERATION_FAILURE;
		}
		mav.addObject("resultSet", result);
		
		mav.setViewName("springJsonView");
		return mav;
	}
	
	
	/**
	 * 拆分表达式
	 * @param rools
	 * @return
	 */
	public static String[] splitRools(String rools){
		String s3="&|<>=!";
		StringTokenizer st = new StringTokenizer(rools,s3,true);
		List<String> starray = new ArrayList<String>();
		int i=0;
		while(st.hasMoreTokens()){
			String value = st.nextToken().trim();
			if(value.equals("&")||value.equals("|")||value.equals("=")){
				starray.add(value+st.nextToken().trim());
			}
			else if(value.equals(">")||value.equals("<")||value.equals("!")){
				String ifequal = st.nextToken().trim();
				if(ifequal.equals("=")){
					starray.add(value+ifequal);
				}
				else{
					starray.add(value);
					i++;
					starray.add(ifequal);
				}
			}
			else{
				starray.add(value);
			}
			i++;
		}
		return starray.toArray(new String[0]);
	}
	
	/**
	 *科室控件，根据科室名称查询是否存在科室code
	 * @param deptName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/checkDeptCode")
	public @ResponseBody
	Object selectDeptCodeByDeptName(final ModelMap model,String deptName) throws UnsupportedEncodingException {
		deptName=decodeString(deptName);
		String deptCode = "";
		try{
			List<DictDepartment> dictDept = new ArrayList<DictDepartment>();
			dictDept = ruleManagerService.selectDeptCodeByDeptName(deptName);
			if(dictDept.size()>0){
				deptCode = dictDept.get(0).getCode();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("resultSet", deptCode);
		return model;
	}
	/**
	 * 前端ajax传递ruleDto中文乱码转换
	 * @param ruleDto
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public RuleDto decodeRuleDto(RuleDto ruleDto) throws UnsupportedEncodingException{
		ruleDto.setRuleName(decodeString(ruleDto.getRuleName()));
		ruleDto.setMemo(decodeString(ruleDto.getMemo()));
		ruleDto.setTitles(decodeString(ruleDto.getTitles()));
		ruleDto.setExe(decodeString(ruleDto.getExe()));
		ruleDto.setData(decodeString(ruleDto.getData()));
		return ruleDto;
	}
	public String decodeString(String str) throws UnsupportedEncodingException{
		if(str!=null){
			if(str.contains("+")){
				String[] strs = str.split("\\+");
				StringBuffer sbf = new StringBuffer("");
				for(int i = 0; i < strs.length; i++){
					sbf.append(java.net.URLDecoder.decode(strs[i], "UTF-8")).append("+");
				}
				return sbf.toString().substring(0, sbf.toString().length()-1);
			}
			str = java.net.URLDecoder.decode(str, "UTF-8");
		}
		return str;
	}
}
