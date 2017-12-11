package com.phuag.sample.modules.sys.logging;

import com.phuag.sample.common.config.logging.AopAccessLoggerSupport;
import com.phuag.sample.common.logging.AccessLogInfo;
import com.phuag.sample.common.logging.AccessLoggerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by vvvvvv on 2017/12/7.
 */
@Component
@Slf4j
public class AccessLog implements AccessLoggerListener {

    @Autowired
    private AopAccessLoggerSupport aopAccessLoggerSupport;

    @PostConstruct
    public void addLoggerSupport() {
        aopAccessLoggerSupport.addListener(this);
    }

    @Override
    public void onLogger(AccessLogInfo logInfo) {
        log.info("拦截到调用信息:{}",logInfo.toString());
    }
}
