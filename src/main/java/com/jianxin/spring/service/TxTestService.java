package com.jianxin.spring.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jianxinliu
 * @date 2022/07/03 19:04
 */
@Component
public class TxTestService {
    @Resource
    private JdbcTemplate h2JdbcTemplate;

    /**
     * 自己注入自己有时会出现循环依赖，所以采用延迟注入，使用时再注入
     */
    @Lazy
    @Resource
    private TxTestService txTestService;

    @Transactional(rollbackFor = Exception.class)
    public void insertStudent() {
        h2JdbcTemplate.execute(String.format("insert into student(sno, city) values(%d, 'jack')", 9999));
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertThenRollback() throws Exception {
        h2JdbcTemplate.execute(String.format("insert into student(sno, city) values(%d, 'jack')", 9998));
        throw new Exception("");
    }

    // 事务无效
    public void invokeInsertThenRollback1() throws Exception {
        // 此方法不加 @Transactional
        // 直接调用带有 @Transactional 的方法，并不会开启事务。因为 spring 的声明式事务是使用 aop 代理实现的，
        // 开启事务的前提是方法被代理调用，这种内部调用的方式并没有走代理，所以不会开启事务。就算抛异常，也不会回滚
        insertThenRollback();
    }

    // 获取当前的代理对象，使用该对象调用，事务有效
    public void invokeInsertThenRollback2() throws Exception {
        ((TxTestService)AopContext.currentProxy()).insertThenRollback();
    }


    // 通过对象自己调用，事务有效
    public void invokeInsertThenRollback() throws Exception {
        txTestService.insertThenRollback();
    }
}
