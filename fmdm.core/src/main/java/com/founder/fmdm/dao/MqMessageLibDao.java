package com.founder.fmdm.dao;

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

import com.founder.fmdm.entity.MqMessageLib;

@Dao
@AnnotateWith(annotations = {
		@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class) })
public interface MqMessageLibDao {
	/**
	 * 插入接收消息
	 * 
	 * @param entity
	 * @return
	 */
	@Insert(excludeNull = true)
	int insertMqMessageLib(MqMessageLib entity);

	/**
	 * 更新消息处理状态
	 * 
	 * @param entity
	 * @return
	 */
	@Update(excludeNull = true)
	int updateMqMessageLib(MqMessageLib entity);
	
	/**
	 * 
	 * @param sengFlg
	 * @return
	 */
    @Select
    List<MqMessageLib> selectBySendFlg(Integer sendFlg);

}
