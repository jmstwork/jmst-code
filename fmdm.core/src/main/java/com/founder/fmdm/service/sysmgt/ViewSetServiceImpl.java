package com.founder.fmdm.service.sysmgt;

import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fmdm.dao.sysmgt.ViewsDao;
import com.founder.fmdm.dao.sysmgt.ViewsFieldsDao;
import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictView;
import com.founder.fmdm.entity.RoleView;
import com.founder.fmdm.entity.ViewField;
import com.founder.fmdm.service.term.dto.ViewTypeDto;
import com.founder.fmdm.service.term.dto.ViewsDto;
import com.founder.fmdm.service.term.dto.ViewsFieldsDto;

@Service
public class ViewSetServiceImpl implements ViewSetService {

	@Autowired
	ViewsDao viewsDao;
	@Autowired
	ViewsFieldsDao viewsFieldsDao;
	
	
	@Override
	public List<ViewsDto> selectAllViewList() {
		return viewsDao.selectAllViewList();
	}
	@Override
	public List<ViewsDto> selectViewsData(String roleId,String viewName, String viewType,
			String dictName, SelectOptions options) {
		return viewsDao.selectViewsData(roleId,viewName, viewType, dictName, options);
	}
    @Override
    public List<Map<String, Object>> selectDictMainList(){
    	return viewsDao.selectDictMainList();
    }
	/**
	 * 根据视图id查询视图
	 * @param viewId
	 * @return 视图
	 */
	@Override
	public DictView selectViewByViewId(String viewId){
		return viewsDao.selectViewByViewId(viewId);
	}
	/**
	 * 根据术语id和视图类型查询视图
	 * @param dictId
	 * @return 视图
	 */
	@Override
	public List<DictView> selectViewByDictIdandViewType(String dictId,String viewType){
		return viewsDao.selectViewByDictIdandViewType(dictId,viewType);
	}
	/**
	 * 根据视图id查询视图列
	 * @param viewId
	 * @return 视图
	 */
	@Override
	public DictView selectViewsByViewId(String viewId){
		return viewsDao.selectViewsByViewId(viewId);
	}
	@Override
	public List<ViewsFieldsDto> selectFieldsByViewIdList(String viewId) {
		return viewsDao.selectFieldsByViewIdList(viewId);
	}
	
	@Override
	public List<ViewField> selectViewsFieldsByViewIdList(String viewId){
		return viewsFieldsDao.selectViewsFieldsByViewIdList(viewId);
	}
	
	@Override
	public List<ViewsFieldsDto> selectFieldsByDictIdList(String dictId, String viewId){
		return viewsFieldsDao.selectFieldsByDictIdList(dictId,viewId);
	}
	@Override
	@Transactional
	public int addViews(DictView views){
		return viewsDao.insert(views);
	}
	@Override
	@Transactional
	public int addViewsFields(ViewField viewsFields){
		return viewsFieldsDao.insert(viewsFields);		
	}
	@Override
	@Transactional
	public int updateView(DictView views){
		return viewsDao.update(views);
	}	
	/**
	 * 更新视图字段表
	 * @return 
	 */
	public int updateViewsFields(ViewField viewsFields){
		return viewsFieldsDao.update(viewsFields);
	}
	
	/**
	 * 更新dict_field表
	 * @return 
	 */
	public int updateDictField(DictField dictFields){
		return viewsFieldsDao.update(dictFields);
	}
	
	
	@Override
	public List<ViewsDto> selectViewListByRoleId(String roleId,String viewName,String viewType,String dictName,SelectOptions options) {
		return viewsDao.selectViewListByRoleId(roleId,viewName,viewType,dictName,options);
	}
	@Override
	public List<ViewsDto> selectViewListByIdList(List<String> viewIdList) {
		return viewsDao.selectViewListByIdList(viewIdList);
	}
	@Override
	public List<RoleView> selectRolesViewsListByRoleId(
			String roleId) {
		return viewsDao.selectRoleViewListByRoleId(roleId);
	}
	@Override
	@Transactional
	public int deleteRolesViews(RoleView rolesViews) {
		return viewsDao.updateRolesViews(rolesViews);
	}
	
	@Override
	@Transactional
	public int addRolesViews(RoleView rolesViews) {
		return viewsFieldsDao.insertRolesViews(rolesViews);
	}
		
	/**
	 * 根据视图ID删除视图字段表
	 * @return
	 */
/*	public int deleteViewsFieldsByViewId(String viewId){
		return viewsFieldsDao.delete(viewId);
	}*/
	
	public List<ViewTypeDto> selectViewsType(){
		return viewsDao.selectViewsType();
	}
	
	public RoleView selectRoleViewListByCondition(String roleId, String viewId) {
		return viewsDao.selectRoleViewListByCondition(roleId,viewId);
	}
	@Override
	public ViewField selectFieldsByViewFieldId(String viewFieldId) {
		return viewsDao.selectFieldsByViewFieldId(viewFieldId);
	}
	@Override
	public ViewField selectFieldByViewIdAndFieldId(String viewId,String fieldId) {
		return viewsDao.selectFieldByViewIdAndFieldId(viewId,fieldId);
	}
	@Override
	public List<ViewsDto> selectViewListByRoleId(String roleId) {
		return viewsDao.selectListByRoleId(roleId);
	}
	
	/**
	 * 查询role是否有所属的视图
	 * @param roleId
	 * @return 
	 */
	@Override
	public int isViewExistByRoleId(String roleId){
		return viewsDao.isViewExistByRoleId(roleId);
	}
	
	/**
	 * 查询view是否有关联的角色
	 * @param ViewId
	 * @return
	 */
	@Override
	public int isRoleExistByViewId(String viewId){
		return viewsDao.isRoleExistByViewId(viewId);
	}
	
	@Override
	public String selectRolesStringByViewId(String viewId){
		return viewsDao.selectRolesStringByViewId(viewId);
	}

	@Override
	public void deleteViewsByRoleId(String roleId){
		viewsDao.deleteViewsByRoleId(roleId);
	}
	
	@Override
	public void deleteViewFieldByViewFieldId(String viewFieldId) {
		viewsDao.deleteViewFieldByViewFieldId(viewFieldId);
	}
	
	@Override
    public DictField selectFieldByFieldId(String fieldId){
    	return viewsDao.selectFieldByFieldId(fieldId);
    }
	@Override
	public List<String> selectViewsByRoleId(String roleId) {
		return viewsDao.selectViewsByRoleId(roleId);
	}
	
}
