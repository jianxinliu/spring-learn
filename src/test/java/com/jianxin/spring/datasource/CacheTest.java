package com.jianxin.spring.datasource;

import com.jianxin.spring.cache.SimpleCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author jianxinliu
 * @date 2022/07/18 23:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CacheTest {

    @Resource
    private SimpleCache simpleCache;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            String cityByName = simpleCache.getCityByName();
            log.info("find: {}", cityByName);
        }
        simpleCache.reload();
        log.info("find when cache evict: {}", simpleCache.getCityByName());
    }

}
