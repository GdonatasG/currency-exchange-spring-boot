package com.example.microservices.currencyexchangeservice;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);


    @GetMapping(path = "/sample-api")
    //@Retry(name = "sample-api", fallbackMethod = "hardcodedFallBack")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedFallBack")
    public String sampleApi() {
        logger.info("Sample API call");
        ResponseEntity<String> forEntity =
                new RestTemplate().getForEntity("http://localhost:8080/not-available", String.class);

        return forEntity.getBody();
    }

    @GetMapping(path = "/sample-api-limited")
    //@Retry(name = "sample-api", fallbackMethod = "hardcodedFallBack")
    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedFallBack")
    @RateLimiter(name = "sample-api")
    public String sampleApiLimited() {
        logger.info("Sample API call");

        return "Sample API";
    }

    public String hardcodedFallBack(Exception ex) {
        return "fallback-value";
    }
}
