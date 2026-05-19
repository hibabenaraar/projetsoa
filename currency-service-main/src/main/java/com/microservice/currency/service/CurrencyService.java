package com.microservice.currency.service;

import com.microservice.currency.dto.ConversionResponse;
import com.microservice.currency.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CurrencyService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyService.class);

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
        try {
            return fetchExchangeRate(currency);
        } catch (Exception e) {
            log.warn("External API unavailable, returning fallback rates for {}: {}", currency, e.getMessage());
            return buildFallbackRates(currency);
        }
    }

    private ExchangeRate buildFallbackRates(String currency) {
        Map<String, Double> fallbackRates = Map.ofEntries(
                Map.entry("USD", 1.0),
                Map.entry("EUR", 0.92),
                Map.entry("GBP", 0.79),
                Map.entry("JPY", 154.0),
                Map.entry("CAD", 1.36),
                Map.entry("CHF", 0.88),
                Map.entry("AUD", 1.53),
                Map.entry("CNY", 7.24),
                Map.entry("INR", 83.5),
                Map.entry("MAD", 10.0)
        );
        return new ExchangeRate(currency.toUpperCase(), fallbackRates, LocalDate.now().toString());
    }

    private ExchangeRate fetchExchangeRate(String currency) {
        return webClient.get()
                .uri(apiUrl + currency.toUpperCase())
                .retrieve()
                .bodyToMono(ExchangeRate.class)
                .block(Duration.ofMillis(timeout));
    }
}
