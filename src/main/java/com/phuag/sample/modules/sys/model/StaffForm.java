package com.phuag.sample.modules.sys.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by vvvvvv on 2017/9/18.
 */
@NoArgsConstructor
@Data
public class StaffForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String sex;

    private Byte age;

}
