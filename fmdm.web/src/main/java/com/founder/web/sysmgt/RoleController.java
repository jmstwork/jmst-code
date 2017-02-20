package com.founder.web.sysmgt;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.Role;
import com.founder.fmdm.entity.RoleView;
import com.founder.fmdm.entity.TseUsers;
import com.founder.fmdm.entity.UserRole;
import com.founder.fmdm.service.authority.RoleAuthorityService;
import com.founder.fmdm.service.sysmgt.SysUserService;
import com.founder.fmdm.service.sysmgt.ViewSetService;
import com.founder.fmdm.service.term.dto.TseUsersDto;
import com.founder.fmdm.service.term.dto.ViewTypeDto;
import com.founder.fmdm.service.term.dto.ViewsDto;
import com.founder.util.StringUtil;
import com.founder.web.term.AbstractController;

@Controller
@RequestMapping("/role")
public class RoleController extends AbstractController {

	@Autowired
	// RoleService roleService;
	SysUserService sysUserService;

	@Autowired
	ViewSetService viewSetService;
	
	@Autowired
	RoleAuthorityService roleAuthorityService;
	
	@Autowired
	LogUtils logUtils;
		
	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	public ModelAndView initListPage(final RoleSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.put("roleSearchCondition", cond);
		model.addAttribute("page_title", "角色管理");
		List<Role> roleList = sysUserService.selectRoleList(cond.getRoleName(),null,options);
		model.put("roleList", roleList);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/roleList");
	}

	@RequestMapping(value = "/roleList", method = RequestMethod.POST)
	public ModelAndView showRoles(final RoleSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		model.put("roleSearchCondition", cond);
		model.addAttribute("page_title", "角色管理");
		SelectOptions options = getSelectOptions(cond);
		List<Role> roleList = sysUserService.selectRoleList(cond.getRoleName(),null,options);
		model.put("roleList", roleList);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/roleList");
	}
	
	//角色管理模块-加载角色编辑框
	@RequestMapping(value = "/roleEdit")
	public ModelAndView initEditPage(final RoleSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		ModelAndView mav = new ModelAndView("sysmgt/roleEdit");
		mav.addObject("roleSearchCondition", cond);
		// SelectOptions options = getSelectOptions(cond);
		Role role = new Role();
		String roleId = cond.getRoleId();
		if (roleId != null && !"".equals(roleId)) {
			role = sysUserService.selectRoleById(roleId);
		}
		mav.addObject("role", role);
		// pageSetting(cond, options);
		return mav;
	}
	
	//正在测试的编辑
//	@RequestMapping(value = "/roleEditTest")
//	public ModelAndView initTestPage(final RoleSearchCondition cond, final ModelMap model,
//							@RequestParam("tabTitle") String tabTitle) throws Exception {
//		model.clear();
//		model.put("roleCondition", cond);
//		model.addAttribute("page_title", "权限管理->角色->编辑");
//
//		Role role = new Role();
//		String roleId = cond.getRoleId();
//		if (roleId != null && !"".equals(roleId)) {
//			role = sysUserService.selectRoleById(roleId);
//		}
//		model.addAttribute("role", role);
//		//让标签默认为第1项
//		model.addAttribute("tabTitle", tabTitle);
//		return includeTemplate(model, "role/roleEdit");
//	}
	//删除一个或多个角色
	@RequestMapping(value = "/deleteRoles")
	public @ResponseBody Object deleteRoles(final RoleSearchCondition cond, final ModelMap model) {
		model.clear();
		int result;
		try {
			String ids = cond.getRoleId();
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				Role role = sysUserService.selectRoleById(id);
				role.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
				role.setDeleteTime(new Date());
				role.setDeleteFlg(1);
				
				//逻辑删除role，物理删除和角色有关的权限，视图，账户的关系
				this.deleteRoleAndBindData(role);
				//记录日志
				logUtils.insertLog("00104", "00104002", "删除角色[{}]", role.getRoleName());
				/*
				 * //根据角色Id检索用户角色关联表信息，将属于此角色的用户全部删除 //List<UsersRoles>
				 * usersRolesList = sysUserService.selectUsersRolesListById(id);
				 * List<UsersRoles> usersRolesList =
				 * sysUserService.selectUsersRolesListByRoleId(id);
				 * for(UsersRoles usersRoles:usersRolesList){
				 * usersRoles.setDeleteFlg(1);
				 * usersRoles.setDeleteBy(SecurityContextHolder.getContext().
				 * getAuthentication().getName()); usersRoles.setDeleteTime(new
				 * Date()); result=sysUserService.deleteUsersRoles(usersRoles);
				 * }
				 */
			}
			result = 1;
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		//设置tab默认选中第二项
		model.addAttribute("tabTitle", 1);
		return model;
	}
	
