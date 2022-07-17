package com.jianxin.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

/**
 * @author jianxinliu
 * @date 2022/07/17 12:18
 */
@Component
@Slf4j
public class TestConfig implements CommandLineRunner {

    @Value("${test.foo-test}")
    private Integer fooTest;

    @Value("${test.bar}")
    private String bar;

    @Value("${test.array:0,1,2,3,4}")
    private int[] array;

    private static String staticName;

    @Value("#{${test.cityMap}}")
    private Map<String, String> cityMap;

    @Value("${test.staticName}")
    public void setStaticName(String name) {
        TestConfig.staticName = name;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("config from file: foo: {}, bar: {}, staticName: {}", fooTest, bar, staticName);

        log.info("array " + Arrays.toString(array));

        cityMap.forEach((k, v) -> log.info("k: {}, v: {}", k, v));
    }
}
