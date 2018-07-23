package com.phuag.sample.modules.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author vvvvvv
 * @date 2017/9/18
 */
@NoArgsConstructor
@Data
public class StaffDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String sex;

    private Byte age;

}
