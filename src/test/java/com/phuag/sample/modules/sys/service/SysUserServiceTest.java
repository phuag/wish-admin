package com.phuag.sample.modules.sys.service;

import com.phuag.sample.modules.sys.domain.SysRole;
import com.phuag.sample.modules.sys.domain.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vvvvvv on 2017/11/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void getSysUserById() throws Exception {
    }
//    @Test
    public void getSysUserRolesByUser() throws Exception {
        SysUser user = new SysUser();
        user.setId("1");
        List<SysRole> roles = sysUserService.getSysUserRolesByUser(user);
        for (SysRole role : roles){
            System.out.println(role.getName());
        }
        Assert.assertEquals(1,roles.size());
    }



}