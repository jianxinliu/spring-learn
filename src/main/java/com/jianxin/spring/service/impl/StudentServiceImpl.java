package com.jianxin.spring.service.impl;

import com.jianxin.spring.entity.Student;
import com.jianxin.spring.repository.StudentRepository;
import com.jianxin.spring.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jianxinliu
 * @date 2022/07/18 22:27
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private JedisPool jedisPool;

    @Override
    public List<Student> getAll() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public String findCityByName(String name) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "stu_" + name;
            Boolean exists = jedis.exists(key);
            if (exists) {
                log.info("hit cache: {}", key);
                return jedis.get(key);
            } else {
                Student stu = studentRepository.findByName(name);
                jedis.set(key, stu.getCity());
                return stu.getCity();
            }
        }
    }
}
