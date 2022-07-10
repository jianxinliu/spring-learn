package com.jianxin.spring.datasource;

import com.jianxin.spring.entity.Student;
import com.jianxin.spring.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianxinliu
 * @date 2022/07/10 19:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaInitTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void run() throws Exception {
        initStudents();

        studentRepository.findByGender("male").forEach(System.out::println);
    }

    private void initStudents() {
        long count = studentRepository.count();
        if (count > 0) {
            return;
        }

        List<Student> students = new ArrayList<>(3);
        students.add(Student.builder()
                .name("jack")
                .city("beijing")
                .gender("male")
                .build());
        students.add(Student.builder()
                .name("rose")
                .city("shanghai")
                .gender("female")
                .build());
        students.add(Student.builder()
                .name("pony")
                .city("shenzhen")
                .gender("male")
                .build());
        studentRepository.saveAll(students);
    }
}
