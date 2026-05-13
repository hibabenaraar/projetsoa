package com.soa.billing.client;

import com.soa.billing.dto.remote.ConversionRequest;
import com.soa.billing.dto.remote.ConversionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "currency-service", contextId = "currencyClient")
public interface CurrencyFeignClient {

	@PostMapping("/api/currency/convert")
	ConversionResponse convert(@RequestBody ConversionRequest request);
}
