//package com.test.firstproject.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//
//@Table(name = "departments")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Department {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @OneToMany(mappedBy = "department")
//    private List<Student> students = new ArrayList<>();
//}