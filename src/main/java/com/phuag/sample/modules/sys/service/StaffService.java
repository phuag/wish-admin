package com.phuag.sample.modules.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.utils.DTOUtils;
import com.phuag.sample.exception.ResourceNotFoundException;
import com.phuag.sample.modules.sys.dao.StaffMapper;
import com.phuag.sample.modules.sys.domain.Staff;
import com.phuag.sample.modules.sys.model.StaffDetail;
import com.phuag.sample.modules.sys.model.StaffForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2015/11/17 0017.
 */
@Service
@Transactional
public class StaffService {
    private static final Logger log = LoggerFactory
            .getLogger(StaffService.class);

    @Resource
    private StaffMapper staffMapper;

    public StaffService(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }

    public int saveStaff(StaffForm form) {
        Staff staff = DTOUtils.map(form,Staff.class);
        if (StringUtils.isBlank(staff.getId())) {
            staff.preInsert();
            return staffMapper.insert(staff);
        } else {
            staff.preUpdate();
            return staffMapper.updateByPrimaryKey(staff);
        }

    }

    public int updateStaff(String staffId, StaffForm form) {
        Assert.hasText(staffId, "staff id can not be null");
        Staff staff = staffMapper.selectByPrimaryKey(staffId);
        DTOUtils.mapTo(form,staff);
        staff.preUpdate();
        return staffMapper.updateByPrimaryKey(staff);
    }


    public StaffDetail getStaffById(String staffId) {
        Assert.hasText(staffId, "staff id can not be null");
        Staff staff = staffMapper.selectByPrimaryKey(staffId);
        if(staff==null){
            throw new ResourceNotFoundException(staffId);
        }
        return DTOUtils.map(staff,StaffDetail.class);
    }

    public int deleteStaffById(String staffId) {
        return staffMapper.deleteByPrimaryKey(staffId);
    }


    public PageInfo<StaffDetail> searchStaff(String keyword, Pageable page) {
        log.debug("search staffs by keyword@" + keyword + ", page @" + page);
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        //TODO need to add sort function ,but the pagerhelper is not support well.
        List<Staff> staffs;
        if (StringUtils.isNotBlank(keyword)) {
            staffs = staffMapper.searchStaffByName(keyword);
        } else {
            staffs = staffMapper.selectAll();
        }

        List<StaffDetail> staffDetails = DTOUtils.mapList(staffs,StaffDetail.class);

        return new PageInfo<StaffDetail>(staffDetails);
    }



}
