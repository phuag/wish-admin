/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.phuag.sample.common.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.phuag.sample.common.utils.Reflections;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;


/**
 * 数据Entity类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class TreeEntity<T> extends DataEntity<T> {

	private static final long serialVersionUID = 1L;

//	protected T parent;	// 父级编号
	protected String parentId;
	protected String parentIds; // 所有父级编号
	protected String name; 	// 名称
	protected Integer sort;		// 排序
	
	public TreeEntity() {
		super();
		this.sort = 30;
	}
	
	public TreeEntity(String id) {
		super(id);
	}
	
	/**
	 * 父对象，只能通过子类实现，父类实现mybatis无法读取
	 * @return
	 */
//	@JsonBackReference
////	@NotNull
//	public abstract T getParent();
//
//	/**
//	 * 父对象，只能通过子类实现，父类实现mybatis无法读取
//	 * @return
//	 */
//	public abstract void setParent(T parent);
//
//	public String getParentId() {
//		String id = null;
//		if (parent != null){
//			id = (String) Reflections.getFieldValue(parent, "id");
//		}
//		return StringUtils.isNotBlank(id) ? id : "0";
//	}
	
}
