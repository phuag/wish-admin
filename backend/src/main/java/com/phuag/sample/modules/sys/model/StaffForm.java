package com.phuag.sample.modules.sys.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author vvvvvv
 * @date 2017/9/18
 */
@NoArgsConstructor
@Data
public class StaffForm implements Serializable {

    private String name;

    private String sex;

    private Byte age;

}
