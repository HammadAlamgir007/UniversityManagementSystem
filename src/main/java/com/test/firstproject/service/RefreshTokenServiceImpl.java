package com.test.firstproject.service;


import com.test.firstproject.entity.RefreshToken;
import com.test.firstproject.entity.User;
import com.test.firstproject.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl
        implements RefreshTokenService {


    private final RefreshTokenRepository repository;



    @Override
    public RefreshToken createRefreshToken(User user) {


        RefreshToken refreshToken =
                new RefreshToken();


        refreshToken.setUser(user);


        refreshToken.setToken(
                UUID.randomUUID().toString()
        );


        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusDays(7)
        );


        return repository.save(refreshToken);

    }



    @Override
    public RefreshToken verifyExpiration(
            RefreshToken token) {


        if(token.getExpiryDate()
                .isBefore(LocalDateTime.now())){


            repository.delete(token);


            throw new RuntimeException(
                    "Refresh token expired"
            );

        }


        return token;

    }
    @Override
    public RefreshToken findByToken(String token) {


        return repository.findByToken(token)

                .orElseThrow(
                        () -> new RuntimeException(
                                "Refresh token not found"
                        )
                );

    }

}