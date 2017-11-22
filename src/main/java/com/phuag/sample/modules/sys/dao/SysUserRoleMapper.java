package com.phuag.sample.modules.sys.dao;


import com.phuag.sample.modules.sys.domain.SysUserRoleKey;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(SysUserRoleKey key);

    int insert(SysUserRoleKey record);

    int insertSelective(SysUserRoleKey record);
}