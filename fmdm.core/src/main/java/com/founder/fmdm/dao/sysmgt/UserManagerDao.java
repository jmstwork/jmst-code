package com.founder.fmdm.dao.sysmgt;

import java.util.List;
import java.util.Map;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.service.term.dto.UserInfoDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface UserManagerDao { 

	@Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
	 List<UserInfoDto> selectUserInfoTable(String userNo,String userName, int status, SelectOptions options); 
	
	@Select
	public List<Map<String,Object>> selectSystemReg(String searchSysID,String searchSysName,
			String searchSupName, SelectOptions options);
}
