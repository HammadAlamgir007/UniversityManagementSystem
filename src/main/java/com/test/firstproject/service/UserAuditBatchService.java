//package com.test.firstproject.service;
//import com.test.firstproject.entity.User;
//
//import com.test.firstproject.repository.UserRepository;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserAuditBatchService {
//
//    private final UserRepository userRepository;
//
//    public UserAuditBatchService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Scheduled(cron = "0 */5 * * * *")
//    public void processUsersInBatches() {
//        System.out.println("Starting daily user audit batch job...");
//
//        int batchSize = 100;
//
//
//        while (true) {
//            LocalDateTime halfHourAgo = LocalDateTime.now().minusMinutes(30);
//
//            Page<User> userPage = userRepository.findByUpdatedAtBefore(halfHourAgo, PageRequest.of(0, batchSize));
//            List<User> users = userPage.getContent();
//
//            if (users.isEmpty()) {
//                break;
//            }
//
//            processAndSaveChunk(users);
//
//        }
//    }
//
//    public void processAndSaveChunk(List<User> users) {
//
//
//            LocalDateTime halfHour = LocalDateTime.now().minusMinutes(30);
//            List<User> usersToDelete = new ArrayList<>();
//
//        for (User user : users) {
//            System.out.println("Auditing user: " + user.getUsername() + " | Role: " + user.getRole());
//            if (user.getUpdatedAt().isBefore(halfHour)) {
//                System.out.println("Deleting inactive user: " + user.getUsername());
//                usersToDelete.add(user);
//            }
//        }
//        if (!usersToDelete.isEmpty()) {
//            userRepository.deleteAll(usersToDelete);
//        }
//        System.out.println("Finished processing a batch of " + users.size() + " users.");
//    }
//}