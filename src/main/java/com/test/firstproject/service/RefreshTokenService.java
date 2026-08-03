package com.test.firstproject.service;


import com.test.firstproject.entity.RefreshToken;
import com.test.firstproject.entity.User;

import java.util.Optional;


public interface RefreshTokenService {


    RefreshToken createRefreshToken(User user);
    void deleteExpiredTokens();

    RefreshToken verifyExpiration(
            RefreshToken token
    );

    RefreshToken findByToken(String token);
}