package com.founder.web.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.founder.fmdm.Constants;
import com.founder.fmdm.entity.RlmgModelType;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.service.rule.RlmgModelService;
import com.founder.fmdm.service.rule.dto.RlmgModelInfoDto;
import com.founder.fmdm.service.rule.dto.RlmgRuleDto;
import com.founder.fmdm.service.rule.dto.RlmgVersionDto;
import com.founder.web.rule.dto.RlmgModelTpyeCondition;
import com.founder.web.rule.dto.RlmgRuleCondition;

import com.founder.web.rule.dto.RlmgVersionCondition;

import com.founder.web.term.AbstractController;

/**
 * 规则管理之模型数据展示与处理
 * 创建日期：2013-02-19
 * @date yang_jianbo
 * @version commsrv1.2
 * 
 */

@Controller
@RequestMapping("/rmlg")
public class RlmgModelController extends AbstractController
{
	/**字段被规则引用**/
	final static int FIELDISUSED = 1;
	
	/**字段没被规则引用**/
	final static int FIELDISNOTUSED = 2;

	@Autowired
    RlmgModelService rlmgModelService;
	
	@Autowired
	LogUtils logUtils;


    /** 
     * 新增和编辑跳转
     * @param mav
     * @param opt
     * @param modelId
     * @return
     * @throws Exception 
     */
    @RequestMapping(value ="/modelOperateView")
    public ModelAndView modelAdd(final ModelMap model,RlmgModelTpyeCondition rlmgModelTpyeCondition,String opt)
            throws Exception
    {
    	if(Constants.OPERATION_EDIT.equals(opt)){
    		RlmgModelInfoDto rlmgModelInfoDto = new RlmgModelInfoDto();
    		rlmgModelInfoDto.setModelId(rlmgModelTpyeCondition.getModelId());
    		List<RlmgModelType> modelList = rlmgModelService.keepModelType(rlmgModelInfoDto);
    		List<RlmgModelInfoDto> fieldList = rlmgModelService.keepModelDetail(rlmgModelInfoDto);
    		rlmgModelTpyeCondition.setModelId(modelList.get(0).getModelId());
    		rlmgModelTpyeCondition.setModelName(modelList.get(0).getModelName());
    		rlmgModelTpyeCondition.setModelEnName(modelList.get(0).getModelEnName());
    		if(fieldList.size()>0){
    			int listSize =fieldList.size();
    			RlmgModelInfoDto rm;
            	for(int i=0; i<listSize ; i++){
            		rm = (RlmgModelInfoDto) fieldList.get(i);
            		//判断此字段是否被引用 
            		int fieldCount = rlmgModelService.selectFieldFromRules(rm.getFieldId());
            		if(fieldCount > 0){
            			rm.setUsedFlg(FIELDISUSED);
            			model.addAttribute("usedFlg",FIELDISUSED );
            		}else{
            			rm.setUsedFlg(FIELDISNOTUSED);
            		}
            	}
    		}
    		model.addAttribute("fieldList", fieldList);
    		model.addAttribute("rlmgModelTpyeCondition", rlmgModelTpyeCondition);
    		model.addAttribute("page_title", "编辑模型");
    		model.addAttribute("opt", Constants.OPERATION_EDIT);
    	}else{
    		model.addAttribute("page_title", "新建模型");
    		model.addAttribute("opt", Constants.OPERATION_ADD);
    	}
        return includeTemplate(model,"rule/modelOperate");
    }

