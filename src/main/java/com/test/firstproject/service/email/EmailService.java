package com.test.firstproject.service.email;

public interface EmailService {

    void sendEmail(String to,
                   String subject,
                   String body);

    void sendPasswordResetEmail(
            String to,
            String token
    );
}