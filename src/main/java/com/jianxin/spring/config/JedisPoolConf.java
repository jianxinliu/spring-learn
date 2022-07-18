package com.jianxin.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author jianxinliu
 * @date 2022/07/18 22:05
 */
@Configuration
public class JedisPoolConf {

    @Bean
    @ConfigurationProperties(prefix = "redis")
    public JedisPoolConfig getJedisPoolConf() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host) {
        return new JedisPool(getJedisPoolConf(), host);
    }
}
