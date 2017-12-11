package com.phuag.sample.modules.sys.service;


import com.phuag.sample.modules.sys.domain.Staff;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by vvvvvv on 2017/10/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StaffServiceTest {

    @Autowired
    private StaffService staffService;
    @Test
    public void saveStaff() throws Exception {
    }

    @Test
    public void updateStaff() throws Exception {
    }

    @Test
    public void getStaffById() throws Exception {
    }

    @Test
    public void deleteStaff() throws Exception {
        log.info("{}",1);
        Staff staff = new Staff();
        staff.setId("42");
        int res = staffService.delete(staff);
        Assert.assertEquals(1,res);
    }

    @Test
    public void deleteStaffById() throws Exception {
        int res = staffService.delete("44");
        Assert.assertEquals(1,res);
    }



    @Test
    public void searchStaff() throws Exception {
    }



}
