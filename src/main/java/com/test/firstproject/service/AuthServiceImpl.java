package com.test.firstproject.service;

import com.test.firstproject.dto.request.LoginRequest;
import com.test.firstproject.dto.request.SignupRequest;
import com.test.firstproject.dto.response.LoginResponse;
import com.test.firstproject.entity.RefreshToken;
import com.test.firstproject.entity.Role;
import com.test.firstproject.entity.User;

import com.test.firstproject.exception.UsernameAlreadyExistsException;
import com.test.firstproject.exception.UsernameDoesNotExistException;
import com.test.firstproject.repository.UserRepository;
import com.test.firstproject.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void signup(SignupRequest request)
    {

        log.info("Creating new student with username: {}", request.username());
          if (userRepository.existsByUsername(request.username())){
              log.warn("Student already exists with username: {}", request.username());
              throw new UsernameAlreadyExistsException(request.username());
          }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);
        log.info("Student created successfully");
    }
    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() ->
                        new UsernameDoesNotExistException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new UsernameDoesNotExistException(
                    "Invalid username or password");
        }

        String accessToken   = jwtService.generateToken(
                user.getUsername());
        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        user
                );

        return new LoginResponse(accessToken,

                refreshToken.getToken());
    }
}
