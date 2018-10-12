package com.phuag.sample.modules.sys.domain;

import com.phuag.sample.common.persistence.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends DataEntity<SysMenu> {

    private String name;

    private String code;

    private String permission;

    private String permissionCode;

    private String isRequired;

}