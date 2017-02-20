package com.founder.web.security;

import org.springframework.security.core.GrantedAuthority;

public class Privilege implements GrantedAuthority
{
    private static final long serialVersionUID = -9087487516531717536L;

    public static final String DEFAULT_PRIVILEGE_ID = "ROLE_0000";

    private String privilegeId;
    
    public static Privilege DEFAULT_PRIVILEGE = new Privilege(DEFAULT_PRIVILEGE_ID);
    
    public Privilege(String privilegeId)
    {
        this.privilegeId = privilegeId;
    }
    
    public String getAuthority()
    {
        return privilegeId;
    }

}
