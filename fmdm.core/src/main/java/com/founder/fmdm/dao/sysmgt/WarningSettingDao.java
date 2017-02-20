package com.founder.fmdm.dao.sysmgt;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.IamSysInfo;
import com.founder.fmdm.entity.NfSetting;
import com.founder.fmdm.entity.NfSettingDetail;
import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.TDictPerson;
import com.founder.fmdm.service.sysmgt.dto.DepartmentDto;
import com.founder.fmdm.service.sysmgt.dto.WarningSettingDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface WarningSettingDao {
	@Select
	List<WarningSettingDto> selectWsList(String unitId, String unitName, String receiveId,String rulegroupId,String tel,String email,String settingId,String userType,String orderBy,SelectOptions options);

	@Select
	List<IamSysInfo> selectSysNameBySysId(String sysId);
	
	/***
     *  已启用账户列表,根据条件opt，排除已经用于警告设定的用户
     * 
     * @param userListDto
     * @param pageContext
     * @return
     */
	@Select
    public List<TDictPerson> selectUserListDictPerson(String userNo, String userName,SelectOptions options);

    /**
     * 取对象类型C002
     * @return
     */
	@Select
    public List<SystemCode> selectCodeList(String cdDiv);
    
    /**
     * 取规则组英文名
     * @param rulegroupId
     * @return
     */
	@Select
    public List<RlmgRulegroup> selectRulegroupEnName(String rulegroupId);
    
    /**
     * 根据settingId和nftype及sValue获取信息
     */
    @Select(mapKeyNaming = MapKeyNamingType.LOWER_CASE)
    public List<NfSettingDetail> selectDetailByCondition(String settingId,String nfType,String sValue);
    
    @Select
    public List<DepartmentDto> selectDeptDataFromDictDepartment(String deptCode);
    
    @Select
    public NfSetting selectNfSettingById(String settingId);
    
    @Select
    public List<NfSetting> selectNfSettingByCond(String userNo,String groupCode,String userType,String unitId);
    
    @Insert
    int insertNfSetting(NfSetting ns);
    
    @Update
    int updateNfSetting(NfSetting ns);
    
    @Update
    int updateNfSettingDetail(NfSettingDetail nsd);
    
    @Insert
    int insertNfSettingDetail(NfSettingDetail nsd);
}
