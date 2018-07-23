package com.phuag.sample.modules.sys.dao;

import com.phuag.sample.common.persistence.CrudDao;
import com.phuag.sample.modules.sys.domain.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vvvvvv
 */
@Repository(value="staffMapper")
@Mapper
public interface StaffMapper extends CrudDao<Staff> {

    @Select("SELECT * FROM staff " +
            "WHERE del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_NORMAL} " +
            "AND LOWER(name) LIKE concat('%',LOWER(#{keyword,jdbcType=VARCHAR}),'%')")
    List<Staff> searchStaffByName(String keyword);
}