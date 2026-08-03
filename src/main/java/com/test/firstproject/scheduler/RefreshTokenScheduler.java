package com.test.firstproject.scheduler;

import com.test.firstproject.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenScheduler {

    private final RefreshTokenService refreshTokenService;
//@Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupExpiredTokens() {

        log.info("Starting refresh token cleanup...");

        refreshTokenService.deleteExpiredTokens();

        log.info("Refresh token cleanup completed.");

    }

}