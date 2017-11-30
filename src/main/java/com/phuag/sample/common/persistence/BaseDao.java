package com.phuag.sample.common.persistence;

import com.phuag.sample.common.persistence.provider.CoustomSqlProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * DAO支持类实现
 * @author phuag
 * @version 2017-11-28
 */
public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {

    /**
     * 获取逻辑上未删除的所有数据
     * @return del_flag为0的数据
     */
    @SelectProvider(
            type = CoustomSqlProvider.class,
            method = "dynamicSQL"
    )
    List<T> logicalSelectAll();

    /**
     * 逻辑删除
     * @param var1 待删除的对象的条件
     * @return 该删除操作影响的数据条数
     */
    @UpdateProvider(
            type = CoustomSqlProvider.class,
            method = "dynamicSQL"
    )
    int logicalDelete(T var1);

    /**
     * 逻辑删除,根据主键
     * @param var1 主键值
     * @return 该删除操作影响的数据条数
     */
    @UpdateProvider(
            type = CoustomSqlProvider.class,
            method = "dynamicSQL"
    )
    int logicalDeleteByPrimaryKey(Object var1);

}