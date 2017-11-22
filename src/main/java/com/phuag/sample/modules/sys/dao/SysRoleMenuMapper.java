package com.phuag.sample.modules.sys.dao;


import com.phuag.sample.modules.sys.domain.SysRoleMenuKey;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(SysRoleMenuKey key);

    int insert(SysRoleMenuKey record);

    int insertSelective(SysRoleMenuKey record);
}