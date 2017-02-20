package com.founder.fmdm.dao.sysmgt;

import java.util.List;
import java.util.Map;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictView;
import com.founder.fmdm.entity.RoleView;
import com.founder.fmdm.entity.ViewField;
import com.founder.fmdm.service.term.dto.ViewTypeDto;
import com.founder.fmdm.service.term.dto.ViewsDto;
import com.founder.fmdm.service.term.dto.ViewsFieldsDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface ViewsDao 
{
	@Select
	List<ViewsDto> selectAllViewList();
	
	@Select
	List<ViewsDto> selectViewsData(String roleId,String viewName, String viewType,
			String dictName, SelectOptions options);
	
	@Select
	List<Map<String, Object>> selectDictMainList();
	
	@Select
	List<ViewsFieldsDto> selectFieldsByViewIdList(String viewId);
	
	@Select
	DictView selectViewByViewId(String viewId);
	
	@Select
	List<DictView> selectViewByDictIdandViewType(String dictId,String viewType);
	
	@Select
	DictView selectViewsByViewId(String viewId);	
    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(DictView entity);

	@Update
    int update(DictView entity);
    
	@Select
	List<ViewsDto> selectViewListByRoleId(String roleId,String viewName,String viewType,String dictName,SelectOptions options);
    
	@Select
	List<ViewsDto> selectViewListByIdList(List<String> viewIdList);
	
	@Select
	List<ViewTypeDto> selectViewsType();

	@Select
	List<RoleView> selectRoleViewListByRoleId(String roleId);
	
	@Update
	int updateRolesViews(RoleView rolesViews);
	
	@Select
	RoleView selectRoleViewListByCondition(String roleId,String viewId);
	
	@Select
	ViewField selectFieldsByViewFieldId(String viewFieldId);
	
	@Select
	ViewField selectFieldByViewIdAndFieldId(String viewId, String fieldId);

	@Select
	List<ViewsDto> selectListByRoleId(String roleId);
	
	@Select
	int isViewExistByRoleId(String roleId);
	
	@Select
	int isRoleExistByViewId(String viewId);
	
	@Select
	String selectRolesStringByViewId(String viewId);
	
	
	/**
	 * 物理删除角色和视图的关联关系
	 * @param roleId
	 * @return
	 */
	@Update(sqlFile=true)
	int deleteViewsByRoleId(String roleId);
	
	/**
	 * 删除视图的某个字段
	 * @param viewFieldId
	 * @return
	 */
	@Update(sqlFile=true)
	int deleteViewFieldByViewFieldId(String viewFieldId);
	
	@Select
	DictField selectFieldByFieldId(String fieldId);
	
	@Select
	List<String> selectViewsByRoleId(String roleId);

}
