/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.phuag.sample.common.service;

import com.phuag.sample.common.persistence.TreeDao;
import com.phuag.sample.common.persistence.TreeEntity;
import com.phuag.sample.common.utils.Reflections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class TreeService<D extends TreeDao<T>, T extends TreeEntity<T>> extends CrudService<D, T> {

    @Transactional(readOnly = false)
    public int save(T entity) {

        @SuppressWarnings("unchecked")
        Class<T> entityClass = Reflections.getClassGenricType(getClass(), 1);

        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        T parentEntity = null;
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId("0");
            try {
                parentEntity = entityClass.getConstructor(String.class).newInstance("0");
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        } else {
            parentEntity = super.select(entity.getParentId());
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = entity.getParentIds();

        // 设置新的父节点串
        entity.setParentIds(parentEntity.getParentIds() + parentEntity.getId() + ",");

        // 更新子节点 parentIds
        T o = null;
        try {
            o = entityClass.newInstance();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        o.setParentIds("%," + entity.getId() + ",%");
        List<T> list = dao.selectByParentIdsLike(o);
        for (T e : list) {
            if (e.getParentIds() != null && oldParentIds != null) {
                e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
                preUpdateChild(entity, e);
                dao.updateParentIds(e);//todo need to test it.
            }
        }
        // 保存或更新实体
        return super.save(entity);
    }

    /**
     * 预留接口，用户更新子节前调用
     *
     * @param childEntity
     */
    protected void preUpdateChild(T entity, T childEntity) {

    }

}
