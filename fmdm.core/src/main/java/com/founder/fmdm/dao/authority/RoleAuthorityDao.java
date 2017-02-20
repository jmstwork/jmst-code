package com.founder.fmdm.dao.authority;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.RoleAuthority;
import com.founder.fmdm.entity.SysMenuButton;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface RoleAuthorityDao {
	@Select
	List<RoleAuthority> getAuthority(String roleId);
	
	@Insert
	int setAuthority(RoleAuthority roleAurhority);
	
	/**
	 * 物理删除角色权限关联的关系
	 * @param roleId
	 * @return
	 */
	@Update(sqlFile=true)
	int deleteAllAuthorityByRoleId(String roleId);
	
	/**
	 * 用于一次性获取权限树
	 * @return List<SysMenuButton> 全部的权限
	 */
	@Select
	public List<SysMenuButton> selectAllAuthority();
	
	@Select
	public int isAuthorityExistByRoleId(String roleId);
}
