package com.founder.web.security;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.service.dto.DefaultUserDetails;
import com.founder.fmdm.Constants;
import com.founder.fmdm.entity.TseUsers;
import com.founder.fmdm.service.sysmgt.SysUserService;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController
{
	@Autowired
	SysUserService sysUserService;
	
    @RequestMapping(value="/login")
    public ModelAndView login(String status) throws Exception
    {
    	ModelAndView mav = new ModelAndView();
    	if(status!=null)
    		mav.addObject("loginMsg", status);
    	/**
    	 * @author li_zhong2
    	 * 允许医院自定义登录页面,包括登录背景图片等
    	 */
    	String hospitalLoginStyle = Constants.HOSPITAL_LOGIN_STYLE;
    	String loginBgPic = null;
    	//web服务启动时，如果properties目录下存在定制的图片，会按照用户配置的路径复制到项目中，读取项目
        if(!StringUtils.isEmpty(Constants.HOSPITAL_LOGIN_BG)){
        	loginBgPic = backPic(loginBgPic,Constants.HOSPITAL_LOGIN_BG);
        }
    	if("PKUIH".equals(hospitalLoginStyle)){
    		mav.setViewName("common/guojiLogin");
    	}else if("FOUNDER".equals(hospitalLoginStyle)){
    		mav.setViewName("common/login");
    		mav.addObject("loginBgPic","images/login/background.png");
    	}
//        String loginBgHospital = Constants.COMPANY_LOGIN_BGHOSPITAL;
//       
//        if(!StringUtils.isEmpty(Constants.HOSPITAL_LOGIN_BGHOSPITAL)){
//        	loginBgHospital = backPic(loginBgHospital,Constants.HOSPITAL_LOGIN_BGHOSPITAL);
//        }
//        mav.addObject("loginBgHospital",backPic(loginBgHospital,Constants.HOSPITAL_LOGIN_BGHOSPITAL));
        // $[BUG]0046046 ADD END
        return mav;
    }
    
    //获取最后读取的logo图片
    public String backPic(String companyPic,String hospitalPic){
    	//获取cdr项目所在的根路径
        String webPath = new File(this.getClass().getResource("/").getPath()).getAbsolutePath();
        //处理windows与linux之间的差异
        if(webPath.contains("WEB-INF")){
        	webPath = webPath.substring(0,webPath.indexOf("WEB-INF"));
        }
        String picPath = Constants.HOSPITAL_PIC_FOLDER + "/" + hospitalPic;
       	File file = new File(webPath + picPath);
       	if(file.exists()&&file.isFile()){
       		return picPath;
       	}
       	
    	return companyPic;
    }
    
    
    @ModelAttribute("users")
    public List<Map<String, String>> populateUsers()
    {
        List<Map<String, String>> users = new ArrayList<Map<String, String>>();
        
        return users;
    }
    @RequestMapping(value="/index")
    public ModelAndView includeTemplate(Map<String, ?> model, String fragment,HttpServletRequest request,HttpServletResponse response) throws Exception
    {
		DefaultUserDetails userDetails = (DefaultUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		if(!"admin".equals(userDetails.getUsername())){
		TseUsers user = sysUserService.selectSysUserByUserName(userDetails.getUsername());
		Integer enableFlag = user.getEnableFlag();
		if(enableFlag==0){//停用
			request.getRequestDispatcher("/security/login.html?status=disabled").forward(request, response);
		}}
        ModelAndView mav = new ModelAndView("template-table", model);
        ModelMap mm = mav.getModelMap();
        mm.addAttribute("menus", userDetails.getMenus());
        mm.addAttribute("page_title", "首页");
        mm.addAttribute("frag_content","welcome");
        
        //   $Author :yang_mingjie
        //   $Date : 2014/08/12 8:49$
        //   [BUG]0046046 ADD BEGIN
        handleMainBgPic(mav);
        // $[BUG]0046046 ADD END
        return mav;
    }
}
