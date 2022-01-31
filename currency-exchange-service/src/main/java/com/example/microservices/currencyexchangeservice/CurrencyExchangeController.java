package com.example.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final Environment environment;
    private final CurrencyExchangeRepository repository;

    public CurrencyExchangeController(
            Environment environment,
            CurrencyExchangeRepository repository
    ) {
        this.environment = environment;
        this.repository = repository;
    }

    @GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to
    ) {
        logger.info("retrieveExchangeValue called with from {} to {}", from, to);

        CurrencyExchange currencyExchange =
                repository.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for conversion from " + from + " to " + to);
        }

        currencyExchange
                .setEnvironment(environment.getProperty("local.server.port"));

        String port = environment.getProperty("local.server.port");
        String host = environment.getProperty("HOSTNAME");
        String version = "v12";

        currencyExchange.setEnvironment(port + " " + version + " " + host);

        return currencyExchange;
    }
}
