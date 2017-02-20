package com.founder.fmdm.dao.subscribe;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.SubsSys;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface SystemListDao {
	
	/**
	 * 检索系统一览
	 * @param sysId
	 * @param sysName
	 * @param options
	 * @return
	 */
	@Select
	public List<SubsSys> selecSystemData(String sysId, String sysName,
			SelectOptions options);
}
