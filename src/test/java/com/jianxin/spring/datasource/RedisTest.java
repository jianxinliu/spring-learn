package com.jianxin.spring.datasource;

import com.jianxin.spring.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author jianxinliu
 * @date 2022/07/18 22:12
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private StudentService studentService;

    @Resource
    public JedisPool jedisPool;

    @Test
    public void test() {
        try (Jedis conn = jedisPool.getResource()) {
            conn.set("jianxin", "liu");

            String out = conn.get("jianxin");
            log.info(out);

            log.info("{}", conn.keys("*"));
        }

        String jack = studentService.findCityByName("jack");
        log.info("jack from: {}", jack);

        String rose = studentService.findCityByName("rose");
        log.info("rose from: {}", rose);

        String jack1 = studentService.findCityByName("jack");
        log.info("jack from: {}", jack1);
    }
}
