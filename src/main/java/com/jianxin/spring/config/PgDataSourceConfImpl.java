package com.jianxin.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author jianxinliu
 * @date 2022/07/03 12:08
 */
@Configuration
@Slf4j
public class PgDataSourceConfImpl implements DataSourceConf {

    @Override
    @Bean(name = "pgDataSourceProperties")
    @ConfigurationProperties("spring.datasource.pg")
    public DataSourceProperties getDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Override
    @Bean(name = "pgDataSource")
    public DataSource getDataSource(@Qualifier("pgDataSourceProperties") DataSourceProperties dataSourceProperties) {
        log.info("pg dataSource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Override
    @Bean(name = "pgJdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("pgDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Override
    public PlatformTransactionManager getTxManager(@Qualifier("pgDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
