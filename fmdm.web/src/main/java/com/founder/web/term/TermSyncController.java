package com.founder.web.term;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.log.LogUtils;
import com.founder.core.service.dto.DefaultUserDetails;
import com.founder.fmdm.entity.DatasourceSet;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.TermSync;
import com.founder.fmdm.service.term.TermSyncService;
import com.founder.fmdm.service.term.dto.DatasourceDto;
import com.founder.fmdm.service.term.dto.SyncTermDto;

@Controller
@RequestMapping("/term")
public class TermSyncController extends AbstractController {

	@Autowired
	TermSyncService syncTermService;
	
	@Autowired
	LogUtils logUtils;

	/**
	 * 数据源列表查询
	 * 
	 * @param datasourceDto
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/datasourcelist")
	public ModelAndView dataSourceList(final TermSearchCondition cond,
			final ModelMap model) throws Exception {

		model.addAttribute("page_title", "数据源列表");
		SelectOptions options = getSelectOptions(cond);
		// 获取提供系统
		List<Map<String, Object>> sysList = syncTermService.getSysList();
		// 获取数据库类型
		List<SystemCode> dataTypeList = syncTermService.getDataTypeList();
		// 获取数据源列表信息
		List<DatasourceDto> datasourceList = syncTermService
				.selectDatasourceTable(cond.getSystemId(), null, options);

		DatasourceSet datasourceSet = new DatasourceSet();
		model.addAttribute("datasourceList", datasourceList);
		model.addAttribute("sysList", sysList);
		model.addAttribute("dataTypeList", dataTypeList);
		model.addAttribute("datasourceSet", datasourceSet);
		pageSetting(cond, options);
		return includeTemplate(model, "term/datasourcelist");
	}

	/**
	 * 数据源连接测试
	 * 
	 * @param model
	 * @param datasourceDto
	 * @return
	 */
	@RequestMapping(value = "/datasourceTest", method = RequestMethod.POST)
	public @ResponseBody
	Object dataSourceTest(final ModelMap model, DatasourceDto datasourceDto) {
		int status = syncTermService.testDataSource(datasourceDto);
		model.clear();
		model.addAttribute("status", status);
		return model;
	}

