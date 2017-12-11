package com.phuag.sample.configuration.logging;

import com.phuag.sample.common.config.logging.AccessLoggerParser;
import com.phuag.sample.common.config.logging.AopAccessLoggerSupport;
import com.phuag.sample.common.logging.AccessLoggerListener;
import com.phuag.sample.common.logging.DefaultAccessLoggerParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Created by vvvvvv on 2017/12/6.
 */
@Configuration
@ConditionalOnClass(AccessLoggerListener.class)
public class AopAccessLoggerAutoConfiguration {

    @Bean
    public AopAccessLoggerSupport aopLoggerSupport() {
        return new AopAccessLoggerSupport();
    }

    @Bean
    @DependsOn("aopLoggerSupport")
    public DefaultAccessLoggerParser defaultAccessLoggerParser(AopAccessLoggerSupport loggerSupport) {
        DefaultAccessLoggerParser bean = new DefaultAccessLoggerParser();
        loggerSupport.addParser(bean);
        return bean;
    }
}
