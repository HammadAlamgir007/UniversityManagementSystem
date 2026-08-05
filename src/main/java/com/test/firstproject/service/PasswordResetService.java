package com.test.firstproject.service;

import com.test.firstproject.dto.request.ResetPasswordRequest;
import com.test.firstproject.entity.PasswordResetToken;
import com.test.firstproject.entity.User;
import com.test.firstproject.repository.PasswordResetTokenRepository;
import com.test.firstproject.repository.RefreshTokenRepository;
import com.test.firstproject.repository.UserRepository;
import com.test.firstproject.service.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j // <-- 1. Add Lombok logging annotation
@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailService emailService;

    public void forgotPassword(String email) {
        log.info("Initiating password reset process for email: {}", email);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            // Use warn or debug here. It's good security practice NOT to tell the user the email doesn't exist.
            log.warn("Password reset requested for non-existent email: {}", email);
            return;
        }

        log.debug("Deleting any existing reset tokens for user ID: {}", user.getId());
        tokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .used(false)
                .build();

        tokenRepository.save(resetToken);
        log.debug("Saved new password reset token to database for user ID: {}", user.getId());

        emailService.sendPasswordResetEmail(user.getEmail(), token);
        log.info("Password reset email sent successfully to: {}", email);
    }

    public void resetPassword(ResetPasswordRequest request) {
        log.info("Attempting to reset password using token");

        PasswordResetToken token = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> {
                    log.error("Password reset failed: Invalid Token provided");
                    return new RuntimeException("Invalid Token");
                });

        if (token.isUsed()) {
            log.error("Password reset failed: Token already used for user ID: {}", token.getUser().getId());
            throw new RuntimeException("Token already used");
        }

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.error("Password reset failed: Token expired for user ID: {}", token.getUser().getId());
            throw new RuntimeException("Token expired");
        }

        User user = token.getUser();
        log.debug("Valid token found. Updating password for user ID: {}", user.getId());

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.debug("Invalidating existing refresh tokens for user ID: {}", user.getId());
        refreshTokenRepository.deleteByUserId(user.getId());

        token.setUsed(true);
        tokenRepository.save(token);

        log.info("Password successfully reset for user ID: {}", user.getId());
    }
}