/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.phuag.sample.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.persistence.CrudDao;
import com.phuag.sample.common.persistence.DataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 *
 * @author ThinkGem
 * @version 2014-05-16
 */

public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public T select(String id) {
        return dao.selectByPrimaryKey(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public T selectOne(T entity) {
        return dao.selectOne(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<T> select(T entity) {
        return dao.select(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public PageInfo<T> findPage(Pageable page, T entity) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        return new PageInfo<T>(dao.select(entity));
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public int save(T entity) {
        if (entity.isNewRecord()) {
            entity.preInsert();
            return dao.insert(entity);
        } else {
            entity.preUpdate();
            return dao.updateByPrimaryKey(entity);
        }
    }

    /**
     * 删除数据
     *
     * @param entity 删除的条件封装在对象中
     * @return 数据库受影响的行数,所以大于0成功,等于0失败
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int delete(T entity) {
        return dao.logicalDelete(entity);
    }

    /**
     * 逻辑删除,根据主键删除数据
     *
     * @param id 需要删除对象的主键值
     * @return 数据库受影响的行数,所以大于0成功，等于0失败
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int delete(String id) {
        return dao.logicalDeleteByPrimaryKey(id);
    }

}
