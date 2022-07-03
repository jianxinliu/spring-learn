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
 * @author jianxinliu
 * @date 2022/07/03 11:33
 */
@Configuration
@Slf4j
public class H2DataSourceConfImpl implements DataSourceConf {
    @Override
    @Primary
    @Bean(name = "h2DataSourceProperties")
    @ConfigurationProperties("spring.datasource.h2")
    public DataSourceProperties getDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Override
    @Primary
    @Bean(name = "h2DataSource")
    public DataSource getDataSource(@Qualifier("h2DataSourceProperties") DataSourceProperties dataSourceProperties) {
        log.info("h2 dataSource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Override
    @Primary
    @Bean(name = "h2JdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("h2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Override
    public PlatformTransactionManager getTxManager(@Qualifier("h2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
