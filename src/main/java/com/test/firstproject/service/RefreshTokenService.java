package com.test.firstproject.service;


import com.test.firstproject.entity.RefreshToken;
import com.test.firstproject.entity.User;


public interface RefreshTokenService {


    RefreshToken createRefreshToken(User user);


    RefreshToken verifyExpiration(
            RefreshToken token
    );

    RefreshToken findByToken(String token);
}