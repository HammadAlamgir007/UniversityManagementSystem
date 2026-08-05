package com.test.firstproject.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "student",
            fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
            orphanRemoval = true)
    private StudentProfile profile;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "department_id")
//    private Department department;
//
//    @ManyToMany
//    @JoinTable(
//            name="Student_Course",
//            joinColumns = @JoinColumn (name ="student_id"),
//            inverseJoinColumns=@JoinColumn(name = "course_id")
//    )
//    private List<Course> course= new ArrayList<>();

}