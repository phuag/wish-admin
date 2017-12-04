/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.phuag.sample.common.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phuag.sample.common.utils.IdGen;
import com.phuag.sample.modules.sys.domain.SysUser;
import com.phuag.sample.utils.UserUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 数据Entity类,关联表对象别继承此类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Data
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;

	@Length(min=0, max=255)
	protected String remarks;	// 备注

	@JsonIgnore
	protected String createBy;	// 创建者
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createDate;	// 创建日期

	@JsonIgnore
	protected String updateBy;	// 更新者
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateDate;	// 更新日期
	@JsonIgnore
	@Length(min=1, max=1)
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	
	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord()){
			setId(IdGen.uuid());
		}
		SysUser user = UserUtils.getUser();
		String userId = user.getId();
		if (StringUtils.isNotBlank(userId)){
			this.updateBy = userId;
			this.createBy = userId;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		SysUser user = UserUtils.getUser();
		String userId = user.getId();
		if (StringUtils.isNotBlank(userId)){
			this.updateBy = userId;
		}
		this.updateDate = new Date();
	}
	


}
