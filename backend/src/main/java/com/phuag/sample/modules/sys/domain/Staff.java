package com.phuag.sample.modules.sys.domain;

import com.phuag.sample.common.persistence.DataEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Staff extends DataEntity<Staff> {

    private String name;

    private String sex;

    private Byte age;
}