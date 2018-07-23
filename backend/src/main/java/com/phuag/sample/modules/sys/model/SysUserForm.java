package com.phuag.sample.modules.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author vvvvvv
 * @date 2018/7/6
 */
@NoArgsConstructor
@Data
public class SysUserForm {


    /**
     * 归属部门id
     */
    private String officeId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 工号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 是否允许入网
     */
    private String loginFlag;
}