    /**
     * 新增和编辑数据保存
     * @param rlmgModelInfoDto
     * @param mav
     * @param opt
     * @return
     */
    @RequestMapping(value ="/modelSave")
    public @ResponseBody Object modelSave(RlmgModelTpyeCondition rlmgModelTpyeCondition,final ModelMap model,String opt) throws Exception
    {	
    	model.clear();
    	RlmgModelInfoDto rlmgModelInfoDto = new RlmgModelInfoDto();
    	rlmgModelInfoDto.setModelId(rlmgModelTpyeCondition.getModelId());
    	rlmgModelInfoDto.setModelName(rlmgModelTpyeCondition.getModelName());
    	rlmgModelInfoDto.setModelEnName(rlmgModelTpyeCondition.getModelEnName());
    	rlmgModelInfoDto.setFieldId(rlmgModelTpyeCondition.getFieldId());
    	rlmgModelInfoDto.setFieldName(rlmgModelTpyeCondition.getFieldName());
    	rlmgModelInfoDto.setFieldEnName(rlmgModelTpyeCondition.getFieldEnName());
    	rlmgModelInfoDto.setFieldType(rlmgModelTpyeCondition.getFieldType());
    	List<RlmgModelType> rlmgModelList = rlmgModelService.selectModelByName(rlmgModelInfoDto.getModelName(), rlmgModelInfoDto.getModelEnName());
    	if(Constants.OPERATION_EDIT.equalsIgnoreCase(rlmgModelTpyeCondition.getOpt())){
    		if(null != rlmgModelList){
    			for(int i= 0; i < rlmgModelList.size();i++){
    				//如果模型名相同且模型ID不同，则该模型名已经被引用，不能新建或修改
    				if(rlmgModelInfoDto.getModelName().equals(rlmgModelList.get(i).getModelName()) 
    						&& !rlmgModelInfoDto.getModelId().equals(rlmgModelList.get(i).getModelId())){
    					model.addAttribute("status", 0);
    		    		model.addAttribute("msg","模型名已经被引用，无法修改！");
    		            //return includeTemplate(model,"rule/modelOperate");
    		    		return model;
    				}
    				
    				if(rlmgModelInfoDto.getModelEnName().equals(rlmgModelList.get(i).getModelEnName()) 
    						&& !rlmgModelInfoDto.getModelId().equals(rlmgModelList.get(i).getModelId())){
    					model.addAttribute("status", 0);
    		    		model.addAttribute("msg","模型英文名已经被引用，无法修改！");
    		    		return model;
    				}
    			}
    		}
    	}else if (Constants.OPERATION_ADD.equalsIgnoreCase(rlmgModelTpyeCondition.getOpt())){
    		if(null != rlmgModelList && 0 != rlmgModelList.size()){
    			model.addAttribute("status", 0);
	    		model.addAttribute("msg","模型名或 英文名已经被引用，无法修改！");
	    		return model;
    		}
    	}
    	RlmgModelType rlmgModelType = (RlmgModelType) rlmgModelService.selectModelById(rlmgModelTpyeCondition.getModelId());
    	int result = rlmgModelService.saveModel(rlmgModelInfoDto,rlmgModelTpyeCondition.getOpt());
    	if(Constants.OPERATION_EDIT.equals(rlmgModelTpyeCondition.getOpt())){
    		String editDescribe = "";
    		if(StringUtils.isNotEmpty(rlmgModelTpyeCondition.getModelName()) && !rlmgModelType.getModelName().equals(rlmgModelTpyeCondition.getModelName())){
    			editDescribe = editDescribe + "原模型名称为["+rlmgModelType.getModelName()+"],现模型名称为["+rlmgModelTpyeCondition.getModelName()+"]";
    		}
    		if(StringUtils.isNotEmpty(rlmgModelTpyeCondition.getModelEnName()) && !rlmgModelType.getModelEnName().equals(rlmgModelTpyeCondition.getModelEnName())){
    			editDescribe = editDescribe + "原模型英文名称为["+rlmgModelType.getModelEnName()+"],现模型英文名称为["+rlmgModelTpyeCondition.getModelEnName()+"]";
    		}
    		if(!"".equals(editDescribe)){
    			editDescribe = "(" + editDescribe + ")";
    		}
    		logUtils.insertLog("00402", "00402002", "修改模型[{}]" + editDescribe, rlmgModelInfoDto.getModelName());
    		List<RlmgModelType> modelList = rlmgModelService.keepModelType(rlmgModelInfoDto);
    		List<RlmgModelInfoDto> fieldList = rlmgModelService.keepModelDetail(rlmgModelInfoDto);
    		rlmgModelTpyeCondition.setModelId(modelList.get(0).getModelId());
    		rlmgModelTpyeCondition.setModelName(modelList.get(0).getModelName());
    		rlmgModelTpyeCondition.setModelEnName(modelList.get(0).getModelEnName());
    		if(fieldList.size()>0){
    			int listSize =fieldList.size();
    			RlmgModelInfoDto rm;
            	for(int i=0; i<listSize ; i++){
            		rm = (RlmgModelInfoDto) fieldList.get(i);
            		//判断此字段是否被引用 
            		int fieldCount = rlmgModelService.selectFieldFromRules(rm.getFieldId());
            		if(fieldCount > 0){
            			rm.setUsedFlg(FIELDISUSED);
            			model.addAttribute("usedFlg",FIELDISUSED );
            		}else{
            			rm.setUsedFlg(FIELDISNOTUSED);
            		}
            	}
    		}
    		model.addAttribute("fieldList", fieldList);
    		model.addAttribute("rlmgModelTpyeCondition", rlmgModelTpyeCondition);
    		model.addAttribute("opt", Constants.OPERATION_EDIT);
    		model.addAttribute("page_title", "编辑模型");
    		model.addAttribute("status", result > 0 ? 1:0);
    		model.addAttribute("result",result);
    		return model;
    	}else{
    		if(result > 0){
    			logUtils.insertLog("00202", "00402001", "新增模型[{}]", rlmgModelInfoDto.getModelName());
    		}
    		model.addAttribute("status", result > 0 ? 1:0);
    		model.addAttribute("result",result);
    		return model;
    	}
    }

