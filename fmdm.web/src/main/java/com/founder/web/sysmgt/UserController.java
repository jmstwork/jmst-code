package com.founder.web.sysmgt;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.AppSettings;
import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.RmtResourceManage;
import com.founder.fmdm.entity.RmtUserAccessAuth;
import com.founder.fmdm.entity.Role;
import com.founder.fmdm.entity.TseUsers;
import com.founder.fmdm.entity.TsysAccountInfo;
import com.founder.fmdm.entity.UserRole;
import com.founder.fmdm.service.subscribe.dto.TreeDto;
import com.founder.fmdm.service.sysmgt.SysUserService;
import com.founder.fmdm.service.term.dto.TseUsersDto;
import com.founder.util.StringUtil;
import com.founder.web.common.ResetPwUtil;
import com.founder.web.term.AbstractController;
import com.founder.web.term.TermSearchCondition;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	SysUserService sysUserService;

	@Autowired
	LogUtils logUtils;
	
	private String password = AppSettings.getConfig("INIT_PASSWORD");
	
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public ModelAndView initListPage(final UserSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.put("sysUserSearchCondition", cond);
		model.addAttribute("page_title", "账户管理");
		List<TseUsersDto> userList = sysUserService.selecTseUsersData(null,cond.getUserAccount(), cond.getUserName(),
				cond.getEnableFlg(), options);
		model.put("userList", userList);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/userList");
	}

	@RequestMapping(value = "/userList", method = RequestMethod.POST)
	public ModelAndView showUsers(final UserSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		model.put("sysUserSearchCondition", cond);
		model.addAttribute("page_title", "账户管理");
		SelectOptions options = getSelectOptions(cond);
		List<TseUsersDto> userList = sysUserService.selecTseUsersData(null,cond.getUserAccount(), cond.getUserName(),
				cond.getEnableFlg(),options);
		model.put("userList", userList);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/userList");
	}

	@RequestMapping(value = "/userEdit")
	public ModelAndView initEditPage(final UserSearchCondition cond, final ModelMap model) throws Exception {
		model.clear();
		model.put("sysUserSearchCondition", cond);
		model.addAttribute("page_title", "账户管理");

		TseUsers user = new TseUsers();
		String userAccount = cond.getUserAccount();
		if (!StringUtils.isEmpty(userAccount)) {
			user = sysUserService.selectSysUserById(userAccount);
			model.addAttribute("user", user);
			model.addAttribute("operateFlg", "edit");
			/*List<Roles> roleList = sysUserService.selectRoleListById(userId);
			model.addAttribute("roleList", roleList);*/
		} else {
			model.addAttribute("user", user);
			model.addAttribute("operateFlg", "add");
		}

		return includeTemplate(model, "sysmgt/userEdit");
	}

	@RequestMapping(value = "/checkUserExist")
	public @ResponseBody Object checkUserExist(final UserSearchCondition cond, final ModelMap model) {
		model.clear();
		int result;
		TseUsers user = sysUserService.selectSysUserByUserName(cond.getUserAccount());
		if (user != null)
			result = 1;
		else
			result = 0;
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}

	@RequestMapping(value = "/saveUser")
	public @ResponseBody Object saveUserInfo(final UserSearchCondition cond, final ModelMap model, String roleIds) {
		model.clear();
		int result = 0;
		try {

			TseUsers user = new TseUsers();
			if ("edit".equalsIgnoreCase(cond.getOperateFlg())) {
				user = sysUserService.selectSysUserById(cond.getUserAccount());
				//user.setUserPasswd(cond.getUserPasswd());
				user.setUserMail(cond.getUserMail());
				user.setUserMobile(cond.getUserMobile());
				user.setSuperUser(cond.getSuperUser());
				user.setMemo(cond.getMemo());
				user.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				user.setLastUpdateTime(new Date());
				result = sysUserService.updateUser(user);
				logUtils.insertLog("00101", "00101001", "修改账户[{}]",user.getUserName());
			} else if("add".equalsIgnoreCase(cond.getOperateFlg())) {
				user = new TseUsers();
				user.setUserId(StringUtils.isEmpty(cond.getUserId())?cond.getUserAccount():cond.getUserId());
				user.setUserAccount(cond.getUserAccount());
				user.setUserName(cond.getUserName());
				user.setUserPasswd(ResetPwUtil.md5(cond.getUserPasswd()));
				user.setUserMail(cond.getUserMail());
				user.setUserMobile(cond.getUserMobile());
				user.setEnableFlag(0);
				user.setDeleteFlg(0);
				user.setSuperUser(cond.getSuperUser());
				user.setMemo(cond.getMemo());
				user.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				user.setCreateTime(new Date());
				user.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				user.setLastUpdateTime(new Date());
				user.setDeleteFlg(0);
				result = sysUserService.addUser(user);
				logUtils.insertLog("00101", "00101000", "新建账户[{}]", cond.getUserName());
			}
		} catch (Exception e) {
			result = 0;
			model.addAttribute("msg", "出现异常，请联系管理员");
			logger.debug(e.toString());
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}

	@RequestMapping(value = "/pwdRset")
	public @ResponseBody Object pwdRset(final String userId,final ModelMap model) {
		model.clear();
		int result = 0;
		if(!StringUtil.isEmpty(userId)){
			TseUsers user = sysUserService.selectSysUserById(userId);
			user.setUserPasswd(ResetPwUtil.md5(password));
			result = sysUserService.updateUser(user);
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	@RequestMapping(value = "/saveUserRole")
	public @ResponseBody Object saveUserRole(final UserSearchCondition cond, final ModelMap model, String roleIds) {
		model.clear();
		int result = 0;
		try {
			// 在将新的账户角色关系插入
			if (!StringUtils.isEmpty(roleIds)) {
				String[] roleIdArry = roleIds.split(",");
				for (String roleId : roleIdArry) {
					UserRole usersRoles = new UserRole();
					usersRoles.setUserRoleId(StringUtil.getUUID());
					usersRoles.setRoleId(roleId);
					usersRoles.setUseAccount(cond.getUserAccount());
					usersRoles.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
					usersRoles.setCreateTime(new Date());
					usersRoles.setDeleteFlg(0);
					result = sysUserService.addUsersRoles(usersRoles);
				}
			}
		} catch (Exception e) {
			result = 0;
			model.addAttribute("msg", "出现异常，请联系管理员");
			logger.debug(e.toString());
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	@RequestMapping(value = "/deleteUserRole")
	public @ResponseBody Object deleteUserRole(final UserSearchCondition cond, final ModelMap model, String roleIds) {
		model.clear();
		int result = 0;
		try {
			// 在将新的账户角色关系插入
			if (!StringUtils.isEmpty(roleIds)) {
				String[] roleIdArry = roleIds.split(",");
				for (String roleId : roleIdArry) {
					UserRole role= sysUserService.selectUserRoleListByCondition(roleId, cond.getUserAccount());
					role.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
					role.setDeleteTime(new Date());
					role.setDeleteFlg(1);
					result = sysUserService.updateUserRole(role);
				}
			}
		} catch (Exception e) {
			result = 0;
			model.addAttribute("msg", "出现异常，请联系管理员");
			logger.debug(e.toString());
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	@RequestMapping(value = "/checkUserAccount")
	public @ResponseBody Object checkUserAccount(final UserSearchCondition cond, final ModelMap model) {
		model.clear();
		int result = 0;
		try {
			TseUsers user = sysUserService.selectSysUserByUserName(cond.getUserAccount());
			if(!StringUtils.isEmpty(user)){
				result = 1;
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}
	
	
	@RequestMapping(value = "/operateUsers")
	public @ResponseBody Object operateUsers(final UserSearchCondition cond, final ModelMap model) {
		model.clear();
		int result;
		try {
			String ids = cond.getUserId();
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				TseUsers user = sysUserService.selectSysUserById(id);
				user.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				user.setLastUpdateTime(new Date());
				if ("start".equalsIgnoreCase(cond.getOperateFlg())) {
					user.setEnableFlag(1);
					user.setEnabled(1);
					logUtils.insertLog("00101", "00101002", "启用账户[{}]", user.getUserName());
				} else if ("stop".equalsIgnoreCase(cond.getOperateFlg())) {
					user.setEnabled(0);
					user.setEnableFlag(0);
					logUtils.insertLog("00101", "00101003", "停用账户[{}]", user.getUserName());
				}
				sysUserService.updateUser(user);
			}
			result = 1;
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}

	@RequestMapping(value = "/selectUsers")
	public ModelAndView showSelectUsers(final UserSearchCondition cond) {
		ModelAndView mav = new ModelAndView("sysmgt/selectUsers");
		SelectOptions options = getSelectOptions(cond);
		List<TsysAccountInfo> userList = sysUserService.selectAccountData(cond.getUserAccount(), cond.getUserName(),
				options);
		mav.addObject("userList", userList);
		pageSetting(cond, options);
		mav.addObject("sysUserSearchCondition", cond);
		return mav;
	}
	
	/**
	 * 修改密码界面
	 * @param cond
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/editPassword")
	public ModelAndView editPassword(final TermSearchCondition cond,
			final ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("term/editPassword");
		return mav;
	}
	
	/**
	 * 检查旧密码是否正确
	 * @param cond
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkOldPassword")
	public @ResponseBody Object checkOldPassword(final UserSearchCondition cond, final ModelMap model) {
		model.clear();
		int result;
		try {
			TseUsers user = sysUserService.selectSysUserByUserName(cond.getUserAccount());
			if(user.getUserPasswd().equalsIgnoreCase(ResetPwUtil.md5(cond.getUserPasswd()))){
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
	

	@RequestMapping(value = "/savePasswd")
	public @ResponseBody Object savePasswd(final UserSearchCondition cond, final ModelMap model) {
		model.clear();
		int result;
		try {
			TseUsers user = sysUserService.selectSysUserByUserName(cond.getUserAccount());
			user.setUserPasswd(ResetPwUtil.md5(cond.getUserPasswd()));
			user.setLastUpdateBy(cond.getUserAccount());
			user.setLastUpdateTime(new Date());
			result = sysUserService.updateUser(user);
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		return model;
	}

	@RequestMapping(value = "/rmtAuthSet")
	public ModelAndView rmtAuthSet(final UserSearchCondition cond, final ModelMap model)
			throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("sysmgt/rmtAuthSet");
		mav.addObject("userAccount", cond.getUserAccount());
		String userName = java.net.URLDecoder.decode(cond.getUserName(), "utf-8");
		mav.addObject("userName", userName);
		Map<String, List<RmtResourceManage>> resMap = new LinkedHashMap<String, List<RmtResourceManage>>();

		// 临时存放系统名称
		Map<String, Object> newResMap = new LinkedHashMap<String, Object>();
		List<RmtResourceManage> resList = sysUserService.selectResrource();
		List<RmtUserAccessAuth> userAuthList = sysUserService.selectUserAuthorities(cond.getUserAccount());
		// 检查此账户的权限
		for (int i = 0; i < userAuthList.size(); i++) {
			for (int j = 0; j < resList.size(); j++) {
				if (userAuthList.get(i).getResourceId().equals(resList.get(j).getResourceId())
						&& (cond.getUserAccount()).equals(userAuthList.get(i).getUserId())) {
					// 有权限，将删除标记设为1
					resList.get(j).setDeleteFlg(1);
				}
			}
		}
		for (RmtResourceManage rmtRes : resList) {
			newResMap.put(rmtRes.getSystemName(), rmtRes);
		}
		Iterator<String> it = newResMap.keySet().iterator();
		while (it.hasNext()) {
			List<RmtResourceManage> resourceList = new ArrayList<RmtResourceManage>();
			String key = it.next();
			for (RmtResourceManage rmtRes : resList) {
				if (key.equals(rmtRes.getSystemName())) {
					resourceList.add(rmtRes);
				}
			}
			resMap.put(key, resourceList);
		}
		// 循环放入父项目是否为全选
		Map<String, List<RmtResourceManage>> resNewMap = new LinkedHashMap<String, List<RmtResourceManage>>();
		// 父选项框是否为全选判断
		Set<Map.Entry<String, List<RmtResourceManage>>> set = resMap.entrySet();
		for (Iterator<Map.Entry<String, List<RmtResourceManage>>> itNew = set.iterator(); itNew.hasNext();) {
			// 子项目是否为全选标记
			boolean checkFlg = true;
			Map.Entry<String, List<RmtResourceManage>> ResEntry = (Map.Entry<String, List<RmtResourceManage>>) itNew
					.next();
			String keyNew = ResEntry.getKey();
			List<RmtResourceManage> rmtResList = ResEntry.getValue();
			for (RmtResourceManage rmtRes : rmtResList) {
				if (rmtRes.getDeleteFlg() != 1) {
					checkFlg = false;
				}
			}
			if (checkFlg) {
				keyNew += "flg";
			}
			resNewMap.put(keyNew, rmtResList);
		}
		mav.addObject("resMap", resNewMap);
		return mav;
	}
	
	//补发权限树 test
	@RequestMapping(value="/showRmtTree")
	@ResponseBody
	public Object showRmtTree(final UserSearchCondition cond, final ModelMap model)
			throws UnsupportedEncodingException{
		ModelAndView mav = new ModelAndView("sysmgt/rmtAuthSet");
		mav.addObject("userAccount", cond.getUserAccount());
		String userName = java.net.URLDecoder.decode(cond.getUserName(), "utf-8");
		mav.addObject("userName", userName);
		Map<String, List<RmtResourceManage>> resMap = new LinkedHashMap<String, List<RmtResourceManage>>();

		// 临时存放系统名称
		Map<String, Object> newResMap = new LinkedHashMap<String, Object>();
		List<RmtResourceManage> resList = sysUserService.selectResrource();
		List<RmtUserAccessAuth> userAuthList = sysUserService.selectUserAuthorities(cond.getUserAccount());
		// 检查此账户的权限
		for (int i = 0; i < userAuthList.size(); i++) {
			for (int j = 0; j < resList.size(); j++) {
				if (userAuthList.get(i).getResourceId().equals(resList.get(j).getResourceId())
						&& (cond.getUserAccount()).equals(userAuthList.get(i).getUserId())) {
					// 有权限，将删除标记设为1
					resList.get(j).setDeleteFlg(1);
				}
			}
		}
//		for (RmtResourceManage rmtRes : resList) {
//			newResMap.put(rmtRes.getSystemName(), rmtRes);
//		}
		List<TreeDto> rmtTree = new ArrayList<TreeDto>();
		Map<String, TreeDto> nodes = new HashMap<String, TreeDto>();//一级菜单,id为key
		TreeDto rootNode = new TreeDto();
		rootNode.setpId("-1");
		rootNode.setName("补发权限设置");
		rootNode.setId("0");
		rmtTree.add(rootNode);
		for(RmtResourceManage rmt: resList){//节点
			TreeDto tree = new TreeDto();
			tree.setName(rmt.getResourceName());
			tree.setId(rmt.getResourceId());
			tree.setpId(rmt.getSystemId());
			rmtTree.add(tree);
			if(!nodes.containsKey(rmt.getSystemId())){
				TreeDto node = new TreeDto();
				//还有东西
			}
		}
		
		
		
		return mav;
	}
	
	@RequestMapping(value = "/saveUserAuth")
	public @ResponseBody Object saveUserAuth(final UserSearchCondition cond, String resourceIds, final ModelMap model)
			throws Exception {
		model.clear();
		int resultSet = sysUserService.updateUserAuth(resourceIds, cond.getUserAccount(),
				SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("resultSet", resultSet);
		return model;
	}

	/**
	 * 账户启用停用同步
	 */
	@RequestMapping(value = "/updateUserStatus", method = RequestMethod.POST)
	public @ResponseBody Object updateUserStatus(final ModelMap model, String userIds, int status) {
		int backStatus = sysUserService.updateUserStatus(userIds, status);
		model.addAttribute("backStatus", backStatus);
		return model;
	}

	// 角色展示列表
	@RequestMapping(value = "/selectRole")
	public ModelAndView selectRole(final UserSearchCondition cond, final ModelMap model) {
		ModelAndView mav = new ModelAndView("sysmgt/selectRoles");
		cond.setPageCount(7);
		SelectOptions options = getSelectOptions(cond);
		List<Role> roleList = sysUserService.selectRoleList(cond.getRoleName(),cond.getUserAccount(),options);
		mav.addObject("roleList", roleList);
		mav.addObject("userAccount", cond.getUserAccount());
		pageSetting(cond, options);
		mav.addObject("sysUserSearchCondition", cond);
		return mav;
	}

	/*// 已选择角色展示列表
	@RequestMapping(value = "/selectRoles")
	public @ResponseBody Object selectUnits(String roleIds, final ModelMap model) {
		String[] roleIdArry = roleIds.split(",");
		List roleIdList = Arrays.asList(roleIdArry);
		List<Roles> roleList = sysUserService.selectRoleListByIdList(roleIdList);
		model.addAttribute("roleList", roleList);
		return model;
	}*/

	// 角色权限设定初始化
	@RequestMapping(value = "/selectUsersRolesList")
	public ModelAndView selectUsersRolesList(final UserSearchCondition cond, final ModelMap model/*,WebRequest request*/) throws Exception {
		model.clear();
		model.addAttribute("page_title", "账户管理");
		SelectOptions options = getSelectOptions(cond);
		TseUsers user = sysUserService.selectSysUserById(cond.getUserAccount());
		model.addAttribute("user", user);
		List<Role> roleList = sysUserService.selectRoleListById(cond.getUserAccount(),cond.getRoleName(),options);
		model.addAttribute("roleList", roleList);
		pageSetting(cond, options);
		model.addAttribute("sysUserSearchCondition", cond);
		return includeTemplate(model, "sysmgt/usersRolesList");
	}

}
