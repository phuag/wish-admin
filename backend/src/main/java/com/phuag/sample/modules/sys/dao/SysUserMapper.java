package com.phuag.sample.modules.sys.dao;

import com.phuag.sample.common.persistence.CrudDao;
import com.phuag.sample.modules.sys.domain.SysMenu;
import com.phuag.sample.modules.sys.domain.SysOffice;
import com.phuag.sample.modules.sys.domain.SysRole;
import com.phuag.sample.modules.sys.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "sysUserMapper")
@Mapper
public interface SysUserMapper extends CrudDao<SysUser> {

//    SysUser selectByPrimaryKey(String id);

    @Select("SELECT * FROM sys_user " +
            "WHERE del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_NORMAL}  " +
            "and login_name= #{loginName,jdbcType=VARCHAR}")
    SysUser selectSysUserByLoginName(String loginName);

    @Select("SELECT * FROM sys_role " +
            "WHERE del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_NORMAL}  " +
            "and id in " +
            "(select role_id from sys_user_role where user_id = #{id,jdbcType=VARCHAR})")
    List<SysRole> getSysUserRolesByUser(SysUser user);

    @Select("SELECT * FROM sys_office " +
            "WHERE del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_NORMAL}  " +
            "and id = #{officeId,jdbcType=VARCHAR}")
    SysOffice getSysUserOffice(SysUser user);

//    @Select("SELECT * FROM sys_user " +
//            "WHERE del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_NORMAL}  " +
//            "AND LOWER(login_name) LIKE concat('%',LOWER(#{name,jdbcType=VARCHAR}),'%') " +
//            "AND office_id = #{officeId,jdbcType=VARCHAR}")

    /**
     * 根据单位id以及关键字查找系统用户
     * sql语句在xml文件中
     *
     * @param officeId 用户单位id
     * @param keyword  关键字
     * @return
     */
    List<SysUser> getByOfficeAndName(@Param("officeId") String officeId, @Param("keyword") String keyword);

    @Select("SELECT * FROM sys_menu " +
            "WHERE del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_NORMAL}  " +
            "and id in (" +
            "select t.menu_id from sys_role_menu t, sys_user_role w " +
            "where w.role_id = t.role_id and w.user_id =#{id,jdbcType=VARCHAR}" +
            ")")
    List<SysMenu> getSysMenu(SysUser sysUser);
}