	/**
	 * 把角色delete_flg变为1，把中间表数据，#物理#删除
	 * @param role
	 */
	@Transactional
	private void deleteRoleAndBindData(Role role) throws Exception{
		//逻辑删除角色
		sysUserService.updateRole(role);
		//物理删除角色-账户绑定关系
		sysUserService.deleteUsersByRoleId(role.getRoleId());
		//物理删除角色-视图绑定关系
		viewSetService.deleteViewsByRoleId(role.getRoleId());
		//物理删除角色-权限绑定关系
		roleAuthorityService.deleteAuthorityByRoleId(role.getRoleId());
	}
	
	// 角色视图设定
	@RequestMapping(value = "/setRoleView")
	public ModelAndView setRoleView(final ViewSearchCondition cond,String roleId,String flag,final ModelMap model) throws Exception {
		model.clear();
		model.put("roleSearchCondition", cond);
		model.addAttribute("page_title", "角色管理-视图设定");
		SelectOptions options = getSelectOptions(cond);
		Role role = new Role();
		role = sysUserService.selectRoleById(roleId);
		model.addAttribute("role", role);
		List<ViewsDto> viewList = viewSetService.selectViewListByRoleId(roleId, cond.getViewName(), cond.getViewType(), cond.getDictName(), options);
		model.addAttribute("viewList", viewList);
		pageSetting(cond, options);
		List<ViewTypeDto> viewTypeList = viewSetService.selectViewsType();
		model.put("viewTypeList", viewTypeList);
		model.put("viewSearchCondition", cond);
		model.put("flag", flag);
		return includeTemplate(model, "sysmgt/viewSet");
	}

	// 用户展示列表
	@RequestMapping(value = "/selectUserAccount")
	public ModelAndView selectUserAccount(final UserSearchCondition cond,String roleId,final ModelMap model) {
		ModelAndView mav = new ModelAndView("sysmgt/selectUserAccount");
		cond.setPageCount(7);
		SelectOptions options = getSelectOptions(cond);
		List<TseUsersDto> userAccountList = sysUserService.selecTseUsersData(roleId,cond.getUserAccount(), cond.getUserName(),
				null,options);
		mav.addObject("userList", userAccountList);
		mav.addObject("roleId", roleId);
		pageSetting(cond, options);
		mav.addObject("sysUserSearchCondition", cond);
		return mav;
	}

	// 已选择用户展示列表
	@RequestMapping(value = "/selectUsersById")
	public @ResponseBody Object selectUnits(String userIds, final ModelMap model) {
		String[] userIdArry = userIds.split(",");
		List<String> userIdList = Arrays.asList(userIdArry);
		List<TseUsersDto> userList = sysUserService.selectUserListByIdList(userIdList);
		model.addAttribute("userList", userList);
		return model;
	}

	// 通过“新增”添加用户
	@RequestMapping(value = "/selectUserByUserAccount")
	public @ResponseBody Object selectUser(String userAccount, final ModelMap model) {
		TseUsersDto user = sysUserService.selectTseUsersDtoByUserAccount(userAccount);
		model.addAttribute("user", user);
		return model;
	}

	@RequestMapping(value = "/checkRoleExist")
	public @ResponseBody Object checkRoleExist(final RoleSearchCondition cond, final ModelMap model) {
		model.clear();
		int result;
		Role role = sysUserService.selectRoleByRoleName(cond.getRoleName());
		if (role != null)
			result = 1;
		else
			result = 0;
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}

