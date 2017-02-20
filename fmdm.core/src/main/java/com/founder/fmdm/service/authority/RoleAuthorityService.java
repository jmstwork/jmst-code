package com.founder.fmdm.service.authority;

import java.util.List;

import com.founder.fmdm.entity.RoleAuthority;
import com.founder.fmdm.service.subscribe.dto.TreeDto;

public interface RoleAuthorityService {
	/**
	 * 
	 * @param 权限按钮的id字符数组
	 */
	public void saveRoleAuthority(List<RoleAuthority> authority, String roleId);
	
	/**
	 * 
	 * @param roleId
	 * @return 权限按钮的id字符数组
	 */
	public List<RoleAuthority> getRoleAuthority(String roleId);
	
	/**
	 * 获取全部权限
	 * @return
	 */
	public List<TreeDto> selectAllAuthority();
	
	/**
	 * 查看角色是否在角色权限表中有权限
	 * @param
	 * @return 
	 */
	public int isAuthorityExistByRoleId(String roleId);
	
	/**
	 * 物理删除角色和权限关联的关系
	 * @param roleId
	 */
	public void deleteAuthorityByRoleId(String roleId);
}
