package com.phuag.sample.modules.sys.domain;

import com.phuag.sample.common.persistence.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by vvvvvv on 2017/12/28.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOffice extends TreeEntity<SysOffice> {
    private String code; 	// 机构编码
    private String type; 	// 机构类型（1：公司；2：部门；3：小组）
    private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
    private String address; // 联系地址
    private String zipCode; // 邮政编码
    private String master; 	// 负责人
    private String phone; 	// 电话
    private String fax; 	// 传真
    private String email; 	// 邮箱
    private String useable;//是否可用


}
