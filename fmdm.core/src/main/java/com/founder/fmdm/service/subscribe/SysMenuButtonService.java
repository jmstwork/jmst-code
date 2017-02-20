package com.founder.fmdm.service.subscribe;

import java.util.List;

import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.service.subscribe.dto.SysAuthorityDto;
import com.founder.fmdm.service.subscribe.dto.TreeDto;

public interface SysMenuButtonService {
	
	public TreeDto selectNodeById(String id);

	public List<TreeDto> selectSysMenuTree();
	
	public List<TreeDto> selectChildByPid(String pid);
	
	public int handleSysMenu(String flag , SysMenuButton sysMenuButton);
	
	public List<SysAuthorityDto> selectAllSysAuthority();
	
	public SysMenuButton selectSysMenuButton(String id);
}
