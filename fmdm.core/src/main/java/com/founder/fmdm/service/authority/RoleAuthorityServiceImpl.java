package com.founder.fmdm.service.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.authority.RoleAuthorityDao;
import com.founder.fmdm.entity.RoleAuthority;
import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.service.subscribe.dto.TreeDto;

@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService{
	
	@Autowired
	private RoleAuthorityDao roleAuthorityDao;
	

	@Override
	public void saveRoleAuthority(List<RoleAuthority> authoritys, String roleId) {
		// TODO Auto-generated method stub
		//先删除
		roleAuthorityDao.deleteAllAuthorityByRoleId(roleId);
		//再添加，可以用insertBatch优化
		for(RoleAuthority authority: authoritys){
			roleAuthorityDao.setAuthority(authority);
		}
	}

	@Override
	public List<RoleAuthority> getRoleAuthority(String roleId) {
		// TODO Auto-generated method stub
		return roleAuthorityDao.getAuthority(roleId);
	}

	@Override
	public List<TreeDto> selectAllAuthority() {
		// TODO Auto-generated method stub
		List<TreeDto> tree = new ArrayList<TreeDto>();
		List<SysMenuButton> menus = roleAuthorityDao.selectAllAuthority();
		for(SysMenuButton smb:menus){
			TreeDto node = new TreeDto();
			node.setId(smb.getSmbId());
			node.setpId(smb.getParentNode());
			node.setName(smb.getSmbName());
			node.setUrl(smb.getSmbLink());
			node.setType(smb.getType());
			tree.add(node);
		}
		return tree;
	}
	
	/**
	 * 查看角色是否在角色权限表中有权限
	 * @param
	 * @return 
	 */
	public int isAuthorityExistByRoleId(String roleId){
		return roleAuthorityDao.isAuthorityExistByRoleId(roleId);
	}
	
	public void deleteAuthorityByRoleId(String roleId){
		roleAuthorityDao.deleteAllAuthorityByRoleId(roleId);
	}
}
