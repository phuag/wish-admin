/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.phuag.sample.common.persistence;

import com.phuag.sample.common.persistence.provider.CoustomSqlProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * DAO支持类实现
 * @author ThinkGem
 * @version 2014-05-16
 * @param <T>
 */
public interface TreeDao<T extends TreeEntity<T>> extends CrudDao<T> {

	/**
	 * 找到所有子节点
	 * @param entity,将根据entity对象的parentIds值进行查询
	 * @return
	 */
	@SelectProvider(
			type = CoustomSqlProvider.class,
			method = "dynamicSQL"
	)
	public List<T> selectByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 * @param entity
	 * @return
	 */
	@UpdateProvider(
			type = CoustomSqlProvider.class,
			method = "dynamicSQL"
	)
	public int updateParentIds(T entity);
	
}