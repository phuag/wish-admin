package com.phuag.sample.modules.sys.domain;

import com.phuag.sample.common.persistence.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends DataEntity<SysUser>{
//    private String id;

    private String companyId;// 归属公司

    private String officeId;// 归属部门

    private String loginName;// 登录名

    private String password;// 密码

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

//    private String createBy;

//    private Date createDate;

//    private String updateBy;

//    private Date updateDate;

//    private String remarks;

//    private String delFlag;

    public boolean isAdmin(){
        return isAdmin(this.id);
    }

    public static boolean isAdmin(String id){
        return id != null && "1".equals(id);
    }
}