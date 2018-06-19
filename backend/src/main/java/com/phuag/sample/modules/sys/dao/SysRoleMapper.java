package com.phuag.sample.modules.sys.dao;


import com.phuag.sample.common.persistence.CrudDao;
import com.phuag.sample.modules.sys.domain.Staff;
import com.phuag.sample.modules.sys.domain.SysRole;

import java.util.List;

public interface SysRoleMapper extends CrudDao<SysRole> {
    int deleteByPrimaryKey(String id);

    SysRole selectByPrimaryKey(String id);


}