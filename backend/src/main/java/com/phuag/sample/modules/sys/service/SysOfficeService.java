package com.phuag.sample.modules.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.service.TreeService;
import com.phuag.sample.modules.sys.dao.SysOfficeMapper;
import com.phuag.sample.modules.sys.domain.SysOffice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vvvvvv on 2017/12/28.
 */
@Service
@Transactional
@Slf4j
public class SysOfficeService extends TreeService<SysOfficeMapper, SysOffice> {
    public List<SysOffice> searchSysOffice(String officeId) {
        log.debug("search office by officeId@{}", officeId);
        List<SysOffice> offices;
        if (StringUtils.isNotBlank(officeId)) {
            offices = dao.selectSubOfficeById(officeId);
        } else {
            offices = dao.logicalSelectAll();
        }
        return offices;
    }

    public String getOfficeNameWithPath(SysOffice office) {
        String officeNameWithPath = "";
        String parentIds = office.getParentIds();
        String[] ids = parentIds.split(",");
        for (String id : ids) {
            if(id.equals("0")){
                continue;
            }
            String name = dao.selectByPrimaryKey(id).getName();
            officeNameWithPath = officeNameWithPath + "/" + name;
        }
        return officeNameWithPath + "/" + office.getName();
    }
}
