package com.soa.billing.client;

import com.soa.billing.dto.remote.ShippingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shipping-service", contextId = "shippingClient")
public interface ShippingFeignClient {

	@GetMapping("/shipping/order/{orderId}")
	ShippingDto getShippingByOrderId(@PathVariable("orderId") Long orderId);
}
