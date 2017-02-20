package com.founder.web.sysmgt;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.drools.core.util.StringUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.Role;
import com.founder.fmdm.entity.RoleAuthority;
import com.founder.fmdm.service.authority.RoleAuthorityService;
import com.founder.fmdm.service.subscribe.dto.TreeDto;
import com.founder.fmdm.service.sysmgt.SysUserService;
import com.founder.fmdm.service.term.dto.TseUsersDto;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/authority")
public class AuthorityController extends AbstractController {
	private static final String ROLE_FLAG ="role";//用于前端区分，是角色选项卡，还是账户选项卡
	private static final String USER_FLAG ="user";
	
	private static final String TAB_TITLE_ONE = "0";//角色编辑
	private static final String TAB_TITLE_TWO = "1";//成员管理
	private static final String TAB_TITLE_THREE = "2";//系统权限
	private static final String TAB_TITLE_FOUR = "3";//术语权限
	@Autowired
	SysUserService sysUserService;

	@Autowired
	RoleAuthorityService roleAuthorityService;
	
	@Autowired
	LogUtils logUtils;
	
	//从连接点击权限管理，会打开角色选项卡
	@RequestMapping(value = "/authorityMain", method = RequestMethod.GET)
	public ModelAndView initRolePage(final RoleSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.addAttribute("page_title", "权限管理");
		List<Role> roleList = sysUserService.selectRoleList(cond.getRoleName(),null,options);
		model.put("roleList", roleList);
		//这很重要,因为前台两个选项卡在一页，所以必须要给model赋值
		model.put("accountCondition", new AuthorityCondition());
		pageSetting(cond, options);
		//标识当前是角色选卡，用于打开角色选卡
		model.put("option", ROLE_FLAG);
		model.put("roleCondition", cond);
		return includeTemplate(model, "sysmgt/authorityMain");
	}
	
	@RequestMapping(value = "/showRoleInAuthority")
	public ModelAndView JumpRolePage(final RoleSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.addAttribute("page_title", "权限管理");
		List<Role> roleList = sysUserService.selectRoleList(cond.getRoleName(),null,options);
		model.put("roleList", roleList);
		model.put("accountCondition", new AuthorityCondition());
		model.put("roleCondition", cond);
		//标识当前是角色选卡，用于打卡角色选卡
		model.put("option", ROLE_FLAG);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/authorityMain");
	}

	
	@RequestMapping(value="/showUserInAuthority")
	public ModelAndView initUserPage(final AuthorityCondition cond, final ModelMap model) throws Exception{
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.addAttribute("page_title", "权限管理");
		List<TseUsersDto> userList = sysUserService.selecTseUsersData(null,cond.getUserAccount(), cond.getUserName(),
		null,options);
		model.put("userList", userList);
		//这很重要,因为前台两个选项卡在一页，所以必须要给model赋值
		model.put("roleCondition", new RoleSearchCondition());
		//标识当前是账户选卡，用于打开账户选卡
		model.put("option", USER_FLAG);
		pageSetting(cond, options);
		model.put("accountCondition", cond);
		return includeTemplate(model, "sysmgt/authorityMain");
	}
		
	
	/**
	 * 显示角色权限的asyncbox
	 * @param cond
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setRoleSysAuthority")
	public ModelAndView setSysAuthority(String roleId,String flag,final RoleSearchCondition cond, final ModelMap model) throws Exception{
		model.clear();
		ModelAndView mav = new ModelAndView("authority/setRoleSysAuthority");
		mav.addObject("roleId", roleId);
		mav.addObject("flag", flag);
		List<RoleAuthority> authoritys = roleAuthorityService.getRoleAuthority(roleId);
		mav.addObject("authoritys", authoritys);
		return mav;
	}
	
	/**
	 * 根据角色id。加载权限树
	 * @param roleId
	 * @return 权限树json
	 */
	@RequestMapping(value="/getRoleAuthorityTree")
	@ResponseBody
	public List<TreeDto> getRoleAuthorityTree(String roleId){
		List<TreeDto> allAuthorityList = roleAuthorityService.selectAllAuthority();
		List<RoleAuthority> roleAuthorityList = roleAuthorityService.getRoleAuthority(roleId);
		allAuthorityList = this.createCheckedAuthorityTree(allAuthorityList, roleAuthorityList);
		return allAuthorityList;
	}
	
