package com.jianxin.spring.datasource;

import com.jianxin.spring.service.TxTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 使用注解管理事务
 * @author jianxinliu
 * @date 2022/07/03 18:58
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TransactionTest2 {

    @Resource
    private TxTestService txTestService;

    @Resource
    private JdbcTemplate h2JdbcTemplate;

    @Test
    public void run() throws Exception {
        txTestService.insertStudent();
        log.info("AAA: {}", count());

        try {
            txTestService.insertThenRollback();
        } catch (Exception e) {
            log.info("BBB: {}", count());
        }

        try {
            txTestService.invokeInsertThenRollback();
        } catch (Exception e) {
            log.info("CCC: {}", count());
        }
    }

    private long count() {
        return (long) h2JdbcTemplate.queryForList("select count(1) as cnt from student").get(0).get("cnt");
    }
}
