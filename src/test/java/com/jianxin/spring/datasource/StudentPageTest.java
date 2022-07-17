package com.jianxin.spring.datasource;

import com.jianxin.spring.repository.StudentPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jianxinliu
 * @date 2022/07/10 22:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentPageTest {

    @Autowired
    private StudentPageRepository studentPageRepository;

    @Test
    public void test() {
        studentPageRepository.findAll(Sort.by("city").descending()).forEach(System.out::println);
    }
}
