package com.phuag.sample.configuration.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author vvvvvv
 * @date 2017/3/9
 */
@ConfigurationProperties(prefix = "shiro.cookie")
@Data
public class ShiroCookieProperties {
    private String name = "rememberMe";

    private String value;

    /**
     * one year
     */
    private int maxAge = 60 * 60 * 24 * 365;

    private int version = -1;

    private boolean secure;

    private boolean httpOnly = true;

    private String cipherKey;

    private String encryptionCipherKey;

    private String decryptionCipherKey;

}
