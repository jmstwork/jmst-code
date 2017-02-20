package com.founder.core.service.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.founder.fmdm.service.sysmgt.dto.MenuDto;
import com.founder.fmdm.service.sysmgt.dto.RoleViewsDto;

public class DefaultUserDetails implements UserDetails{

	
	private static final long serialVersionUID = 5946152436253765975L;
	
	private String username;
	private String realName;
	private String password;
	private Integer superUser; 
	private boolean enabled;   
	private List<GrantedAuthority> authorities; 
	private List<RoleViewsDto> roles;
	private List<MenuDto> menus;
	
	public DefaultUserDetails() {
	}

	public DefaultUserDetails(String username, String realName,
			String password,boolean enabled,List<GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.realName = realName;
		this.password = password;
		this.enabled = enabled;
		this.authorities = authorities;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {   
		return username;   
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {   
		return password;   
	}
	
	public Integer getSuperUser() {
		return superUser;
	}

	public void setSuperUser(Integer superUser) {
		this.superUser = superUser;
	}
	
	public boolean isEnabled(){   
		return enabled;   
	}

	public void setEnabled(boolean enabled){   
		this.enabled = enabled; 
	}
	
	public boolean isAccountNonExpired() {   
		return enabled;   
	}   

	public boolean isAccountNonLocked() {   
		return enabled;   
	}   
 
	public boolean isCredentialsNonExpired() {   
		return enabled;   
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	} 
	
	

	public List<RoleViewsDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleViewsDto> roles) {
		this.roles = roles;
	}

	public List<MenuDto> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}

	@Override
    public boolean equals(Object rhs) {
        if (rhs instanceof DefaultUserDetails) {
            return username.equals(((DefaultUserDetails) rhs).getUsername());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}