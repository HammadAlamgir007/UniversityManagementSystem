package com.test.firstproject.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MicrobankProperties.class)
public class WebClientConfig {
    @Bean
    public WebClient webClient(MicrobankProperties properties) {
        String baseUrl =
                properties.getFullBaseUrl();

        HttpClient httpClient = HttpClient.create()

                .responseTimeout(Duration.ofSeconds(30));

        return WebClient.builder()

                .baseUrl(baseUrl)

                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )

                .clientConnector(
                        new ReactorClientHttpConnector(httpClient)
                )

                .filter(logRequest())

                .filter(logResponse())

                .build();

    }

    private ExchangeFilterFunction logRequest() {

        return ExchangeFilterFunction.ofRequestProcessor(request -> {

            log.info("========== OUTGOING REQUEST ==========");

            log.info("Method : {}", request.method());

            log.info("URL    : {}", request.url());

            request.headers()

                    .forEach((name, values) ->
                            log.debug("{} : {}", name, values));

            log.info("======================================");

            return reactor.core.publisher.Mono.just(request);

        });

    }

    private ExchangeFilterFunction logResponse() {

        return ExchangeFilterFunction.ofResponseProcessor(response -> {

            log.info("========== API RESPONSE ==========");

            log.info("Status : {}", response.statusCode());

            log.info("==================================");

            return reactor.core.publisher.Mono.just(response);

        });

    }

}