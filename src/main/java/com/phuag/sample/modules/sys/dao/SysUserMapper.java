package com.phuag.sample.modules.sys.dao;

import com.phuag.sample.common.persistence.CrudDao;
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
            "WHERE del_flag = 0 " +
            "and login_name= #{loginName,jdbcType=VARCHAR}")
    SysUser selectSysUserByLoginName(String loginName);

    List<SysRole> getSysUserRolesByUser(SysUser user);
}