	/**
	 * 数据源添加和更新
	 * 
	 * @param model
	 * @param datasourceDto
	 * @return
	 */
	@RequestMapping(value = "/datasourceAdd", method = RequestMethod.POST)
	public @ResponseBody
	Object dataSourceAdd(final ModelMap model, DatasourceSet datasourceSet,
			String updateFlg) {
		DefaultUserDetails user = null;
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof DefaultUserDetails) {
			user = (DefaultUserDetails) auth.getPrincipal();
		}
		int status = 1;
		if (updateFlg != null && ("update").equals(updateFlg)) {//更新
			DatasourceSet dataOrign = syncTermService.selectDatasourceSetById(datasourceSet.getDatasourceId());
			datasourceSet.setCreateBy(dataOrign.getCreateBy());
			datasourceSet.setCreateTime(dataOrign.getCreateTime());
			datasourceSet.setLastUpdateBy(user.getUsername());
			datasourceSet.setDeleteFlg(0);
			status = syncTermService.updateDataSource(datasourceSet);		
		} else {//新增
			datasourceSet.setCreateBy(user.getUsername());
			status = syncTermService.addDataSource(datasourceSet);
		}
		model.clear();
		model.addAttribute("status", status);
		return model;
	}
	
	/**
	 * 数据源删除
	 */
	@RequestMapping(value = "/datasourceDelete", method = RequestMethod.POST)
	public @ResponseBody
	Object dataSourceDelete(final ModelMap model, String datasourceId,
			int updateCount) {
		DefaultUserDetails user = null;
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof DefaultUserDetails) {
			user = (DefaultUserDetails) auth.getPrincipal();
		}
		// 校验数据源在术语同步表中是否引用
		int count = syncTermService.checkDataFromSync(datasourceId);
		if (count > 0) {
			model.addAttribute("count", count);
			return model;
		}
		DatasourceSet datasourceSet=syncTermService.selectDatasourceSetById(datasourceId);
		datasourceSet.setDatasourceId(datasourceId);
		datasourceSet.setDeleteFlg(1);
		datasourceSet.setDeleteBy(user.getUsername());
		datasourceSet.setDeleteTime(new Date());
		int status = syncTermService.deleteDataSource(datasourceSet);
		model.addAttribute("status", status);
		model.addAttribute("count", count);
		return model;
	}

	/**
	 * 跳转到数据源更新画面
	 */
	@RequestMapping(value = "/datasourceUpdate", method = RequestMethod.POST)
	public @ResponseBody
	Object dataSourceUpdate(final ModelMap model, String datasourceId) {
		DatasourceSet datasourceSet = syncTermService.selectDatasourceSetById(datasourceId);
		model.addAttribute("datasourceSet", datasourceSet);
		return model;
	}

	/**
	 * 数据源名称重复校验
	 */
	@RequestMapping(value = "/checkDataName", method = RequestMethod.POST)
	public @ResponseBody
	Object checkDataName(final ModelMap model, String datasourceName,String datasourceId) {
		int nameCount = syncTermService.checkDataName(datasourceName,datasourceId);
		model.addAttribute("nameCount", nameCount);
		return model;
	}

	/**
	 * 术语同步列表 ，检索
	 * @throws Exception 
	 */
	@RequestMapping(value = "/synclist")
	public ModelAndView synclist(TermSearchCondition cond, final ModelMap model) throws Exception {
		model.addAttribute("page_title", "术语同步列表");
		SelectOptions options = getSelectOptions(cond);
		List<SyncTermDto> syncTermList = syncTermService.selectSyncTermTable(
				cond.getDictId(), cond.getDictName(), options);
		pageSetting(cond, options);
		model.addAttribute("syncTermList", syncTermList);
		return includeTemplate(model, "term/synclist");
	}

	/**
	 * 术语服务启用同步校验SQL
	 */
	@RequestMapping(value = "/testDictSql", method = RequestMethod.POST)
	public @ResponseBody
	Object testDictSql(final ModelMap model, String syncIds, int status) {
		String testResult = syncTermService.testDictSql(syncIds, status);
		model.addAttribute("testResult", testResult);
		return model;
	}

	/**
	 * 术语服务启用停用同步
	 */
	@RequestMapping(value = "/updateDictStatus", method = RequestMethod.POST)
	public @ResponseBody
	Object updateDictStatus(final ModelMap model, String syncIds, int status) {
		int backStatus = syncTermService.updateDictStatus(syncIds, status);
		
		model.addAttribute("backStatus", backStatus);
		return model;
	}

	/**
	 * 术语服务新增和编辑画面
	 * @throws Exception 
	 */
	@RequestMapping(value = "/syncedit")
	public ModelAndView synceditList(final ModelMap model, String editSyncId) throws Exception {
		model.addAttribute("page_title", "术语同步设置");
		// 获取数据源
		List<DatasourceDto> datasourceList = syncTermService.selectDatasourceList(null, null);
		// 获取字典信息：1.新增画面，字典信息去掉不在同步术语表中的字典 2.修改画面为全部字典信息
		List<Map<String, Object>> dictList = new ArrayList<Map<String, Object>>();
		dictList = syncTermService.getDictList();
		// 获取同步术语信息
		SyncTermDto syncTermDto = new SyncTermDto();
		String updateFlg = null;
		if (editSyncId != null && !("").equals(editSyncId)) {
			// 2.修改画面为全部字典信息
			dictList = syncTermService.getAllDictList();
			List<SyncTermDto> syncTermList = syncTermService.selectSyncTermList(editSyncId, null);
			syncTermDto = syncTermList.get(0);
			updateFlg = "update";
		}
		model.addAttribute("updateFlg", updateFlg);
		model.addAttribute("syncTermDto", syncTermDto);
		model.addAttribute("datasourceList", datasourceList);
		model.addAttribute("dictList", dictList);
		return includeTemplate(model, "term/syncedit");
	}

	/**
	 * 术语服务添加与修改保存
	 */
	@RequestMapping(value = "/synceditSave")
	public @ResponseBody
	Object synceditSave(TermSync syncTerm, String updateFlg,
			final ModelMap model) {
		DefaultUserDetails user = null;
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof DefaultUserDetails) {
			user = (DefaultUserDetails) auth.getPrincipal();
		}
		int status = 1;
		if (updateFlg != null && ("update").equals(updateFlg)) {
			TermSync termOrign = syncTermService.selectSyncTermById(syncTerm.getSyncId());
			syncTerm.setSyncStatus(termOrign.getSyncStatus());
			syncTerm.setCreateTime(termOrign.getCreateTime());
			syncTerm.setCreateBy(termOrign.getCreateBy());
			syncTerm.setCreateBy(user.getUsername());
			syncTerm.setDeleteFlg(0);
			status = syncTermService.updateSyncTerm(syncTerm);
		} else {
			syncTerm.setCreateBy(user.getUsername());
			status = syncTermService.insertSyncTerm(syncTerm);
		}

		model.clear();
		model.addAttribute("status", status);
		return model;
	}

	/**
	 * 校验sql是否可执行得到字段结果集
	 */
	@RequestMapping(value = "/testSql")
	public @ResponseBody
	Object testSql(String fromSql, String toSql, String datasourceId,
			final ModelMap model) {
		List<DatasourceDto> datasourceList = syncTermService
				.selectDatasourceList(null, datasourceId);
		String fromSqlStr = syncTermService.returnFromSql(fromSql,
				datasourceList.get(0));
		String toSqlStr = syncTermService.returnToSql(toSql);
		model.clear();
		model.addAttribute("fromSqlStr", fromSqlStr);
		model.addAttribute("toSqlStr", toSqlStr);
		return model;
	}

	/**
	 * 术语服务删除
	 */
	@RequestMapping(value = "/deleteSyncTerm")
	public @ResponseBody
	Object syncTermDelete(String syncId,  final ModelMap model) {
		DefaultUserDetails user = null;
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof DefaultUserDetails) {
			user = (DefaultUserDetails) auth.getPrincipal();
		}
		TermSync termSync = syncTermService.selectSyncTermById(syncId);
		termSync.setDeleteFlg(1);
		termSync.setDeleteBy(user.getUsername());
		termSync.setDeleteTime(new Date());
		int status = syncTermService.deleteTermSync(termSync);
		model.clear();
		model.addAttribute("status", status);
		return model;
	}
	/**
	 * 术语失败原因
	 */
	@RequestMapping(value = "/faultReason")
	public @ResponseBody Object faultReason(String syncId,final ModelMap model) {
		TermSync termSync = syncTermService.selectSyncTermById(syncId);
		model.clear();
		model.addAttribute("logTxt", termSync.getLogTxt());
		return model;
	}
}
