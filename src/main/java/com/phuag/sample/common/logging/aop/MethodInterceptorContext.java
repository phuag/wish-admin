package com.phuag.sample.common.logging.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * Created by vvvvvv on 2017/12/7.
 */
public interface MethodInterceptorContext {
    Object getTarget();

    Method getMethod();

    <T> Optional<T> getParameter(String name);

    <T extends Annotation> T getAnnotation(Class<T> type);

    Map<String,Object> getParams();
}
