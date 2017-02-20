package com.founder.web.sysmgt;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.fmdm.entity.DictField;
import com.founder.fmdm.entity.DictView;
import com.founder.fmdm.entity.ViewField;
import com.founder.fmdm.service.sysmgt.SysUserService;
import com.founder.fmdm.service.sysmgt.ViewSetService;
import com.founder.fmdm.service.term.dto.DictFieldDto;
import com.founder.fmdm.service.term.dto.ViewTypeDto;
import com.founder.fmdm.service.term.dto.ViewsDto;
import com.founder.fmdm.service.term.dto.ViewsFieldsDto;
import com.founder.util.StringUtil;
import com.founder.web.term.AbstractController;



@Controller
@RequestMapping("/view")
public class ViewSetController extends AbstractController {
	
	
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	ViewSetService viewSetService;
	
	@Autowired
	LogUtils logUtils;


	
	@RequestMapping(value = "/viewlist", method = RequestMethod.GET)
	public ModelAndView initListPage(final ViewSearchCondition cond,
			final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.put("viewSearchCondition", cond);
		model.addAttribute("page_title", "视图管理");
		List<ViewsDto> viewList =  viewSetService.selectViewsData(null,cond.getViewName(),cond.getViewType(),cond.getDictName(),options);
		model.put("viewList",viewList);
		List<ViewTypeDto> viewTypeList =  viewSetService.selectViewsType();
		model.put("viewTypeList",viewTypeList);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/viewlist");
	}
	
	@RequestMapping(value = "/viewlist", method = RequestMethod.POST)
	public ModelAndView viewSearchList(final ViewSearchCondition cond,
			final ModelMap model) throws Exception {
		model.clear();
		SelectOptions options = getSelectOptions(cond);
		model.put("viewSearchCondition", cond);
		model.addAttribute("page_title", "视图管理");
		List<ViewsDto> viewList =  viewSetService.selectViewsData(null,cond.getViewName(),cond.getViewType(),cond.getDictName(),options);
		model.put("viewList",viewList);
		List<ViewTypeDto> viewTypeList =  viewSetService.selectViewsType();
		model.put("viewTypeList",viewTypeList);
		pageSetting(cond, options);
		return includeTemplate(model, "sysmgt/viewlist");
	}
	
	@RequestMapping(value = "/viewshow", method = RequestMethod.POST)
	public ModelAndView showViews(final ViewSearchCondition cond) {
		ModelAndView mav = new ModelAndView("sysmgt/viewshow");
		if(cond.getOperation().equals("edit") && cond.getViewId()!=null && !cond.getViewId().isEmpty()){
			DictView views =  viewSetService.selectViewByViewId(cond.getViewId());
			cond.setViewType(views.getViewType());
			cond.setDictId(views.getDictId());
			cond.setViewName(views.getViewName());
			cond.setAddFlg(views.getAddFlg()+"");
			cond.setDeleteFlg(views.getDeleteFlg()+"");
			cond.setReleaseFlg(views.getReleaseFlg()+"");
			cond.setApproveFlg(views.getApproveFlg()+"");
		}
		List<Map<String, Object>> dictMainList =  viewSetService.selectDictMainList();
		mav.addObject("dictMainList",dictMainList);
		List<ViewTypeDto> viewTypeList =  viewSetService.selectViewsType();
		mav.addObject("viewTypeList",viewTypeList);
		List<ViewsFieldsDto> fieldsList = null;
		if(cond.getViewId() != null && !cond.getViewId().isEmpty()){
			fieldsList =  viewSetService.selectFieldsByViewIdList(cond.getViewId());
		}
		mav.addObject("viewSearchCondition", cond);
		mav.addObject("fieldsList",fieldsList);
		return mav;
	}
	
	
	@RequestMapping(value = "/viewshow", method = RequestMethod.GET)
	public ModelAndView showViewsGET(final ViewSearchCondition cond) {
		ModelAndView mav = new ModelAndView("sysmgt/viewshow");
		//编辑的时候viewId不为空
		if(cond.getViewId()!=null && !cond.getViewId().isEmpty()){
			DictView views =  viewSetService.selectViewByViewId(cond.getViewId());
			cond.setViewType(views.getViewType());
			cond.setDictId(views.getDictId());
			cond.setViewName(views.getViewName());
			cond.setAddFlg(views.getAddFlg()+"");
			cond.setDeleteFlg(views.getDeleteFlg()+"");
			cond.setReleaseFlg(views.getReleaseFlg()+"");
			cond.setApproveFlg(views.getApproveFlg()+"");
		}
		List<Map<String, Object>> dictMainList =  viewSetService.selectDictMainList();
		mav.addObject("dictMainList",dictMainList);
		List<ViewTypeDto> viewTypeList =  viewSetService.selectViewsType();
		mav.addObject("viewTypeList",viewTypeList);
		List<ViewsFieldsDto> fieldsList = null;
		if(cond.getViewId() != null && !cond.getViewId().isEmpty()){
			fieldsList =  viewSetService.selectFieldsByViewIdList(cond.getViewId());
		}
		mav.addObject("viewSearchCondition", cond);
		mav.addObject("fieldsList",fieldsList);
		return mav;
	}
	
