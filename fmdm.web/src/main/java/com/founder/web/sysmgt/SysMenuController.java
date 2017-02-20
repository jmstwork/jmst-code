package com.founder.web.sysmgt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.service.dto.DefaultUserDetails;
import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.service.subscribe.SysMenuButtonService;
import com.founder.fmdm.service.subscribe.dto.SysAuthorityDto;
import com.founder.fmdm.service.subscribe.dto.TreeDto;
import com.founder.util.StringUtil;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/menus")
public class SysMenuController extends AbstractController {
	
	@Autowired
	private SysMenuButtonService sysMenuButtonService;

	
	@RequestMapping(value = "/manage")
	public ModelAndView systemAuthority(final ModelMap model)  throws Exception {
		
		DefaultUserDetails userDetails = (DefaultUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails);
		
		//查询菜单下面的按钮
		//List<SysAuthorityDto> sysAuthority = sysMenuButtonService.selectAllSysAuthority();
		//model.addAttribute("sysAuthority", sysAuthority);
		
		model.addAttribute("page_title", "菜单管理");
		return includeTemplate(model, "sysmgt/menuManage");
	}
	
	@RequestMapping(value = "/getChildNodes")
	@ResponseBody
	public List<TreeDto> getChildNodes(String id,String name,String otherParam){
		List<TreeDto> data = new ArrayList<TreeDto>();
		if(id==null){
			data = sysMenuButtonService.selectSysMenuTree();
		}else{
			data = sysMenuButtonService.selectChildByPid(id);
		}
		return  data;
	}
	
	
	@RequestMapping(value = "/handleNodes")
	@ResponseBody
	public Object handleNodes(String smbId,String parentNode,String smbName,String smbLink,String type,String updateCount,final ModelMap model) throws Exception{
		SysMenuButton smb = new SysMenuButton();
		smbId = StringUtil.isEmpty(smbId) ? null:smbId;
		
		smb.setSmbId(smbId);
		smb.setParentNode(parentNode);
		smb.setSmbName(smbName);
		smb.setSmbLink(smbLink);
		if(type!=null){
			smb.setType(Integer.valueOf(type));
		}
		if(!StringUtil.isEmpty(updateCount)){
			smb.setUpdateCount(Integer.valueOf(updateCount));
		}
		
		int result = 0;
		if(smbId == null){
			result = sysMenuButtonService.handleSysMenu("insert",smb);
		}else{
			result = sysMenuButtonService.handleSysMenu("update",smb);
		}
		
		
		model.addAttribute("success", result > 0 ? true : false);
		return model;
	}
	
	@RequestMapping(value = "/deleteNodes")
	@ResponseBody
	public Object deleteNodes(String smbId,final ModelMap model) throws Exception{
		SysMenuButton smb = sysMenuButtonService.selectSysMenuButton(smbId);
		smbId = StringUtil.isEmpty(smbId) ? null:smbId;
		
		if(smbId != null){
			smb.setSmbId(smbId);
			smb.setDeleteFlg(1);
		}
		int result = sysMenuButtonService.handleSysMenu("delete",smb);
		
		model.addAttribute("success", result > 0 ? true : false);
		return model;
	}
	
	

}
