//package com.test.firstproject.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "courses")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Course {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToMany(mappedBy = "course")
//    private List<Student> students = new ArrayList<>();
//}