package com.founder.fmdm.dao.iamlog;

import java.util.Date;
import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.IamLog;
import com.founder.fmdm.service.iamlog.dto.LogDetailDto;
import com.founder.fmdm.service.iamlog.dto.LogManagerDetailDto;


@Dao
@AnnotateWith(annotations = { @Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface IamLogDao {

	@Insert
	public int saveIamLog(IamLog iamLog);

	// iam_Log表信息查询sql接口
	@Select
	public List<LogDetailDto> selectLogManager(String operorId, Date operDt1, Date operDt2, String operModu,
			String operObj, SelectOptions options);

	// iam_Resr表信息查询sql接口
	@Select
	List<LogManagerDetailDto> selectModu();

	// iam_Opt表信息查询sql接口
	@Select
	List<LogManagerDetailDto> selectObj(String resrCode);
}
