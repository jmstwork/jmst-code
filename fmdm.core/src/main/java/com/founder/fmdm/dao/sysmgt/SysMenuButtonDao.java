package com.founder.fmdm.dao.sysmgt;

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

import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.service.subscribe.dto.SysAuthorityDto;

@Dao
@AnnotateWith(annotations = {
		@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface SysMenuButtonDao {
	
	@Select
	public SysMenuButton selectMenuButtonById(String id);
	
	@Select
	public List<SysMenuButton> selectChildByPid(String pid);
	
	@Insert
	public int insert(SysMenuButton sysMenuButton);
	
	@Update
	public int update(SysMenuButton sysMenuButton);
	
	@Select
	public List<SysAuthorityDto> selectAllSysAuthority();
	
}
