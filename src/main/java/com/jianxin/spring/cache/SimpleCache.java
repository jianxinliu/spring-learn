package com.jianxin.spring.cache;

import com.jianxin.spring.repository.StudentRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jianxinliu
 * @date 2022/07/18 23:02
 */
@Service
@CacheConfig(cacheNames = "student")
public class SimpleCache {

    @Resource
    private StudentRepository studentRepository;

    @Cacheable
    public String getCityByName() {
        return studentRepository.findByName("jack").getCity();
    }

    /**
     * 清理缓存
     */
    @CacheEvict
    public void reload() {}
}