	/**
     * 模型一览跳转
     * @param mav
     * @return
     */
    @RequestMapping(value = "/modelView")
    public ModelAndView modelView(final ModelMap model,RlmgModelTpyeCondition rlmgModelTpyeCondition)
    		 throws Exception
    {
    	model.addAttribute("page_title", "模型一览");
    	SelectOptions options = getSelectOptions(rlmgModelTpyeCondition);
    	String modelName = rlmgModelTpyeCondition.getModelName();
    	List<RlmgModelType>  modelList = rlmgModelService.selectModels(modelName,options); 
	    model.put("modelList", modelList);
	    pageSetting(rlmgModelTpyeCondition, options);
        return includeTemplate(model,"rule/modelList");
    }
    /**
     * 模型删除
     * @param rlmgModelInfoDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteModel")
    public @ResponseBody
	Object deleteModel(final String modelId, final ModelMap model) {
		model.clear();
        // 检查模型是否跟规则绑定，如果绑定就不能删除
        int result = rlmgModelService.selectRuleByModelId(modelId);
        if (result == 0)
        {
        	RlmgModelType rlmgModelType = (RlmgModelType) rlmgModelService.selectModelById(modelId);
            int rs = rlmgModelService.deleteModel(modelId);
            if (rs > 0)
            {
            	result = 1;
            	logUtils.insertLog("00203", "00402003", "删除模型[{}]", rlmgModelType.getModelName());
            }
        }
        else
        {
        	result = 0;
        }
    	model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
    }
    /**
     * 审批一览跳转
     * @param mav
     * @return
     */
    @RequestMapping(value = "/examApproveView")
    public ModelAndView examApproveView(final ModelMap model,RlmgRuleCondition rlmgRuleCondition)
    		 throws Exception
    {
    	model.addAttribute("page_title", "审批一览");
    	SelectOptions options = getSelectOptions(rlmgRuleCondition);
    	String name = rlmgRuleCondition.getRuleName();
    	String ruleName;
    	if(name != null){
    		ruleName = name.replaceAll(",","");
    	}else{
    		ruleName = name;
    	}
    	rlmgRuleCondition.setRuleName(ruleName);
    	String status = rlmgRuleCondition.getStatus();
    	List<RlmgRuleDto>  ruleList = rlmgModelService.selectRules(ruleName,status,options);
	    model.put("ruleList", ruleList);
	    pageSetting(rlmgRuleCondition, options);
        return includeTemplate(model,"rule/examApproveList");
    }
    
