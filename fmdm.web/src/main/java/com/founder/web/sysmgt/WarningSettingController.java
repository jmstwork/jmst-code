package com.founder.web.sysmgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fmdm.dao.sysmgt.WarningSettingDao;
import com.founder.fmdm.entity.IamSysInfo;
import com.founder.fmdm.entity.NfSetting;
import com.founder.fmdm.entity.NfSettingDetail;
import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.TDictPerson;
import com.founder.fmdm.service.rule.GroupManagerService;
import com.founder.fmdm.service.sysmgt.ConstantsDef;
import com.founder.fmdm.service.sysmgt.UserManagerService;
import com.founder.fmdm.service.sysmgt.WarningSettingService;
import com.founder.fmdm.service.sysmgt.dto.DepartmentDto;
import com.founder.fmdm.service.sysmgt.dto.SystemRegisterDto;
import com.founder.fmdm.service.sysmgt.dto.WarningSettingDto;
import com.founder.web.term.AbstractController;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/sysmgt")
public class WarningSettingController extends AbstractController
{
    @Autowired
    GroupManagerService groupManagerService;

    @Autowired
    UserManagerService userManagerService;

    @Autowired
    WarningSettingService warningSettingService;

    @Autowired
    WarningSettingDao warningSettingDao;

    static Logger logger = Logger.getLogger(WarningSettingController.class);

    @RequestMapping("/wsList")
    public ModelAndView examApproveList(WebRequest request,WarningSettingDto wsDto, String opt,final WsSearchCondition cond,ModelMap model) throws Exception
    {
    	model.clear();
    	String result = request.getParameter("resultSet");
    	model.put("result", result);
        String orderBy = "";
        model.addAttribute("page_title", "警告设置");
        //email与tel共用一个控件
        if("email".equalsIgnoreCase(cond.getNoticeMethod())){
        	cond.setEmail(cond.getTel());
        	wsDto.setEmail(cond.getTel());
        	wsDto.setTel("");
        }
        wsDto.setSettingId("");
        List<DepartmentDto> deptList = warningSettingService.selectDeptDataFromDictDepartment("all");
        List<RlmgRulegroup> ruleGroupList = groupManagerService.selectInitGros();
        List<SystemCode> codeList = warningSettingService.selectCodeList("all");
        if (ConstantsDef.OPERATION_EDIT.equalsIgnoreCase(opt))
        {
            orderBy = "s.last_update_time desc nulls last,s.create_time desc";
        }
        else
        {
            orderBy = "s.create_time desc nulls last";
        }
        SelectOptions options = getSelectOptions(cond);
        List<WarningSettingDto> wsList = warningSettingService.selectWSList(
                wsDto, orderBy, options);
        // 取详细表中的sysIds
        for (WarningSettingDto ws : wsList)
        {
            List<NfSettingDetail> emailList = warningSettingDao.selectDetailByCondition(
                    ws.getSettingId(), "2", null);
            if (emailList.size() > 0)
            {
                ws.setEmail(emailList.get(0).getSettingValue());
            }
            List<NfSettingDetail> telList = warningSettingDao.selectDetailByCondition(
                    ws.getSettingId(), "1", null);
            if (telList.size() > 0)
            {
                ws.setTel(telList.get(0).getSettingValue());
            }
            List<NfSettingDetail> sysList = warningSettingDao.selectDetailByCondition(
                    ws.getSettingId(), "3", null);
            if (sysList.size() > 0)
            {
                StringBuffer sb = new StringBuffer();
                for (NfSettingDetail nsd : sysList)
                {
                    sb.append(nsd.getSettingValue() + ":");
                }
                if (sb != null)
                {
                    ws.setSysIds(sb.substring(0, sb.length() - 1));
                }
            }
            List<SystemCode> cList = warningSettingService.selectCodeList(ws.getUserType()==null?"":ws.getUserType());
            if(cList.size()>0){
                ws.setUserTypeName(cList.get(0).getCdValue());
            }
        }
        pageSetting(cond, options);
        JSONArray departDtoJson = JSONArray.fromObject( deptList );
        String dictDepartmentJson = departDtoJson.toString();
        model.put("ruleGroup", ruleGroupList);
		model.put("dictDepartmentJson", dictDepartmentJson);
		model.put("codeList", codeList);
        model.put("wsList", wsList);
        model.put("wsCondition", cond);
        return includeTemplate(model, "sysmgt/warningSettingList");
    }

