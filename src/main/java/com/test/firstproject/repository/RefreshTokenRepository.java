package com.test.firstproject.repository;


import com.test.firstproject.entity.RefreshToken;
import com.test.firstproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {


    Optional<RefreshToken> findByToken(String token);
   Optional<RefreshToken> findByUser(User user);
    void deleteByExpiryDateBefore(
            LocalDateTime now
    );
    void deleteByUserId(Long userId);

}