	@RequestMapping(value = "/fieldTable", method = RequestMethod.POST)
	public ModelAndView fieldTableShow(String dictId, String viewId, String viewType) {
		ModelAndView mav = new ModelAndView("sysmgt/fieldTable");
		List<ViewsFieldsDto> fieldsList = null;
		if(dictId != null && !"".equals(dictId)){
			fieldsList =  viewSetService.selectFieldsByDictIdList(dictId, viewId);
		}
		mav.addObject("fieldsList",fieldsList);
		mav.addObject("viewType",viewType);
		return mav;
	}
	
	@RequestMapping(value = "/saveView", method = RequestMethod.POST)
	public @ResponseBody Object saveViews(final ViewSearchCondition cond,String fieldTableArr, final ModelMap model) {
		model.clear();
		String scanType = "2";//查看
		String searchType = "0";//检索
		String editType = "1";//编辑
		List<FieldOperationBean> fieldOperationBeanList = null;
		if(!StringUtils.isEmpty(fieldTableArr)){
			fieldOperationBeanList = JsonUtil.getListFromJsonArrStr(fieldTableArr, FieldOperationBean.class);
		}
		int result = 0;
		try {
			if(fieldOperationBeanList != null && !fieldOperationBeanList.isEmpty()){
				DictView views = new DictView();
				if(cond.getViewId() != null && !cond.getViewId().isEmpty() && cond.getOperation().equals("edit")){
					views =  viewSetService.selectViewByViewId(cond.getViewId());
					views.setDictId(cond.getDictId());
					views.setViewName(cond.getViewName());
					views.setViewType(cond.getViewType());
					views.setAddFlg(checkFlgValue(cond.getAddFlg()));
					views.setDeleteFlg(checkFlgValue(cond.getDeleteFlg()));
					views.setApproveFlg(checkFlgValue(cond.getApproveFlg()));
					views.setReleaseFlg(checkFlgValue(cond.getReleaseFlg()));
					views.setViewId(cond.getViewId());
					views.setLastUpdateTime(new Date());
					views.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
					views.setDeleteFlag(0);
					result = viewSetService.updateView(views);
					String editDescribe = "";
					if(null != cond.getViewName() && !views.getViewName().equals(cond.getViewName())){
						editDescribe = editDescribe + "原视图名称为["+cond.getViewName()+"],新视图名称为["+cond.getViewName()+"]     ";
					}
					if(null != cond.getViewType() && !views.getViewType().equals(cond.getViewType())){
						editDescribe = editDescribe + "原视图类别为["+cond.getViewType()+"],新视图类别为["+cond.getViewType()+"]     ";
					}
					
					if(!"".equals(editDescribe)){
						editDescribe = "(" + editDescribe + ")";
					}
					logUtils.insertLog("00608", "00608002", "修改视图[{}]" + editDescribe, cond.getViewName());
				}else{
					views.setDictId(cond.getDictId());
					views.setViewName(cond.getViewName());
					views.setViewType(cond.getViewType());
					views.setAddFlg(checkFlgValue(cond.getAddFlg()));
					views.setDeleteFlg(checkFlgValue(cond.getDeleteFlg()));
					views.setApproveFlg(checkFlgValue(cond.getApproveFlg()));
					views.setReleaseFlg(checkFlgValue(cond.getReleaseFlg()));
					views.setViewId(StringUtil.getUUID());
					views.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
					views.setCreateTime(new Date());
					views.setDeleteFlag(0);
					views.setLastUpdateTime(new Date());
					views.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
					result = viewSetService.addViews(views);	
					logUtils.insertLog("00608", "00608001", "新增视图[{}]", cond.getViewName());
				}
				
/*				Boolean isDel = false;
				for(FieldOperationBean fob : fieldOperationBeanList){
					if(fob.getViewFieldId() == null || "".equals(fob.getViewFieldId())){
						isDel = true;
						break;
					}
				}
				if(isDel){
					result = viewSetService.deleteViewsFieldsByViewId(cond.getViewId());
				}*/
				for(FieldOperationBean fob : fieldOperationBeanList){
					ViewField vf = new ViewField();
					if(!StringUtils.isEmpty(fob.getViewFieldId())){
						vf = viewSetService.selectFieldsByViewFieldId(fob.getViewFieldId());
					}
//					ViewsFields vf = viewSetService.selectFieldsByViewIdList(fob.getViewFieldId()).get(0);
					vf.setFieldId(fob.getFieldId());
					vf.setViewId(views.getViewId());
					vf.setEditFlg(checkFlgValue(fob.getEditFlg()));
					vf.setItemFlg(checkFlgValue(fob.getItemFlg()));
					vf.setItemOrder(checkFlgValue(fob.getItemOrder()));
					vf.setFieldOrder(checkFlgValue(fob.getFieldOrder()));
					vf.setOrderFlg(checkFlgValue(fob.getOrderFlg()));
					vf.setRetrievalFlg(checkFlgValue(fob.getRetrievalFlg()));
					vf.setDeleteFlg(0);

					if(cond.getViewId() != null && !cond.getViewId().isEmpty() && cond.getOperation().equals("edit")){
						vf.setLastUpdateTime(new Date());
						vf.setViewFieldId(fob.getViewFieldId());
/*						if(fob.getViewFieldId() == null || "".equals(fob.getViewFieldId())){
							result = viewSetService.updateViewsFields(vf);
						}*/
						vf.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
						result = viewSetService.updateViewsFields(vf);
					}else{
						vf.setViewFieldId(StringUtil.getUUID());
						vf.setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
						vf.setCreateTime(new Date());
						result = viewSetService.addViewsFields(vf);					
					}
					
					DictField dfd =viewSetService.selectFieldByFieldId(vf.getFieldId());
					if(searchType.equals(cond.getViewType())){
						dfd.setDispOrder(vf.getFieldOrder().toString());
					}else{
						dfd.setDispOrder(vf.getItemOrder().toString());
					}
					if(vf.getItemFlg().equals(Integer.parseInt("1"))){
						dfd.setIsShow("Y");
					}else{
						dfd.setIsShow("N");
					}
					if(vf.getRetrievalFlg().equals(Integer.parseInt("1"))){
						dfd.setIsFilter("Y");
					}else{
						dfd.setIsFilter("N");
					}
					if(vf.getEditFlg().equals(Integer.parseInt("1"))){
						dfd.setFieldIsEdit("Y");
					}else{
						dfd.setFieldIsEdit("N");
					}
					int a = viewSetService.updateDictField(dfd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		model.addAttribute("result", result > 0 ? 1 : 0);
		return model;
	}	
	
	@RequestMapping(value = "/deleteViews")
	public @ResponseBody Object deleteViews(final ViewSearchCondition cond,
			final ModelMap model) {
		model.clear();
		int result;
		StringBuffer deleteMsg= new StringBuffer();
		boolean isExistRole = false;
		try {
			String ids = cond.getViewId();
			String[] idArr = ids.split(",");
			for(String viewId : idArr){
				isExistRole = this.isExistRoleByViewId(viewId);
				DictView views = viewSetService.selectViewsByViewId(viewId);
				if(!isExistRole){
					views.setDeleteFlg(1);
					views.setDeleteFlag(1);
					views.setDeleteTime(new Date());
					views.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
					result=viewSetService.updateView(views);
					logUtils.insertLog("00608", "00608003", "删除视图[{}]", views.getViewName());
					//根据视图Id检索视图字段表信息,删除视图字段关系
					List<ViewField> viewsFieldsList = viewSetService.selectViewsFieldsByViewIdList(viewId);
					for(ViewField viewsFields:viewsFieldsList){
						viewsFields.setDeleteFlg(1);
						viewsFields.setDeleteBy(SecurityContextHolder.getContext().getAuthentication().getName());
						viewsFields.setDeleteTime(new Date());
						result=viewSetService.updateViewsFields(viewsFields);
					}
				}else{
					deleteMsg.append("视图[").append(views.getViewName()).append("]还与角色：")
							.append(viewSetService.selectRolesStringByViewId(viewId)).append("关联</br>");
				}
				
			}
			if(!isExistRole){
				result = 1;
			}else{
				result = 0;
			}
		} catch (Exception e) {
			result = 0;
		}
		model.addAttribute("status", result > 0 ? 1 : 0);
		model.addAttribute("deleteMsg", deleteMsg.toString());
		return model;
	}
	
	public int checkFlgValue(String flg){
		if(null == flg || flg.trim().length()==0){
			return 0;
		}
		return Integer.valueOf(flg);
	}
	
	
	private boolean isExistRoleByViewId(String viewId){
		int count = viewSetService.isRoleExistByViewId(viewId);
		if(count>0)
			return true;
		return false;
	}
}


