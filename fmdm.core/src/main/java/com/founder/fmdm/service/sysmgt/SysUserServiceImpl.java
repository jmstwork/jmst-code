package com.founder.fmdm.service.sysmgt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.dao.sysmgt.TseUsersDao;
import com.founder.fmdm.entity.RmtResourceManage;
import com.founder.fmdm.entity.RmtUserAccessAuth;
import com.founder.fmdm.entity.Role;
import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.entity.TseUsers;
import com.founder.fmdm.entity.TsysAccountInfo;
import com.founder.fmdm.entity.UserRole;
import com.founder.fmdm.service.sysmgt.dto.MenuDto;
import com.founder.fmdm.service.sysmgt.dto.RoleViewsDto;
import com.founder.fmdm.service.term.dto.TseUsersDto;
@Service
public class SysUserServiceImpl implements SysUserService{

	@Autowired
	TseUsersDao sysUsersDao;
	
	@Override
	@Transactional
	public int addUser(TseUsers entity){
		return sysUsersDao.insert(entity);
	}
	
	@Override
	@Transactional
	public int updateUser(TseUsers entity){
		return sysUsersDao.update(entity);
	}
	
	@Override
	public TseUsers selectSysUserById(String userAccount){
		return sysUsersDao.selectUserById(userAccount);
	}
	
	@Override
	public TseUsers selectSysUserByUserName(String userName){
		return sysUsersDao.selectUserByUserName(userName);
	}
	
	@Override
	public List<TseUsersDto> selecTseUsersData(String roleId,String userAccount,String userName,Integer enableFlg,SelectOptions options){
		
		return sysUsersDao.selectUsersData(roleId,userAccount, userName,enableFlg,options);
	}
	
	@Override
	public List<TsysAccountInfo> selectAccountData(String userAccount,String userName,SelectOptions options){
		return sysUsersDao.selectAccountData(userAccount, userName, options);
	}

	@Override
	/***
	 * 访问资源管理表
	 */
	public List<RmtResourceManage> selectResrource() {
		List<RmtResourceManage> resLst = sysUsersDao.selectMenu();
			return resLst;
	}
	
	/***
	 *  查询用户权限
	 */
	@Override
	public List<RmtUserAccessAuth> selectUserAuthorities(String userId){
		List<RmtUserAccessAuth> userAuthList = sysUsersDao.selectUserAuthorities(userId);
		return userAuthList;
	}

	@Override
	@Transactional 
	public int updateUserAuth(String resourceIds,String userId,String authName){
		int resultSet=1;
		List<RmtUserAccessAuth>  userAuthList= this.selectUserAuthorities(userId);
		for(int i=0;i<userAuthList.size();i++){
			RmtUserAccessAuth userAuth= userAuthList.get(i);
			sysUsersDao.deleteUserAuth(userAuth); 
		}
		String[] resourceIdArry = resourceIds.split("@",-1);
		for(int i=0;i<resourceIdArry.length;i++){
			RmtUserAccessAuth rmtAuth=new RmtUserAccessAuth();
			rmtAuth.setCreateBy(authName);
			rmtAuth.setResourceId(resourceIdArry[i]);
			rmtAuth.setUserId(userId);
			rmtAuth.setCreateTime(new Date());
			sysUsersDao.insertRmtAuth(rmtAuth);
			
		}
		return resultSet;
	}
	
