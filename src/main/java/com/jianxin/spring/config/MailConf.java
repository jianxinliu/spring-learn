package com.jianxin.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jianxinliu
 * @date 2022/07/03 23:25
 */
@Component
@Data
@ConfigurationProperties(prefix = "java.mail")
public class MailConf {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
