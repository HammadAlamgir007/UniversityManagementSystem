package com.test.firstproject.batch;

import com.test.firstproject.entity.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StudentProcessor implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) {
        student.setName(student.getName().toUpperCase());
        return student;
    }
}