package com.test.firstproject.batch;

import com.test.firstproject.entity.Student;
import com.test.firstproject.repository.StudentRepository;

// Updated Spring Batch 6.0 Imports
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@Configuration
public class StudentBatchConfig {

    @Bean
    public RepositoryItemReader<Student> studentReader(StudentRepository repository) {

        RepositoryItemReader<Student> reader = new RepositoryItemReader<>();

        reader.setRepository(repository);
        reader.setMethodName("findAll");
        reader.setPageSize(10);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));

        return reader;
    }

    @Bean
    public Job studentJob(JobRepository jobRepository, Step studentStep) {
        return new JobBuilder("studentJob", jobRepository)
                .start(studentStep)
                .build();
    }

    @Bean
    public Step studentStep(
            JobRepository jobRepository,
            RepositoryItemReader<Student> studentReader,
            StudentProcessor processor,
            StudentWriter writer) {

        // In Spring Batch 6.0, chunk() only takes the chunk size!
        return new StepBuilder("studentStep", jobRepository)
                .<Student, Student>chunk(10)
                .reader(studentReader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}