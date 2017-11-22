package com.phuag.sample;

import com.phuag.sample.modules.sys.domain.Staff;

/**
 * Created by vvvvvv on 2017/10/27.
 */
public class FixTures {
    public static Staff createStaff(String name,String sex,byte age){
        Staff staff = new Staff();
        staff.setName(name);
        staff.setAge(age);
        return staff;
    }
}
