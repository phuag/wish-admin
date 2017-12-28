package com.phuag.sample.modules.sys.service;

import com.phuag.sample.common.service.CrudService;
import com.phuag.sample.modules.sys.dao.SysRoleMapper;
import com.phuag.sample.modules.sys.domain.SysRole;
import com.phuag.sample.modules.sys.model.StaffForm;
import com.phuag.sample.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vvvvvv on 2017/12/26.
 */
@Service
@Transactional
public class SysRoleService extends CrudService<SysRoleMapper, SysRole> {

    public int saveSysRole(SysRole form) {
        return super.save(form);
    }


}
