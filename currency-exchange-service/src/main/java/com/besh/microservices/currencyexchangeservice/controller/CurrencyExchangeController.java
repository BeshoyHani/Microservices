package com.besh.microservices.currencyexchangeservice.controller;

import com.besh.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.besh.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("currency-exchange/from/{from}/to/{to}")
public class CurrencyExchangeController {
    private Environment environment;
    private CurrencyExchangeRepository currencyExchangeRepository;

    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping
    public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to){
        String port = environment.getProperty("local.server.port");
        CurrencyExchange currencyExchange = this.currencyExchangeRepository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