	/**
	 * 保存角色权限
	 * @param 被选中的权限的id，以@划分
	 * @param roleId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/saveRoleSysAuthority")
	public void saveRoleSysAuthority(String param,String roleId,HttpServletResponse response) throws IOException{
		String[] smbIds = param.split("\\@");
		List<RoleAuthority> authoritys = new LinkedList<RoleAuthority>();
		for(String smbId: smbIds){
			if(!"".equals(smbId)){
				RoleAuthority authority = new RoleAuthority();
				authority.setRoleId(roleId);
				authority.setSmbId(smbId);
				authority.setRoleAuthorityId(StringUtils.generateUUID());
				authoritys.add(authority);
			}
		}
		try{
			roleAuthorityService.saveRoleAuthority(authoritys, roleId);
			response.getWriter().write("0");
			response.getWriter().flush();
			String roleName = sysUserService.selectRoleById(roleId).getRoleName();
			logUtils.insertLog("00104", "00104006", "修改角色[{}]权限",roleName);
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("-1");
			response.getWriter().flush();
		}
	}
	
	@RequestMapping(value = "/systemAuthority_{roleId}")
	public ModelAndView systemAuthority(@PathVariable String roleId,final ModelMap model)  throws Exception {
		
		System.out.println(roleId);
//		List<TreeDto> tree = sysMenuButtonService.selectSysMenuTree();
//		List<TreeDto> data = new ArrayList<TreeDto>();
//		for(TreeDto t : tree){
//			if(t.getpId().equals("0")){
//				data.add(t);
//			}
//		}
//		model.put("tree", data);
		return includeTemplate(model, "authority/systemAuthority");
	}
	
	/**
	 * 获得勾选后的权限树，即，角色的所拥有的权限被打上勾
	 * @param allAuthorityList 全部权限
	 * @param roleAuthorityList 角色拥有权限
	 * @return 修改后的List<TreeDto>
	 */
	private List<TreeDto> createCheckedAuthorityTree(List<TreeDto> allAuthorityList, List<RoleAuthority> roleAuthorityList){
		for(RoleAuthority roleAuthority: roleAuthorityList){
			for(TreeDto allAuthority: allAuthorityList){
				if(allAuthority.getId().equalsIgnoreCase(roleAuthority.getSmbId())){
					allAuthority.setChecked(true);
				}
			}
		}
		return allAuthorityList;
	}
	
	
	//一下都为测试功能，未完成
	//角色編輯的總界面
	//任何一个分页，都要跳转到总页面，总页面会根据tabTitle，来打开某个分页
	@RequestMapping(value="/roleEdit")
	public ModelAndView initEditPage(final RoleSearchCondition cond, final ModelMap model,
			@RequestParam(value="tabTitle",required=false) String tabTitle) throws Exception {
		model.clear();
		model.put("roleCondition", cond);
		model.addAttribute("page_title", "权限管理->角色->编辑");

		Role role = new Role();
		String roleId = cond.getRoleId();
		if (roleId != null && !"".equals(roleId)) {
			role = sysUserService.selectRoleById(roleId);
		}
		//这很重要
		model.addAttribute("roleSearchCondition", cond);
		//model.addAttribute("tabTitle",tabTitle);
		model.addAttribute("role", role);
		return includeTemplate(model, "authority/role/edit");
//		return includeTemplate(model, "authority/role/editTest");
	}
	
	//初始化成员管理页面
//	@RequestMapping(value="/initMemberShip")
//	private ModelAndView initMemberShipPage(ModelMap model,
//			RoleSearchCondition cond,String tabTitle) throws Exception{
//		model.put("roleSearchCondition", cond);
//		SelectOptions options = getSelectOptions(cond);
//		Role role = new Role();
//		String roleId = cond.getRoleId();
//		if (roleId != null && !"".equals(roleId)) {
//			role = sysUserService.selectRoleById(roleId);
//			model.addAttribute("role", role);
//			List<TseUsersDto> userList = sysUserService.selectUserListByRoleId(roleId,cond.getUserAccount(),cond.getUserName(),options);
//			model.addAttribute("userList", userList);
//		} else {
//			model.addAttribute("role", role);
//		}
//		model.addAttribute("tabTitle", tabTitle);
//		pageSetting(cond, options);
////		return new ModelAndView("role/roleUserList",model);
//		return includeTemplate(model, "authority/role/edit");
//	}
	
	//test
	@RequestMapping(value="/role/roleEdit")
	public ModelAndView initRoleEditPage(final RoleSearchCondition cond, final ModelMap model)throws Exception{
		model.clear();
		model.put("roleCondition", cond);
		model.addAttribute("page_title", "权限管理->角色->编辑");

		Role role = new Role();
		String roleId = cond.getRoleId();
		if (roleId != null && !"".equals(roleId)) {
			role = sysUserService.selectRoleById(roleId);
		}
		model.addAttribute("role", role);
		return new ModelAndView("role/roleEdit");
	}
	
	//test
	//单击tab的成员管理
	@RequestMapping(value="/role/userList")
	public ModelAndView initMemberShip(final RoleSearchCondition cond, final ModelMap model,
			@RequestParam(value="tabTitle",required=false) String tabTitle) throws Exception {
		model.clear();
		model.put("roleSearchCondition", cond);
		model.addAttribute("page_title", "关联用户设定");
		SelectOptions options = getSelectOptions(cond);
		Role role = new Role();
		String roleId = cond.getRoleId();
		if (roleId != null && !"".equals(roleId)) {
			role = sysUserService.selectRoleById(roleId);
			model.addAttribute("role", role);
			List<TseUsersDto> userList = sysUserService.selectUserListByRoleId(roleId,cond.getUserAccount(),cond.getUserName(),options);
			model.addAttribute("userList", userList);
		} else {
			model.addAttribute("role", role);
		}
		model.addAttribute("tabTitle", tabTitle);
		pageSetting(cond, options);
		//return new ModelAndView("role/roleUserList",model);
		return new ModelAndView("authority/role/roleUserList");
		//return includeTemplate(model, "sysmgt/roleUserList");
	}
	
	//用ajax获取userList
//	@RequestMapping(value="ajaxMemberShip")
//	public @ResponseBody Object ajaxMemberShip(final RoleSearchCondition cond, final ModelMap model){
//		
//	}
	
}
