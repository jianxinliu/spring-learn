package com.jianxin.spring.repository;

import com.jianxin.spring.entity.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author jianxinliu
 * @date 2022/07/10 22:34
 */
public interface StudentPageRepository extends PagingAndSortingRepository<Student, Long> {
}
