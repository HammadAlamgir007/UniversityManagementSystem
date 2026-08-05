package com.test.firstproject.repository;

import com.test.firstproject.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);

    void deleteByUserId(Long userId);
}