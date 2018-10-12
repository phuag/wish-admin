package com.phuag.sample.modules.sys.service;

import com.phuag.sample.modules.sys.domain.SysMenu;
import com.phuag.sample.modules.sys.domain.SysUser;
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
 * @author phuag
 * @date 2018/9/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysUserServiceTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void getSysMenu() {
        SysUser user = sysUserService.getSysUserById("1");
        List<SysMenu> menus= sysUserService.getSysMenu(user);
        Assert.assertNotNull("menus is null",menus);
    }
}