package com.soa.billing.client;

import com.soa.billing.dto.remote.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order", contextId = "orderClient")
public interface OrderFeignClient {

	@GetMapping("/orders/{id}")
	OrderDto getOrderById(@PathVariable("id") Long id);
}
