package com.jianxin.spring.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * Spring 中编程式事务
 * @author jianxinliu
 * @date 2022/07/03 18:32
 */
@Component
@Slf4j
public class TransactionTest implements CommandLineRunner {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource(name = "h2JdbcTemplate")
    private JdbcTemplate h2JdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("count before tx: {}", getCount());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (int i = 0; i < 10; i++) {
                    h2JdbcTemplate.execute(String.format("insert into student(sno, city) values(%d, 'jack')", 9999 + i));
                }
                log.info("count in tx: {}", getCount());
//                status.flush();
                status.setRollbackOnly();
            }
        });
        log.info("count after tx: {}", getCount());
    }

    private Long getCount() {
        return (Long) h2JdbcTemplate.queryForList("select count(1) as cnt from student").get(0).get("cnt");
    }
}
