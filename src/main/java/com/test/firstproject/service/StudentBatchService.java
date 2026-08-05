//package com.test.firstproject.service;
//
//import com.test.firstproject.batch.StudentProcessor;
//import com.test.firstproject.batch.StudentReader;
//import com.test.firstproject.batch.StudentWriter;
//import com.test.firstproject.entity.Student;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.infrastructure.item.Chunk;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class StudentBatchService {
//
//    private final StudentReader reader;
//    private final StudentProcessor processor;
//    private final StudentWriter writer;
//
//    public void executeBatch() {
//
//        log.info("Batch Started");
//
//        List<Student> students = reader.readStudents();
//
//        List<Student> processedStudents =
//                students.stream()
//                        .map(processor::process)
//                        .toList();
//
//        writer.write( new Chunk<>(processedStudents) );
//
//        log.info("Batch Completed");
//
//    }
//
//}
