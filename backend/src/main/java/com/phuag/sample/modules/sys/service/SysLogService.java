package com.phuag.sample.modules.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.service.CrudService;
import com.phuag.sample.common.utils.DTOUtils;
import com.phuag.sample.modules.sys.dao.SysLogMapper;
import com.phuag.sample.modules.sys.domain.Staff;
import com.phuag.sample.modules.sys.domain.SysLog;
import com.phuag.sample.modules.sys.model.StaffDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vvvvvv on 2017/12/4.
 */
@Service
@Transactional
@Slf4j
public class SysLogService extends CrudService<SysLogMapper,SysLog> {
    public PageInfo<SysLog> searchSysLog(String keyword, Pageable page) {
        log.debug("search staffs by keyword@" + keyword + ", page @" + page);
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        //TODO need to add sort function ,but the pagerhelper is not support well.
        List<SysLog> sysLogs;
        if (StringUtils.isNotBlank(keyword)) {
//            staffs = dao.searchStaffByName(keyword);
            sysLogs = dao.selectAll();
        } else {
            sysLogs = dao.selectAll();
        }

//        List<StaffDetail> staffDetails = DTOUtils.mapList(sysLogs,StaffDetail.class);

        return new PageInfo<SysLog>(sysLogs);
    }
}
