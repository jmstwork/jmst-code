package com.founder.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.founder.core.service.dto.DefaultUserDetails;
import com.founder.fmdm.Constants;
import com.founder.fmdm.entity.TseUsers;
import com.founder.fmdm.service.sysmgt.SysUserService;
import com.founder.fmdm.service.sysmgt.dto.MenuDto;
import com.founder.fmdm.service.sysmgt.dto.RoleViewsDto;

public class UserDetailsServiceImpl implements UserDetailsService
{
//	@Autowired
//	private TseUsersDao tsysUsersDao;
	
	@Autowired
	private SysUserService sysUserService;
	
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
    	TseUsers entity = sysUserService.selectUserByUserName(username);
    	if(entity != null)
    	{
    		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(Privilege.DEFAULT_PRIVILEGE);
            DefaultUserDetails user = new DefaultUserDetails(username, entity.getUserName(), entity.getUserPasswd(),true, authorities);
            user.setSuperUser(entity.getSuperUser());
            
            //添加菜单和角色视图
            List<MenuDto> menus = sysUserService.selectMenuListByUser(entity.getUserId(), entity.getUserAccount());
            List<RoleViewsDto> roles = sysUserService.selectRoleListByUserAccount(entity.getUserAccount());
            user.setMenus(menus);
            user.setRoles(roles);
            return user;
    	}
    	else if("admin".equals(username))
        {
    		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(Privilege.DEFAULT_PRIVILEGE);
            // 明文密码为123，加密后为202cb962ac59075b964b07152d234b70
            DefaultUserDetails user = new DefaultUserDetails(username, "系统管理员", Constants.ADMIN_USER_PASSWORD, true, authorities);
            user.setSuperUser(1);
            
            List<MenuDto> menus = sysUserService.selectMenuListByUser(null, null);  //查询出全部菜单
            user.setMenus(menus);
            return user;
        }
        return null;
    }

}
