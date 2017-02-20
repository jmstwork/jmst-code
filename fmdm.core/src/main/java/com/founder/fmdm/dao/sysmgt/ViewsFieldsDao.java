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

import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.RoleView;
import com.founder.fmdm.entity.ViewField;
import com.founder.fmdm.service.term.dto.ViewsFieldsDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface ViewsFieldsDao 
{

	@Select
	List<ViewsFieldsDto> selectFieldsByDictIdList(String dictId,String viewId);
	
	@Select
	List<ViewField> selectViewsFieldsByViewIdList(String viewId);	
    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(ViewField viewsFields);
    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(ViewField viewsFields);
    
    @Update
    int update(DictField dictFields);

    /**
     * @param rolesViews
     * @return affected rows
     */
    @Insert
	int insertRolesViews(RoleView rolesViews);
    
/*    @Delete
    int delete(String viewId);*/
}
