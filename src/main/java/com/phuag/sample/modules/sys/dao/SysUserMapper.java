package com.phuag.sample.modules.sys.dao;

import com.phuag.sample.common.persistence.CrudDao;
import com.phuag.sample.modules.sys.domain.SysOffice;
import com.phuag.sample.modules.sys.domain.SysRole;
import com.phuag.sample.modules.sys.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="sysUserMapper")
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
            "and id = #{companyId,jdbcType=VARCHAR}")
    SysOffice getSysUserOffice(SysUser user);
}