    /**
     * 版本一览跳转
     * @param mav
     * @return
     */
    @RequestMapping(value = "/versionView")
    public ModelAndView versionView(final ModelMap model,RlmgVersionCondition rlmgVersionCondition)
    		throws Exception
    {
    	model.addAttribute("page_title", "版本一览");
    	SelectOptions options = getSelectOptions(rlmgVersionCondition);
    	String versionNo = rlmgVersionCondition.getVersionNo();
    	if (versionNo == null){ 
    		versionNo = "";
    	}else if(versionNo.contains("V")||versionNo.contains("v")){
    		versionNo = versionNo.toLowerCase().replace("v", "");
    	}
    	List<RlmgVersionDto>  versionList = rlmgModelService.selectVersions(versionNo,options);
    	model.put("versionList", versionList);
    	pageSetting(rlmgVersionCondition, options);
    	return includeTemplate(model,"rule/versionList");
    }
    
    /**
     * 规则详细跳转
     * @param mav
     * @return
     */
    @RequestMapping(value = "/ruleDataDetailView")
    public ModelAndView ruleDetailsView(final ModelMap model,RlmgRuleCondition rlmgRuleCondition,String description)
    		 throws Exception
    {
    	model.addAttribute("page_title", "规则详细");
    	String ruleId = rlmgRuleCondition.getRuleId();
    	String a = rlmgRuleCondition.getRuleName();
    	String b = a.replaceAll(",", "");
    	rlmgRuleCondition.setRuleName(b);
    	List<RlmgRuleDetail> colList = rlmgModelService.selectRuleDataDetailCol(ruleId);
	    model.put("colList", colList);
	    model.put("colSize", colList.size());
	    model.put("ruleId", ruleId);
        List<RlmgRuleData> rowNoList = rlmgModelService.selectRowNoByValue(ruleId,description);
        StringBuffer bf = new StringBuffer();
        if(rowNoList.size()>0){
            for(int i=0;i<rowNoList.size();i++){
                RlmgRuleData rlmgRuleData = (RlmgRuleData)rowNoList.get(i);
                if(i==rowNoList.size()-1){
                    bf.append(rlmgRuleData.getDataRowNo());
                }else{
                    bf.append(rlmgRuleData.getDataRowNo()+",");
                }
            }  
        }else{
            bf.append(-1);
        }
            
        int maxRowNo = rlmgModelService.selectMaxDataRowNo(ruleId); 
        List<RlmgRuleData> list= rlmgModelService.selectRuleDataMap(ruleId, bf.toString(),rlmgRuleCondition.getCurrentPage(),colList.size());
        
        List<Object> resultList = new ArrayList<Object>();
        for(int j=0;j<=maxRowNo;j++){
            Map<String,Object> tempMap_j = new HashMap<String, Object>();
            for(RlmgRuleData rlmgRuleData : list){
                if(j==rlmgRuleData.getDataRowNo()){
                    tempMap_j.put(rlmgRuleData.getRuleItemId(), rlmgRuleData.getDataValue());
                }
            }
            if(tempMap_j.size()>0){
                resultList.add(tempMap_j);
            }
        }
//        if (!StringUtils.equals(description, null)&&!StringUtils.equals(description, "")){
//        	maxRowNo = resultList.size()-1;
//        }
        rlmgRuleCondition.setTotalSize(rowNoList.size());
        rlmgRuleCondition.setTotalPage((int) Math.ceil((double)(rowNoList.size())/10));
        model.put("rlmgRuleCondition",rlmgRuleCondition);
        model.put("resultList", resultList);
        model.put("resultListArray", resultList.toArray()); 
        return includeTemplate(model,"rule/ruleDetailsList");
    }
    
