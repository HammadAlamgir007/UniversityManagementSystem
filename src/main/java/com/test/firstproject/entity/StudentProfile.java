package com.test.firstproject.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String cnic;

    private String address;

    private String bloodGroup;

    private String imageName;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

}