	@Override
	public int updateUserStatus(String userIds, int status) {
		try {
			String[] userId = userIds.split("@");
			for (int i = 0; i < userId.length; i++) {
				TseUsers tseUsers = sysUsersDao.selectUserById(userId[i]);
				tseUsers.setUserId(userId[i]);
				tseUsers.setEnableFlag(status);
				sysUsersDao.update(tseUsers);
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	@Override
	public List<Role> selectRoleList(String roleName,String userAccount,SelectOptions options) {
		return sysUsersDao.selectRoleList(roleName,userAccount,options);
	}

	@Override
	public List<Role> selectRoleListById(String userId,String roleName,SelectOptions options) {
		return sysUsersDao.selectRoleListById(userId,roleName,options);
	}

	@Override
	public List<Role> selectRoleListByIdList(List<String> roleIdList) {
		return sysUsersDao.selectRoleListByIdList(roleIdList);
	}

	@Override
	@Transactional
	public int deleteUsersRoles(UserRole usersRoles) {
		return sysUsersDao.updateUsersRoles(usersRoles);
	}

	@Override
	@Transactional
	public int addUsersRoles(UserRole usersRoles) {
		return sysUsersDao.addUsersRoles(usersRoles);
	}

	@Override
	public List<UserRole> selectUsersRolesListById(String userId) {
		return sysUsersDao.selectUserRoleListById(userId);
	}
	
	@Override
	 public List<TseUsersDto> selectUserListByRoleId (String roleId,String userAccount,String userName,SelectOptions options){
		return sysUsersDao.selectUserListByRoleId(roleId,userAccount,userName,options);
	}
	
	@Override
	public TseUsersDto selectTseUsersDtoByUserAccount(String userAccount) {
		return sysUsersDao.selectTseUsersDtoByUserAccount(userAccount);
	}
	    
	/**
     * @param roleId
     * @return the TseUsers Roles
     */
	@Override
	public Role selectRoleById(String roleId){
		return sysUsersDao.selectRoleById(roleId);
	}
	
	@Override
	public List<TseUsersDto> selectUserListByIdList(List<String> userIdList) {
		return sysUsersDao.selectUserListByIdList(userIdList);
	}
	
	/**
     * @param role
     * @return affected rows
     */
	@Override
	@Transactional
	public int updateRole(Role role){
		return sysUsersDao.updateRole(role);
	}
    
    @Override
	public Role selectRoleByRoleName(String roleName) {
		return sysUsersDao.selectRoleByRoleName(roleName);
	}
    
    @Override
	public int selectUsersByRoleId(String roleId) {
		return sysUsersDao.selectUsersByRoleId(roleId);
	}
    
	@Override
	public List<UserRole> selectUsersRolesListByRoleId(String roleId){
		return sysUsersDao.selectUserRoleListByRoleId( roleId);
	}

	@Override
	@Transactional
	public int addRole(Role entity) {
		return sysUsersDao.insertRole(entity);
	}

	@Override
	public UserRole selectUserRoleListByCondition(String roleId, String userId) {
		return sysUsersDao.selectUserRoleListByCondition(roleId,userId);
	}

	@Override
	public int updateUserRole(UserRole role) {
		return sysUsersDao.updateUsersRoles(role);
	}

	@Override 
	public List<TseUsersDto> selectUserList(String userName, String userAccount, SelectOptions options){
		return sysUsersDao.selectUserList(userName, userAccount, options);
	}
	@Override
	public List<RoleViewsDto> selectRoleListByUserAccount(String userAccount) {
		List<RoleViewsDto> roleViewsDto = new ArrayList<RoleViewsDto>();
		List<Role> roles = sysUsersDao.selectRoleListByUserAccount(userAccount);
		for(Role r : roles){
			RoleViewsDto dto = new RoleViewsDto();
			dto.setRoleId(r.getRoleId());
			dto.setRoleName(r.getRoleName());
			dto.setMemo(r.getMemo());
			dto.setViews(sysUsersDao.selectviewsByRoleId(r.getRoleId()));
			roleViewsDto.add(dto);
		}
		
		return roleViewsDto;
	}

	@Override
	public List<MenuDto> selectMenuListByUser(String userId, String userAccount) {
		Integer type = new Integer(1);
		List<MenuDto> menuList = new ArrayList<MenuDto>();
		List<SysMenuButton> sysMenuButton = sysUsersDao.selectMenuListByUser(userId, userAccount);
		Iterator<SysMenuButton> iter = sysMenuButton.iterator();  
		//先循环查询出一级菜单
		while(iter.hasNext()){  
			SysMenuButton smb = iter.next();  
			if(type.equals(smb.getType())){
				MenuDto menu = new MenuDto();
				menu.setId(smb.getSmbId());
				menu.setName(smb.getSmbName());
				menu.setPid(smb.getParentNode());
				menu.setLevel(smb.getType());
				menu.setUrl(smb.getSmbLink());
				menuList.add(menu);
				iter.remove();
			}  
		}
		
		//查询二级菜单
		for(MenuDto dto : menuList){
			iter = sysMenuButton.iterator();  
			while(iter.hasNext()){  
				SysMenuButton smb = iter.next();  
				if(smb.getParentNode().equals(dto.getId())){
					MenuDto menu = new MenuDto();
					menu.setId(smb.getSmbId());
					menu.setName(smb.getSmbName());
					menu.setPid(smb.getParentNode());
					menu.setLevel(smb.getType());
					menu.setUrl(smb.getSmbLink());
					dto.getChild().add(menu);
					iter.remove();
				}  
			}
		}
		return menuList;
	}

	@Override
	public TseUsers selectUserByUserName(String userName) {
		return sysUsersDao.selectUserByUserName(userName);
	}
	
	public void deleteUsersByRoleId(String roleId){
		sysUsersDao.deleteUsersByRoleId(roleId);
	}
}
