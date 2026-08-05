package com.test.firstproject.repository;

import com.test.firstproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    // Inside UserRepository.java
    Page<User> findByUpdatedAtBefore(LocalDateTime time, Pageable pageable);
    boolean existsByUsername(String username);

}