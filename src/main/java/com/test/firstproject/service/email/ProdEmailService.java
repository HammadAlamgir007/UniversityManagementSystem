//package com.test.firstproject.service.email;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@Profile("prod")
//public class ProdEmailService implements EmailService {
//
//    @Override
//    public void sendEmail(
//            String to,
//            String subject,
//            String body
//    ) {
//
//        log.info("Sending REAL EMAIL");
//
//        // JavaMailSender code would go here
//
//    }
//
//}