    /**
     * 发布前验证，待审批和已驳回的不能发布
     * @param ruleIds
     * @param model
     * @return
     */
    @RequestMapping(value = "/checkStatus")
    public @ResponseBody Object checkStatus(String ruleIds,ModelMap model)
    {
    	model.clear();
    	String data;
    	int resultSet=rlmgModelService.checkStatus(ruleIds);
    	if(resultSet==1)
			data = "1";
		else
			data = "0";
		return data;
    }
    @RequestMapping(value = "/checkStatus1")
    public @ResponseBody Object checkStatus1(String ruleIds,ModelMap model)
    {
    	model.clear();
    	String data;
    	int resultSet=rlmgModelService.checkStatus1(ruleIds);
    	if(resultSet==1)
			data = "1";
		else
			data = "0";
		return data;
    }
    @RequestMapping(value = "/checkStatus2")
    public @ResponseBody Object checkStatus2(String ruleIds,ModelMap model)
    {
    	model.clear();
    	String data;
    	int resultSet=rlmgModelService.checkStatus2(ruleIds);
    	if(resultSet==1)
    		data = "1";
    	else
    		data = "0";
    	return data;
    }
    /**
     * 发布声明窗口
     * @param mav
     * @param flag
     * @param ruleIds
     * @return
     */
    @RequestMapping(value = "/releaseAgreeRejectView")
    public ModelAndView releaseAgreeReject(ModelAndView mav,String flag,String ruleIds)
    {
        mav = new ModelAndView();
        mav.setViewName("/rule/releaseAgreeReject");
        mav.addObject("flag", flag);
        mav.addObject("ruleIds", ruleIds);
        return mav;
    }
    /**
     * 审批声明窗口
     * @param mav
     * @param flag
     * @param ruleIds
     * @return
     */
    @RequestMapping(value = "/agreeView")
    public ModelAndView agreeView(ModelAndView mav,String flag,String ruleIds)
    {
        mav = new ModelAndView();
        mav.setViewName("/rule/agreeView");
        mav.addObject("flag", flag);
        mav.addObject("ruleIds", ruleIds);
        return mav;
    }
    @RequestMapping(value = "/rejectView")
    public ModelAndView rejectView(ModelAndView mav,String flag,String ruleIds)
    {
    	mav = new ModelAndView();
    	mav.setViewName("/rule/rejectView");
    	mav.addObject("flag", flag);
    	mav.addObject("ruleIds", ruleIds);
    	return mav;
    }
    /**
     * 发布成功，插入数据库版本表,并修改状态为已发布
     * @param ruleIds
     * @param content
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/saveRuleVersion")
	public @ResponseBody Object saveRuleVersion(String ruleIds, String content) throws Exception{
		String data;
		String content1 = new String(content.getBytes("ISO-8859-1"),"GBK");
		int resultSet = rlmgModelService.insertRuleVersion(ruleIds,content1,SecurityContextHolder.getContext()
				.getAuthentication().getName());
		if(resultSet==1)
			data = "1";
		else
			data = "0";
		return data;
	}
    /**
     * 审批通过
     * @param ruleIds
     * @param content
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/changeStatus")
	public @ResponseBody Object changeStatus(String ruleIds, String content) throws Exception{
		String data;
		String content1 = new String(content.getBytes("ISO-8859-1"),"GBK");
		int resultSet = rlmgModelService.changeRuleStatus(ruleIds,content1,SecurityContextHolder.getContext()
				.getAuthentication().getName());
		if(resultSet==1)
			data = "1";
		else
			data = "0";
		return data;
	}
    /**
     * 审批驳回
     * @param ruleIds
     * @param content
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/changeStatus1")
	public @ResponseBody Object changeStatus1(String ruleIds, String content) throws Exception{
		String data;
		String content1 = new String(content.getBytes("ISO-8859-1"),"GBK");
		int resultSet = rlmgModelService.changeRuleStatus1(ruleIds,content1,SecurityContextHolder.getContext()
				.getAuthentication().getName());
		if(resultSet==1)
			data = "1";
		else
			data = "0";
		return data;
	}
	
    
}
