package com.founder.fmdm.dao.sysmgt;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.MapKeyNamingType;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fmdm.entity.RmtResourceManage;
import com.founder.fmdm.entity.RmtUserAccessAuth;
import com.founder.fmdm.entity.Role;
import com.founder.fmdm.entity.SysMenuButton;
import com.founder.fmdm.entity.TseUsers;
import com.founder.fmdm.entity.TsysAccountInfo;
import com.founder.fmdm.entity.UserRole;
import com.founder.fmdm.service.term.dto.TseUsersDto;
import com.founder.fmdm.service.term.dto.ViewsDto;

@Dao
@AnnotateWith(annotations = {@Annotation(target = AnnotationTarget.CLASS, type = Component.class),
        @Annotation(target=AnnotationTarget.CONSTRUCTOR, type= Autowired.class)})
public interface TseUsersDao 
{
    /**
     * @param userId
     * @return the TseUsers entity
     */
    @Select
    TseUsers selectById(String userId);

    
    /**
     * @param userId
     * @return the TseUsers entity
     */
	@Select
	TseUsers selectUserById(String userAccount);
	
	/**
     * @param userId
     * @return the TseUsers entity
     */
	@Select
	TseUsers selectUserByUserName(String userName);
	
	
    @Select
    List<TseUsersDto> selectUsersData(String roleId,String userAccount,String userName, Integer enableFlg,SelectOptions options);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(TseUsers entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update(excludeNull=true)
    int update(TseUsers entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(TseUsers entity);
    
    /**
     * @param userAccount
     * @param userName
     * @param options
     * @return
     */
    @Select
    List<TsysAccountInfo> selectAccountData(String userAccount,String userName,SelectOptions options);
   
    @Select
    List<RmtResourceManage> selectMenu();
    
    @Select
	List<RmtUserAccessAuth> selectUserAuthorities(String userId);
    
    @Delete
    int deleteUserAuth(RmtUserAccessAuth entity);
    
    @Insert
    int insertRmtAuth(RmtUserAccessAuth entity);
    
    @Select
    List<Role> selectRoleList(String roleName,String userAccount,SelectOptions options);
    
    @Select
    List<Role> selectRoleListById(String userId,String roleName,SelectOptions options);
    
    @Select
    List<Role> selectRoleListByIdList(List<String> roleIdList);
    
    @Update
    int updateUsersRoles(UserRole usersRoles);
    
    @Insert
    int addUsersRoles(UserRole usersRoles);
    
    @Select
    List<UserRole> selectUserRoleListById(String userId) ;
    
    @Select
    List<UserRole> selectUserRoleListByRoleId(String roleId);
    
    @Select
	Role selectRoleById(String roleId);
    
    @Select
    List<TseUsersDto> selectUserListByIdList(List<String> userIdList);
    
    @Select
    List<TseUsersDto>selectUserListByRoleId(String roleId,String userAccount,String userName,SelectOptions options);
   
    @Select
	TseUsersDto selectTseUsersDtoByUserAccount(String userAccount);
    
    @Update(excludeNull=true)
    int updateRole(Role role);
    
    @Select
	Role selectRoleByRoleName(String roleName);
    
    @Select
    int selectUsersByRoleId(String roleId);
    
    /**
     * @param entity
     * @return affected rows
     */
    @Insert
	int insertRole(Role entity);
    
    @Select
    UserRole selectUserRoleListByCondition(String roleId, String userId);
    
    /**
     * @param 账户名称
     * @param 登录账号
     * @param options
     * @return List<TesUserDto>
     */
    @Select(mapKeyNaming=MapKeyNamingType.LOWER_CASE)
    List<TseUsersDto> selectUserList(String userName,String userAccount,SelectOptions options);

    @Select
    List<Role> selectRoleListByUserAccount(String userAccount);
    
    @Select
    List<ViewsDto> selectviewsByRoleId(String roleId);
    
    @Select
    List<SysMenuButton> selectMenuListByUser(String userId, String userAccount);
    
    @Update(sqlFile=true)
    int deleteUsersByRoleId(String roleId);
}
