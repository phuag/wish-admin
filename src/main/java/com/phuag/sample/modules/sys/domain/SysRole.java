package com.phuag.sample.modules.sys.domain;

import com.phuag.sample.common.persistence.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends DataEntity<SysRole> {
    private String id;

    private String officeId;

    private String name; // 角色名称

    private String enname;// 英文名称

    private String roleType;// 权限类型

    private String dataScope;// 数据范围

    private String isSys;//是否是系统数据

    private String useable;//是否是可用

//    private String createBy;
//
//    private Date createDate;
//
//    private String updateBy;
//
//    private Date updateDate;
//
//    private String remarks;
//
//    private String delFlag;

    }