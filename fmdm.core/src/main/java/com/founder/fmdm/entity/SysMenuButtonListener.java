package com.founder.fmdm.entity;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.founder.core.service.dto.DefaultUserDetails;
import com.founder.fmdm.Constants;
import com.founder.msg.utils.DateUtils;


public class SysMenuButtonListener implements EntityListener<SysMenuButton> {
	
	DefaultUserDetails userDetails;
	
	@Override
	public void preInsert(SysMenuButton entity, PreInsertContext context) {
		userDetails = (DefaultUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		 entity.setCreateBy(userDetails.getUsername());
		 entity.setCreateTime(DateUtils.getSystemTime());
		 entity.setLastUpdateBy(userDetails.getUsername());
		 entity.setLastUpdateTime(DateUtils.getSystemTime());
		 entity.setUpdateCount(0);
		 entity.setDeleteFlg(0);		
	}

	@Override
	public void preUpdate(SysMenuButton entity, PreUpdateContext context) {
		userDetails = (DefaultUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof DefaultUserDetails) {
			userDetails = (DefaultUserDetails) auth.getPrincipal();
		}
		if(entity.getDeleteFlg() == null
				|| Constants.DELETE_FLAG_DEFAULT.equals(entity.getDeleteFlg())){
			 entity.setLastUpdateBy(userDetails.getUsername());
			 entity.setLastUpdateTime(DateUtils.getSystemTime());
		}else if(Constants.DELETE_FLAG_NONDEFAULT.equals(entity.getDeleteFlg())){
			 entity.setDeleteBy(userDetails.getUsername());
			 entity.setDeleteTime(DateUtils.getSystemTime());
		}		
	}

	@Override
	public void preDelete(SysMenuButton entity, PreDeleteContext context) {
	}

	@Override
	public void postInsert(SysMenuButton entity, PostInsertContext context) {
	}

	@Override
	public void postUpdate(SysMenuButton entity, PostUpdateContext context) {
	}

	@Override
	public void postDelete(SysMenuButton entity, PostDeleteContext context) {
	}

}