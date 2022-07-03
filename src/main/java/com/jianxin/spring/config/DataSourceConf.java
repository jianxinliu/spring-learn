package com.jianxin.spring.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author jianxinliu
 * @date 2022/07/03 12:08
 */
public interface DataSourceConf {
    DataSourceProperties getDataSourceProperties();

    DataSource getDataSource(DataSourceProperties dataSourceProperties);

    JdbcTemplate getJdbcTemplate(DataSource dataSource);

    PlatformTransactionManager getTxManager(DataSource dataSource);
}
