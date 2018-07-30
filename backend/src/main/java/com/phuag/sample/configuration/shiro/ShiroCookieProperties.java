package com.phuag.sample.configuration.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author vvvvvv
 * @date 2017/3/9
 */
@ConfigurationProperties(prefix ="shiro.cookie")
public class ShiroCookieProperties {
    private String name = "rememberMe";

    private String value;

    /**
     * one year
     */
    private int maxAge = 60*60*24*365;

    private int version = -1;

    private boolean secure;

    private boolean httpOnly = true;

    private String cipherKey;

    private String encryptionCipherKey;

    private String decryptionCipherKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public String getCipherKey() {
        return cipherKey;
    }

    public void setCipherKey(String cipherKey) {
        this.cipherKey = cipherKey;
    }

    public String getEncryptionCipherKey() {
        return encryptionCipherKey;
    }

    public void setEncryptionCipherKey(String encryptionCipherKey) {
        this.encryptionCipherKey = encryptionCipherKey;
    }

    public String getDecryptionCipherKey() {
        return decryptionCipherKey;
    }

    public void setDecryptionCipherKey(String decryptionCipherKey) {
        this.decryptionCipherKey = decryptionCipherKey;
    }
}
