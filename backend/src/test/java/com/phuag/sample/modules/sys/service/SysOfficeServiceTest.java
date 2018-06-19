package com.phuag.sample.modules.sys.service;

import com.phuag.sample.modules.sys.domain.SysOffice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vvvvvv on 2018/1/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysOfficeServiceTest {
    @Autowired
    private SysOfficeService sysOfficeService;

    @Test
    public void searchSysOffice() throws Exception {
        List<SysOffice> offices = sysOfficeService.searchSysOffice("");
        Assert.assertEquals(26, offices.size());
    }

}