	@RequestMapping(value = "/saveRole")
	public @ResponseBody Object saveRoleInfo(final RoleSearchCondition cond, String userIds, final ModelMap model) {
		model.clear();
		int result;
		try {

			Role role = new Role();
			if (cond.getRoleId() != null && !"".equals(cond.getRoleId())) {
				// 根据角色Id检索用户角色关联表信息，将属于此角色的用户全部删除
				List<UserRole> usersRolesList = sysUserService
						.selectUsersRolesListByRoleId(cond.getRoleId());
				for (UserRole usersRoles : usersRolesList) {
					usersRoles.setDeleteFlg(1);
					usersRoles.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
					usersRoles.setDeleteTime(new Date());
					result = sysUserService.deleteUsersRoles(usersRoles);
				}

				role = sysUserService.selectRoleById(cond.getRoleId());
				String editDescribe = "";
				if (StringUtils.isNotEmpty(cond.getRoleName()) && !role.getRoleName().equals(cond.getRoleName())) {
					editDescribe = editDescribe + "原角色名称为[" + role.getRoleName() + "],现角色名称为[" + cond.getRoleName()
							+ "]";
				}
				/*
				 * if(StringUtils.isNotEmpty(cond.getMemo()) &&
				 * !cond.getMemo().equals(role.getMemo())){ editDescribe =
				 * editDescribe +
				 * "原角色描述为["+role.getMemo()+"],现角色描述为["+cond.getMemo()+"]"; }
				 */
				if (StringUtils.isNotEmpty(cond.getMemo())) {
					if (StringUtils.isEmpty(role.getMemo())) {
						editDescribe = editDescribe + "新增角色描述[" + cond.getMemo() + "]";
					} else if (!cond.getMemo().equals(role.getMemo())) {
						editDescribe = editDescribe + "原角色描述为[" + role.getMemo() + "],现角色描述为[" + cond.getMemo() + "]";
					}
				} else {
					if (StringUtils.isNotEmpty(role.getMemo())) {
						editDescribe = editDescribe + "删除角色描述[" + role.getMemo() + "]";
					}
				}
				if (!"".equals(editDescribe)) {
					editDescribe = "(" + editDescribe + ")";
				}
				// String roleName = role.getRoleName();

				//role = new Roles();
				role.setRoleId(cond.getRoleId());
				role.setRoleName(cond.getRoleName());
				role.setMemo(cond.getMemo());
				role.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				role.setLastUpdateTime(new Date());
				result = sysUserService.updateRole(role);
				if(!"".equals(editDescribe))
					logUtils.insertLog("00104", "00104003", "修改角色[{}]" + editDescribe, cond.getRoleName());

				/*
				 * //将新的用户角色关系插入 if(userIds !=null && !"".equals(userIds)){
				 * String[] userIdArry=userIds.split(","); for(String userId :
				 * userIdArry){ UsersRoles usersRoles= new UsersRoles();;
				 * usersRoles.setUserRoleId(StringUtil.getUUID());
				 * usersRoles.setRoleId(cond.getRoleId());
				 * usersRoles.setUseId(userId);
				 * usersRoles.setCreateBy(SecurityContextHolder.getContext().
				 * getAuthentication().getName()); usersRoles.setCreateTime(new
				 * Date()); usersRoles.setDeleteFlg(0); result=
				 * sysUserService.addUsersRoles(usersRoles); } }
				 */

			} else {
				String roleId = String.valueOf(new Date().getTime());
				role.setRoleId(roleId);
				role.setRoleName(cond.getRoleName());
				role.setDeleteFlg(0);
				role.setMemo(cond.getMemo());
				role.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				role.setCreateTime(new Date());
				role.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				role.setLastUpdateTime(new Date());
				result = sysUserService.addRole(role);
				logUtils.insertLog("00104", "00104001", "新增角色[{}]", cond.getRoleName());

				/*
				 * //将新的用户角色关系插入 if(userIds !=null && !"".equals(userIds)){
				 * String[] userIdArry=userIds.split(","); for(String userId :
				 * userIdArry){ UsersRoles usersRoles= new UsersRoles();;
				 * usersRoles.setUserRoleId(StringUtil.getUUID());
				 * usersRoles.setRoleId(roleId);
				 * usersRoles.setUseId(userId);
				 * usersRoles.setCreateBy(SecurityContextHolder.getContext().
				 * getAuthentication().getName()); usersRoles.setCreateTime(new
				 * Date()); usersRoles.setDeleteFlg(0); result=
				 * sysUserService.addUsersRoles(usersRoles); } }
				 */
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	//查询要删除的角色是否有关联，并返回提示信息
	@RequestMapping(value = "/checkUserExit")
	public @ResponseBody Object checkUserExit(final String roleId, final ModelMap model) {
		model.clear();
		StringBuffer msg = new StringBuffer("");
		String[] idArr = roleId.split(",");
		int isRoleExist = 0;//记录条数
		int isViewExist = 0;
		int isAuthorityExist = 0;
		String allowDel = "allow";
		// 查询角色中是否有关联用户
		for (String id : idArr) {
			isRoleExist = this.isRoleExist(id);
			isViewExist = this.isViewExist(id);
			isAuthorityExist = this.isAuthorityExist(id);
			//存在关联的角色，视图或权限
			if((isRoleExist+isViewExist+isAuthorityExist)!=0){
				//生成删除提示
				msg.append(this.mergeErrorMsg(isRoleExist, 
						isViewExist, isAuthorityExist,id)).append("</br>");
				//不可删除
				allowDel = "unAllow";
			}
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("allowDel",allowDel);
		return model;
	}
	
	private String mergeErrorMsg(int isRoleExist, int isViewExist, 
			int isAuthorityExist,String roleId){
		String roleName = sysUserService.selectRoleById(roleId).getRoleName();
		StringBuffer sfb = new StringBuffer("角色[").append(roleName)
							.append("]").append("-");
		if(isRoleExist!=0)
			sfb.append("用户*").append(isRoleExist).append(",");
		if(isViewExist!=0)
			sfb.append("视图*").append(isViewExist).append(",");
		if(isAuthorityExist!=0)
			sfb.append("系统权限*").append(isAuthorityExist).append(",");
		return sfb.substring(0, sfb.length()-1);
	}
	
	private int isRoleExist(String roleId){
		return sysUserService.selectUsersByRoleId(roleId);
	}
	
	private int isViewExist(String roleId){
		return viewSetService.isViewExistByRoleId(roleId);
	}
	
	private int isAuthorityExist(String roleId){
		return roleAuthorityService.isAuthorityExistByRoleId(roleId);
	}
	
	/**
	 * 检查一个角色下是否包含了该账号
	 * @param cond
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkRoleUser")
	public @ResponseBody Object checkOldPassword(final RoleSearchCondition cond, final ModelMap model) {
		model.clear();
		int result;
		try {
			UserRole user = sysUserService.selectUserRoleListByCondition(cond.getRoleId(), cond.getUserAccount());
			if(user != null){
				result = 1;
			}else{
				result = 0;
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}

	// 用户展示列表
	@RequestMapping(value = "/selectViews")
	public ModelAndView selectViews(final ViewSearchCondition cond,String roleId,final ModelMap model) {
		ModelAndView mav = new ModelAndView("sysmgt/selectViews");
		cond.setPageCount(7);
		SelectOptions options = getSelectOptions(cond);
		List<ViewsDto> viewList = viewSetService.selectViewsData(roleId,cond.getViewName(), cond.getViewType(),
				cond.getDictName(), options);
		model.put("viewList", viewList);
		model.put("roleId", roleId);
		List<ViewTypeDto> viewTypeList = viewSetService.selectViewsType();
		model.put("viewTypeList", viewTypeList);
		pageSetting(cond, options);
		mav.addObject("viewSearchCondition", cond);
		return mav;
	}

	// 已选择视图展示列表
	@RequestMapping(value = "/selectViewsByViewsId")
	public @ResponseBody Object selectViews(String viewIds, final ModelMap model) {
		String[] viewIdArry = viewIds.split(",");
		List<String> viewIdList = Arrays.asList(viewIdArry);
		List<ViewsDto> viewList = viewSetService.selectViewListByIdList(viewIdList);
		model.addAttribute("viewList", viewList);
		return model;
	}

	@RequestMapping(value = "/saveRoleView")
	public @ResponseBody Object saveRoleViewInfo(final RoleSearchCondition cond, String viewIds, final ModelMap model) {
		model.clear();
		int result = 0;
//		String wrongMsg = null;
		String roleName;
		String viewName;
		List<String> viewIdList = viewSetService.selectViewsByRoleId(cond.getRoleId());
		
//		for(int i=0;i<viewIds.split(",").length;i++){
//			String viewId = viewIds.split(",")[i];
//			DictView addView = viewSetService.selectViewByViewId(viewId);
//			for(int j=0;j<viewIdList.size();j++){
//				DictView oldView = viewSetService.selectViewByViewId(viewIdList.get(j));
//				if(StringUtils.equals(addView.getDictId(), oldView.getDictId())&&StringUtils.equals(addView.getViewType(), oldView.getViewType())){
//					result = 0;
//					wrongMsg = "一个角色下不能重复添加视图类型和表结构都相同的视图";
//					model.addAttribute("status", result > 0 ? 1 : 0);
//					model.addAttribute("wrongMsg", wrongMsg);
//					return model;
//				}
//			}
//		}
		try {
			if (cond.getRoleId() != null && !"".equals(cond.getRoleId())) {
				// 将新的角色视图关系插入
				roleName = sysUserService.selectRoleById(cond.getRoleId()).getRoleName();
				if (viewIds != null && !"".equals(viewIds)) {
					String[] viewIdArry = viewIds.split(",");
					for (String viewId : viewIdArry) {
						RoleView rolesViews = new RoleView();
						rolesViews.setRoleViewId(StringUtil.getUUID());
						rolesViews.setRoleId(cond.getRoleId());
						rolesViews.setViewId(viewId);
						rolesViews.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
						rolesViews.setCreateTime(new Date());
						rolesViews.setDeleteFlg(0);
						viewName = viewSetService.selectViewByViewId(viewId).getViewName();
						result = viewSetService.addRolesViews(rolesViews);
						logUtils.insertLog("00104", "00104007", "新增角色[{}]关联术语[{}]", 
								roleName,viewName);
					}
				}

			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
//		model.addAttribute("wrongMsg", wrongMsg);
		return model;
	}
	
	@RequestMapping(value = "/deleteRoleView")
	public @ResponseBody Object deleteRoleView(final RoleSearchCondition cond, String viewIds, final ModelMap model) {
		model.clear();
		int result = 0;
		String roleName;
		String viewName;
		if (cond.getRoleId() != null && !"".equals(cond.getRoleId())) {
			roleName = sysUserService.selectRoleById(cond.getRoleId()).getRoleName();
			if (viewIds != null && !"".equals(viewIds)) {
				String[] viewIdArry = viewIds.split(",");
				for (String viewId : viewIdArry) {
					RoleView rolesViews = new RoleView();
					rolesViews = viewSetService.selectRoleViewListByCondition(cond.getRoleId(),viewId);
					rolesViews.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
					rolesViews.setDeleteTime(new Date());
					rolesViews.setDeleteFlg(1);
					result = viewSetService.deleteRolesViews(rolesViews);
					viewName = viewSetService.selectViewByViewId(viewId).getViewName();
					logUtils.insertLog("00104", "00104008", "删除角色[{}]关联术语[{}]", 
							roleName,viewName);
				}
			}
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	
	
	
	@RequestMapping(value = "/userList")
	public ModelAndView userList(final RoleSearchCondition cond, final ModelMap model,
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
//		return new ModelAndView("role/roleUserList",model);
		return includeTemplate(model, "sysmgt/roleUserList");
	}
	
	
	@RequestMapping(value = "/saveRoleUser")
	public @ResponseBody Object saveRoleUser(final RoleSearchCondition cond, String userAccounts, final ModelMap model) {
		model.clear();
		int result = 0;
		if (!StringUtils.isEmpty(cond.getRoleId())) {
			if (!StringUtils.isEmpty(userAccounts)) {
				String[] userIdArry = userAccounts.split(",");
				String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
				String roleName = sysUserService.selectRoleById(cond.getRoleId()).getRoleName();
				for (String userAccount : userIdArry) {
					UserRole usersRoles = new UserRole();
					usersRoles.setUserRoleId(StringUtil.getUUID());
					usersRoles.setRoleId(cond.getRoleId());
					usersRoles.setUseAccount(userAccount);
					usersRoles.setCreateBy(loginName);
					usersRoles.setCreateTime(new Date());
					usersRoles.setDeleteFlg(0);
					usersRoles.setLastUpdateBy(loginName);
					usersRoles.setLastUpdateTime(new Date());
					
					userAccount = sysUserService.selectSysUserById(userAccount).getUserAccount();
					result = sysUserService.addUsersRoles(usersRoles);
					logUtils.insertLog("00104", "00104004", "新增角色[{}]关联账户[{}]",
							roleName,userAccount);
				}
			}
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	
	@RequestMapping(value = "/deleteRoleUser")
	public @ResponseBody Object deleteRoleUser(final RoleSearchCondition cond, String userIds, final ModelMap model) {
		model.clear();
		int result = 0;
		String roleName = sysUserService.selectRoleById(cond.getRoleId()).getRoleName();
		String userAccount;
		if (cond.getRoleId() != null && !"".equals(cond.getRoleId())) {
			if (userIds != null && !"".equals(userIds)) {
				String[] userIdArry = userIds.split(",");
				for (String userId : userIdArry) {
					UserRole usersRoles = new UserRole();
					usersRoles = sysUserService.selectUserRoleListByCondition(cond.getRoleId() , userId);
					usersRoles.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
					usersRoles.setDeleteTime(new Date());
					usersRoles.setDeleteFlg(1);
					result = sysUserService.deleteUsersRoles(usersRoles);
					userAccount= usersRoles.getUseAccount();
					logUtils.insertLog("00104", "00104005", "删除角色[{}]关联账户[{}]",
							roleName,userAccount);
				}
			}
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
}
