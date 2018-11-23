package com.phuag.sample.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author vvvvvv
 * @date 2017/3/2
 */
@ConfigurationProperties(prefix = "druid")
@Data
public class DruidProperties {
    private String url;
    private String username;
    private String password;

    private int initialSize;
    private int minIdle;
    private int maxActive;
    private long maxWait;

    private String validationQuery;

    private boolean testOnBorrow;

}
