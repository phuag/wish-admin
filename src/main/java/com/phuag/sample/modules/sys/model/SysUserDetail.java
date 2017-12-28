package com.phuag.sample.modules.sys.model;

import com.phuag.sample.modules.sys.domain.SysOffice;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vvvvvv on 2017/12/27.
 */
@NoArgsConstructor
@Data
public class SysUserDetail implements Serializable {
    private String id;

    private SysOffice office;// 归属部门

    private String loginName;// 登录名

    private String no;// 工号

    private String name;// 姓名

    private String sex;// 性别

    private Date birth;// 生日

    private String email;// 邮箱

    private String phone;// 电话

    private String mobile;	// 手机

    private String userType;// 用户类型

    private String photo;// 头像

    private String loginIp;// 最后登陆IP

    private Date loginDate;//最后登录日期

    private String loginFlag;// 是否允许登陆
}
