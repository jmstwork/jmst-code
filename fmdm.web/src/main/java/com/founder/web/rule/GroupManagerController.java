package com.founder.web.rule;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.service.rule.GroupManagerService;
import com.founder.fmdm.service.rule.dto.GroListDto;
import com.founder.web.rule.dto.GroupManagerCondition;
import com.founder.web.term.AbstractController;


@Controller
@RequestMapping(value = "/groupManager")
public class GroupManagerController extends AbstractController {

	final static int GROUPISUSED = 2;

	@Autowired
	GroupManagerService groupManagerService;
	
	@Autowired
	LogUtils logUtils;

	@RequestMapping("/groupList")
	public ModelAndView groupList(GroListDto groListDto,GroupManagerCondition cond, String operFlg)
			throws Exception {
		
		ModelAndView mav = new ModelAndView();
		ModelMap model = mav.getModelMap();
		model.addAttribute("page_title", "规则组管理");
		SelectOptions options = getSelectOptions(cond);
		model.addAttribute("operFlg", operFlg);
		
		//查询规则组信息
		List<RlmgRulegroup> grolist = groupManagerService.selectGros(
				groListDto, operFlg, options);
		model.addAttribute("grolist", grolist);
		pageSetting(cond, options);
		return includeTemplate(model, "rule/groupManagerList");
	}
	
	@RequestMapping("/saveGro")
	public @ResponseBody Object saveGro(GroListDto groListDto)
			throws Exception {
		
		ModelAndView mav = new ModelAndView();
		ModelMap model = mav.getModelMap();
		
		/*String checkResult = checkNameEnname(groListDto.getCondGroName(), groListDto.getCondGroNameEn());
		if(!"0_0".equals(checkResult)){
			model.addAttribute("status", 0);
			model.addAttribute("failMessage","中文名或英文名已经被引用，保存失败!");
			return model;
		}*/
		
		RlmgRulegroup testGroup =  groupManagerService.checkGroName(groListDto.getCondGroName().trim());
		if(null != testGroup){
			model.addAttribute("status", 0);
			model.addAttribute("failMessage","中文名已经被引用，保存失败!");
			return model;
		}
		
		testGroup =  groupManagerService.checkGroNameEn(groListDto.getCondGroNameEn());
		if(null != testGroup){
			model.addAttribute("status", 0);
			model.addAttribute("failMessage","英文名已经被引用，保存失败!");
			return model;
		}
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		RlmgRulegroup ruleGroup = new RlmgRulegroup();
		ruleGroup.setRulegroupName(groListDto.getCondGroName());
		ruleGroup.setRulegroupEnName(groListDto.getCondGroNameEn());
		ruleGroup.setCreateBy(userName);
		int result = groupManagerService.saveGro(ruleGroup);
		if(1 == result){
			logUtils.insertLog("00401", "00401001", "新增规则组[{}]", ruleGroup.getRulegroupName());
		}
		model.addAttribute("status", result > 0 ? 1:0);
		return model;
	}
	
	@RequestMapping("/deleteGro")
	public @ResponseBody Object deleteGro(GroListDto groListDto) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		ModelMap model = mav.getModelMap();
		
		
		if(StringUtils.isEmpty(groListDto.getGroupId())){
			model.addAttribute("status", 0);
			model.addAttribute("failMessage","删除失败！");
			return model;
		}
		
		// 校验此规则组是否有规则引用
		int ruleResult = groupManagerService.checkRuleIsUsed(groListDto.getGroupId());
		if (ruleResult > 0) {
			model.addAttribute("status", ruleResult > 0 ? 1:0);
			model.addAttribute("failMessage","该规则组已有规则引用，不能删除！");
			return model;
		}
		RlmgRulegroup groDetailDto = groupManagerService.selectGroById(groListDto.getGroupId());
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		int result = groupManagerService.deleteGro(groListDto.getGroupId(), userName);
		if(1 == result){
			logUtils.insertLog("00303", "00401003", "删除规则组[{}]", groDetailDto.getRulegroupName());
		}
		model.addAttribute("status", result > 0 ? 1:0);
		return model;
	}
	
	@RequestMapping("/editGro")
	public ModelAndView editGro(GroListDto groListDto) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		ModelMap model = mav.getModelMap();
		model.addAttribute("page_title", "规则组修改");
		
		RlmgRulegroup groDetailDto = groupManagerService.selectGroById(groListDto.getGroupId());
		model.addAttribute("groDetailDto", groDetailDto);
		
		return includeTemplate(model, "rule/groupDetail");
	}
	
	
	@RequestMapping("/updateGro")
	public @ResponseBody Object updateGro(GroListDto groListDto)
			throws Exception {
		
		ModelAndView mav = new ModelAndView();
		ModelMap model = mav.getModelMap();
		
		
		/*String checkResult = checkNameEnname(groListDto.getCondGroName(), groListDto.getCondGroNameEn());
		String[] check = checkResult.split("_");
		if(Integer.parseInt(check[0]) > 1 || Integer.parseInt(check[1]) > 1 ){
			model.addAttribute("status", 0);
			model.addAttribute("failMessage","中文名或英文名已经被引用，保存失败!");
			return model;
		}*/
		
		RlmgRulegroup testGroup =  groupManagerService.checkGroName(groListDto.getCondGroName());
		if(null != testGroup && !testGroup.getRulegroupId().equals(groListDto.getGroupId())){
			model.addAttribute("status", 0);
			model.addAttribute("failMessage","中文名已经被引用，保存失败!");
			return model;
		}
		
		testGroup =  groupManagerService.checkGroNameEn(groListDto.getCondGroNameEn());
		if(null != testGroup && !testGroup.getRulegroupId().equals(groListDto.getGroupId())){
			model.addAttribute("status", 0);
			model.addAttribute("failMessage","英文名已经被引用，保存失败!");
			return model;
		}
		
		
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		RlmgRulegroup ruleGroup = groupManagerService.selectGroById(groListDto.getGroupId());
		
		String editDescribe = "";
		if(null != ruleGroup.getRulegroupName() && !ruleGroup.getRulegroupName().equals(groListDto.getCondGroName())){
			editDescribe = editDescribe + "原规则组名称为["+ruleGroup.getRulegroupName()+"],新规则组名称为["+groListDto.getCondGroName()+"]     ";
		}
		
		if(null != ruleGroup.getRulegroupEnName() && !ruleGroup.getRulegroupEnName().equals(groListDto.getCondGroNameEn())){
			editDescribe = editDescribe + "原规则组英文名称为["+ruleGroup.getRulegroupEnName()+"],新规则组英文名称为["+groListDto.getCondGroNameEn()+"]";
		}
		if(!"".equals(editDescribe)){
			editDescribe = "(" + editDescribe + ")";
		}
		//RlmgRulegroup ruleGroup = new RlmgRulegroup();
		//ruleGroup.setRulegroupId(groListDto.getGroupId());
		ruleGroup.setRulegroupName(groListDto.getCondGroName());
		ruleGroup.setRulegroupEnName(groListDto.getCondGroNameEn());
		ruleGroup.setLastUpdateBy(userName);
		int result = groupManagerService.updateGro(ruleGroup);
		
		if(1 == result){
			logUtils.insertLog("00303", "00401002", "修改规则组[{}]" + editDescribe, groListDto.getCondGroName());
		}
		
		model.addAttribute("status", result > 0 ? 1:0);
		return model;
	}

}
