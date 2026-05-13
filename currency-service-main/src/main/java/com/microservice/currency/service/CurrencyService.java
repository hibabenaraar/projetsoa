package com.microservice.currency.service;

import com.microservice.currency.dto.ConversionResponse;
import com.microservice.currency.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;

@Service
public class CurrencyService {

    private final WebClient webClient;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.timeout}")
    private long timeout;

    public CurrencyService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ConversionResponse convert(String from, String to, Double amount) {
        ExchangeRate exchangeRate = fetchExchangeRate(from);

        Double rate = exchangeRate.getRates().get(to);
        Double convertedAmount = amount * rate;

        return new ConversionResponse(from, to, amount, convertedAmount, rate);
    }

    public ExchangeRate getExchangeRates(String currency) {
        return fetchExchangeRate(currency);
    }

    private ExchangeRate fetchExchangeRate(String currency) {
        return webClient.get()
                .uri(apiUrl + currency.toUpperCase())
                .retrieve()
                .bodyToMono(ExchangeRate.class)
                .block(Duration.ofMillis(timeout));
    }
}
