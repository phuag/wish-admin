package com.phuag.sample.configuration.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author vvvvvv
 * @date 2017/3/10
 */
@ConfigurationProperties(prefix = "shiro.sign-in")
@Data
public class ShiroSignInProperties {

    /**
     * 登录请求的用户名字段名
     */
    private String userParam = "username";

    /**
     * 登录请求的密码字段名
     */
    private String passwordParam = "password";

    private String rememberMeParam = "rememberMe";

}
