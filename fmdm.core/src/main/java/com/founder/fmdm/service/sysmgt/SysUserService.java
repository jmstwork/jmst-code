package com.founder.fmdm.service.sysmgt;

import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.entity.RmtResourceManage;
import com.founder.fmdm.entity.RmtUserAccessAuth;
import com.founder.fmdm.entity.Role;
import com.founder.fmdm.entity.TseUsers;
import com.founder.fmdm.entity.TsysAccountInfo;
import com.founder.fmdm.entity.UserRole;
import com.founder.fmdm.service.sysmgt.dto.MenuDto;
import com.founder.fmdm.service.sysmgt.dto.RoleViewsDto;
import com.founder.fmdm.service.term.dto.TseUsersDto;

public interface SysUserService {
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addUser(TseUsers user);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public int updateUser(TseUsers user);

	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 */
	public TseUsers selectSysUserById(String userAccount);
	
	/**
	 * 查询用户信息
	 * @param userName
	 * @return
	 */
	public TseUsers selectSysUserByUserName(String userName);
	
	/**
	 * 查询用户列表
	 * @param userAccount
	 * @param userName
	 * @param options
	 * @return
	 */
	public List<TseUsersDto> selecTseUsersData(String roleId,String userAccount,String userName,Integer enableFlg,SelectOptions options);
	
	/**
	 * 查询账户信息列表
	 * @param userAccount
	 * @param userName
	 * @param options
	 * @return
	 */
	public List<TsysAccountInfo> selectAccountData(String userAccount,String userName,SelectOptions options);

	/***
	 *  访问资源管理表
	 */
	List<RmtResourceManage> selectResrource();
	
	/***
	 *  查询用户权限
	 */
	public List<RmtUserAccessAuth> selectUserAuthorities(String userId);
	
	public int updateUserAuth(String resourceIds,String userId,String authName);
	

	/**
	 * 更新术语状态
	 */
	@Transactional
	int updateUserStatus(String userIds, int status);
	
	/**
	 * 查询角色列表
	 */
	List<Role>  selectRoleList(String roleName,String userAccount,SelectOptions options);
	
	/**
	 * 用户所属角色查询
	 */
	List<Role> selectRoleListById(String userId,String roleName,SelectOptions options);
	
	/**
	 * 多角色选择
	 */
	List<Role> selectRoleListByIdList(List<String> roleIdList);
	/**
	 * 删除用户关系角色表
	 */
	int deleteUsersRoles(UserRole usersRoles);
	
	/**
	 * 添加用户关系角色表
	 * @return
	 */
	int addUsersRoles(UserRole usersRoles);
	
	/**
	 * 用户角色关系表查询
	 */
	List<UserRole> selectUsersRolesListById(String userId);
	
	 /**
	  * 查询所属角色用户
	  */
	public List<TseUsersDto> selectUserListByRoleId (String roleId,String userAccount,String userName,SelectOptions options);

	/**
	 * 查询添加用户信息
	 * @param userAccount
	 * @return TseUsersDto
	 */
	public TseUsersDto selectTseUsersDtoByUserAccount(String userAccount);
	
	/**
	 * 查询角色信息
	 * @param roleId
	 * @return
	 */
	public Role selectRoleById(String roleId);
	
	/**
	 * 查询选中用户信息
	 */
    public List<TseUsersDto> selectUserListByIdList(List<String> userIdList);
    
    /**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);
	
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public int updateUserRole(UserRole role);
	
	/**
	 * 查询角色信息
	 */
	public Role selectRoleByRoleName(String roleName);

	/**
	 * 查询角色中是否有关联用户
	 */
	public int selectUsersByRoleId(String roleId);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addRole(Role role);
	
	/**
	 * 用户角色关系表查询
	 */
   public List<UserRole> selectUsersRolesListByRoleId(String roleId);
   
   /**
	 * 用户角色关系表查询
	 */
  public UserRole selectUserRoleListByCondition(String roleId,String userId);
  
  /**
   * @param 用户名字（不是账户名）
   * @param 账户名
   * @return 用户列表
   */
  public List<TseUsersDto> selectUserList(String userName, String userAccount, SelectOptions options);
  
  
  
  public TseUsers selectUserByUserName(String userName);
  
  /**
   * 查询用户的拥有的所有角色
   * @param userAccount
   * @return
   */
  public List<RoleViewsDto> selectRoleListByUserAccount(String userAccount);
  
  
  /**
   * 查询用户拥有的菜单
   * @param userId
   * @param userAccount
   * @return
   */
  List<MenuDto> selectMenuListByUser(String userId, String userAccount);
  
  /**
   * 在中间表中，物理删除角色和用户的绑定关系
   * @param roleId
   */
  public void deleteUsersByRoleId(String roleId);
}