    @RequestMapping(value="/wsOperateView")
    public ModelAndView modelAdd(final ModelMap model, String opt,
            WarningSettingDto wsDto,final WsSearchCondition cond,String resetOpt) throws Exception
    {
        List<DepartmentDto> deptList = warningSettingService.selectDeptDataFromDictDepartment("all");
        List<SystemCode> codeList = warningSettingService.selectCodeList("all");
        List<RlmgRulegroup> ruleGroupList = groupManagerService.selectInitGros();
        String orderBy = "s.create_time desc nulls last";
        String view = "";
        if(ConstantsDef.OPERATION_EDIT.equalsIgnoreCase(resetOpt)){
			opt = "edit";
		}else if(ConstantsDef.OPERATION_ADD.equalsIgnoreCase(resetOpt) || opt == ""){
			opt = "add";
		}
        
        if(ConstantsDef.OPERATION_ADD.equalsIgnoreCase(opt)){
    		model.addAttribute("page_title", "新建警告设定");
    		view = "sysmgt/warningSettingAdd";
    	}else if(ConstantsDef.OPERATION_EDIT.equalsIgnoreCase(opt)){
    		model.addAttribute("page_title", "修改警告设定");
    		view = "sysmgt/warningSettingEdit";
    	}
        if (!"".equalsIgnoreCase(wsDto.getSettingId())
            && wsDto.getSettingId() != null)
        {
        	SelectOptions options = getSelectOptions(cond);
            List<WarningSettingDto> wsList = warningSettingService.selectWSList(
                    wsDto, orderBy, options);
            WarningSettingDto ws = (WarningSettingDto) wsList.get(0);
            // 取详细表中的sysIds
            for (WarningSettingDto ws1 : wsList)
            {
                //获取科室所对应的名称
                List<DepartmentDto> dList = warningSettingService.selectDeptDataFromDictDepartment(ws1.getUnitId()==null?"":ws1.getUnitId());
                if(dList.size()>0){
                    ws.setUnitName(dList.get(0).getName());
                }
                
                List<SystemCode> cList = warningSettingService.selectCodeList(ws1.getUserType()==null?"":ws1.getUserType());
                if(cList.size()>0){
                    ws.setUserTypeName(cList.get(0).getCdValue());
                }
                
                // List<RlmgRulegroup> rgList = groupManagerService.selectInitGros(ws1.getr);
                
                List<NfSettingDetail> emailList = warningSettingDao.selectDetailByCondition(
                        ws1.getSettingId(), "2", null);
                if (emailList.size() > 0)
                {
                    ws.setEmail(emailList.get(0).getSettingValue());
                }
                List<NfSettingDetail> telList = warningSettingDao.selectDetailByCondition(
                        ws1.getSettingId(), "1", null);
                if (telList.size() > 0)
                {
                    ws.setTel(telList.get(0).getSettingValue());
                    StringBuffer weekSb = new StringBuffer();
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgMon()))
                    {
                        weekSb.append("1:");
                        ws1.setMonday(true);
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgTus()))
                    {
                        weekSb.append("2:");
                        ws1.setTuesday(true);
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgWed()))
                    {
                        weekSb.append("3:");
                        ws1.setWednesday(true);
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgThu()))
                    {
                        weekSb.append("4:");
                        ws1.setThursday(true);
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgFri()))
                    {
                        weekSb.append("5:");
                        ws1.setFriday(true);
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgSat()))
                    {
                        weekSb.append("6:");
                        ws1.setSaturday(true);
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgSun()))
                    {
                        weekSb.append("7:");
                        ws1.setSunday(true);
                    }
                    String weekDays = "";
                    if (!"".equalsIgnoreCase(weekSb.toString()))
                    {
                        weekDays = weekSb.toString().substring(0,
                                weekSb.toString().length() - 1);
                    }
                    ws.setReceiveDays(weekDays);
                }
                List<NfSettingDetail> sysList = warningSettingDao.selectDetailByCondition(
                        ws1.getSettingId(), "3", null);
                if (sysList.size() > 0)
                {

                    StringBuffer sb = new StringBuffer();
                    for (NfSettingDetail nsd : sysList)
                    {
                        sb.append(nsd.getSettingValue() + ",");
                    }
                    if (sb != null)
                    {
                        ws.setSysIds(sb.substring(0, sb.length() - 1));
                    }
                }
            }
            if (ws.getSysIds() != null && !"".equalsIgnoreCase(ws.getSysIds()))
            {
                StringBuffer temp = new StringBuffer();
                String sysNames = null;
                List<IamSysInfo> sysList = null;
                if (ws.getSysIds().indexOf(",") >= 0)
                {
                    String[] arr = ws.getSysIds().split(",");
                    temp.append("	所选系统：");
                    for (String sysId : arr)
                    {
                        sysList = warningSettingService.selectSysNameBySysId(sysId);
                        if(sysList.size()!=0){
                        	
                        	  for (IamSysInfo sys : sysList)
                              {
                        		  temp.append(sys.getSysName()).append(",");
                              }
                        }
                    }
                    sysNames = temp.substring(0, temp.length()-1);
                    String arrNames[] = sysNames.toString().split(",");
                    StringBuffer sbNames = new StringBuffer();
                    if(arrNames.length>7){
                        for(int i=0;i<7;i++){
                            sbNames.append(arrNames[i]).append(",");
                        }
                        //sysNames = sbNames.substring(0, sbNames.length()-1)+"...";
                    }
                }
                else
                {
                    sysList = warningSettingService.selectSysNameBySysId(ws.getSysIds());
                    for (IamSysInfo sys : sysList)
                    {
                        sysNames = sys.getSysName();
                    }
                }
                ws.setSysNames(sysNames);
            }
            else
            {
                ws.setSysNames(null);
            }
           
            model.put("ws", ws);
        }
		JSONArray departDtoJson = JSONArray.fromObject( deptList );
		String dictDepartmentJson = departDtoJson.toString();
		model.put("dictDepartmentJson", dictDepartmentJson);
		model.put("codeList", codeList);
		model.put("ruleGroup", ruleGroupList);
        if (ConstantsDef.OPERATION_EDIT.equalsIgnoreCase(opt))
        {
        	model.put(ConstantsDef.OPERATION, ConstantsDef.OPERATION_EDIT);
        }
        else
        {
        	model.put(ConstantsDef.OPERATION, ConstantsDef.OPERATION_ADD);
        }
        return includeTemplate(model, view);
    }

    
    
    @RequestMapping("/userSelectView")
    public ModelAndView userSelectView(ModelAndView mav, String opt)
    {
        mav = new ModelAndView();
        mav.addObject(ConstantsDef.OPERATION, opt);
        mav.setViewName("sysmgt/userSelect");
        return mav;
    }

    @RequestMapping("/systemSelectList")
    public ModelAndView systemSelectList(final WsSearchCondition cond,SystemRegisterDto systemRegisterDto,String sysChoosed)
            throws Exception
    {
        ModelAndView mav = new ModelAndView("sysmgt/systemSelect");
        cond.setPageCount(8);
        SelectOptions options = getSelectOptions(cond);
        List<Map<String,Object>> systemReg = userManagerService.selectSystemReg(systemRegisterDto,options);
        pageSetting(cond, options);
        mav.addObject("sysCondition",cond);
        mav.addObject("sysList",systemReg);
        mav.addObject("sysIds",sysChoosed);
        return mav;
    }
    
    @RequestMapping("/userSelectList")
    public ModelAndView userSelectList(TDictPerson dictPerson, String opt,final UserSearchCondition cond)
            throws Exception
    {
        ModelAndView mav = new ModelAndView("sysmgt/userSelect");
        SelectOptions options = getSelectOptions(cond);
        dictPerson.setCode(cond.getUserId());
        dictPerson.setName(cond.getUserName());
        List<TDictPerson> userList = warningSettingService.selectUserListDictPerson(
                dictPerson, options);
        pageSetting(cond, options);
        mav.addObject("userList",userList);
        mav.addObject("userCondition",cond);
        return mav;
    }
    
    @RequestMapping("/wsOperate")
    public ModelAndView wsOperate(HttpServletRequest request,
            HttpServletResponse response,final ModelMap model,
            WarningSettingDto wsDto, String opt,String resetOpt) throws Exception
    {
    	model.clear();
    	Map<String,Object> map= new HashMap<String, Object>();
    	List<DepartmentDto> deptList = warningSettingService.selectDeptDataFromDictDepartment("all");
        List<RlmgRulegroup> ruleGroupList = groupManagerService.selectInitGros();
        List<SystemCode> codeList = warningSettingService.selectCodeList("all");
        JSONArray departDtoJson = JSONArray.fromObject( deptList );
        String dictDepartmentJson = departDtoJson.toString();
        model.put("ruleGroup", ruleGroupList);
		model.put("dictDepartmentJson", dictDepartmentJson);
		model.put("codeList", codeList);
		if(ConstantsDef.OPERATION_ADD.equalsIgnoreCase(resetOpt) || opt == ""){
			opt = "add";
		}
		model.put(ConstantsDef.OPERATION, opt);
    	try{
    	      // 注意：采用重定向的目的是避免右键刷新按钮重复提交
    	      int result = warningSettingService.wsOperate(wsDto, opt);
    	      if (result > 0)
    	      {
    	           map.put(ConstantsDef.RESULTSET,1);
    	      }else{
    	    	   map.put(ConstantsDef.RESULTSET,0);
    	      }

    	   }catch (Exception e){
    	      logger.error(e.getMessage(), e);
    	      map.put(ConstantsDef.RESULTSET, -1);
    	      List<IamSysInfo> sysList = new ArrayList<IamSysInfo>();
    	      String sysNames = null;
    	      if (wsDto.getSysIds() != null && !"".equalsIgnoreCase(wsDto.getSysIds())){
    	          if (wsDto.getSysIds().indexOf(",") > 0){
    	              String[] arr = wsDto.getSysIds().split(",");
    	              for (int i=0;i<arr.length;i++){
    	                   sysList = warningSettingService.selectSysNameBySysId(arr[i]);
    	                   for(IamSysInfo sys:sysList){
    	                       if(i==0){
    	                            sysNames = sys.getSysName();
    	                       }else{
    	                            sysNames = sysNames+", "+sys.getSysName();
    	                       }
    	                    }
    	              }
    	              sysNames = sysNames.substring(0, sysNames.length());
    	              String arrNames[] = sysNames.split(", ");
    	              StringBuffer sbNames = new StringBuffer();
    	              if(arrNames.length>7){
    	                  for(int i=0;i<7;i++){
    	                      sbNames.append(arrNames[i]).append(", ");
    	                  }
    	                  sysNames = sbNames.substring(0, sbNames.length())+"...";
    	              }
    	           }else{
    	                  sysList = warningSettingService.selectSysNameBySysId(wsDto.getSysIds());
    	                  for (IamSysInfo sys : sysList){
    	                        sysNames = sys.getSysName();
    	                  }
    	           }
    	           wsDto.setSysNames(sysNames);
    	     }else{
    	           wsDto.setSysNames(null);
    	     }

    	     if (wsDto.getReceiveDays() != null){
    	         if (wsDto.getReceiveDays().contains("1")){
    	            wsDto.setMonday(true);
    	         }
    	         if (wsDto.getReceiveDays().contains("2")){
    	            wsDto.setTuesday(true);
    	         }
    	         if (wsDto.getReceiveDays().contains("3")){
    	            wsDto.setWednesday(true);
    	         }
    	         if (wsDto.getReceiveDays().contains("4")){
    	            wsDto.setThursday(true);
    	         }
    	         if (wsDto.getReceiveDays().contains("5")){
    	            wsDto.setFriday(true);
    	         }
    	         if (wsDto.getReceiveDays().contains("6")){
    	            wsDto.setSaturday(true);
    	         }
    	         if (wsDto.getReceiveDays().contains("7")){
    	            wsDto.setSunday(true);
    	         }
    	      }
    	         model.put("ws", wsDto);
    	         
    	      }
    	      ModelAndView mav = new ModelAndView("redirect:/sysmgt/wsList.html", map);
    	      return mav;
    }
    
    @RequestMapping("/checkExist")
    public ModelAndView checkExist(String unitId, String userNo,String groupCode,String userType)
    {
        ModelAndView mav = new ModelAndView();
        List<NfSetting> list = null;
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("deleteFlg", 0);
        condition.put("userNo", userNo);
        condition.put("rulegroupId", groupCode);
        condition.put("userType", userType);
        if (!"".equalsIgnoreCase(unitId)&&unitId!=null)
        {
            condition.put("unitId", unitId);
        }
        list = warningSettingDao.selectNfSettingByCond(userNo, groupCode, userType, unitId);
        
        if (list.size() > 0)
        {
            mav.addObject(ConstantsDef.RESULTSET, ConstantsDef.ACCOUNT_STATUS_1);
        }
        else
        {
            mav.addObject(ConstantsDef.RESULTSET, ConstantsDef.ACCOUNT_STATUS_0);
        }
        mav.setViewName(ConstantsDef.SPRINGJSONVIEW);
        return mav;
    }
}
