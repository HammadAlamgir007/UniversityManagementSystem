//package com.test.firstproject.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
//@Configuration
//@EnableAsync
//public class AsyncConfig {
//
////    @Bean("blbExecutor")
////    public Executor blbExecutor() {
////
////        ThreadPoolTaskExecutor executor =
////                new ThreadPoolTaskExecutor();
////
////        executor.setCorePoolSize(5);
////
////        executor.setMaxPoolSize(10);
////
////        executor.setQueueCapacity(100);
////
////        executor.setThreadNamePrefix("BLB-");
////
////        executor.initialize();
////
////        return executor;
////    }
//    @Bean("Youtube")
//    public Executor youtubeExecutor() {
//
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(5);
//
//        executor.initialize();
//
//        return executor;
//    }
//
//}