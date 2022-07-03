package com.jianxin.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 单数据源时，可使用 @ConfigurationProperties 注解直接将数据源配置映射到需要该配置的方法入参
 *
 * @author jianxinliu
 * @date 2022/07/03 17:11
 */
//@Configuration
@Slf4j
public class SingleDataSource {
    @Primary
    @Bean(name = "h2DataSource")
    @ConfigurationProperties("spring.datasource.h2")
    public DataSource getDataSource(DataSourceProperties dataSourceProperties) {
        log.info("h2 dataSource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "h2JdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("h2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public PlatformTransactionManager getTxManager(@Qualifier("h2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
