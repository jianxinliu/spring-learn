package com.jianxin.spring.service;

import com.jianxin.spring.entity.Student;

import java.util.List;

/**
 * @author jianxinliu
 * @date 2022/07/18 22:25
 */
public interface StudentService {

    List<Student> getAll();

    String findCityByName(String name);
}
