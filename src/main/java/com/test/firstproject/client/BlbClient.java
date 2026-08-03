package com.test.firstproject.client;

import com.test.firstproject.config.MicrobankProperties;
import com.test.firstproject.dto.request.SearchCustomerRequest;
import com.test.firstproject.dto.response.SearchCustomerResponse;
import com.test.firstproject.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
@Slf4j
@Component
@RequiredArgsConstructor
public class BlbClient {

    private final WebClient webClient;
    private final MicrobankProperties properties;

    public SearchCustomerResponse searchCustomer(
            SearchCustomerRequest request) {

        log.info("Calling Microbank Search Customer API");

        try {
            return webClient.post()

                    .uri(properties.getSearchCustomerEndpoint())

                    .bodyValue(request)

                    .retrieve()

                    .onStatus(

                            HttpStatusCode::isError,

                            response -> response.bodyToMono(String.class)

                                    .flatMap(error ->

                                            Mono.error(

                                                    new ExternalApiException(

                                                            String.valueOf(response.statusCode().value()),

                                                            error

                                                    )

                                            )

                                    )

                    )

                    .bodyToMono(SearchCustomerResponse.class)

                    .timeout(Duration.ofSeconds(15))

                    .block();

        }

        catch (ExternalApiException ex) {

            throw ex;

        }

        catch (Exception ex) {

            throw new ExternalApiException(

                    "MICROBANK_CONNECTION",

                    "Unable to connect to Microbank."

            );
        }
    }
}
