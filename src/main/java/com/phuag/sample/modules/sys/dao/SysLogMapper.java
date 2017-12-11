package com.phuag.sample.modules.sys.dao;

import com.phuag.sample.common.persistence.CrudDao;
import com.phuag.sample.modules.sys.domain.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by vvvvvv on 2017/12/4.
 */
@Repository(value="sysLogMapper")
@Mapper
public interface SysLogMapper extends CrudDao<SysLog> {

}
