package com.jianxin.spring.datasource;

import com.jianxin.spring.config.MailConf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author jianxinliu
 * @date 2022/07/03 10:54
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DataSourceTest {

    @Resource
    private MailConf mailConf;

    @Resource(name = "h2DataSource")
    private DataSource dataSource;

//    @Resource(name = "pgDataSource")
//    private DataSource pgDataSource;

    @Resource(name = "h2JdbcTemplate")
    private JdbcTemplate h2JdbcTemplate;

//    @Resource(name = "pgJdbcTemplate")
//    private JdbcTemplate pgJdbcTemplate;

    @Test
    public void aa() throws Exception {
        log.info(dataSource.toString());
        Connection connection = dataSource.getConnection();
        log.info(connection.toString());

//        log.info(pgDataSource.toString());
//        log.info(pgDataSource.getConnection().toString());

        h2JdbcTemplate.queryForList("select * from student limit 2").forEach(stu -> log.info(stu.toString()));

        log.info("_________");

//        pgJdbcTemplate.queryForList("select * from eda.student order by class desc limit 2").forEach(stu -> log.info(stu.toString()));

        connection.close();

        log.info(mailConf.toString());
    }
}
