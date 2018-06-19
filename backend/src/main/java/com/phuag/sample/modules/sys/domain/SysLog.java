package com.phuag.sample.modules.sys.domain;

import com.phuag.sample.common.persistence.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by vvvvvv on 2017/12/4.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLog extends DataEntity<SysLog> {

    private String type; 		// 日志类型（1：接入日志；2：错误日志）
    private String title;		// 日志标题
    private String remoteAddr; 	// 操作用户的IP地址
    private String requestUri; 	// 操作的URI
    private String method; 		// 操作的方式
    private String params; 		// 操作提交的数据
    private String userAgent;	// 操作用户代理信息
    private String exception; 	// 异常信息

    @Transient
    private Date beginDate;		// 开始日期
    @Transient
    private Date endDate;		// 结束日期

    // 日志类型（1：接入日志；2：错误日志）
    public static final String TYPE_ACCESS = "1";
    public static final String TYPE_EXCEPTION = "2";
}
