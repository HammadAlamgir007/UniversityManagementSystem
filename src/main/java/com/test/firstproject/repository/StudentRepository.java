package com.test.firstproject.repository;

import com.test.firstproject.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// crudRepostiry, JpaRepository, 3 type
public interface StudentRepository
        extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name,
            String email
    );
    @EntityGraph(attributePaths = {"profile"})
    @Query("""
    SELECT s
    FROM Student s
    LEFT JOIN FETCH s.profile
""")
    List<Student> findAllWithProfiles();
    Optional<Student> findWithProfileById(Long id);
    boolean existsByEmail(String email);
}

