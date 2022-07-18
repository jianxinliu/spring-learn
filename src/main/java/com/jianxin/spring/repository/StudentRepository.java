package com.jianxin.spring.repository;

import com.jianxin.spring.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author jianxinliu
 * @date 2022/07/10 17:04
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findByGender(String gender);

    Student findByName(String name);
}
