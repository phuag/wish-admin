package com.phuag.sample.common.logging;

/**
 * 访问日志监听器
 * Created by vvvvvv on 2017/12/6.
 */
public interface AccessLoggerListener {

    void onLogger(AccessLogInfo logInfo);

    default void onLogBefore(AccessLogInfo logInfo){}
}
