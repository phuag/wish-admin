package com.phuag.sample.modules.sys.model;

import java.io.Serializable;

/**
 * Created by vvvvvv on 2017/9/18.
 */
public class StaffDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String sex;

    private Byte age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString(){
        return "StaffDetail{" + "name=" + name + ", sex=" + sex + "age=" + age + "}";
    }
}
