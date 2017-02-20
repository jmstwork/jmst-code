package com.founder.fmdm.dao.sysmgt;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.IamParm;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface SysSettingDao
{
    // 查询数据库中的parName的值
    @Select
    int selectParms(String parName);
    
    @Select
    IamParm selectParameter(String parName);
    
    @Update
    int executeParmUpdate(IamParm parm);
    
    @Insert
    int executeParmAdd(IamParm parm);

}
