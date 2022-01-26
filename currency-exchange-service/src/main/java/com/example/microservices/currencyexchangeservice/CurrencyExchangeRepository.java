package com.example.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository
        extends JpaRepository<CurrencyExchange, Integer> {
    CurrencyExchange findByFromAndTo(String from, String to);
}
