//package com.test.firstproject.service.email;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@Profile("dev")
//public class DevEmailService implements EmailService {
//
//    @Override
//    public void sendEmail(
//            String to,
//            String subject,
//            String body
//    ) {
//
//        log.info("==================================");
//        log.info("DEV EMAIL");
//        log.info("To      : {}", to);
//        log.info("Subject : {}", subject);
//        log.info("Body    : {}", body);
//        log.info("==================================");
//
//    }
//
//}