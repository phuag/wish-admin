package com.phuag.sample.modules.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.service.CrudService;
import com.phuag.sample.common.utils.DTOUtils;
import com.phuag.sample.exception.ResourceNotFoundException;
import com.phuag.sample.modules.sys.dao.StaffMapper;
import com.phuag.sample.modules.sys.domain.Staff;
import com.phuag.sample.modules.sys.model.StaffDetail;
import com.phuag.sample.modules.sys.model.StaffForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class StaffService extends CrudService<StaffMapper, Staff> {

//    @Resource
//    private StaffMapper staffMapper;

//    public StaffService(StaffMapper staffMapper) {
//        this.staffMapper = staffMapper;
//    }

    public int saveStaff(StaffForm form) {
        Staff staff = DTOUtils.map(form, Staff.class);
        return super.save(staff);
//        if (StringUtils.isBlank(staff.getId())) {
//            staff.preInsert();
//            return staffMapper.insert(staff);
//        } else {
//            staff.preUpdate();
//            return staffMapper.updateByPrimaryKey(staff);
//        }

    }

    public int updateStaff(String staffId, StaffForm form) {
        Assert.hasText(staffId, "staff id can not be null");
        Staff staff = dao.selectByPrimaryKey(staffId);
        DTOUtils.mapTo(form, staff);
        staff.preUpdate();
        return dao.updateByPrimaryKey(staff);
    }

    public StaffDetail getStaffById(String staffId) {
        Assert.hasText(staffId, "staff id can not be null");
//        Staff staff = staffMapper.selectByPrimaryKey(staffId);
        Staff staff = super.select(staffId);
        if (staff == null) {
            throw new ResourceNotFoundException(staffId);
        }
        return DTOUtils.map(staff, StaffDetail.class);
    }


    public PageInfo<StaffDetail> searchStaff(String keyword, Pageable page) {
        log.debug("search staffs by keyword@" + keyword + ", page @" + page);
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        //TODO need to add sort function ,but the pagerhelper is not support well.
        List<Staff> staffs;
        if (StringUtils.isNotBlank(keyword)) {
            staffs = dao.searchStaffByName(keyword);
        } else {
            staffs = dao.logicalSelectAll();
        }

        List<StaffDetail> staffDetails = DTOUtils.mapList(staffs, StaffDetail.class);

        return new PageInfo<StaffDetail>(staffDetails);
    }


}
