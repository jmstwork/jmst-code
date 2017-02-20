package com.founder.web.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class DatabaseFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource
{

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
    {
        if ((object == null) || !this.supports(object.getClass())) 
        {
            throw new IllegalArgumentException("Object must be a FilterInvocation");
        }
//        FilterInvocation fi = (FilterInvocation) object;
//        
//        String url = fi.getRequestUrl();
        
        Collection<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
        ConfigAttribute attr = new SecureUrl(Privilege.DEFAULT_PRIVILEGE_ID);
        result.add(attr);
        
        return result;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes()
    {
        Collection<ConfigAttribute> authorize = new ArrayList<ConfigAttribute>();
        ConfigAttribute attr = new SecurityConfig(Privilege.DEFAULT_PRIVILEGE_ID);
        authorize.add(attr);
        attr = new SecurityConfig("ROLE_r001");
        authorize.add(attr);
        attr = new SecurityConfig("ROLE_r002");
        authorize.add(attr);
        attr = new SecurityConfig("ROLE_r003");
        authorize.add(attr);
        attr = new SecurityConfig("ROLE_r004");
        authorize.add(attr);
        
        return authorize;
    }

    public boolean supports(Class<?> clazz)
    {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}
