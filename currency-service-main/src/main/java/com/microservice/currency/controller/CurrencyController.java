package com.microservice.currency.controller;

import com.microservice.currency.dto.ConversionRequest;
import com.microservice.currency.dto.ConversionResponse;
import com.microservice.currency.model.ExchangeRate;
import com.microservice.currency.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/convert")
    public ConversionResponse convert(@RequestBody ConversionRequest request) {
        return currencyService.convert(request.getFrom(), request.getTo(), request.getAmount());
    }

    @GetMapping("/rates/{currency}")
    public ExchangeRate getRates(@PathVariable String currency) {
        return currencyService.getExchangeRates(currency);
    }
}
