package com.founder.web.sysmgt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fmdm.entity.IamParm;
import com.founder.fmdm.service.sysmgt.SystSettingService;
import com.founder.fmdm.service.sysmgt.dto.SysSettingDto;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/sysmgt")
public class SysSettingController extends AbstractController
{

	final static String EPATH = "MQExceptionPath";
	
    @Autowired
    SystSettingService sysSettingService;

    /**
    * 这个功能用于页面连接并同时获取
    * 数据库的值传到页面对应选项
    * @param 
    * @return mav 方法操作成功返回值为mav
    * @throws Exception  如果出现异常发生抛出
    * @author xiefei
    */
    @RequestMapping("/sysSetting")
    public ModelAndView init(final ModelMap model) throws Exception
    {
    	model.clear();
        int pcr = sysSettingService.selectPwdCreateRule();
        int rm = sysSettingService.selectRemindMode();
        IamParm entity = sysSettingService.selectEPath();
        if(entity != null){
        	model.put("mqExceptionPath", entity.getParValue());
        }
        model.addAttribute("page_title", "系统设置");
        model.put("pwdCreateRule", pcr);
        model.put("remindMode", rm);
        return includeTemplate(model, "sysmgt/sysSetting");
    }

    /**
    * 这个功能用于页面输入选项后保存到数据库
    * @param 
    * @return mav 方法操作成功返回值为mav
    * @throws Exception  如果出现异常发生抛出
    * @author xiefei
    */
    @RequestMapping("/sysSettingSave") 
    public @ResponseBody
	Object saveSetting(SysSettingDto sysSettingDto,final ModelMap model)
            throws Exception
    {
    	model.clear();
        String backMsg;
        int result = sysSettingService.saveParatemer(sysSettingDto);
        if (result == 1)
        {
            backMsg = "";
        }else if(result == 2){
        	backMsg = "路径不对";
        }else{
        	result = 0;
        	backMsg = "保存失败";
        }
        model.put("backMsg", backMsg);
        model.addAttribute("page_title", "系统设置");
        model.put("pwdCreateRule", sysSettingDto.getPwdCreateRule());
        model.put("remindMode", sysSettingDto.getEmailMode()
            + sysSettingDto.getMessageMode());
        model.put("mqExceptionPath", sysSettingDto.getMqExceptionPath());
        model.addAttribute("status", result);
        return model;
    }

}
