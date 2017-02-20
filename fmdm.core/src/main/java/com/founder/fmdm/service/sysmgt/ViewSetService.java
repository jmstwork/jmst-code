package com.founder.fmdm.service.sysmgt;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;

import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictView;
import com.founder.fmdm.entity.RoleView;
import com.founder.fmdm.entity.ViewField;
import com.founder.fmdm.service.term.dto.DictFieldDto;
import com.founder.fmdm.service.term.dto.ViewTypeDto;
import com.founder.fmdm.service.term.dto.ViewsDto;
import com.founder.fmdm.service.term.dto.ViewsFieldsDto;

public interface ViewSetService {
	
	/**
	 * 为超级管理员查询所有视图
	 * @return
	 */
	public List<ViewsDto> selectAllViewList();
	/**
	 * 查询视图列表
	 * 
	 * @param viewName
	 * @param viewType
	 * @param dictName
	 * @param options
	 * @return 视图列表
	 */
	public List<ViewsDto> selectViewsData(String roleId,String viewName, String viewType,
			String dictName, SelectOptions options);

	/**
	 * 查询术语列表
	 * @return 术语列表
	 */
	public List<Map<String, Object>> selectDictMainList();
	
	/**
	 * 根据视图id查询列名列表
	 * @param viewId
	 * @return 列名列表
	 */
	public List<ViewsFieldsDto> selectFieldsByViewIdList(String viewId);
	
	
	/**
	 * 根据viewFieldId查询fields信息
	 * @param viewFieldId
	 * @return
	 */
	public ViewField selectFieldsByViewFieldId(String viewFieldId);
	
	/**
	 * 根据视图ID和字段ID查询字段信息
	 * @param viewFieldId
	 * @return
	 */
	public ViewField selectFieldByViewIdAndFieldId(String viewId,String fieldId);
	
	/**
	 * 根据视图id查询视图
	 * @param viewId
	 * @return 
	 */
	public DictView selectViewByViewId(String viewId);
	
	/**
	 * 根据术语id和视图类型查询视图
	 * @param viewId
	 * @return 
	 */
	public List<DictView> selectViewByDictIdandViewType(String dictId,String viewType);
	
	/**
	 * 根据视图id查询视图列
	 * @param viewId
	 * @return 
	 */
	public DictView selectViewsByViewId(String viewId);
		
	/**
	 * 根据术语id查询列名列表
	 * @param viewId
	 * @return 列名列表
	 */
	public List<ViewsFieldsDto> selectFieldsByDictIdList(String dictId,String viewId);
	
	/**
	 * 根据术语id查询列名列表
	 * @param viewId
	 * @return 列名列表
	 */
	public List<ViewField> selectViewsFieldsByViewIdList(String viewId);
		
	/**
	 * 添加视图表
	 * @return
	 */
	int addViews(DictView views);
	
	/**
	 * 添加视图字段表
	 * @return
	 */
	int addViewsFields(ViewField viewsFields);
	
	/**
	 * 更新视图表
	 * @return
	 */
	int updateView(DictView views);
	
	/**
	 * 更新视图字段表
	 * @return
	 */
	int updateViewsFields(ViewField viewsFields);
	
	/**
	 * 更新dict_field表
	 * @return
	 */
	int updateDictField(DictField dictFields);
	
	 /**
	  * 查询所属角色视图
	  */
	public List<ViewsDto> selectViewListByRoleId(String roleId,String viewName,String viewType,String dictName,SelectOptions options);
	
	/**
	  * 查询所属角色视图
	  */
	public List<ViewsDto> selectViewListByIdList(List<String> viewIdList);

	/**
	  * 根据roleId查询所属角色视图
	  */
	public List<RoleView> selectRolesViewsListByRoleId(String roleId);
	
	/**
	  * 根据roleId,viewId查询所属角色视图
	  */
	RoleView selectRoleViewListByCondition(String roleId,String viewId);

	/**
	 * 删除用户关系角色表
	 */
	public int deleteRolesViews(RoleView rolesViews);
	/**
	 * 添加角色视图关系表
	 * @return
	 */
	public int addRolesViews(RoleView rolesViews);
	
	/**
	 * 根据视图ID删除视图字段表
	 * @return
	 */
/*	int deleteViewsFieldsByViewId(String viewId);*/
	
	/**
	 * 查询视图类别
	 * */
	public List<ViewTypeDto> selectViewsType();

	public List<ViewsDto> selectViewListByRoleId(String roleId);
	
	/**
	 * 查询role是否有所属的视图
	 * @param roleId
	 * @return 
	 */
	public int isViewExistByRoleId(String roleId);
	
	/**
	 * 查询view是否有关联的角色
	 * @param ViewId
	 * @return
	 */
	public int isRoleExistByViewId(String ViewId);
	
	/**
	 * 
	 * @param viewId
	 * @return 改视图所绑定的全部角色，以，分隔
	 */
	public String selectRolesStringByViewId(String viewId);

	DictField selectFieldByFieldId(String fieldId);

	public List<String> selectViewsByRoleId(String roleId);
	/**
	 * 物理删除角色和视图的关系
	 * @param viewId
	 */
	public void deleteViewsByRoleId(String viewId);
	
	/**
	 * 物理视图中的某个字段
	 * @param viewFieldId
	 */
	public void deleteViewFieldByViewFieldId(String viewFieldId);

}
