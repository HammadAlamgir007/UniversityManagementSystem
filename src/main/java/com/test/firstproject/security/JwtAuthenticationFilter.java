package com.test.firstproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService
            userDetailsService;

    // throw and throws?
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");
        log.info("Authorization Header: {}", authHeader);

        if (authHeader == null
                || !authHeader.startsWith("Bearer "))
            {
                log.info("No Bearer token found.");
                // throw exception? Unauthorized exception with clear message
            filterChain.doFilter(request, response);

            return;

        }

        String jwt =
                authHeader.substring(7);
        log.info("JWT: {}", jwt);

        String username = jwtService.extractUsername(jwt);
        log.info("Username extracted from token: {}", username);
        if (username != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("User loaded from database: {}", userDetails.getUsername());
            if (jwtService.isTokenValid(
                    jwt,
                    userDetails.getUsername())) {
                log.info("JWT is valid.");
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
                log.info("User authenticated successfully.");
            } else {
                log.warn("JWT validation failed.");
            }
            }



        filterChain.doFilter(request, response);

    }
}