package com.founder.fmdm.service.subscribe.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.fmdm.dao.sysmgt.SysMenuButtonDao;
import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.service.subscribe.SysMenuButtonService;
import com.founder.fmdm.service.subscribe.dto.SysAuthorityDto;
import com.founder.fmdm.service.subscribe.dto.TreeDto;
import com.founder.util.StringUtil;

@Service
public class SysMenuButtonServiceImpl implements SysMenuButtonService {
	
	private static final String ROOT = "0";
	
	private static final Integer MENU_TYPE1 = 1;
	private static final Integer MENU_TYPE2 = 2;

	@Autowired
	private SysMenuButtonDao sysMenuButtonDao;



	@Override
	public List<TreeDto> selectSysMenuTree() {
		List<TreeDto> tree = new ArrayList<TreeDto>();
		TreeDto root = selectNodeById(ROOT);
		root.setOpen(true);
		tree.add(root);
		tree.addAll(selectChildByPid(ROOT));
		return tree;
	}



	@Override
	public List<TreeDto> selectChildByPid(String pid) {
		List<TreeDto> tree = new ArrayList<TreeDto>();
		List<SysMenuButton> menus = sysMenuButtonDao.selectChildByPid(pid);
		
		for(SysMenuButton smb:menus){
			TreeDto node = new TreeDto();
			node.setId(smb.getSmbId());
			node.setpId(smb.getParentNode());
			node.setName(smb.getSmbName());
			node.setUrl(smb.getSmbLink());
			//
			node.setType(smb.getType());
			
			node.getOtherAttr().put("updateCount", smb.getUpdateCount());
			if(pid.equals(ROOT)){
				node.setIsParent("true");
			}
			
			//查询菜单下面对于的按钮操作
//			if(smb.getType().equals(MENU_TYPE2)){
//				node.setButtons(selectChildByPid(smb.getSmbId()));
//			}
			tree.add(node);
		}
		return tree;
	}



	@Override
	public TreeDto selectNodeById(String id) {
		SysMenuButton smb = sysMenuButtonDao.selectMenuButtonById(id);
		TreeDto node = new TreeDto();
		if(smb!=null){
			node.setId(smb.getSmbId());
			node.setpId(smb.getParentNode());
			node.setName(smb.getSmbName());
			node.setUrl(smb.getSmbLink());
			node.setType(smb.getType());
			if(smb.getType().equals(MENU_TYPE1)){                //类型为1的菜单(一级菜单)
				node.setIsParent("true");
			}
			
		}
		return node;
	}



	@Override
	public int handleSysMenu(String flag ,SysMenuButton sysMenuButton) {
		int result = 0;
		if("insert".equalsIgnoreCase(flag)){
			sysMenuButton.setSmbId(StringUtil.getUUID());
			result = sysMenuButtonDao.insert(sysMenuButton);
		}else if("update".equalsIgnoreCase(flag)||"delete".equalsIgnoreCase(flag)){
			result =  sysMenuButtonDao.update(sysMenuButton);
		}
		return result;
	}



	@Override
	public List<SysAuthorityDto> selectAllSysAuthority() {
		return sysMenuButtonDao.selectAllSysAuthority();
	}



	@Override
	public SysMenuButton selectSysMenuButton(String id) {
		// TODO Auto-generated method stub
		return sysMenuButtonDao.selectMenuButtonById(id);
	}

}
