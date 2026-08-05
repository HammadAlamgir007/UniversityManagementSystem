package com.test.firstproject.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService
{

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(
            String to,
            String subject,
            String body)
    {
        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        log.info("Email sent successfully to {}", to);

    }
    @Override
    public void sendPasswordResetEmail(
            String to,
            String token) {

        String resetLink =
                "http://localhost:3000/reset-password?token=" + token;

        String body = """

                Hello,

                Click the link below to reset your password.

                %s

                This link expires in 30 minutes.

                If you didn't request this email,
                please ignore it.

                """.formatted(resetLink);

        sendEmail(
                to,
                "Reset Password",
                body
        );
    }
}
