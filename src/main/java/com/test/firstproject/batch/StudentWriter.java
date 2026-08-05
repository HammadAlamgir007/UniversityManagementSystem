package com.test.firstproject.batch;

import com.test.firstproject.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentWriter implements ItemWriter<Student> {

    @Override
    public void write(Chunk<? extends Student> students) {
        students.forEach(student ->
                log.info("{} - {}",
                        student.getId(),
                        student.getName()));
    }
}